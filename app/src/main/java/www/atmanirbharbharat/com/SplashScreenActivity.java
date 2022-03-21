package www.atmanirbharbharat.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;


public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;


    private Boolean logincheck = false;
    private int defaultFragmentNumber = 100;

    //after 2000ms the screen will timeout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);


        sharedpreferences = getSharedPreferences(SharedPref.SHARED_PREFS, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        logincheck = sharedpreferences.getBoolean(SharedPref.LOGIN_CHECK, false);
        defaultFragmentNumber = sharedpreferences.getInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 100);

        Glide.with(SplashScreenActivity.this).load(R.drawable.splashimage).into((ImageView) findViewById(R.id.img_splash).findViewById(R.id.img_splash));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (logincheck) {
                    if (defaultFragmentNumber == 100) {
                        Intent i = new Intent(SplashScreenActivity.this,
                                HomeScreenActivity.class);
                        //Intent is used to switch from one activity to another.

                        startActivity(i);
                        //invoke the SecondActivity.

                        finish();
                    } else {
                        Intent i = new Intent(SplashScreenActivity.this,
                                RegisterSlideActivity.class);
                        //Intent is used to switch from one activity to another.

                        startActivity(i);
                        //invoke the SecondActivity.

                        finish();

                    }
                    //the current activity will get finished.
                } else {
                    Intent i = new Intent(SplashScreenActivity.this,
                            RegisterSlideActivity.class);
                    //Intent is used to switch from one activity to another.

                    startActivity(i);
                    //invoke the SecondActivity.

                    finish();
                }


            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}