package www.atmanirbharbharat.com;

import static www.atmanirbharbharat.com.util.AppConstant.hideLoading;
import static www.atmanirbharbharat.com.util.AppConstant.showLoading;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.homeScreen.activity.Activity_CustomLoan;
import www.atmanirbharbharat.com.models.ApplyLoanModel;
import www.atmanirbharbharat.com.models.Res_ROI;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

public class ManualLoadDetail_Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private String token ;

    private Button Btn_applyforloan;
    private String PayMode ;
    private String Lic_claim= "yes";

    String Principleamnt,duration;

    private TextView loanAmountTextView,loanPeriodTextView,loanAmountText,processingFeeTextView,currentROI,
            paymodeTextview,emicountTextview,repaytextview, bouncingchrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_load_detail);
        
        findView();

        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, " ");

        Principleamnt = getIntent().getStringExtra("amnt");
        duration = getIntent().getStringExtra("duration");
        PayMode = getIntent().getStringExtra("paymode");
        Lic_claim = getIntent().getStringExtra("licclaim");

        Log.i("arp","loanintent=== "+Principleamnt+duration+PayMode+Lic_claim);

  /*      rate_of_interest = getIntent().getDoubleExtra("roi",0.0);
        process_fee_percent = getIntent().getDoubleExtra("pfp",0.0);
        bouncing_charges_percent = getIntent().getDoubleExtra("bcp",0.0);
*/
        _GetManual_ROI();

        Btn_applyforloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           _SubmitManual_Loan(Principleamnt,duration);

            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    } // oncreate

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ManualLoadDetail_Activity.this,Activity_CustomLoan.class));
        finish();
    }

    private void findView() {
        Btn_applyforloan = findViewById(R.id.Btn_applyforloan);

        loanAmountTextView = findViewById(R.id.loanAmountTextView);
        loanPeriodTextView = findViewById(R.id.loanPeriodTextView);
        loanAmountText = findViewById(R.id.loanAmountText);
        processingFeeTextView = findViewById(R.id.processingFeeTextView);
        currentROI = findViewById(R.id.currentROI);
        paymodeTextview = findViewById(R.id.paymodeTextview);
        emicountTextview = findViewById(R.id.emicountTextview);
        repaytextview = findViewById(R.id.repaytextview);
        bouncingchrg = findViewById(R.id.bouncingchrg);

    }

    private void _SubmitManual_Loan(String amnt, String Duration) {
        if (NetworkInfo.hasConnection(this)) {

            showLoading(ManualLoadDetail_Activity.this);

            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<ApplyLoanModel> call = apiService.ApplyManualLoan(token,amnt,Duration,PayMode,Lic_claim);
            call.enqueue(new Callback<ApplyLoanModel>() {
                @Override
                public void onResponse(Call<ApplyLoanModel> call, Response<ApplyLoanModel> response) {
                    hideLoading();

                    ApplyLoanModel res  = response.body();
                    if (res != null) {
                        Log.i("arp","ApplyManualLoan= "+ new Gson().toJson(res));
                        if (res.getStatus()==200) {
                            Toast.makeText(ManualLoadDetail_Activity.this, res.getMessage(), Toast.LENGTH_SHORT).show();

                            finish();

                        } else if(res.getStatus()==201){
                            Toast.makeText(ManualLoadDetail_Activity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }
                @Override
                public void onFailure(Call<ApplyLoanModel> call, Throwable t) {
                    Log.i("arp","ApplyManualLoan= "+ t);
                    //       Toast.makeText(this, "Error ! please try again", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ManualLoadDetail_Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    hideLoading();
                }
            });
        } else {
            showAlertDialog();
            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        }

    }

    private void _GetManual_ROI() {

        if (NetworkInfo.hasConnection(this)) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<Res_ROI> call = apiService.Get_ROI_ManualAmnt(token,Principleamnt,duration,PayMode,Lic_claim);
            call.enqueue(new Callback<Res_ROI>() {
                @Override
                public void onResponse(@NotNull Call<Res_ROI> call, @NotNull Response<Res_ROI> response) {
                    Res_ROI res =  response.body();
                    Log.i("arp","Res_ROI= " + new Gson().toJson(res));

                    if (res != null) {
                        if (response.body() != null && res.getStatus() == 200) {

                            _SetData(res);


                        } else if (res.getStatus() == 201) {
                            Toast.makeText(ManualLoadDetail_Activity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(@NotNull Call<Res_ROI> call, @NotNull Throwable t) {
                    Log.i("arp","Res_ROI= "+ t);
                    Toast.makeText(ManualLoadDetail_Activity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {

            showAlertDialog();
            Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        }
    }


    @SuppressLint("SetTextI18n")
    private void _SetData(Res_ROI res) {

/*        {"data":{"amount":2000,"bouncing_charges":20,"bouncing_charges_percent":"1.00","disbursal_amount":1900,
                "emi_amount":22,"loan_closer_amount":2160,"loan_duration":100,"payment_mode":"Monthly","process_fee_percent":"5.00",
                "processing_fee":100,"rate_of_interest":"8.00","total_emi_count":100},"error":false,"status":200}  */

        loanAmountTextView.setText(res.getData().getAmount() +" INR");
        loanPeriodTextView.setText(res.getData().getLoanDuration()+" days");
        loanAmountText.setText(res.getData().getAmount() +" INR");
        processingFeeTextView.setText(res.getData().getProcessingFee()+" INR");
        currentROI.setText(res.getData().getRateOfInterest());
        paymodeTextview.setText(res.getData().getPaymentMode());
        bouncingchrg.setText(res.getData().getBouncingChargesPercent()+"");

        emicountTextview.setText(res.getData().getTotalEmiCount().toString() + "( "+res.getData().getEmiAmount().toString()+" INR"+")");
        repaytextview.setText(res.getData().getDisbursalAmount()+" INR");

    }


    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ManualLoadDetail_Activity.this);
        builder.setMessage("You need to make sure your device is conected to Internet")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }
}