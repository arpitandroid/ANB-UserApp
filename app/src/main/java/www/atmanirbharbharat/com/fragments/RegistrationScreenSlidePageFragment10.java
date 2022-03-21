package www.atmanirbharbharat.com.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.models.ContactModel;
import www.atmanirbharbharat.com.models.SaveContactModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

public class RegistrationScreenSlidePageFragment10 extends Fragment implements View.OnClickListener {

    ViewGroup viewGroup;
    Button nextButton;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 111;

    private ProgressDialog mProgressDialog;
    List<ContactModel> contactList;

    private Handler handler;


    String token;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_registration_screen_slide10, container, false);
        init(viewGroup);
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        token = sharedPreferences.getString(SharedPref.TOKEN, "");
        nextButton = viewGroup.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(this);

//        mProgressDialog = new ProgressDialog(getActivity());
//        mProgressDialog.setTitle("Account Setup ...");
//        mProgressDialog.setMessage("Please wait while we setup your Easy Loan Mantra Account");
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.show();


//        handler = new Handler();

//        getcontactruntime();



    }

    private void getcontactruntime() {
        handler .postDelayed(new Runnable() {
            @Override
            public void run() {

                checkPermission();
            }
        }, 10);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.nextButton) {

//            mProgressDialog = new ProgressDialog(getActivity());
//            mProgressDialog.setTitle("Final Setup ...");
//            mProgressDialog.setMessage("Please wait while we do the Final check");
//            mProgressDialog.setCanceledOnTouchOutside(false);
//            mProgressDialog.show();

            //convert contact list to JSON string
//            Gson gson = new Gson();
//            String json = gson.toJson(contactList);
//            //send contacts through API
//             contactSendApiList(json);



            editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 100);
            editor.apply();

            Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    private void checkAndroidVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            contactList = getContacts(getContext());
            //convert contact list to JSON string
            Gson gson = new Gson();
            String json = gson.toJson(contactList);

            mProgressDialog.dismiss();
            if(contactList.isEmpty()){
                Toast.makeText(getActivity(), "Something went wrong, Open App again", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }else{
                nextButton.setVisibility(View.VISIBLE);
            }


            //send contacts through API
           // contactSendApiList(json);

        } else {
            mProgressDialog.dismiss();
            Toast.makeText(getActivity(), "your phone does not support Easy Loan Mantra please upgrade to android version - M", Toast.LENGTH_SHORT).show();
        }

    }



    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS) + ContextCompat
                .checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.READ_CONTACTS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale
                            (getActivity(), Manifest.permission.WRITE_CONTACTS)) {

                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                        PERMISSIONS_MULTIPLE_REQUEST);

            } else {
                requestPermissions(
                        new String[]{Manifest.permission
                                .READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                        PERMISSIONS_MULTIPLE_REQUEST);
            }
        }
        else{
            checkAndroidVersion();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "Permission denied ! Please accept it", Toast.LENGTH_SHORT).show();
                    System.exit(0);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    //function to get contact details
    public List<ContactModel> getContacts(Context ctx) {
        List<ContactModel> list = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(ctx.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {
                        ContactModel info = new ContactModel();
                        info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.mobileNumber = cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        info.photo = photo;
                        info.photoURI = pURI;
                        list.add(info);
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        return list;
    }

    private void contactSendApiList(String contacts) {
        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<SaveContactModel> call = apiService.postContactsList(token, contacts);
            call.enqueue(new Callback<SaveContactModel>() {
                @Override
                public void onResponse(Call<SaveContactModel> call, Response<SaveContactModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200) {

                        editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 100);
                        editor.apply();

                        Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                        startActivity(intent);
                        getActivity().finish();


                    } else {
                        Toast.makeText(getActivity(), "Something went wrong ! please try again", Toast.LENGTH_SHORT).show();

                    }

                    mProgressDialog.dismiss();
                }



                @Override
                public void onFailure(Call<SaveContactModel> call, Throwable t) {
             //       Toast.makeText(getActivity(), "Error ! please try again", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), ""+t, Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();

//                    Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
//                    startActivity(intent);
//                    getActivity().finish();

                }
            });
        } else {

            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            mProgressDialog.dismiss();

        }
    }

    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You need to make sure your device is conected to Internet")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().onBackPressed();

                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }



}