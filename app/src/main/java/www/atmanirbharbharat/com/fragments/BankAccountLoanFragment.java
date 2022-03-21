package www.atmanirbharbharat.com.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.PersonalInformationModel;
import www.atmanirbharbharat.com.models.User;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;


public class BankAccountLoanFragment extends Fragment implements View.OnClickListener {

    Button confirmBankDetailsButton;

    EditText bankNameEditText;
    EditText accountNumberEditText;
    EditText namInBankAcctEditText;
    EditText ifscCodeEditText;

    String bankName;
    String accountNumber;
    String namInBankAcct;
    String ifscCode;
    String token;
    String bankApprovalStatus = "PENDING";

    private static final String TOKEN = "token";
    private static final String BANK_APPROVAL_STATUS = "ba_status";
    private static final String ACCOUNT_HOLDER_NAME = "acc_holder_name";
    private static final String IFSC_CODE = "ifcs_code";
    private static final String ACCOUNT_NUMBER = "acc_no";
    private static final String BANK_NAME = "bank_name";


    private SharedPreferences sharedPreferences;


    private HashMap<String, String> mParam;
    User user;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_bank_account_loan, container, false);
        init(viewGroup);
        if (getContext() instanceof HomeScreenActivity){
            Gson gson = new Gson();
            String json = sharedPreferences.getString(SharedPref.HOMESCREENDATA,"");
            user = gson.fromJson(json, User.class);
            if (user.getBankApprovalStatus().equals("NOT_AVAILABLE")){
                setDataToTextView();
            }
        }

        return viewGroup;
    }

    private void setDataToTextView() {
        bankNameEditText.setText(user.getBankName());
        accountNumberEditText.setText(user.getAccountNumber());
        namInBankAcctEditText.setText(user.getAccHolderName());
        ifscCodeEditText.setText(user.getIFSCCode());
    }


    private void init(ViewGroup viewGroup) {
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        confirmBankDetailsButton = viewGroup.findViewById(R.id.confirmBankDetailsButton);
        bankNameEditText = viewGroup.findViewById(R.id.bankNameEditText);
        accountNumberEditText = viewGroup.findViewById(R.id.accountNumberEditText);
        namInBankAcctEditText = viewGroup.findViewById(R.id.namInBankAcctEditText);
        ifscCodeEditText = viewGroup.findViewById(R.id.ifscCodeEditText);
        confirmBankDetailsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirmBankDetailsButton) {
            confirmBankDetailsButton.setEnabled(false);
            validation();

        }
    }


    //initialising parameters
    private void initParam() {
        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (token.length() > 0) {
            mParam.put(TOKEN, token);
        }
        if (bankApprovalStatus.length() > 0) {
            mParam.put(BANK_APPROVAL_STATUS, bankApprovalStatus);
        }
        if (bankName.length() > 0) {
            mParam.put(BANK_NAME, bankName);
        }
        if (accountNumber.length() > 0) {
            mParam.put(ACCOUNT_NUMBER, bankApprovalStatus);
        }
        if (namInBankAcct.length() > 0) {
            mParam.put(ACCOUNT_HOLDER_NAME, namInBankAcct);
        }
        if (ifscCode.length() > 0) {
            mParam.put(IFSC_CODE, ifscCode);
        }
    }

    private void getData() {
        bankName = bankNameEditText.getText().toString();
        accountNumber = accountNumberEditText.getText().toString();
        namInBankAcct = namInBankAcctEditText.getText().toString();
        ifscCode = ifscCodeEditText.getText().toString();

        token = sharedPreferences.getString(SharedPref.TOKEN, "@null");

    }

    public void validation() {

        getData();
        if (bankName.length() <= 0) {
            Toast.makeText(getActivity(), "Bank Name cannot be empty", Toast.LENGTH_SHORT).show();
            confirmBankDetailsButton.setEnabled(true);

        }
        else if (accountNumber.length() <= 0) {
            Toast.makeText(getActivity(), "Account Number Cannot be empty", Toast.LENGTH_SHORT).show();
            confirmBankDetailsButton.setEnabled(true);
        } else if (namInBankAcct.length() <= 0) {
            Toast.makeText(getActivity(), "Account Holder Name cannot be empty", Toast.LENGTH_SHORT).show();
            confirmBankDetailsButton.setEnabled(true);
        } else if (ifscCode.length() <= 0) {
            Toast.makeText(getActivity(), "IFSC code cannot be empty", Toast.LENGTH_SHORT).show();
            confirmBankDetailsButton.setEnabled(true);
        } else {
            BankDetailsUploadApiHit();
        }


    }

    private void BankDetailsUploadApiHit() {
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
                        getActivity().finish();

                    } else {
                        confirmBankDetailsButton.setEnabled(true);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PersonalInformationModel> call, Throwable t) {
                    confirmBankDetailsButton.setEnabled(true);
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            confirmBankDetailsButton.setEnabled(true);

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