package www.atmanirbharbharat.com.LoanExtention;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.ApplyExtensionModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

public class Apply_LoanExtention extends AppCompatActivity {

    private EditText AmtEditText, ET_Loanduration ;
    private String PayMode ;
     String Lic_claim= "yes";
     private String LoanAppliedId;
    private SharedPreferences sharedPreferences;
    private String token ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_loan);

        AmtEditText = findViewById(R.id.AmtEditText);
        ET_Loanduration = findViewById(R.id.ET_Loanduration);
        TextView titleNotification = findViewById(R.id.titleNotification);
        LinearLayout lay_licAmnt = findViewById(R.id.lay_licAmnt);
        lay_licAmnt.setVisibility(View.GONE);

        // Intent from AppliedLoanDetailFragment ----
        LoanAppliedId = getIntent().getStringExtra("LoanAppliedId");
        Log.i("arp", "Ext : LoanAppliedId= "+LoanAppliedId);
        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);

        titleNotification.setText("Loan Extention\n"+"Your Loan ID :"+LoanAppliedId);

        token = sharedPreferences.getString(SharedPref.TOKEN, "");

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Button bnt = findViewById(R.id.next_ROI);
        bnt.setText("apply");
       findViewById(R.id.next_ROI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AmtEditText.getText().toString().trim().length()==0){
                    Toast.makeText(Apply_LoanExtention.this, "Please enter amount", Toast.LENGTH_SHORT).show();

                } else if(ET_Loanduration.getText().toString().trim().length()==0){
                    Toast.makeText(Apply_LoanExtention.this, "Please enter Duration", Toast.LENGTH_SHORT).show();

                }else {

                    extendLoanApi();
                        /* startActivity(new Intent(Activity_LoanExtention.this,ManualLoadDetail_Activity.class)
                                .putExtra("amnt",AmtEditText.getText().toString().trim())
                                .putExtra("duration",ET_Loanduration.getText().toString())
                                .putExtra("paymode",PayMode)
                                .putExtra("licclaim",Lic_claim)
                               .putExtra("roi",rate_of_interest)
                                .putExtra("pfp",process_fee_percent)
                                .putExtra("bcp",bouncing_charges_percent));*/

                     //   finish();
                 }
               }
           });


        Spinner spinner_Paymode = findViewById(R.id.spinner_Paymode);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Payment_Mode, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner_Paymode.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);
        spinner_Paymode.setAdapter(adapter);
        spinner_Paymode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                 PayMode = adapterView.getItemAtPosition(position).toString();
              //  Toast.makeText(Activity_CustomLoan.this, PayMode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }  // OnCreate ---



    @Override
    public void onBackPressed() {
        super.onBackPressed();
       finish();
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.RB_yes:
                if (checked)
                    Lic_claim = "yes";
                break;
            case R.id.RB_no:
                if (checked)
                    Lic_claim = "no";
                break;
        }
    }


    private void extendLoanApi() {
        if (NetworkInfo.hasConnection(Apply_LoanExtention.this)) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<ApplyExtensionModel> call = apiService.applyLoanExtensionApi(token, LoanAppliedId,AmtEditText.getText().toString().trim(),
                    ET_Loanduration.getText().toString(),PayMode);
            call.enqueue(new Callback<ApplyExtensionModel>() {
                @Override
                public void onResponse(Call<ApplyExtensionModel> call, Response<ApplyExtensionModel> response) {
                    if (response.body() != null) {
                        Log.i("arp","res: extendLoanApi=> "+new Gson().toJson(response.body()));
                        if(response.body().getStatus() == 200){
                            Toast.makeText(Apply_LoanExtention.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Apply_LoanExtention.this, Activity_ListLoanExtention.class).putExtra("LoanAppliedId",LoanAppliedId));
                            finish();
                        }else {
                            Toast.makeText(Apply_LoanExtention.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Apply_LoanExtention.this, "Some error occurred Try Again..", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApplyExtensionModel> call, Throwable t) {
                    Toast.makeText(Apply_LoanExtention.this, "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Apply_LoanExtention.this);
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