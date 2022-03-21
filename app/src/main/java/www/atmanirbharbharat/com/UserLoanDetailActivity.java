package www.atmanirbharbharat.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import www.atmanirbharbharat.com.homeScreen.fragments.loansFragment.AppliedLoanDetailFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.loansFragment.PayLoanFragment;

public class UserLoanDetailActivity extends AppCompatActivity {

    public static final String LOANDETAILS = "loanDetails";
    AppliedLoanDetailFragment appliedLoanDetailFragment = new AppliedLoanDetailFragment();
    PayLoanFragment payLoanFragment = new PayLoanFragment();

    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loan_detail);

        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentFrameLayout, appliedLoanDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }

    public void openPayLoanFragment() {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentFrameLayout, payLoanFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        // This will get you total fragment in the backStack
        int count = getFragmentManager().getBackStackEntryCount();
        switch (count) {
            case 0:
                finish();
                break;
            case 1:
                // handle back press of fragment one
                break;
            case 2:
                // and so on for fragment 2 etc
                break;
            default:
                getFragmentManager().popBackStack();
                break;
        }
    }
}