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
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.EditDataActivity;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.AdhaarCardSelfieModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PancardFragment extends Fragment implements View.OnClickListener {

    Button uploadSelfieButton;
    Button nextButton;
    ImageButton backButton;
    ProgressBar progressbar;
    ImageView imageSelfie;


    private String currentPhotoPath;
    ViewGroup viewGroup;
    boolean next = false;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String tokenLogin;
    Uri imageURi;

    String responceImageURL;
    String panUrl;
    int selectedResponse = 0;
    //0=camera ;1=gallery; 2=pdf; 3=cancel

    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        tokenLogin = sharedPreferences.getString(SharedPref.TOKEN, "@null");
        panUrl = sharedPreferences.getString(SharedPref.PANCARD_URL, "@null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_pancard_upload, container, false);
        init(viewGroup);


//        if (panUrl!=null){
//            if (getContext() instanceof EditDataActivity) {
////            new RetreiveImageTask((ImageView) getActivity().findViewById(R.id.imageBackAdhaar))
////                    .execute(adhaarBackImageUrl);
//                CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
//                circularProgressDrawable.setStrokeWidth(5f);
//                circularProgressDrawable.setCenterRadius(30f);
//                circularProgressDrawable.start();
//                Glide.with(this).load(panUrl).placeholder(circularProgressDrawable).into(imageSelfie);
//            }
//        }

        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        uploadSelfieButton = viewGroup.findViewById(R.id.uploadSelfieButton);
        nextButton = viewGroup.findViewById(R.id.nextButton);
        backButton = viewGroup.findViewById(R.id.backButton);
        progressbar = viewGroup.findViewById(R.id.progressbar);
        imageSelfie = viewGroup.findViewById(R.id.imageFrontAdhaar);
        progressbar.setVisibility(View.GONE);
        uploadSelfieButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.uploadSelfieButton) {
            uploadSelfieButton.setEnabled(false);
            checkAndroidVersion();

        }
        if (view.getId() == R.id.backButton) {
            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
            getActivity().finish();
        }
        if (view.getId() == R.id.nextButton) {
            progressbar.setVisibility(View.GONE);
            if (getContext() instanceof EditDataActivity) {
                startActivity(new Intent(getActivity(),HomeScreenActivity.class));
                getActivity().finish();            } else
                ((RegisterSlideActivity) getActivity()).nextFragment(6);//change position

        }
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


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

                        try {

                            Uri uri = data.getData();

//                            InputStream is = getActivity().getContentResolver().openInputStream(data.getData());
                            progressbar.setVisibility(View.VISIBLE);
                            if (selectedResponse == 2) {
                                uploadPdfApiHit(new File(uri.getPath()));
                            } else
                                uploadImageApiHit(getBytes(uri));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }
        }
    }

    private void uploadPdfApiHit(File file) {
        if (NetworkInfo.hasConnection(requireActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("pan_card_image", "pan_card_image.jpeg", requestFile);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), tokenLogin);

            Call<AdhaarCardSelfieModel> call = apiService.uploadPancardimage(token, body);
            call.enqueue(new Callback<AdhaarCardSelfieModel>() {
                @Override
                public void onResponse(Call<AdhaarCardSelfieModel> call, Response<AdhaarCardSelfieModel> response) {


                    if (response.isSuccessful() && response.body().getStatus() == 200) {
//
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        responceImageURL = response.body().getImgURL();
                        editor = sharedPreferences.edit();
                        editor.putString(SharedPref.ADHAR_FRONT_URL, responceImageURL);
                        editor.apply();
                        if (selectedResponse != 2)
                            setImageView();

                    } else {
                        uploadSelfieButton.setEnabled(true);
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
                public void onFailure(Call<AdhaarCardSelfieModel> call, Throwable t) {
                    uploadSelfieButton.setEnabled(true);
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

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
        if (NetworkInfo.hasConnection(requireActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("pan_card_image", "pan_card_image.jpeg", requestFile);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), tokenLogin);

            Call<AdhaarCardSelfieModel> call = apiService.uploadPancardimage(token, body);
            call.enqueue(new Callback<AdhaarCardSelfieModel>() {
                @Override
                public void onResponse(Call<AdhaarCardSelfieModel> call, Response<AdhaarCardSelfieModel> response) {


                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        responceImageURL = response.body().getImgURL();


                        if (selectedResponse != 2)
                            setImageView();
                    } else {
                        uploadSelfieButton.setEnabled(true);
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
                public void onFailure(Call<AdhaarCardSelfieModel> call, Throwable t) {
                    uploadSelfieButton.setEnabled(true);
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void setImageView() {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(this).load(responceImageURL).placeholder(circularProgressDrawable).into(imageSelfie);
        progressbar.setVisibility(View.GONE);
        next = true;
        viewNextButton();
    }


    private void viewNextButton() {
        if (next) {
            uploadSelfieButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);

        }
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

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
        builder.setTitle("Choose your picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    uploadSelfieButton.setEnabled(false);
                    selectedResponse = 0;
                    String fileName = "photo";
                    File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    try {
                        File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                        currentPhotoPath = imageFile.getAbsolutePath();
                        imageURi = FileProvider.getUriForFile(getActivity(), "www.atmanirbharbharat.com.fileprovider", imageFile);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURi);
                        startActivityForResult(intent, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    selectedResponse = 1;
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);

                } else if (options[item].equals("Select Pdf")) {
                    selectedResponse = 2;
                    Intent selectPdf = new Intent();
                    selectPdf.setAction(Intent.ACTION_GET_CONTENT);
                    selectPdf.setType("application/pdf");
                    startActivityForResult(selectPdf, 1);

                } else if (options[item].equals("Cancel")) {
                    selectedResponse = 3;
                    uploadSelfieButton.setEnabled(true);
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void OpenPicker() {
     //   ContentUriUtils.INSTANCE.getFilePath(requireActivity(), uri);
     //0=camera ;1=gallery; 2=pdf; 3=cancel

    }
}