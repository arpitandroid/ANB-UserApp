package www.atmanirbharbharat.com.homeScreen.fragments.loansFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.AdminBankDetailsModel;
import www.atmanirbharbharat.com.models.PostPaymentApiModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;


public class PayLoanFragment extends Fragment implements View.OnClickListener {

    Button paidLoanButton;
    ImageButton backButtonImage;

    TextView loanAmountTextView;
    TextView emailAddressTextView;
    TextView bankNameTextView;
    TextView ifscCodeTextView;
    TextView UPIIdTextView;
    TextView phoneNumberTextView;
    TextView accountNumberTextView;

    String emailAddress;
    String bankName;
    String ifscCode;
    String UPIId;
    String phoneNumber;
    String accountNumber;

    String token;
    String loanAmount;

    EditText paymentTypeEditText;
    EditText transitionIdEditText;
    EditText paymentDateEditText;
    TextInputLayout textInputLayout3;

    String paymentType;
    String transitionId;
    String paymentDate;
    String id;

    Button okButton;

    Map<String, String> mParam;

    String loanId;

    public static final String LOANAMOUNNT = "LoanAmount";

    public static final String LOAN_ID = "la_id";
    public static final String PAYMENT_TYPE = "payment_type";
    public static final String TRANSACTION_ID = "transaction_id";
    public static final String PAYMENT_DATE = "payment_date";
    public static final String TOKEN = "token";

    SharedPreferences sharedPreferences;
    ProgressBar progress_circular;
    ConstraintLayout mainConstraintLayout;

    final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

    public PayLoanFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, " ");
        loanAmount = getActivity().getIntent().getStringExtra(LOANAMOUNNT);
        loanId = getActivity().getIntent().getStringExtra(AppliedLoanDetailFragment.LOANID);

        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_pay_loan, container, false);
        init(viewGroup);
        getBankDetailsApi();

        return viewGroup;

    }

    private void init(ViewGroup viewGroup) {
        paidLoanButton = viewGroup.findViewById(R.id.paidLoanButton);
        backButtonImage = viewGroup.findViewById(R.id.backButtonImage);

        loanAmountTextView = viewGroup.findViewById(R.id.loanAmountTextView);
        emailAddressTextView = viewGroup.findViewById(R.id.emailAddressTextView);
        bankNameTextView = viewGroup.findViewById(R.id.bankNameTextView);
        ifscCodeTextView = viewGroup.findViewById(R.id.ifscCodeTextView);
        UPIIdTextView = viewGroup.findViewById(R.id.UPIIdTextView);
        phoneNumberTextView = viewGroup.findViewById(R.id.phoneNumberTextView);
        accountNumberTextView = viewGroup.findViewById(R.id.accountNumberTextView);
        progress_circular = viewGroup.findViewById(R.id.progress_circular);
        mainConstraintLayout = viewGroup.findViewById(R.id.mainConstraintLayout);
        paidLoanButton.setOnClickListener(this);
        backButtonImage.setOnClickListener(this);
    }

    private void getBankDetailsApi() {

        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<AdminBankDetailsModel> call = apiService.adminBankDetailsApi(token);
            call.enqueue(new Callback<AdminBankDetailsModel>() {
                @Override
                public void onResponse(Call<AdminBankDetailsModel> call, Response<AdminBankDetailsModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200) {
                        progress_circular.setVisibility(View.GONE);
                        mainConstraintLayout.setVisibility(View.VISIBLE);
                        emailAddress = response.body().getData().getEmail();
                        bankName = response.body().getData().getBankName();
                        ifscCode = response.body().getData().getIFSCCode();
                        UPIId = response.body().getData().getUpi();
                        phoneNumber = response.body().getData().getMobile();
                        accountNumber = response.body().getData().getAccountNo();
                        id = response.body().getData().getId();
                        setData();
                    } else {
                        Toast.makeText(getActivity(), "Some error occurred from our end!", Toast.LENGTH_SHORT).show();
                        Log.i("tag","Some Error Occurred" + Objects.requireNonNull(getActivity()).getPackageName().getClass());
                    }
                }

                @Override
                public void onFailure(Call<AdminBankDetailsModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {

            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        }
    }

    private void initiateDatePickerDialog() {
//        myCalendar.add(Calendar.YEAR, -21);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        paymentDateEditText.setText(sdf.format(myCalendar.getTime()));
    }
    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setMessage("You need to make sure your device is conected to Internet")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().onBackPressed();

                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.paidLoanButton) {
            showPaymentDialog();
        }if (view.getId() == R.id.backButtonImage) {
            getActivity().onBackPressed();
        }
    }

    public void setData() {
        loanAmountTextView.setText(loanAmount);
        emailAddressTextView.setText(emailAddress);
        bankNameTextView.setText(bankName);
        ifscCodeTextView.setText(ifscCode);
        UPIIdTextView.setText(UPIId);
        phoneNumberTextView.setText(phoneNumber);
        accountNumberTextView.setText(accountNumber);
    }

    private void showPaymentDialog() {
        initiateDatePickerDialog();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.loan_paid_dialog, viewGroup, false);
        builder.setView(dialogView);

        paymentTypeEditText = dialogView.findViewById(R.id.paymentTypeEditText);
        transitionIdEditText = dialogView.findViewById(R.id.transitionIdEditText);
        paymentDateEditText = dialogView.findViewById(R.id.paymentDateEditText);
        textInputLayout3 = dialogView.findViewById(R.id.textInputLayout3);
        okButton = dialogView.findViewById(R.id.okButton);

        paymentDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));

                datePicker.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
                datePicker.show();
            }
        });


        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okButton.setEnabled(false);
                validation();
            }
        });
    }

    private void initParam() {
        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (id.length() > 0) {
            mParam.put(LOAN_ID, loanId);
        }
        if (token.length() > 0) {
            mParam.put(TOKEN, token);
        }
        if (paymentType.length() > 0) {
            mParam.put(PAYMENT_TYPE, paymentType);
        }
        if (transitionId.length() > 0) {
            mParam.put(TRANSACTION_ID, transitionId);
        }
        if (paymentDate.length() > 0) {
            mParam.put(PAYMENT_DATE, paymentDate);
        }
    }

    public void validation() {
        getData();
        if (paymentType.length() <= 0) {
            Toast.makeText(getActivity(), "Payment Type cannot be empty", Toast.LENGTH_SHORT).show();
            okButton.setEnabled(true);
        } else if (transitionId.length() <= 0) {
            Toast.makeText(getActivity(), "Transition Id cannot be empty", Toast.LENGTH_SHORT).show();
            okButton.setEnabled(true);
        } else if (paymentDate.length() <= 0) {
            Toast.makeText(getActivity(), "Payment Date cannot be empty", Toast.LENGTH_SHORT).show();
            okButton.setEnabled(true);
        } else {
            paymentDoneApiHit();
        }
    }


    private void paymentDoneApiHit() {
        initParam();


        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<PostPaymentApiModel> call = apiService.postPaymentApi(mParam);
            call.enqueue(new Callback<PostPaymentApiModel>() {
                @Override
                public void onResponse(Call<PostPaymentApiModel> call, Response<PostPaymentApiModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200 ) {

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        if (getContext() instanceof EditDataActivity) {
//                            getActivity().finish();
//                        } else
//                            ((RegisterSlideActivity) getActivity()).nextFragment(4);

                        getActivity().finish();

                    } else {
                        okButton.setEnabled(true);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PostPaymentApiModel> call, Throwable t) {
                    okButton.setEnabled(true);
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            okButton.setEnabled(true);

            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        }
    }


    public void getData() {
        paymentType = paymentTypeEditText.getText().toString();
        transitionId = transitionIdEditText.getText().toString();
        paymentDate = paymentDateEditText.getText().toString();
    }

}