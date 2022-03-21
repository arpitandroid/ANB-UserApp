package www.atmanirbharbharat.com.homeScreen.fragments.homeFragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.PaySlipImageModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static www.atmanirbharbharat.com.util.ApiClient.BASE_URL;


public class PaySlipImageHomeScreenFragment extends Fragment implements View.OnClickListener {

    Button uploadPaySlip;
    Button nextButton;
    ImageView imagepayslip;

    ProgressBar progressbar;

    private String currentPhotoPath;
    ViewGroup viewGroup;
    boolean next = false;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String tokenLogin;
    Uri imageURi;

    String responceImageURL;
    int selectedResponse = 0;


    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    private static final int BUFFER_SIZE = 1024 * 2;
    private static final String IMAGE_DIRECTORY = "/demonuts_upload_gallery";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity()
                .getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        tokenLogin = sharedPreferences.getString(SharedPref.TOKEN, "@null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater
                .inflate(R.layout.fragment_pay_slip_image_home_screen, container, false);
        init(viewGroup);
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        uploadPaySlip = viewGroup.findViewById(R.id.uploadPaySlipButton);
        nextButton = viewGroup.findViewById(R.id.nextButton);
        imagepayslip = viewGroup.findViewById(R.id.imagepayslip);
        progressbar = viewGroup.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);
        uploadPaySlip.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.uploadPaySlipButton) {
            checkAndroidVersion();
        }
        if (view.getId() == R.id.nextButton) {
            progressbar.setVisibility(View.GONE);
            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        try {
                            progressbar.setVisibility(View.VISIBLE);
                            uploadImageApiHit(getBytes(imageURi));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK) {


                        Uri uri = data.getData();
                        String uriString = uri.toString();
                        File myFile = new File(uriString);

                        String path = getFilePathFromURI(getActivity(), uri);
                        progressbar.setVisibility(View.VISIBLE);
                        try {
                            uploadImageApiHit(getBytes(uri));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                    break;
                case 2:
                    if (resultCode == RESULT_OK) {


                        Uri uri = data.getData();
                        String uriString = uri.toString();

                        File myFile = new File(uriString);
                        //                    InputStream is = getContentResolver().openInputStream(data.getData());

                        String path = getFilePathFromURI(getActivity(), uri);
                        progressbar.setVisibility(View.VISIBLE);
                        uploadImage(path);


                    }
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    public static String getFilePathFromURI(Context context, Uri contentUri) {

        //copy file and send new file path
        String fileName = getFileName(contentUri);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }
        if (!TextUtils.isEmpty(fileName)) {
            File copyFile = new File(wallpaperDirectory + File.separator + fileName);
            // create folder if not exists

            copy(context, contentUri, copyFile);
            return copyFile.getAbsolutePath();
        }
        return null;
    }

    public static String getFileName(Uri uri) {
        if (uri == null) return null;
        String fileName = null;
        String path = uri.getPath();
        int cut = path.lastIndexOf('/');
        if (cut != -1) {
            fileName = path.substring(cut + 1);
        }
        return fileName;
    }

    public static void copy(Context context, Uri srcUri, File dstFile) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
            if (inputStream == null) return;
            OutputStream outputStream = new FileOutputStream(dstFile);
            copystream(inputStream, outputStream);
            inputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int copystream(InputStream input, OutputStream output) throws Exception, IOException {
        byte[] buffer = new byte[BUFFER_SIZE];

        BufferedInputStream in = new BufferedInputStream(input, BUFFER_SIZE);
        BufferedOutputStream out = new BufferedOutputStream(output, BUFFER_SIZE);
        int count = 0, n = 0;
        try {
            while ((n = in.read(buffer, 0, BUFFER_SIZE)) != -1) {
                out.write(buffer, 0, n);
                count += n;
            }
            out.flush();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
            try {
                in.close();
            } catch (IOException e) {
                Log.e(e.getMessage(), String.valueOf(e));
            }
        }
        return count;
    }

    private void uploadImage(String path) {

        if (NetworkInfo.hasConnection(getActivity())) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            File file = new File(path);


            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("passbook_image", "passbook_image.pdf", requestBody);
            RequestBody token = RequestBody.create(MediaType.parse("text/plain"), tokenLogin);
            ApiInterface retrofitInterface = retrofit.create(ApiInterface.class);

            Call<PaySlipImageModel> call = retrofitInterface.uploadPaySlipImageApi(token, body);
            call.enqueue(new Callback<PaySlipImageModel>() {
                @Override
                public void onResponse(Call<PaySlipImageModel> call, Response<PaySlipImageModel> response) {


                    if (response.isSuccessful() && response.body().getStatus() == 200) {

                        PaySlipImageModel responseBody = response.body();
                        progressbar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                        getActivity().finish();

                    } else {
                        progressbar.setVisibility(View.GONE);


                        ResponseBody errorBody = response.errorBody();

                        Gson gson = new Gson();

                        try {

                            Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                            Toast.makeText(getActivity(), errorResponse.message(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PaySlipImageModel> call, Throwable t) {

                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        }
    }

    public byte[] getBytes(Uri uri) throws IOException {
        InputStream is = getContext().getContentResolver().openInputStream(uri);

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        return baos.toByteArray();
    }


    private void uploadImageApiHit(byte[] imageBytes) {
        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(BASE_URL).create(ApiInterface.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
            final MultipartBody.Part body = MultipartBody
                    .Part
                    .createFormData("passbook_image", "passbook_image.jpeg", requestFile);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), tokenLogin);

            Call<PaySlipImageModel> call = apiService.uploadPaySlipImageApi(token, body);
            call.enqueue(new Callback<PaySlipImageModel>() {
                @Override
                public void onResponse(Call<PaySlipImageModel> call, Response<PaySlipImageModel> response) {


                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        responceImageURL = response.body().getImgURL();

                        if (selectedResponse != 2)
                            setImageView();
                    } else {
                        uploadPaySlip.setEnabled(true);
                        progressbar.setVisibility(View.GONE);

                        ResponseBody errorBody = response.errorBody();

                        Gson gson = new Gson();

                        try {

                            Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                            Toast.makeText(getActivity(), errorResponse.message(), Toast.LENGTH_SHORT)
                                    .show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<PaySlipImageModel> call, Throwable t) {
                    uploadPaySlip.setEnabled(true);
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT)
                            .show();

                }
            });

        }
    }

    private void setImageView() {

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(this).load(responceImageURL).placeholder(circularProgressDrawable).into(imagepayslip);

        progressbar.setVisibility(View.GONE);
        viewNextButton();


    }


    private void viewNextButton() {

        uploadPaySlip.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);


    }


    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();

        } else {
        }

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (getActivity(), Manifest.permission.CAMERA)) {

                Snackbar.make(getActivity().findViewById(android.R.id.content),
                        "Please Grant Permissions to upload profile photo",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                requestPermissions(
                                        new String[]{Manifest.permission
                                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                        PERMISSIONS_MULTIPLE_REQUEST);
                            }
                        }).show();
            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        } else {
            selectImage(getContext());

        }
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Select Pdf", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    selectedResponse = 0;
                    String fileName = "photo";
                    File storageDirectory = getActivity()
                            .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    try {
                        File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                        currentPhotoPath = imageFile.getAbsolutePath();
                        imageURi = FileProvider
                                .getUriForFile(getActivity(), "www.atmanirbharbharat.com.fileprovider", imageFile);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURi);
                        startActivityForResult(intent, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Select Pdf")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("application/pdf");
                    startActivityForResult(intent, 2);

                } else if (options[item].equals("Cancel")) {
                    selectedResponse = 3;
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}