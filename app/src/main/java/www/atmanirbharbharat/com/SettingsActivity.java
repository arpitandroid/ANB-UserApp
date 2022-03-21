package www.atmanirbharbharat.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.util.ApiClient;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    CardView termsAndConditionCardView;
    CardView contactCardView;
    CardView feedbackCardView;

    ImageButton backImageButton;
    Button logoutButton;

    Button editButton;
    Button cancelButton;
    TextView headingTextView;
    TextView bodyTextView;
    String token;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        termsAndConditionCardView = findViewById(R.id.termsAndConditionCardView);
        contactCardView = findViewById(R.id.contactCardView);
        feedbackCardView = findViewById(R.id.feedbackCardView);
        backImageButton = findViewById(R.id.backImageButton);
        logoutButton = findViewById(R.id.logoutButton);
        editButton = findViewById(R.id.editButton);
        cancelButton = findViewById(R.id.cancelButton);
        headingTextView = findViewById(R.id.headingTextView);
        bodyTextView = findViewById(R.id.bodyTextView);
        termsAndConditionCardView.setOnClickListener(this);
        contactCardView.setOnClickListener(this);
        feedbackCardView.setOnClickListener(this);
        backImageButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, null);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.termsAndConditionCardView) {
            String url = ApiClient.BASE_URL+"terms-and-conditions";
            Intent i = new Intent(this, WebViewActivity.class);
            i.putExtra(WebViewActivity.WEB_URL, url);
            startActivity(i);
        }
        if (view.getId() == R.id.contactCardView) {
            String url = ApiClient.BASE_URL+"webviews/contact?token="+token;
            Intent i = new Intent(this, WebViewActivity.class);
            i.putExtra(WebViewActivity.WEB_URL, url);
            startActivity(i);

        }if (view.getId() == R.id.feedbackCardView) {
            String url = ApiClient.BASE_URL+"webviews/feedback?token="+token;
            Intent i = new Intent(this, WebViewActivity.class);
            i.putExtra(WebViewActivity.WEB_URL, url);
            startActivity(i);
        }
        if (view.getId() == R.id.backImageButton) {
            onBackPressed();
        }
        if (view.getId() == R.id.logoutButton) {
            openDialog();
        }
    }

    private void openDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.home_screen_dialog1, viewGroup, false);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        editButton = dialogView.findViewById(R.id.editButton);
        cancelButton = dialogView.findViewById(R.id.cancelButton);
        headingTextView = dialogView.findViewById(R.id.headingTextView);
        bodyTextView = dialogView.findViewById(R.id.bodyTextView);

        headingTextView.setText(getString(R.string.do_you_want_logout));
        bodyTextView.setText(getString(R.string.logging_out_will));

        editButton.setText(getString(R.string.ok));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();


                Intent intent = new Intent(SettingsActivity.this, RegisterSlideActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


    }
}