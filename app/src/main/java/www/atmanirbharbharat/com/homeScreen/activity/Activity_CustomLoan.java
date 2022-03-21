package www.atmanirbharbharat.com.homeScreen.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import www.atmanirbharbharat.com.ManualLoadDetail_Activity;
import www.atmanirbharbharat.com.R;

public class Activity_CustomLoan extends AppCompatActivity {

    private EditText AmtEditText, ET_Loanduration ;
    private String PayMode ;
     String Lic_claim= "yes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_loan);

        AmtEditText = findViewById(R.id.AmtEditText);
        ET_Loanduration = findViewById(R.id.ET_Loanduration);

       /* sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(SharedPref.TOKEN, " ");*/

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

       findViewById(R.id.next_ROI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(AmtEditText.getText().toString().trim().length()==0){
                    Toast.makeText(Activity_CustomLoan.this, "Please enter amount", Toast.LENGTH_SHORT).show();

                } else if(ET_Loanduration.getText().toString().trim().length()==0){
                    Toast.makeText(Activity_CustomLoan.this, "Please enter Duration", Toast.LENGTH_SHORT).show();

                }else {

                        startActivity(new Intent(Activity_CustomLoan.this,ManualLoadDetail_Activity.class)
                                .putExtra("amnt",AmtEditText.getText().toString().trim())
                                .putExtra("duration",ET_Loanduration.getText().toString())
                                .putExtra("paymode",PayMode)
                                .putExtra("licclaim",Lic_claim)
                               /* .putExtra("roi",rate_of_interest)
                                .putExtra("pfp",process_fee_percent)
                                .putExtra("bcp",bouncing_charges_percent)*/);

                        finish();
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

    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_CustomLoan.this);
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