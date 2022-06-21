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

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.LoanExtention.Activity_ListLoanExtention;
import www.atmanirbharbharat.com.LoanExtention.Apply_LoanExtention;
import www.atmanirbharbharat.com.LoanPaymentDetailActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.UserLoanDetailActivity;
import www.atmanirbharbharat.com.WebViewActivity;
import www.atmanirbharbharat.com.common.SharedPref;
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
    TextView btn_loanExtention;
    TextView btn_childview;
    TextView btn_parentloanbtn;
    private LinearLayout linearLayout;


    Button cancelButton;
    Button okButton;


    String approvedStatus;
    int loanAmount;
    String loanClosureAmount;
    String loanPeriod;
    int processingFee;
    String roi;
    String dueDate;
    String paymentMode;
    String loanDuration;
    String Emiamount;
    String message;
    public static final String LOANID = "AppliedLoanId";
    String loanId,childID,parentID;
    String token;

    SharedPreferences sharedPreferences;
    ProgressBar progress_circular;
    ConstraintLayout mainConstraintLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, " ");
        loanId = Objects.requireNonNull(getActivity()).getIntent().getStringExtra(LOANID);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_applied_loan_detail, container, false);

        init(view);

        getLoanDetailsListApi(loanId);

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
        btn_loanExtention = view.findViewById(R.id.btn_loanExtention);
        btn_childview = view.findViewById(R.id.btn_childview);
        btn_parentloanbtn = view.findViewById(R.id.btn_parentloanbtn);
        payLoanButton.setOnClickListener(this);
        extendLoanButton.setOnClickListener(this);
        backButtonImage.setOnClickListener(this);
        btn_paydetails.setOnClickListener(this);
        btn_loanExtention.setOnClickListener(this);
        btn_childview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childID!=null&& !childID.equals("")){
                    getLoanDetailsListApi(childID);
                }
            }
        });

        btn_parentloanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parentID!=null && !parentID.equals("")){
                    getLoanDetailsListApi(parentID);
                }
            }
        });
    }


    private void getLoanDetailsListApi(String loanId) {
        Log.i("arp","ApiId == "+ loanId);
        if (NetworkInfo.hasConnection(Objects.requireNonNull(getActivity()))) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<CurrentLoanDetailsGetModel> call = apiService.currentLoanDetailsApi(token, loanId);
            call.enqueue(new Callback<CurrentLoanDetailsGetModel>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<CurrentLoanDetailsGetModel> call, Response<CurrentLoanDetailsGetModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200) {
                        Log.i("arp","res: currentLoanDetail=> \n" + new Gson().toJson(response.body()));

                        progress_circular.setVisibility(View.GONE);
                        mainConstraintLayout.setVisibility(View.VISIBLE);

                        textView9.setText("Loan ID- "+response.body().getData().getLoanBasicDetails().getLoanAppliedId());
                        approvedStatus = response.body().getData().getLoanBasicDetails().getLoanStatus();
                        loanAmount = Integer.parseInt(response.body().getData().getLoanBasicDetails().getInitialamount());  /// replace Amount to initial amount
                        loanPeriod = response.body().getData().getLoanBasicDetails().getLoan_duration();
                        processingFee = response.body().getData().getLoanBasicDetails().getProcessingFee();
                        roi = String.valueOf(response.body().getData().getLoanBasicDetails().getROI());
                        paymentMode = response.body().getData().getLoanBasicDetails().getPayment_mode();
                        loanDuration = response.body().getData().getLoanBasicDetails().getLoan_duration();
                        Emiamount = response.body().getData().getLoanBasicDetails().getEmi_amount();
                        message = response.body().getData().getLoanBasicDetails().getRejectComment();
                        loanClosureAmount = response.body().getData().getLoanBasicDetails().getRemaining_balance();

                        if(response.body().getData().getLoanBasicDetails().getExtensionOf()!=null){
                            btn_parentloanbtn.setVisibility(View.VISIBLE);
                            parentID =  response.body().getData().getLoanBasicDetails().getExtensionOf();
                            Log.i("arp","getExtensionOf== "+parentID);
                        }else {
                            btn_parentloanbtn.setVisibility(View.GONE);
                        }

                        if(response.body().getData().getLoanBasicDetails().getChildLaId()!=null){
                            btn_childview.setVisibility(View.VISIBLE);
                            childID =  response.body().getData().getLoanBasicDetails().getChildLaId();
                            Log.i("arp","getChildLaId== "+childID);
                        }else {
                            btn_childview.setVisibility(View.GONE);
                        }

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
            btn_loanExtention.setVisibility(View.VISIBLE);
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
                        Objects.requireNonNull(getActivity()).onBackPressed();

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

        }if(view.getId() == R.id.btn_loanExtention){
            startActivity(new Intent(getActivity(), Activity_ListLoanExtention.class).putExtra("LoanAppliedId",loanId));
        }
        /*if(view.getId() == R.id.btn_parentloanbtn){
            getLoanDetailsListApi();

        }
        if(view.getId() == R.id.btn_childview){
            getLoanDetailsListApi();

        }*/
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