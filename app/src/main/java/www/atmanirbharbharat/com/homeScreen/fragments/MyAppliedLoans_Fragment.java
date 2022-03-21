package www.atmanirbharbharat.com.homeScreen.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;

import static android.content.Context.MODE_PRIVATE;

public class MyAppliedLoans_Fragment extends Fragment {

    private SharedPreferences sharedPreferences;
    private  String token;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);

        token = sharedPreferences.getString(SharedPref.TOKEN, null);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_loadpaidhistory, container, false);
        //init(viewGroup);
        //showLoanPaidDataApi();
        return viewGroup;
    }

}
