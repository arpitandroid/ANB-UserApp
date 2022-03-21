package www.atmanirbharbharat.com.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.EditDataActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.PersonalInformationModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

public class CompanyInformationLoanFragment extends Fragment implements View.OnClickListener {


    EditText companyNameEditText;
    EditText companyPhoneEditText;
    EditText companyIndustryEditText;
    EditText yearsOfWorkingEditText;
    EditText occupationEditText;
    EditText positionEditText;
    EditText monthlySalaryEditText;
    EditText companyAddressEditText;

    Button confirmCompanyDetailsButton;


    String companyName;
    String companyPhone;
    String companyIndustry;
    String yearsOfWorking;
    String occupation;
    String position;
    String monthlySalary;
    String companyAddress;
    String token;
    String paySlipApprovalStatus = "PENDING";
    String companyUploadStatus = "COMPLETE";
    String companyApproveStatus = "company_approve_status";

    private static final String TOKEN = "token";
    private static final String PAY_SLIP_APPROVAL_STATUS = "pa_status";
    private static final String COMPANY_NAME = "company_name";
    private static final String COMPANY_PHONE = "office_telephone";
    private static final String COMPANY_INDUSTRY = "industry";
    private static final String YEARS_OF_WORKING = "years_of_working";
    private static final String OCCUPATION = "job_type";
    private static final String POSITION = "position";
    private static final String MONTHLY_SALARY = "monthly_salary";
    private static final String COMPANY_ADDRESS = "company_address";
    private static final String COMPANY_UPLOAD_STATUS = "company_upload_status";
    private static final String COMPANY_APPROVE_STATUS = "company_approve_status";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private HashMap<String, String> mParam;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_company_information_loan, container, false);
        init(viewGroup);
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        companyNameEditText = viewGroup.findViewById(R.id.companyNameEditText);
        companyPhoneEditText = viewGroup.findViewById(R.id.companyPhoneEditText);
        companyIndustryEditText = viewGroup.findViewById(R.id.companyIndustryEditText);
        yearsOfWorkingEditText = viewGroup.findViewById(R.id.yearsOfWorkingEditText);
        occupationEditText = viewGroup.findViewById(R.id.occupationEditText);
        positionEditText = viewGroup.findViewById(R.id.positionEditText);
        monthlySalaryEditText = viewGroup.findViewById(R.id.monthlySalaryEditText);
        companyAddressEditText = viewGroup.findViewById(R.id.companyAddressEditText);
        confirmCompanyDetailsButton = viewGroup.findViewById(R.id.confirmCompanyDetailsButton);

        confirmCompanyDetailsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirmCompanyDetailsButton) {
            confirmCompanyDetailsButton.setEnabled(false);
            validation();
        }
    }

    public void getData() {
        companyName = companyNameEditText.getText().toString();
        companyPhone = companyPhoneEditText.getText().toString();
        companyIndustry = companyIndustryEditText.getText().toString();
        yearsOfWorking = yearsOfWorkingEditText.getText().toString();
        occupation = occupationEditText.getText().toString();
        position = positionEditText.getText().toString();
        monthlySalary = monthlySalaryEditText.getText().toString();
        companyAddress = companyAddressEditText.getText().toString();

        token = sharedPreferences.getString(SharedPref.TOKEN, "@null");

    }

    //initialising parameters
    private void initParam() {
        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (token.length() > 0) {
            mParam.put(TOKEN, token);
        }
        if (companyName.length() > 0) {
            mParam.put(COMPANY_NAME, companyName);
        }
        if (companyPhone.length() > 0) {
            mParam.put(COMPANY_PHONE, companyPhone);
        }
        if (companyIndustry.length() > 0) {
            mParam.put(COMPANY_INDUSTRY, companyIndustry);
        }
        if (yearsOfWorking.length() > 0) {
            mParam.put(YEARS_OF_WORKING, yearsOfWorking);
        }
        if (occupation.length() > 0) {
            mParam.put(OCCUPATION, occupation);
        }
        if (position.length() > 0) {
            mParam.put(POSITION, position);
        }
        if (monthlySalary.length() > 0) {
            mParam.put(MONTHLY_SALARY, monthlySalary);
        }
        if (companyAddress.length() > 0) {
            mParam.put(COMPANY_ADDRESS, companyAddress);
        }
        if (companyUploadStatus.length() > 0) {
            mParam.put(COMPANY_UPLOAD_STATUS, companyUploadStatus);
        }
        if (companyApproveStatus.length() > 0) {
            mParam.put(COMPANY_APPROVE_STATUS, companyApproveStatus);
        }
    }


    public void validation() {

        getData();
        if (companyName.length() <= 0) {
            Toast.makeText(getActivity(), "Company Name cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);

        }
        if (companyPhone.length() <= 0) {
            Toast.makeText(getActivity(), "Company Phone Number Cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);
        } else if (companyIndustry.length() <= 0) {
            Toast.makeText(getActivity(), "Company Industry cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);
        } else if (yearsOfWorking.length() <= 0) {
            Toast.makeText(getActivity(), "Years of working cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);
        } else if (occupation.length() <= 0) {
            Toast.makeText(getActivity(), "Occupation cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);
        } else if (position.length() <= 0) {
            Toast.makeText(getActivity(), "Position cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);
        } else if (monthlySalary.length() <= 0) {
            Toast.makeText(getActivity(), "Monthly cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);
        } else if (companyAddress.length() <= 0) {
            Toast.makeText(getActivity(), "Company Address cannot be empty", Toast.LENGTH_SHORT).show();
            confirmCompanyDetailsButton.setEnabled(true);
        } else {
            CompanyDetailsUploadApiHit();
        }


    }

    private void CompanyDetailsUploadApiHit() {
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
                        if (getContext() instanceof EditDataActivity) {
                            getActivity().finish();
                        } else {
                            editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 5);
                            editor.apply();
                            if (getContext() instanceof RegisterSlideActivity)
                                ((RegisterSlideActivity) getActivity()).nextFragment(5);

                        }

                    } else {
                        confirmCompanyDetailsButton.setEnabled(true);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PersonalInformationModel> call, Throwable t) {
                    confirmCompanyDetailsButton.setEnabled(true);
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            confirmCompanyDetailsButton.setEnabled(true);

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