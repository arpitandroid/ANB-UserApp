package www.atmanirbharbharat.com;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import www.atmanirbharbharat.com.util.NetworkInfo;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {
    WebView webView;
    ImageButton backButton;
    String url;

    public static final String WEB_URL="webUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        url=getIntent().getStringExtra(WEB_URL);

        init();
        if (NetworkInfo.hasConnection(this)) {
            showWebView();
        } else {
            showAlertDialog();
        }
    }

    private void init() {
        webView = findViewById(R.id.webview);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

    }

    private void showWebView() {



        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setSupportZoom(true);
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);


        webView.loadUrl(url);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                webView.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }


        });
    }

    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You need to make sure your device is conected to Internet")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        onBackPressed();

                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.backButton){
            onBackPressed();
        }
    }
}

