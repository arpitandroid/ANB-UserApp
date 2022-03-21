package www.atmanirbharbharat.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import www.atmanirbharbharat.com.homeScreen.fragments.homeFragment.SelectLoanFragment;

public class LoanActivity extends AppCompatActivity {


    SelectLoanFragment selectLoanFragment = new SelectLoanFragment();
//    BankAccountLoanFragment bankAccountLoanFragment = new BankAccountLoanFragment();
//    CompanyInformationLoanFragment companyInformationLoanFragment = new CompanyInformationLoanFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentFrameLayout, selectLoanFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}