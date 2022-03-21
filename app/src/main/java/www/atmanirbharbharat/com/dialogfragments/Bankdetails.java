package www.atmanirbharbharat.com.dialogfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.EditDataActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.models.PersonalInformationModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;
import static www.atmanirbharbharat.com.fragments.BasicDetailsFragment.modelinfoData;

public class Bankdetails extends DialogFragment {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor ;

    EditText bankNameEditText;
    EditText accountNumberEditText;
    EditText confirmaccountNumberEditText;
    EditText namInBankAcctEditText;
    EditText ifscCodeEditText;

    String bankName;
    String accountNumber;
    String confirmaccountNumber;
    String namInBankAcct;  // Account Holder
    String ifscCode;
    String email = "";

    Button nextButton_BD;
    private HashMap<String, String> mParam;

    /// HashMap ---
    private static final String TOKEN = "token";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String COUNTRY = "country";
    private static final String DOB = "dob";
    private static final String ADHAAR_NO = "aadhar_no";
    private static final String BASIC_DETAILS_APPROVAL_STATUS = "bda_status";
    private static final String PHONE1 = "ec1";
    private static final String RELATION1 = "ecr1";
    private static final String EMERGENCY_CONTACT_STATUS = "ecv_status";
    private static final String PROFILE_COMPLETION_STATUS = "p_c_status";
    private static final String BANK_APPROVAL_STATUS = "ba_status";
    private static final String ACCOUNT_HOLDER_NAME = "acc_holder_name";
    private static final String IFSC_CODE = "ifcs_code";
    private static final String ACCOUNT_NUMBER = "acc_no";
    private static final String BANK_NAME = "bank_name";

    String basicDetailsApprovalStatus = "PENDING";
    String bankApprovalStatus = "PENDING";
    String emergencyContactStatus = "PENDING";
    String profileCompleteStatus = "COMPLETE";
    private Context context ;

    public Bankdetails(Context context) {
        // Required empty public constructor
        this.context =  context;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.dialog_bank_details, container, false);

        FindViews(rootView);

        sharedPreferences = requireActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        rootView.findViewById(R.id.nextButton_BD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* dismiss();
                if (getContext() instanceof EditDataActivity) {
                    startActivity(new Intent(getActivity(), HomeScreenActivity.class));
                    getActivity().finish();
                } else {
                    editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 3);
                    editor.apply();
                    ((RegisterSlideActivity) getActivity()).nextFragment(3);
                }*/

                bankName = bankNameEditText.getText().toString();
                accountNumber = accountNumberEditText.getText().toString();
                confirmaccountNumber = confirmaccountNumberEditText.getText().toString();
                namInBankAcct = namInBankAcctEditText.getText().toString();
                ifscCode = ifscCodeEditText.getText().toString();


                if (accountNumber.length() <= 0) {
                    Toast.makeText(getActivity(), "Account Number Cannot be empty", Toast.LENGTH_SHORT).show();
                    nextButton_BD.setEnabled(true);
                }else if (confirmaccountNumber.length() <= 0) {
                    Toast.makeText(getActivity(), "Confirm Account Number Cannot be empty", Toast.LENGTH_SHORT).show();
                    nextButton_BD.setEnabled(true);
                }else if (!confirmaccountNumber.equals(accountNumber)) {
                    Toast.makeText(getActivity(), "Confirm account number is not matching", Toast.LENGTH_SHORT).show();
                    nextButton_BD.setEnabled(true);
                } else if (namInBankAcct.length() <= 0) {  // Account Holder
                    Toast.makeText(getActivity(), "Account Holder Name cannot be empty", Toast.LENGTH_SHORT).show();
                    nextButton_BD.setEnabled(true);
                } else if (ifscCode.length() <= 0) {
                    Toast.makeText(getActivity(), "IFSC code cannot be empty", Toast.LENGTH_SHORT).show();
                    nextButton_BD.setEnabled(true);
                }
                else if (bankName.length() <= 0) {
                    Toast.makeText(getActivity(), "Bank Name cannot be empty", Toast.LENGTH_SHORT).show();
                    nextButton_BD.setEnabled(true);
                }else {

                    modelinfoData.setAccountNumber(accountNumber);
                    modelinfoData.setBankName(bankName);
                    modelinfoData.setAccHolderName(namInBankAcct); //Account Holder
                    modelinfoData.setIFSCCode(ifscCode);
                    Log.i("arp","bankData= "+ new Gson().toJson(modelinfoData));

                    personalInformationUpdateApiHit();

                }

            }
        });

        return rootView;
    }

    //initialising parameters
    private void initParam() {
        final String token = sharedPreferences.getString(SharedPref.TOKEN, "@null");
        Log.i("arp","token= "+ token);

        email =  modelinfoData.getEmail();

        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (token.length() > 0) {
            mParam.put(TOKEN, token);
        }
        // if (email.length() > 0) {
        // change to optional --
        mParam.put(EMAIL, email);
        // }
        if (modelinfoData.getFirstName().length() > 0) {
            mParam.put(FIRST_NAME, modelinfoData.getFirstName());
        }
        if (modelinfoData.getLastName().length() > 0) {
            mParam.put(LAST_NAME, modelinfoData.getLastName());
        }
        if (modelinfoData.getAddress().length() > 0) {
            mParam.put(ADDRESS, modelinfoData.getAddress());
        }
        if (modelinfoData.getCity().length() > 0) {
            mParam.put(CITY, modelinfoData.getCity());
        }
        if (modelinfoData.getCountry().length() > 0) {
            mParam.put(COUNTRY, modelinfoData.getCountry());
        }
        if (modelinfoData.getDob().length() > 0) {
            mParam.put(DOB, modelinfoData.getDob());
        }
        if (modelinfoData.getAadharNo().length() > 0) {
            mParam.put(ADHAAR_NO, modelinfoData.getAadharNo());
        }
        if (basicDetailsApprovalStatus.length() > 0) {
            mParam.put(BASIC_DETAILS_APPROVAL_STATUS, basicDetailsApprovalStatus);
        }
        if (modelinfoData.getEmergencyContact1().length() > 0) {
            mParam.put(PHONE1, modelinfoData.getEmergencyContact1());
        }
        if (modelinfoData.getEmergencyContactRelation1().length() > 0) {
            mParam.put(RELATION1, modelinfoData.getEmergencyContactRelation1());
        }
        if (emergencyContactStatus.length() > 0) {
            mParam.put(EMERGENCY_CONTACT_STATUS, emergencyContactStatus);
        }
        if (profileCompleteStatus.length() > 0) {
            mParam.put(PROFILE_COMPLETION_STATUS, profileCompleteStatus);
        }

        if (bankApprovalStatus.length() > 0) {
            mParam.put(BANK_APPROVAL_STATUS, bankApprovalStatus);
        }
        if (modelinfoData.getBankName().length() > 0) {
            mParam.put(BANK_NAME, modelinfoData.getBankName());
        }
        if (modelinfoData.getAccountNumber().length() > 0) {
            mParam.put(ACCOUNT_NUMBER, modelinfoData.getAccountNumber());
        }
        if (modelinfoData.getAccHolderName().length() > 0) {
            mParam.put(ACCOUNT_HOLDER_NAME, modelinfoData.getAccHolderName());
        }
        if (modelinfoData.getIFSCCode().length() > 0) {
            mParam.put(IFSC_CODE, modelinfoData.getIFSCCode());
        }
    }

    private void personalInformationUpdateApiHit() {
        initParam();
        Log.i("arp","paramData= "+ new Gson().toJson(mParam));

        if (NetworkInfo.hasConnection(requireContext())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<PersonalInformationModel> call = apiService.personalInformationUpdateApi(mParam);
            call.enqueue(new Callback<PersonalInformationModel>() {
                @Override
                public void onResponse(Call<PersonalInformationModel> call, Response<PersonalInformationModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        // clear static list ---
                        modelinfoData = new PersonalInformationModel().new Data();

                        dismiss();

                        if (context instanceof EditDataActivity) {
                            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
                            getActivity().finish();
                        } else {
                            editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 3);
                            editor.apply();
                            ((RegisterSlideActivity) context).nextFragment(3);

                        }

                    } else {
                        nextButton_BD.setEnabled(true);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PersonalInformationModel> call, Throwable t) {
                    nextButton_BD.setEnabled(true);
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            nextButton_BD.setEnabled(true);

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
                       dialog.dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private void FindViews(View viewGroup) {
        bankNameEditText = viewGroup.findViewById(R.id.bankNameEditText);
        accountNumberEditText = viewGroup.findViewById(R.id.accountNumberEditText);
        confirmaccountNumberEditText = viewGroup.findViewById(R.id.confirmaccountNumberEditText);
        namInBankAcctEditText = viewGroup.findViewById(R.id.namInBankAcctEditText);
        ifscCodeEditText = viewGroup.findViewById(R.id.ifscCodeEditText);

        nextButton_BD = viewGroup.findViewById(R.id.nextButton_BD);

    }

    public int getTheme(){
        return R.style.FullScreenDialog;
    }
}
