package www.atmanirbharbharat.com.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;

public class RegistrationScreenSlidePageFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    //declaration
    Spinner spinner;
    RadioButton radio_new_user;
    RadioButton radio_already_registered;
    Button nextButton;

    String userType;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_registration_screen_slide, container, false);
        init(viewGroup);
        setSpinner();
        return viewGroup;
    }


    //initialization is done here
    private void init(ViewGroup viewGroup) {
        spinner = viewGroup.findViewById(R.id.language_spinner);
        radio_new_user = viewGroup.findViewById(R.id.radio_new_user);
        radio_already_registered = viewGroup.findViewById(R.id.radio_already_registered);
        nextButton = viewGroup.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);

        spinner.setOnItemSelectedListener(this);


    }

    private void setSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.language_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    //on click listener
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.nextButton) {
            if (radio_new_user.isChecked()) {
                userType = "New User";
            }

            if (radio_already_registered.isChecked()) {
                userType = "Already Registered";
            }
        }

        if (view.getId() == R.id.nextButton) {
            ((RegisterSlideActivity) getActivity()).nextFragment(1);
        }


    }
}