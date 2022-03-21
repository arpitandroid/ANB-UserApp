package www.atmanirbharbharat.com.fragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.EditDataActivity;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.PersonalInformationModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;

public class RegistrationScreenSlidePageFragment9 extends Fragment implements View.OnClickListener {

    ViewGroup viewGroup;
    Button nextButton;

    EditText phoneNumberEditText;
    EditText relationEditText;
    EditText phoneNumber2EditText;
    EditText relation2EditText;

    TextView termsAndConditionTextView;

    String phoneNumber;
    String relation;
    String phoneNumber2;
    String relation2;
    String token;
    String emergencyContactStatus = "PENDING";
//    String profileCompleteStatus = "COMPLETED";

    private static final String TOKEN = "token";
    private static final String PHONE1 = "ec1";
    private static final String RELATION1 = "ec2";
    private static final String PHONE2 = "mobile";
    private static final String RELATION2 = "ecr2";
    private static final String EMERGENCY_CONTACT_STATUS = "ecv_status";
//    private static final String PROFILE_COMPLETION_STATUS = "p_c_status";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private HashMap<String, String> mParam;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_registration_screen_slide9, container, false);
        init(viewGroup);
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        nextButton = viewGroup.findViewById(R.id.nextButton);
        phoneNumberEditText = viewGroup.findViewById(R.id.phoneNumberEditText);
        relationEditText = viewGroup.findViewById(R.id.relationEditText);
        phoneNumber2EditText = viewGroup.findViewById(R.id.phoneNumber2EditText);
        relation2EditText = viewGroup.findViewById(R.id.relation2EditText);
        termsAndConditionTextView = viewGroup.findViewById(R.id.termsAndConditionTextView);

        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        nextButton.setOnClickListener(this);
        termsAndConditionTextView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.nextButton) {
            nextButton.setEnabled(false);
            validation();

        }
        if (view.getId() == R.id.termsAndConditionTextView) {
            String url = "https://easyloanmantra.in/terms-and-conditions";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setPackage("com.android.chrome");
            try {
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // Chrome is probably not installed
                // Try with the default browser
                i.setPackage(null);
                startActivity(i);
            }
        }
    }

    private void getData() {
        phoneNumber = phoneNumberEditText.getText().toString();
        relation = relationEditText.getText().toString();
        phoneNumber2 = phoneNumber2EditText.getText().toString();
        relation2 = relation2EditText.getText().toString();

        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, "@null");
    }

    private void initParam() {
        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (phoneNumber.length() > 0) {
            mParam.put(PHONE1, phoneNumber);
        }
        if (phoneNumber2.length() > 0) {
            mParam.put(PHONE2, phoneNumber2);
        }
        if (relation.length() > 0) {
            mParam.put(RELATION1, relation);
        }
        if (relation2.length() > 0) {
            mParam.put(RELATION2, relation2);
        }
        if (token.length() > 0) {
            mParam.put(TOKEN, token);
        }
        if (emergencyContactStatus.length() > 0) {
            mParam.put(EMERGENCY_CONTACT_STATUS, emergencyContactStatus);
        }
//        if (profileCompleteStatus.length() > 0) {
//            mParam.put(PROFILE_COMPLETION_STATUS, profileCompleteStatus);
//        }

    }

    public void validation() {

        getData();
        if (phoneNumber.length() < 10) {
            Toast.makeText(getActivity(), "phone Number cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (phoneNumber2.length() < 10) {
            Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (relation.length() <= 0) {
            Toast.makeText(getActivity(), "First Name cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (relation2.length() <= 0) {
            Toast.makeText(getActivity(), "Last Name cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else {
            personalInformationUpdateApiHit();
        }
    }

    private void personalInformationUpdateApiHit() {
        initParam();


        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<PersonalInformationModel> call = apiService.personalInformationUpdateApi(mParam);
            call.enqueue(new Callback<PersonalInformationModel>() {
                @Override
                public void onResponse(Call<PersonalInformationModel> call, Response<PersonalInformationModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        editor.putBoolean(SharedPref.PROFILE_COMPLETION_CHECK, true);
                        editor.putString(SharedPref.NAME,response.body().getData().getFirstName()+" "+response.body().getData().getLastName());
                        editor.apply();

                        if (getContext() instanceof EditDataActivity) {
                            startActivity(new Intent(getActivity(),HomeScreenActivity.class));
                            getActivity().finish();
                        } else
                            ((RegisterSlideActivity) getActivity()).nextFragment(6);


                    } else {
                        nextButton.setEnabled(true);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PersonalInformationModel> call, Throwable t) {
                    nextButton.setEnabled(true);
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            nextButton.setEnabled(true);

            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

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