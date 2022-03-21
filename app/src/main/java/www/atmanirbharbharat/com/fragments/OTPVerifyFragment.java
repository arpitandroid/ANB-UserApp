package www.atmanirbharbharat.com.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.ContactModel;
import www.atmanirbharbharat.com.models.OtpLoginRegistratinModel;
import www.atmanirbharbharat.com.models.OtpVerificationModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.AppConstant;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;

public class OTPVerifyFragment extends Fragment implements View.OnClickListener {

    EditText et1, et2, et3, et4;
    Button nextButton;
    TextView resendCodeTextView;
    TextView mobileNoTextView;


    String otp;
    String deviceId;
    String mobile;
    String deviceType = "1";
    String fcmToken;

    String token;
    String profileCompletionStatus;
    String companyCompletionStatus;
    String aadharCompletionStatus;

    private HashMap<String, String> mParam;
    private HashMap<String, String> mParamResendApi;
    private ProgressDialog mProgressDialog;


    private static final String MOBILE = "mobile";
    private static final String DEVICE_ID = "deviceId";
    private static final String OTP = "otp";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String FCM_TOKEN = "fcm_token";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    List<ContactModel> contactList;
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 111;


    ProgressBar progressBar;
    ConstraintLayout mainConstraintLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_otp_verify, container, false);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        String msg = "Done";

                        if (!task.isSuccessful()) {
                            msg = "Failed";
                            return;
                        }

                        // Get new FCM registration token
                        fcmToken = task.getResult();

                    }
                });
        init(viewGroup);
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        et1 = viewGroup.findViewById(R.id.et1);
        et2 = viewGroup.findViewById(R.id.et2);
        et3 = viewGroup.findViewById(R.id.et3);
        et4 = viewGroup.findViewById(R.id.et4);
        resendCodeTextView = viewGroup.findViewById(R.id.resendCodeTextView);
        mobileNoTextView = viewGroup.findViewById(R.id.mobileNoTextView);

        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        mobile = sharedPreferences.getString(SharedPref.MOBILE, "@null");
        mobileNoTextView.append(" " + mobile);
        resendCodeTextView.setOnClickListener(this);


        nextButton = viewGroup.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et2.requestFocus();
                } else if (s.length() == 0) {
                    et1.clearFocus();
                }
            }
        });

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et3.requestFocus();
                } else if (s.length() == 0) {
                    et1.requestFocus();
                }
            }
        });

        et3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et4.requestFocus();
                } else if (s.length() == 0) {
                    et2.requestFocus();
                }
            }
        });

        et4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                closeKeyboard();

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et4.clearFocus();

                } else if (s.length() == 0) {
                    et3.requestFocus();
                }
            }
        });

        progressBar = viewGroup.findViewById(R.id.progressbar);
        mainConstraintLayout = viewGroup.findViewById(R.id.mainConstraintLayout);

    }

    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextButton) {
            validation();

        }
        if (view.getId() == R.id.resendCodeTextView) {
            resendCodeTextView.setEnabled(false);
            resendCodeTextView.setText("Sending.....");
            resendOtpHit();
        }
    }

    private void resendOtpHit() {
        getData();
        mParamResendApi = new HashMap<>();
        mParamResendApi.put(MOBILE, mobile);
        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<OtpLoginRegistratinModel> call = apiService.otpLoginRegistrationApi(mParamResendApi);
            call.enqueue(new Callback<OtpLoginRegistratinModel>() {
                @Override
                public void onResponse(Call<OtpLoginRegistratinModel> call, Response<OtpLoginRegistratinModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                        resendCodeTextView.setText("Code Sent Again");

                    } else {
                        resendCodeTextView.setEnabled(true);
                        resendCodeTextView.setText("Resend OTP");
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<OtpLoginRegistratinModel> call, Throwable t) {
                    resendCodeTextView.setEnabled(true);
                    resendCodeTextView.setText("Resend OTP");
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            resendCodeTextView.setEnabled(true);
            resendCodeTextView.setText("Resend OTP");
            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
        }
    }

    //    -----------------------getting data into variables for api hit-----------------
    private void getData() {
        otp = et1.getText().toString() + et2.getText().toString() +
                et3.getText().toString() + et4.getText().toString();


    }

    public void validation() {
        getData();
        if (otp.length() != 4) {
            Toast.makeText(getActivity(), "Enter the four digit OTP", Toast.LENGTH_SHORT).show();
        } else {
            otpVerificationApiHit();
        }
    }

    private void otpVerificationApiHit() {
        initParam();


        if (NetworkInfo.hasConnection(requireActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<OtpVerificationModel> call = apiService.otpVerificationApi(mParam);
            call.enqueue(new Callback<OtpVerificationModel>() {
                @Override
                public void onResponse(Call<OtpVerificationModel> call, Response<OtpVerificationModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                        sharedPreferences = requireActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(SharedPref.TOKEN, response.body().getData().getToken());
                        editor.putBoolean(SharedPref.LOGIN_CHECK, true);
                        editor.apply();

                        token = response.body().getData().getToken();
                        profileCompletionStatus = response.body().getData().getProfileCompletionStatus();
                        companyCompletionStatus = response.body().getData().getCompanyUploadStatus();
                        aadharCompletionStatus = response.body().getData().getAadharUploadStatus();

                        Log.i("arp","otpres= "+ new Gson().toJson(mParam));

                        Toast.makeText(getActivity(), "OTP verified successfully", Toast.LENGTH_SHORT).show();

                        if (profileCompletionStatus.equals("INCOMPLETE")) {
                            editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 2);
                            editor.apply();
                            ((RegisterSlideActivity) getActivity()).nextFragment(2);
                        }  else if (aadharCompletionStatus.equals("INCOMPLETE")) {
                            editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 3);
                            editor.apply();
                            ((RegisterSlideActivity) getActivity()).nextFragment(3);
                        } else {
                            getActivity().finish();
                            Intent intent = new Intent(getActivity(), HomeScreenActivity.class);
                            startActivity(intent);


                        }
                        System.out.println(response.body().getData().getToken());
                        //if profileComppltion status is pending then transfer to profile completion else homescreen

                    } else {
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        nextButton.setEnabled(true);

                    }
                }

                @Override
                public void onFailure(Call<OtpVerificationModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();
                    nextButton.setEnabled(true);

                }
            });
        } else {
            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(false);

        }
    }



    //initialising parameters
    private void initParam() {
        deviceId = AppConstant.getDeviceId(getContext());

        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (otp.length() == 4) {
            mParam.put(OTP, otp);
        }
        if (deviceId.length() > 0) {
            mParam.put(DEVICE_ID, deviceId);
        }
        if (deviceType.length() > 0) {
            mParam.put(DEVICE_TYPE, deviceType);
        }
        if (fcmToken.length() > 0) {
            mParam.put(FCM_TOKEN, fcmToken);
        }
        if (mobile.length() > 0) {
            mParam.put(MOBILE, mobile);
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