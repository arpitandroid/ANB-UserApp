package www.atmanirbharbharat.com;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
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
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.AdhaarCardFrontModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

public class Activity_UpdateProfile extends AppCompatActivity implements View.OnClickListener {

    Button uploadAdhaarFront;
    Button nextButton;
    ImageView imageFrontAdhaar;

    ProgressBar progressbar;

    private String currentPhotoPath;
    private String profileImageUrl;
    //ViewGroup viewGroup;
    boolean next = false;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String tokenLogin;
    Uri imageURi;

    String responceImageURL;
    int selectedResponse = 0;

    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_aadhar_front_scan);
        init();
        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        tokenLogin = sharedPreferences.getString(SharedPref.TOKEN, "@null");
        profileImageUrl = sharedPreferences.getString(SharedPref.PROFILEIMAGE_URL, "@null");
        Log.i("arp","profUrl=> "+profileImageUrl);
        Glide.with(Activity_UpdateProfile.this).load(profileImageUrl).placeholder(R.drawable.elmtextlogoimg).into(imageFrontAdhaar);
    }

  /*  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_aadhar_front_scan, container, false);
        init(viewGroup);
        if (getContext() instanceof EditDataActivity) {
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getContext());
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(this).load(adhaarFrontImageUrl).placeholder(circularProgressDrawable).into(imageFrontAdhaar);

        }
        return viewGroup;
    }*/


    private void init() {
        uploadAdhaarFront = findViewById(R.id.uploadAdhaarFront);
        uploadAdhaarFront.setText("Upload Profile");
        nextButton = findViewById(R.id.nextButton);
       TextView tvtitle = findViewById(R.id.textView);
       TextView tv1 = findViewById(R.id.tv1);
       TextView tv2 = findViewById(R.id.tv2);
        tv1.setVisibility(View.GONE);
        tv2.setVisibility(View.GONE);
       tvtitle.setText("Update Profile Image");
        progressbar = findViewById(R.id.progressbar);
        imageFrontAdhaar = findViewById(R.id.imageFrontAdhaar);
        Glide.with(Activity_UpdateProfile.this).load(profileImageUrl).placeholder(R.drawable.elmtextlogoimg).into(imageFrontAdhaar);
        progressbar.setVisibility(View.GONE);
        uploadAdhaarFront.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.uploadAdhaarFront) {
            checkAndroidVersion();
        }
        if (view.getId() == R.id.nextButton) {
            progressbar.setVisibility(View.GONE);
            onBackPressed();

         /*   if (getContext() instanceof EditDataActivity) {
                ((EditDataActivity) getActivity()).openAdharBackSide();

            } else {
                editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 4);
                editor.apply();
                ((RegisterSlideActivity) getActivity()).nextFragment(4);
            }*/
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

                      uploadImageApiHit(getBytes(uri));


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }
        }
    }

    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_UpdateProfile.this);
        builder.setMessage("You need to make sure your device is conected to Internet")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();

                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    public byte[] getBytes(Uri uri) throws IOException {
        InputStream is = getContentResolver().openInputStream(uri);

        Bitmap bitmap = BitmapFactory.decodeStream(is);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
        return baos.toByteArray();
    }


    private void uploadImageApiHit(byte[] imageBytes) {
        if (NetworkInfo.hasConnection(Activity_UpdateProfile.this)) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
            final MultipartBody.Part body = MultipartBody.Part.createFormData("profile_image", "profile_image.jpeg", requestFile);
            RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), tokenLogin);

            Call<AdhaarCardFrontModel> call = apiService.uploadProfileImage(token, body);
            call.enqueue(new Callback<AdhaarCardFrontModel>() {
                @Override
                public void onResponse(Call<AdhaarCardFrontModel> call, Response<AdhaarCardFrontModel> response) {


                    if (response.isSuccessful() && response.body().getStatus() == 200) {
                        Log.i("arp","res: profupload=> "+ new Gson().toJson(response.body()));
//
                        Toast.makeText(Activity_UpdateProfile.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        responceImageURL = response.body().getImgURL();
                        editor = sharedPreferences.edit();
                        editor.putString(SharedPref.PROFILEIMAGE_URL, responceImageURL);
                        editor.apply();
                        if (selectedResponse != 2)
                            setImageView();

                    } else {
                        uploadAdhaarFront.setEnabled(true);
                        progressbar.setVisibility(View.GONE);

                        ResponseBody errorBody = response.errorBody();

                        Gson gson = new Gson();

                        try {

                            Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                            Toast.makeText(Activity_UpdateProfile.this, errorResponse.message(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AdhaarCardFrontModel> call, Throwable t) {
                    uploadAdhaarFront.setEnabled(true);
                    progressbar.setVisibility(View.GONE);

                    Toast.makeText(Activity_UpdateProfile.this, "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

    private void setImageView() {
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(Activity_UpdateProfile.this);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();

        Glide.with(this).load(responceImageURL).placeholder(circularProgressDrawable).into(imageFrontAdhaar);
        progressbar.setVisibility(View.GONE);
        next = true;
        viewNextButton();

    }


    private void viewNextButton() {
            uploadAdhaarFront.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            checkPermission();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(Activity_UpdateProfile.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(Activity_UpdateProfile.this,
                        Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (Activity_UpdateProfile.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (Activity_UpdateProfile.this, Manifest.permission.CAMERA)) {

                Snackbar.make(findViewById(android.R.id.content),
                        "Please Grant Permissions to upload profile photo",
                        Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                            new String[]{Manifest.permission
                                                    .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                            PERMISSIONS_MULTIPLE_REQUEST);
                                }
                            }
                        }).show();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(
                            new String[]{Manifest.permission
                                    .READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                            PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else {
            selectImage(Activity_UpdateProfile.this);
        }
    }


    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    selectedResponse = 0;
                    String fileName = "photo";
                    File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                    try {
                        File imageFile = File.createTempFile(fileName, ".jpg", storageDirectory);
                        currentPhotoPath = imageFile.getAbsolutePath();
                        imageURi = FileProvider.getUriForFile(Activity_UpdateProfile.this, "www.atmanirbharbharat.com.fileprovider", imageFile);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURi);
                        startActivityForResult(intent, 0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    selectedResponse = 1;
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}