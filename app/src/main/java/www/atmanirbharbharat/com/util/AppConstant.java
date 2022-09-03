package www.atmanirbharbharat.com.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import www.atmanirbharbharat.com.R;

public class AppConstant {

    private static Dialog dialogbox;

    public static String getDeviceId(Context context){
       String deviceId;
       try{
           deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
       }
       catch (Exception ex){
           deviceId = "ErrorUtil fetching device id";
       }


       return deviceId;
    }

    public static void showLoading(Context context) {
        dialogbox = new Dialog(context);
        dialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogbox.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogbox.setCancelable(false);
        dialogbox.setContentView(R.layout.circulerprogress_layout);
        dialogbox.show();
    }

    public static void hideLoading() {
        if (dialogbox != null && dialogbox.isShowing()) {
            dialogbox.dismiss();
            // Log.e("dismiss","true");
        } else {
             Log.e("dismiss","false");
        }
    }

//    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
//            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validateEmail(String emailStr) {
        if(!emailStr.equals("")){
            String regex = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(emailStr);

            Log.i("arp","matcher-value==>"+matcher.matches());
            Log.i("arp","email ==>"+emailStr);
            return matcher.matches();
        }else {
            return false;
        }
    }

}
