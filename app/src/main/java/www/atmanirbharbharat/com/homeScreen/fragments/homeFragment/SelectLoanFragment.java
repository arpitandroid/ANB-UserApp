package www.atmanirbharbharat.com.homeScreen.fragments.homeFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.ApplyLoanModel;
import www.atmanirbharbharat.com.models.LoanList;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

public class SelectLoanFragment extends Fragment implements View.OnClickListener {

    TextView loanAmountTextView;
    TextView loanPeriodTextView;
    TextView loanAmountText;
    TextView disbursalAmountTextView;
    TextView processingFeeTextView;
    TextView currentROI;
    TextView loanNameTextView;

    Button confirmLoanButton;
    ImageButton backButtonImage;

    SharedPreferences sharedPreferences;

    String token;

    Button editButton;
    Button cancelButton;
    TextView headingTextView;
    TextView bodyTextView;
    TextView paymodeTextview;
    TextView emicountTextview;

    TextView getLoanAmountTextView;
    String Lic_claim= "yes";
    RadioGroup radioGroup ;

    public static final String LOANDETAILS = "loanDetails";

    LoanList loanList = new LoanList();

    public SelectLoanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanList = (LoanList) getActivity().getIntent().getSerializableExtra(LOANDETAILS);
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, "@null");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_select_loan, container, false);
        init(viewGroup);
        setText();
        return viewGroup;
    }

    @SuppressLint("SetTextI18n")
    private void setText() {
        loanAmountTextView.setText(loanList.getAmount()+" "+"-/");
        loanAmountText.setText("₹ "+String.valueOf(loanList.getAmount())+" -/");
        disbursalAmountTextView.setText("₹ "+String.valueOf(loanList.getAmount() - loanList.getProcessingFee())+" -/");
        processingFeeTextView.setText("₹ "+String.valueOf(loanList.getProcessingFee())+" -/");
        currentROI.setText(String.valueOf(loanList.getROI())+" %");
        loanNameTextView.setText(String.valueOf(loanList.getLoanName())+" - "+loanList.getLoanId());
        paymodeTextview.setText(getloanrepayDays(loanList.getPayment_mode(),loanList.getLoan_duration()));
        loanPeriodTextView.setText(loanList.getLoan_duration()+" Days");
        emicountTextview.setText("₹ "+loanList.getEmi_amount());
    }

    public String getloanrepayDays(String mode, String daysCount){
        String returnvalue = "";
        if(mode.equalsIgnoreCase("Daily")){
            returnvalue = "Daily - "+daysCount+" EMI";
        }
        else if(mode.equalsIgnoreCase("Weekly")){
            returnvalue = "Weekly - "+ (Integer.parseInt(daysCount))/7 +" EMI";
        }else if(mode.equalsIgnoreCase("every-15-days")){
            returnvalue = "Every-15-days - "+ (Integer.parseInt(daysCount))/15 +" EMI";
        }else if(mode.equalsIgnoreCase("monthly")){
            returnvalue = "Monthly - "+ (Integer.parseInt(daysCount))/30 +" EMI";
        }

        return returnvalue;
    }

    private void init(ViewGroup viewGroup) {

        radioGroup= viewGroup.findViewById(R.id.radiogrp);
        confirmLoanButton = viewGroup.findViewById(R.id.viewStatment);
        backButtonImage = viewGroup.findViewById(R.id.backButtonImage);
        loanAmountTextView = viewGroup.findViewById(R.id.loanAmountTextView);
        loanPeriodTextView = viewGroup.findViewById(R.id.loanPeriodTextView);
        loanAmountText = viewGroup.findViewById(R.id.loanAmountText);
        disbursalAmountTextView = viewGroup.findViewById(R.id.repaytextview);
        processingFeeTextView = viewGroup.findViewById(R.id.processingFeeTextView);
        currentROI = viewGroup.findViewById(R.id.currentROI);
        paymodeTextview = viewGroup.findViewById(R.id.paymodeTextview);
        emicountTextview = viewGroup.findViewById(R.id.emicountTextview);
        loanNameTextView = viewGroup.findViewById(R.id.textView10);
        confirmLoanButton.setOnClickListener(this);
        backButtonImage.setOnClickListener(this);

           radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.RB_yes:
                        Lic_claim = "yes";
                      //  Toast.makeText(getActivity(), Lic_claim, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.RB_no:
                        Lic_claim = "no";
                       // Toast.makeText(getActivity(), Lic_claim, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.viewStatment) {
            openDialog();
        }
        if (view.getId() == R.id.backButtonImage) {
            getActivity().finish();
        }
    }

    private void applyLoanApi() {
        if (NetworkInfo.hasConnection(requireActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<ApplyLoanModel> call = apiService.applyLoanApi(token, loanList.getLoanId(),Lic_claim);
            call.enqueue(new Callback<ApplyLoanModel>() {
                @Override
                public void onResponse(Call<ApplyLoanModel> call, Response<ApplyLoanModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200) {
                        successLoanAppliedDialog( response.body().getMessage());
                    } else {
                        successLoanAppliedDialog( response.body().getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ApplyLoanModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {

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

    private void openDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.home_screen_dialog1, viewGroup, false);
        builder.setView(dialogView);

        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        editButton = dialogView.findViewById(R.id.editButton);
        cancelButton = dialogView.findViewById(R.id.cancelButton);
        headingTextView = dialogView.findViewById(R.id.headingTextView);
        bodyTextView = dialogView.findViewById(R.id.bodyTextView);


        headingTextView.setText(getString(R.string.confirm_the_loan));
        bodyTextView.setText(getString(R.string.the_loan_will_be_under));
        editButton.setText(getString(R.string.ok));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                applyLoanApi();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void successLoanAppliedDialog(String responsemessgae) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.home_screen_dialog1, viewGroup, false);
        builder.setView(dialogView);

        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        editButton = dialogView.findViewById(R.id.editButton);
        cancelButton = dialogView.findViewById(R.id.cancelButton);
        headingTextView = dialogView.findViewById(R.id.headingTextView);
        bodyTextView = dialogView.findViewById(R.id.bodyTextView);

        cancelButton.setVisibility(View.GONE);
        editButton.setText(getString(R.string.ok));
        headingTextView.setText(getString(R.string.loan_dailog_confirmation));

            bodyTextView.setText(responsemessgae);




        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                startActivity(new Intent(getActivity(), HomeScreenActivity.class));
                getActivity().finish();
//                getActivity().finish();

            }
        });
    }

}
