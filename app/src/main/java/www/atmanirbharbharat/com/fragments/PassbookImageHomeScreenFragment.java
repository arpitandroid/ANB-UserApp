package www.atmanirbharbharat.com.fragments;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.CheckPassbookImageModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class PassbookImageHomeScreenFragment extends Fragment implements View.OnClickListener {

    Button openCameraButton;
    Button nextButton;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_passbook_image_home_screen, container, false);
        init(viewGroup);
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        openCameraButton = viewGroup.findViewById(R.id.uploadAdhaarFront);
        nextButton = viewGroup.findViewById(R.id.nextButton);
        progressbar = viewGroup.findViewById(R.id.progressbar);
        progressbar.setVisibility(View.GONE);
        openCameraButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.uploadAdhaarFront) {
            checkAndroidVersion();
        }
        if (view.getId() == R.id.nextButton) {
            progressbar.setVisibility(View.GONE);
            ((EditDataActivity) getActivity()).openEditBankDetailsFragment();

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
        getData();
        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("application/pdf"), file);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("check_image", "check_image.jpeg", requestFile);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), tokenLogin);

            Call<CheckPassbookImageModel> call = apiService.uploadPassbookOrCheckImageApi(token, body);
            call.enqueue(new Callback<CheckPassbookImageModel>() {
                @Override
                public void onResponse(Call<CheckPassbookImageModel> call, Response<CheckPassbookImageModel> response) {


                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        responceImageURL = response.body().getImgURL();
                        editor = sharedPreferences.edit();
                        editor.putString(SharedPref.ADHAR_FRONT_URL, responceImageURL);
                        editor.apply();
                        if (selectedResponse != 2)
                            setImageView();

                    } else {
                        openCameraButton.setEnabled(true);
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
                public void onFailure(Call<CheckPassbookImageModel> call, Throwable t) {
                    openCameraButton.setEnabled(true);
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
        getData();
        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("check_image", "check_image.jpeg", requestFile);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), tokenLogin);

            Call<CheckPassbookImageModel> call = apiService.uploadPassbookOrCheckImageApi(token, body);
            call.enqueue(new Callback<CheckPassbookImageModel>() {
                @Override
                public void onResponse(Call<CheckPassbookImageModel> call, Response<CheckPassbookImageModel> response) {


                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        responceImageURL = response.body().getImgURL();

                        if (selectedResponse != 2)
                            setImageView();
                    } else {
                        openCameraButton.setEnabled(true);
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
                public void onFailure(Call<CheckPassbookImageModel> call, Throwable t) {
                    openCameraButton.setEnabled(true);
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void setImageView() {
        new RetreiveImageTask((ImageView) getActivity().findViewById(R.id.imagePassbook))
                .execute(responceImageURL);

    }


    private void viewNextButton() {
        if (next == true) {
            openCameraButton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);

        }
    }

    public void getData() {
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        tokenLogin = sharedPreferences.getString(SharedPref.TOKEN, "@null");
    }


    ///--------------------------to set image after the image is loaded--------------------
    private class RetreiveImageTask extends AsyncTask<String, Void, Bitmap> {


        ImageView bmImage;

        public RetreiveImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            progressbar.setVisibility(View.GONE);

            next = true;
            viewNextButton();
        }

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
                    openCameraButton.setEnabled(false);
                    selectedResponse = 0;
                    String fileName = "photo";
                    File storageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    try {
                        File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                        currentPhotoPath = imageFile.getAbsolutePath();
                        imageURi = FileProvider.getUriForFile(getActivity(), "www.easyloanmantra.com.fileprovider", imageFile);
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
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

}