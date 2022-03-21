package www.atmanirbharbharat.com.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import static android.app.Activity.RESULT_OK;

public class AuthDetailsUtil implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int PHONE_NUMBER_HINT = 1011;
    public static final int EMAIL_HINT = 1012;
    private static final int RC_SIGN_IN = 9001;
    private static GoogleSignInClient mSignInClient;

        public static void readNumber(Context context) {
        TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        @SuppressLint("MissingPermission") String mPhoneNumber = tMgr.getLine1Number();
    }
    public static void requestGoogleHint(Context context) {
        GoogleSignInOptions options =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .build();
        mSignInClient = GoogleSignIn.getClient(context, options);
        Intent intent = mSignInClient.getSignInIntent();
        ((Activity)context).startActivityForResult(intent, RC_SIGN_IN);
    }
    public static void requestPhoneHint(Context context,GoogleApiClient googleApiClient) throws IntentSender.SendIntentException {
        HintRequest hintRequest = new HintRequest.Builder()
                .setHintPickerConfig(new CredentialPickerConfig.Builder()
                .setShowCancelButton(true).build())
                .setPhoneNumberIdentifierSupported(true)
                .build();
        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(googleApiClient,hintRequest);
        ((Activity)context).startIntentSenderForResult(intent.getIntentSender(),
                PHONE_NUMBER_HINT, null, 0, 0, 0,new Bundle());
    }
    public static void requestEmailHint(Context context) throws IntentSender.SendIntentException {
        HintRequest hintRequest = new HintRequest.Builder()
                .setEmailAddressIdentifierSupported(true)
                .build();
        PendingIntent intent = Credentials.getClient(context).getHintPickerIntent(hintRequest);
        ((Activity)context).startIntentSenderForResult(intent.getIntentSender(),
                EMAIL_HINT, null, 0, 0, 0);
    }
    public static String extractNumber(int requestCode, int resultCode, Intent data){
        if (requestCode == PHONE_NUMBER_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                // credential.getId();  <-- will need to process phone number string
                if (credential != null) {
                    return credential.getId().replaceFirst("\\+91", "");
                }
            }
        }
        return "";
    }
    public static String extractEmail(int requestCode, int resultCode, Intent data){
        if (requestCode == EMAIL_HINT) {
            if (resultCode == RESULT_OK) {
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                // credential.getId();  <-- will need to process phone number string
                if (credential != null) {
                    return credential.getId();
                }
            }
        }
        return "";
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
