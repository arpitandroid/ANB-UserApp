package www.atmanirbharbharat.com.dialogfragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import www.atmanirbharbharat.com.R;

import static www.atmanirbharbharat.com.fragments.BasicDetailsFragment.modelinfoData;

public class nomineedetails extends DialogFragment {
    EditText phoneNumberEditText ;
    EditText relationEditText;
    Button nextButton_ND;
    private Context context ;

    public nomineedetails(FragmentActivity activity) {
        // Required empty public constructor
        this.context = activity ;
    }


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable final ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.dialog_nominee_details, container, false);

         phoneNumberEditText = rootView.findViewById(R.id.phoneNumberEditText);
         relationEditText = rootView.findViewById(R.id.relationEditText);
        nextButton_ND = rootView.findViewById(R.id.nextButton_ND);

        rootView.findViewById(R.id.nextButton_ND).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String phoneNumber = phoneNumberEditText.getText().toString();
               String  relation = relationEditText.getText().toString();

                if (phoneNumber.length() < 10) {
                    Toast.makeText(getActivity(), "contact Number one is not correct", Toast.LENGTH_SHORT).show();
                    nextButton_ND.setEnabled(true);
                }  else if (relation.length() <= 0) {
                    Toast.makeText(getActivity(), "Relation with contact one cannot be empty", Toast.LENGTH_SHORT).show();
                    nextButton_ND.setEnabled(true);
                }else {
                    modelinfoData.setEmergencyContact1(phoneNumber);
                    modelinfoData.setEmergencyContactRelation1(relation);

                      dismiss();
                      FragmentManager fm = requireActivity().getSupportFragmentManager();
                        Bankdetails newFragment = new Bankdetails(context);
                        newFragment.show(fm, "bank details");
                    Log.i("arp","nomineeData= "+ new Gson().toJson(modelinfoData));
                }

            }
        });

        return rootView;
    }// oncreate view

    public int getTheme(){
        return R.style.FullScreenDialog;
    }
}
