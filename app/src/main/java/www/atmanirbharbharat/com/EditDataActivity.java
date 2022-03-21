package www.atmanirbharbharat.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.fragments.BankAccountLoanFragment;
import www.atmanirbharbharat.com.fragments.CompanyInformationLoanFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.homeFragment.PaySlipImageHomeScreenFragment;
import www.atmanirbharbharat.com.fragments.AadharFrontScanFragment;
import www.atmanirbharbharat.com.fragments.AadharBackScanFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.homeFragment.PancardFragment;
import www.atmanirbharbharat.com.fragments.BasicDetailsFragment;
import www.atmanirbharbharat.com.fragments.RegistrationScreenSlidePageFragment9;

public class EditDataActivity extends AppCompatActivity {
    List<Fragment> list;

    private FragmentManager manager;
    SharedPreferences sharedPreferences;

    Intent intent;
    int fragmentNo;
    Boolean cameFromEdit=false;

    AadharBackScanFragment adharBackFragment = new AadharBackScanFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        list = new ArrayList<>();
        list.add(new PancardFragment());
        list.add(new PaySlipImageHomeScreenFragment());
        list.add(new AadharFrontScanFragment());
        list.add(new BasicDetailsFragment());
        list.add(new RegistrationScreenSlidePageFragment9());
        list.add(new BankAccountLoanFragment());



        init();
        intent = getIntent();
        fragmentNo = intent.getIntExtra("fragmentNo",0);
        cameFromEdit = intent.getBooleanExtra("cameFromEdit",false);
        setFragment();

    }


    private void init() {
        manager = getSupportFragmentManager();
        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
    }

    private void setFragment() {

            manager.beginTransaction()
                    .replace(R.id.editDataFrameLayout,list.get(fragmentNo))
                    .commit();


    }

    public void openCompanyFragment(){
        manager.beginTransaction()
                .replace(R.id.editDataFrameLayout,new CompanyInformationLoanFragment())
                .commit();
    }

    public void openEditBankDetailsFragment(){
        manager.beginTransaction()
                .replace(R.id.editDataFrameLayout,new BankAccountLoanFragment())
                .commit();
    }

    public void openAdharBackSide(){
        manager.beginTransaction()
                .replace(R.id.editDataFrameLayout,adharBackFragment)
                .commit();
    }
}