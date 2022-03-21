package www.atmanirbharbharat.com.fragments;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.OtpLoginRegistratinModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;

public class PhoneNumberEnterFragment extends Fragment implements View.OnClickListener {


    Button nextButton;
    TextView termsAndConditionTextView;

    String mobileNumber;
    EditText phoneEditText;

    private HashMap<String, String> mParam;
    private static final String MOBILE = "mobile";

    private SharedPreferences sharedPreferences;
    private AppCompatCheckBox termsAndConditionCheckbox;
    private static final int RC_HINT = 1000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_phone_number_enter, container, false);
        init(viewGroup);
        return viewGroup;
    }

    //initialization is done here
    private void init(ViewGroup viewGroup) {

        nextButton = viewGroup.findViewById(R.id.nextButton);
        phoneEditText = viewGroup.findViewById(R.id.phoneEditText);
        termsAndConditionCheckbox = viewGroup.findViewById(R.id.termsAndConditionCheckbox);
        termsAndConditionTextView = viewGroup.findViewById(R.id.termsAndConditionTextView);
        nextButton.setOnClickListener(this);
        termsAndConditionTextView.setOnClickListener(this);
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==10){
                    closeKeyboard();
                }
            }
        });

    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextButton) {
            if (termsAndConditionCheckbox.isChecked()){
                nextButton.setEnabled(false);
                validation();
            }else {
                Toast.makeText(getActivity(), "Accept the terms and condition", Toast.LENGTH_SHORT).show();
            }

        } if (view.getId() == R.id.termsAndConditionTextView) {
            String url = ApiClient.BASE_URL+"terms-and-conditions";
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


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_HINT) {
//            if (resultCode == RESULT_OK) {
//                Credential cred = data.getParcelableExtra(Credential.EXTRA_KEY);
//                phoneEditText.setText(cred.getId().substring(3));
//            }
//        }
//    }




    //    -----------------------getting data into variables for api hit-----------------
    private void getData() {
        mobileNumber = phoneEditText.getText().toString();
    }


    public void validation() {
        getData();
        if (mobileNumber.length() <= 0) {
            Toast.makeText(getActivity(), "phone Number cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);

        } else {
            otpLoginRegistrationApiHit();
        }
    }


    //Api hit is done here
    private void otpLoginRegistrationApiHit() {
        initParam();

        if (NetworkInfo.hasConnection(requireActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<OtpLoginRegistratinModel> call = apiService.otpLoginRegistrationApi(mParam);
            call.enqueue(new Callback<OtpLoginRegistratinModel>() {
                @Override
                public void onResponse(Call<OtpLoginRegistratinModel> call, Response<OtpLoginRegistratinModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SharedPref.MOBILE, response.body().getData().getMobileNumber());
                        editor.apply();

                        ((RegisterSlideActivity) getActivity()).nextFragment(1);
                    } else {
                        nextButton.setEnabled(true);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<OtpLoginRegistratinModel> call, Throwable t) {
                    nextButton.setEnabled(true);
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        }else {
            nextButton.setEnabled(true);

            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        }
    }


    //initialising parameters
    private void initParam() {
        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (mobileNumber.length() > 0) {
            mParam.put(MOBILE, mobileNumber);
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