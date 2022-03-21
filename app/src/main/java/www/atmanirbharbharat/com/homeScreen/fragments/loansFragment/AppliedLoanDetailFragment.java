package www.atmanirbharbharat.com.homeScreen.fragments.loansFragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.LoanPaymentDetailActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.UserLoanDetailActivity;
import www.atmanirbharbharat.com.WebViewActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.ApplyExtensionModel;
import www.atmanirbharbharat.com.models.CurrentLoanDetailsGetModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;


public class AppliedLoanDetailFragment extends Fragment implements View.OnClickListener {


    Button payLoanButton;
    Button extendLoanButton;
    ImageButton backButtonImage;

    TextView approvedStatusTextView;
    TextView loanAmountTextView;
    TextView loanPeriodTextView;
    TextView loanAmountText;
    TextView repaytext;
    TextView processingFeeTextView;
    TextView currentRoi;
    TextView dueDateTextView;
    TextView messageTextView;
    TextView messageTextHeading ;
    TextView textView9 ;

    TextView extensionChargesTextView;//for dialog
    TextView extensionDaysTextView;//for dialog
    TextView extensionChargesTextView1;//for main screen
    TextView extensionDaysTextView1;//for main screen
    TextView extensionDaysText;//for main screen
    TextView extensionChargesText;//for main screen
    TextView processsingTextView;
    TextView dueTextView;
    TextView penaltyTextView ;
    TextView penaltyTextView2 ;
    TextView penaltyAmountTextView;
    TextView penaltyDaysTextView;
    TextView loanClosureAmountTextView;
    TextView disbursalAmounttxt;
    TextView emiamounttxt;
    TextView btn_paydetails;
    private LinearLayout linearLayout;


    Button cancelButton;
    Button okButton;


    String approvedStatus;
    int loanAmount;
    String loanClosureAmount;
    String loanPeriod;
    String disbursalAmount;
    String interestFee;
    int processingFee;
    String roi;
    String dueAmount;
    String dueDate;
    String paymentMode;
    String loanDuration;
    String Emiamount;
    String penaltyApprovedStatus;
    String moneyReturnStatus;

    String totalExtensionCharges;
    String totalExtensionDate;

    String extensionDate;
    String extensionCharges;
    String extensionStatus;
    String message;
    public static final String LOANID = "AppliedLoanId";

    String loanId;
    String token;

    SharedPreferences sharedPreferences;
    ProgressBar progress_circular;
    ConstraintLayout mainConstraintLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, " ");
        loanId = getActivity().getIntent().getStringExtra(LOANID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_applied_loan_detail, container, false);
        init(view);
        getLoanDetailsListApi();
        return view;
    }

    private void init(View view) {
        payLoanButton = view.findViewById(R.id.addtopup);
        extendLoanButton = view.findViewById(R.id.viewStatment);
        backButtonImage = view.findViewById(R.id.backButtonImage);
        messageTextView = view.findViewById(R.id.messageTextView);
        messageTextHeading = view.findViewById(R.id.messageTextHeading);

        approvedStatusTextView = view.findViewById(R.id.approvedStatusTextView);
        loanAmountTextView = view.findViewById(R.id.loanAmountTextView);
        loanClosureAmountTextView = view.findViewById(R.id.loanClosureAmountTextView);
        loanPeriodTextView = view.findViewById(R.id.loanPeriodTextView);
        loanAmountText = view.findViewById(R.id.loanAmountText);
        repaytext = view.findViewById(R.id.repaytextview);
        processingFeeTextView = view.findViewById(R.id.processingFeeTextView);
        processsingTextView = view.findViewById(R.id.processsingTextView);
        extensionChargesTextView1 = view.findViewById(R.id.extensionChargesTextView1);
        extensionDaysTextView1 = view.findViewById(R.id.extensionDaysTextView1);
        extensionDaysText = view.findViewById(R.id.extensionDaysText);
        extensionChargesText = view.findViewById(R.id.extensionChargesText);
        currentRoi = view.findViewById(R.id.currentROI);
        penaltyDaysTextView = view.findViewById(R.id.penaltyDaysTextView);
        penaltyAmountTextView = view.findViewById(R.id.penaltyAmountTextView);
        penaltyTextView = view.findViewById(R.id.penaltyTextView);
        penaltyTextView2 = view.findViewById(R.id.penaltyTextView2);
        progress_circular = view.findViewById(R.id.progress_circular);
        mainConstraintLayout = view.findViewById(R.id.mainConstraintLayout);
        textView9 = view.findViewById(R.id.textView9);
        disbursalAmounttxt = view.findViewById(R.id.disbursalAmounttxt);
        emiamounttxt = view.findViewById(R.id.emiamounttxt);
        linearLayout = view.findViewById(R.id.linearLayout);
        btn_paydetails = view.findViewById(R.id.btn_paydetails);
        payLoanButton.setOnClickListener(this);
        extendLoanButton.setOnClickListener(this);
        backButtonImage.setOnClickListener(this);
        btn_paydetails.setOnClickListener(this);
    }



    private void getLoanDetailsListApi() {
        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<CurrentLoanDetailsGetModel> call = apiService.currentLoanDetailsApi(token, loanId);
            call.enqueue(new Callback<CurrentLoanDetailsGetModel>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<CurrentLoanDetailsGetModel> call, Response<CurrentLoanDetailsGetModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200) {


                        progress_circular.setVisibility(View.GONE);
                        mainConstraintLayout.setVisibility(View.VISIBLE);

                        textView9.setText("Loan ID- "+response.body().getData().getLoanBasicDetails().getLoanAppliedId());
                        approvedStatus = response.body().getData().getLoanBasicDetails().getLoanStatus();
                        loanAmount = response.body().getData().getLoanBasicDetails().getAmount();
                        loanPeriod = response.body().getData().getLoanBasicDetails().getLoan_duration();
                        processingFee = response.body().getData().getLoanBasicDetails().getProcessingFee();
                        roi = String.valueOf(response.body().getData().getLoanBasicDetails().getROI());
                        paymentMode = response.body().getData().getLoanBasicDetails().getPayment_mode();
                        loanDuration = response.body().getData().getLoanBasicDetails().getLoan_duration();
                        Emiamount = response.body().getData().getLoanBasicDetails().getEmi_amount();
                        message = response.body().getData().getLoanBasicDetails().getRejectComment();
                        loanClosureAmount = response.body().getData().getLoanBasicDetails().getRemaining_balance();




//                        disbursalAmount=response.body().getData().getLoanBasicDetails().getPayableAmount();




//
//                        dueDate = response.body().getData().getLoanBasicDetails().getLoanEndDate();
////                        penaltyAmount = response.body().getData().getPenaltyDetails().getAmount();
//
//                        extensionStatus = response.body().getData().getLoanBasicDetails().getExtStatus();
//                        extensionCharges = response.body().getData().getLoanBasicDetails().getExtensionCharges();
//                        extensionDate = response.body().getData().getLoanBasicDetails().getExtensionDays();
//                        totalExtensionCharges = response.body().getData().getLoanBasicDetails().getTotalExtensionCharges();
//                        totalExtensionDate = response.body().getData().getLoanBasicDetails().getTotalExtensionDays();
//                        penaltyAmount = response.body().getData().getLoanBasicDetails().getLoanPaneltyAmount();
//                        penaltyDays = response.body().getData().getLoanBasicDetails().getLoanPaneltyDays();
//                        penaltyApprovedStatus = response.body().getData().getLoanBasicDetails().getPaneltyStatus();
//                        moneyReturnStatus = response.body().getData().getLoanBasicDetails().getMoneyReturn();
                       setData();

                    }
                    else {
                        Toast.makeText(getActivity(), "Some error occurred from our end!", Toast.LENGTH_SHORT).show();
                        Log.i("tag","Some Error Occurred" + Objects.requireNonNull(getActivity()).getPackageName().getClass().getSimpleName());

                    }
                }

                @Override
                public void onFailure(Call<CurrentLoanDetailsGetModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            });
        } else {

            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        }
    }


    public void setData() {

        approvedStatusTextView.setText(approvedStatus);
        loanAmountTextView.setText("₹ "+loanAmount+" -/");
        loanClosureAmountTextView.setText("₹ "+loanClosureAmount+" -/");
        loanPeriodTextView.setText(loanPeriod + " days");
        loanAmountText.setText("₹ "+loanAmount+" -/");
        processingFeeTextView.setText("₹ "+processingFee+" -/");
        currentRoi.setText(roi+" %");
        int disbursal = loanAmount - processingFee;
        disbursalAmounttxt.setText("₹ "+String.valueOf(disbursal)+" -/");
        repaytext.setText(getloanrepayDays(paymentMode,loanDuration));
        emiamounttxt.setText("₹ "+Emiamount);
        messageTextView.setText(message);



//
//        messageTextView.setVisibility(View.VISIBLE);
//        messageTextHeading.setVisibility(View.VISIBLE);


        if (approvedStatus.equals("PAID")){
            approvedStatusTextView.setText(getString(R.string.loan_closed));
        }

        if (approvedStatus.equals("APPROVED")){
            approvedStatusTextView.setText("Loan Approved");
        }

        if (approvedStatus.equals("REJECTED")){
            approvedStatusTextView.setText("Loan Rejected");
        }

//        if (approvedStatus.equals("PAID")){
//            approvedStatusTextView.setVisibility(View.GONE);
//        }

        if(approvedStatus.equals("RUNNING")){
            approvedStatusTextView.setText("Loan Running");
//            linearLayout.setVisibility(View.VISIBLE);
        }

        if(!approvedStatus.equalsIgnoreCase("pending")){
            btn_paydetails.setVisibility(View.VISIBLE);
        }

        SimpleDateFormat DateFor ;
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        DateFor = new SimpleDateFormat("E, dd MMM yyyy");

        if (dueDate!=null){
            try {
                dueDateTextView.setText( DateFor.format(fromUser.parse(dueDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
        if (view.getId() == R.id.addtopup) {
            showtopupDailog();
        }
        if (view.getId() == R.id.viewStatment) {
            ((UserLoanDetailActivity) getActivity()).openPayLoanFragment();

        }if (view.getId() == R.id.backButtonImage) {
            getActivity().finish();

        }if(view.getId() == R.id.btn_paydetails){
            startActivity(new Intent(getActivity(), LoanPaymentDetailActivity.class).putExtra("LoanAppliedId",loanId));
        }
    }

    private void showtopupDailog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.extend_loan_dialog, viewGroup, false);
        builder.setView(dialogView);

        extensionChargesTextView = dialogView.findViewById(R.id.extensionChargesTextView);
        extensionDaysTextView = dialogView.findViewById(R.id.extensionDaysTextView);
        cancelButton = dialogView.findViewById(R.id.cancelButton);
        okButton = dialogView.findViewById(R.id.okButton);

        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
//        extensionChargesTextView.append(" ₹ " + extensionCharges);
//        extensionDaysTextView.append(" " + extensionDate + " " + "days");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okButton.setEnabled(false);
//                extendLoanApiHit();
                String url = ApiClient.BASE_URL+"webviews/loan_topup?="+token;
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                i.putExtra(WebViewActivity.WEB_URL, url);
                startActivity(i);
                alertDialog.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void extendLoanApiHit() {
        if (NetworkInfo.hasConnection(getActivity())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<ApplyExtensionModel> call = apiService.applyLoanExtensionApi(token, loanId);
            call.enqueue(new Callback<ApplyExtensionModel>() {
                @Override
                public void onResponse(Call<ApplyExtensionModel> call, Response<ApplyExtensionModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200) {
                        reload();
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "Some error occurred from our end!", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ApplyExtensionModel> call, Throwable t) {
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    private void reload() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

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


}