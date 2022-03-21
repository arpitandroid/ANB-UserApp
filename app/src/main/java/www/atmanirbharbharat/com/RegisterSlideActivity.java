package www.atmanirbharbharat.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.fragments.PhoneNumberEnterFragment;
import www.atmanirbharbharat.com.fragments.OTPVerifyFragment;
import www.atmanirbharbharat.com.fragments.AadharFrontScanFragment;
import www.atmanirbharbharat.com.fragments.AadharBackScanFragment;
import www.atmanirbharbharat.com.fragments.BasicDetailsFragment;

public class RegisterSlideActivity extends AppCompatActivity {

    List<Fragment> list;

    private FragmentManager manager;

    SharedPreferences sharedPreferences;

    int defaultFragmentAfterOpening;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_slide);

        list = new ArrayList<>();
 //       list.add(new RegistrationScreenSlidePageFragment());
        list.add(new PhoneNumberEnterFragment());
        list.add(new OTPVerifyFragment());
        list.add(new BasicDetailsFragment());
//        list.add(new CompanyInformationLoanFragment());
        list.add(new AadharFrontScanFragment());
        list.add(new AadharBackScanFragment());
//        list.add(new RegistrationScreenSlidePageFragment10());
        //initialising is done here
        init();
        setFragment();
    }

    private void setFragment() {
        if (defaultFragmentAfterOpening!=100){
            manager.beginTransaction()
                    .replace(R.id.registerFrameLayout,list.get(defaultFragmentAfterOpening))
                    .commit();
        }else
            manager.beginTransaction()
                    .replace(R.id.registerFrameLayout,list.get(0))
                    .commit();

    }

    //initialise a viewPager2 and a pagerAdapter
    private void init() {
        manager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS,MODE_PRIVATE);
        defaultFragmentAfterOpening = sharedPreferences.getInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING,100);
    }


    public void nextFragment(int position){

        manager.beginTransaction()
                .replace(R.id.registerFrameLayout,list.get(position))
                .commit();

    }


}