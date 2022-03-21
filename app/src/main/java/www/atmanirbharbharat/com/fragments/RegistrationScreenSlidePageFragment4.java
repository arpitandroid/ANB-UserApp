package www.atmanirbharbharat.com.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;

public class RegistrationScreenSlidePageFragment4 extends Fragment implements View.OnClickListener {

    Button scanDocumentButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_registration_screen_slide4, container, false);
        init(viewGroup);
        return viewGroup;
    }

    private void init(ViewGroup viewGroup) {
        scanDocumentButton = viewGroup.findViewById(R.id.scanDocumentButton);
        scanDocumentButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.scanDocumentButton) {
            ((RegisterSlideActivity) getActivity()).nextFragment(4);
        }
    }

}