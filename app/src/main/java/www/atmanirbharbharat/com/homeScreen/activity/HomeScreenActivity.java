package www.atmanirbharbharat.com.homeScreen.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.homeScreen.fragments.AppliedLoanHomeScreenFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.DiscoverHomeScreenFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.MainHomeScreenFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.MeHomeScreenFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.MyAppliedLoans_Fragment;

public class HomeScreenActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    private MainHomeScreenFragment mainHomeScreenFragment = new MainHomeScreenFragment();
    private AppliedLoanHomeScreenFragment authentificationHomeScreenFragment = new AppliedLoanHomeScreenFragment();
    private DiscoverHomeScreenFragment discoverHomeScreenFragment = new DiscoverHomeScreenFragment();
    private MeHomeScreenFragment meHomeScreenFragment = new MeHomeScreenFragment();
  //  private MyAppliedLoans_Fragment myAppliedLoans_Fragment = new MyAppliedLoans_Fragment();

//    SwipeRefreshLayout swiperefresh;

    private Fragment activeFragment = null;
    private FragmentManager manager;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        init();

        setUpBottomNavigation();
        addAllFragmentOnce();
    }

    //    ------------------------views are ititialsed here-----------------------
    private void init() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        swiperefresh = findViewById(R.id.swiperefresh);
//        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                recreate();
//                swiperefresh.setRefreshing(false);
//            }
//        });
    }

    //bottom navigatio view setup
    private void setUpBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(HomeScreenActivity.this);
        manager = getSupportFragmentManager();
        activeFragment = mainHomeScreenFragment;
    }

    private void addAllFragmentOnce() {

        manager.beginTransaction()
                .add(R.id.parentLayout, activeFragment)
                .commit();

        manager.beginTransaction()
                .add(R.id.parentLayout, authentificationHomeScreenFragment)
                .hide(authentificationHomeScreenFragment)
                .commit();

        manager.beginTransaction()
                .add(R.id.parentLayout, discoverHomeScreenFragment)
                .hide(discoverHomeScreenFragment)
                .commit();

        manager.beginTransaction()
                .add(R.id.parentLayout, meHomeScreenFragment)
                .hide(meHomeScreenFragment)
                .commit();
/*
        manager.beginTransaction()
                .add(R.id.parentLayout, myAppliedLoans_Fragment)
                .hide(myAppliedLoans_Fragment)
                .commit();*/

    }

    //-------show fragment based on selection item id---------------
    private void showHideFragment(Fragment fragment) {
        manager.beginTransaction()
                .hide(activeFragment)
                .show(fragment)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.id1:
                clearBackStack();
                showHideFragment(mainHomeScreenFragment);
                activeFragment = mainHomeScreenFragment;
                break;

            case R.id.id2:  // loan
                clearBackStack();
                showHideFragment(authentificationHomeScreenFragment);
                activeFragment = authentificationHomeScreenFragment;
                break;

            case R.id.id3: // payment
                clearBackStack();
                showHideFragment(discoverHomeScreenFragment);
                activeFragment = discoverHomeScreenFragment;
                break;

            case R.id.id4:  // profile
                clearBackStack();
                showHideFragment(meHomeScreenFragment);
                activeFragment = meHomeScreenFragment;
                break;

         /*   case R.id.id5: // loan history
                clearBackStack();
                showHideFragment(myAppliedLoans_Fragment);
                activeFragment = myAppliedLoans_Fragment;
                break;*/
        }
        return true;
    }

    //    --------------clear the fragments from the stack-------------------
    private void clearBackStack() {
        FragmentManager fm = this.getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        clearBackStack();
        finish();
    }


}