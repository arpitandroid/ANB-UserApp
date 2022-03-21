package www.atmanirbharbharat.com.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.EditDataActivity;
import www.atmanirbharbharat.com.dialogfragments.nomineedetails;
import www.atmanirbharbharat.com.homeScreen.activity.HomeScreenActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.RegisterSlideActivity;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.PersonalInformationModel;
import www.atmanirbharbharat.com.models.User;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;

public class BasicDetailsFragment extends Fragment implements View.OnClickListener {

    ViewGroup viewGroup;
    Button nextButton;


    EditText emailEditText;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText streetAddressEditText;
    EditText cityEditText;
    //    EditText countryEditText;
    EditText dobEditText;
    EditText adhaarNoEditText;

    EditText phoneNumberEditText;
    EditText relationEditText;

    EditText bankNameEditText;
    EditText accountNumberEditText;
    EditText confirmaccountNumberEditText;
    EditText namInBankAcctEditText;
    EditText ifscCodeEditText;

    TextView scanItAgainTv;
    TextView termsAndConditionTextView;

    ImageView frontAdhaarImage;
    ImageView backAdhaarImage;

    LinearLayout adharCardLinearLayout;

    String email = "";
    String firstName;
    String lastName;
    String streetAddress;
    String city;
    String country;
    String dob;
    String adhaarNumber;
    String token;
    String basicDetailsApprovalStatus = "PENDING";
    String bankApprovalStatus = "PENDING";
    String emergencyContactStatus = "PENDING";
    String profileCompleteStatus = "COMPLETE";

    String phoneNumber;
    String relation;


    String bankName;
    String accountNumber;
    String confirmaccountNumber;
    String namInBankAcct;
    String ifscCode;

    public static PersonalInformationModel.Data modelinfoData;

    private static final String TOKEN = "token";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String ADDRESS = "address";
    private static final String CITY = "city";
    private static final String COUNTRY = "country";
    private static final String DOB = "dob";
    private static final String ADHAAR_NO = "aadhar_no";
    private static final String BASIC_DETAILS_APPROVAL_STATUS = "bda_status";
    private static final String PHONE1 = "ec1";
    private static final String RELATION1 = "ecr1";
    private static final String EMERGENCY_CONTACT_STATUS = "ecv_status";
    private static final String PROFILE_COMPLETION_STATUS = "p_c_status";
    private static final String BANK_APPROVAL_STATUS = "ba_status";
    private static final String ACCOUNT_HOLDER_NAME = "acc_holder_name";
    private static final String IFSC_CODE = "ifcs_code";
    private static final String ACCOUNT_NUMBER = "acc_no";
    private static final String BANK_NAME = "bank_name";

    AppCompatCheckBox personalDetailsCheckBox;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private HashMap<String, String> mParam;

    final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;


    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_basic_details, container, false);
        init(viewGroup);

        if (getContext() instanceof EditDataActivity) {
            adharCardLinearLayout.setVisibility(View.GONE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(SharedPref.HOMESCREENDATA, "");
            user = gson.fromJson(json, User.class);
            setDataToTextView();
        } else {
            setAdhaarImages();

        }
        initiateDatePickerDialog();
        return viewGroup;
    }

    private void setDataToTextView() {
        emailEditText.setText(user.getEmail());
        firstNameEditText.setText(user.getFirstName());
        lastNameEditText.setText(user.getLastName());
        streetAddressEditText.setText(user.getAddress());
        cityEditText.setText(user.getCity());
        dobEditText.setText(user.getDob());
        adhaarNoEditText.setText(user.getAadharNo());

/*        phoneNumberEditText.setText(user.getEmergencyContact1());
        relationEditText.setText(user.getEmergencyContactRelation1());
        bankNameEditText.setText(user.getBankName());
        accountNumberEditText.setText(user.getAccountNumber());
        confirmaccountNumberEditText.setText(user.getAccountNumber());
        namInBankAcctEditText.setText(user.getAccHolderName());
        ifscCodeEditText.setText(user.getIFSCCode());*/
    }

    private void setAdhaarImages() {
        new RetreiveImageTask(frontAdhaarImage)
                .execute(sharedPreferences.getString(SharedPref.ADHAR_FRONT_URL, "@null"));

        new RetreiveImageTask(backAdhaarImage)
                .execute(sharedPreferences.getString(SharedPref.ADHAR_BACK_URL, "@null"));
    }

    private void initiateDatePickerDialog() {
        myCalendar.add(Calendar.YEAR, -21);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobEditText.setText(sdf.format(myCalendar.getTime()));
    }

    private void init(ViewGroup viewGroup) {

        modelinfoData =  new PersonalInformationModel().new Data();

        nextButton = viewGroup.findViewById(R.id.nextButton);
        emailEditText = viewGroup.findViewById(R.id.emailEditText);
        firstNameEditText = viewGroup.findViewById(R.id.firstNameEditText);
        lastNameEditText = viewGroup.findViewById(R.id.lastNameEditText);
        streetAddressEditText = viewGroup.findViewById(R.id.streetAddressEditText);
        cityEditText = viewGroup.findViewById(R.id.cityEditText);
        dobEditText = viewGroup.findViewById(R.id.dobEditText);
        adhaarNoEditText = viewGroup.findViewById(R.id.adhaarNoEditText);

        personalDetailsCheckBox = viewGroup.findViewById(R.id.personalDetailsCheckBox);
        scanItAgainTv = viewGroup.findViewById(R.id.scanItAgainTv);
        termsAndConditionTextView = viewGroup.findViewById(R.id.termsAndConditionTextView);
        frontAdhaarImage = viewGroup.findViewById(R.id.imageFrontAdhaar);
        backAdhaarImage = viewGroup.findViewById(R.id.imageBackAdhaar);
        phoneNumberEditText = viewGroup.findViewById(R.id.phoneNumberEditText);
        relationEditText = viewGroup.findViewById(R.id.relationEditText);
        adharCardLinearLayout = viewGroup.findViewById(R.id.adharCardLinearLayout);

        bankNameEditText = viewGroup.findViewById(R.id.bankNameEditText);
        accountNumberEditText = viewGroup.findViewById(R.id.accountNumberEditText);
        confirmaccountNumberEditText = viewGroup.findViewById(R.id.confirmaccountNumberEditText);
        namInBankAcctEditText = viewGroup.findViewById(R.id.namInBankAcctEditText);
        ifscCodeEditText = viewGroup.findViewById(R.id.ifscCodeEditText);


        sharedPreferences = requireActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        nextButton.setOnClickListener(this);
        dobEditText.setOnClickListener(this);
        scanItAgainTv.setOnClickListener(this);
        termsAndConditionTextView.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.nextButton) {
         //   Bundle bundle = new Bundle();
          //  newFragment.setArguments(bundle);

          /*  if (personalDetailsCheckBox.isChecked()) {
                nextButton.setEnabled(false);*/
                validation();
           /* } else
                Toast.makeText(getActivity(), "Accept the terms and condition", Toast.LENGTH_SHORT).show();*/

        }
        if (view.getId() == R.id.scanItAgainTv) {
            ((RegisterSlideActivity) getActivity()).nextFragment(3);

        }
        if (view.getId() == R.id.termsAndConditionTextView) {
            String url = ApiClient.BASE_URL+"terms-and-conditions";
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setPackage("com.android.chrome");
            try {
                startActivity(i);
            } catch (ActivityNotFoundException e) {
                // Chrome is probably not installed
                // Try with the default browser
                i.setPackage(null);
                startActivity(i);
            }
        }
        if (view.getId() == R.id.dobEditText) {
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));

            datePicker.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
            datePicker.show();
        }

    }


    private void getData() {
        email = emailEditText.getText().toString();
        firstName = firstNameEditText.getText().toString();
        lastName = lastNameEditText.getText().toString();
        streetAddress = streetAddressEditText.getText().toString();
        city = cityEditText.getText().toString();
        country = "India";
        dob = dobEditText.getText().toString();
        adhaarNumber = adhaarNoEditText.getText().toString();

/*        phoneNumber = phoneNumberEditText.getText().toString();
        relation = relationEditText.getText().toString();

        bankName = bankNameEditText.getText().toString();
        accountNumber = accountNumberEditText.getText().toString();
        confirmaccountNumber = confirmaccountNumberEditText.getText().toString();
        namInBankAcct = namInBankAcctEditText.getText().toString();
        ifscCode = ifscCodeEditText.getText().toString();

       */

        token = sharedPreferences.getString(SharedPref.TOKEN, "@null");
    }

    //initialising parameters
    private void initParam() {
        if (mParam == null) {
            mParam = new HashMap<>();
        }

        if (token.length() > 0) {
            mParam.put(TOKEN, token);
        }
       // if (email.length() > 0) {
        // change to optional --
            mParam.put(EMAIL, email);
       // }
        if (modelinfoData.getFirstName().length() > 0) {
            mParam.put(FIRST_NAME, modelinfoData.getFirstName());
        }
        if (modelinfoData.getLastName().length() > 0) {
            mParam.put(LAST_NAME, modelinfoData.getLastName());
        }
        if (modelinfoData.getAddress().length() > 0) {
            mParam.put(ADDRESS, modelinfoData.getAddress());
        }
        if (modelinfoData.getCity().length() > 0) {
            mParam.put(CITY, modelinfoData.getCity());
        }
        if (modelinfoData.getCountry().length() > 0) {
            mParam.put(COUNTRY, modelinfoData.getCountry());
        }
        if (modelinfoData.getDob().length() > 0) {
            mParam.put(DOB, modelinfoData.getDob());
        }
        if (modelinfoData.getAadharNo().length() > 0) {
            mParam.put(ADHAAR_NO, modelinfoData.getAadharNo());
        }
        if (basicDetailsApprovalStatus.length() > 0) {
            mParam.put(BASIC_DETAILS_APPROVAL_STATUS, basicDetailsApprovalStatus);
        }
        if (modelinfoData.getEmergencyContact1().length() > 0) {
            mParam.put(PHONE1, modelinfoData.getEmergencyContact1());
        }
        if (modelinfoData.getEmergencyContactRelation1().length() > 0) {
            mParam.put(RELATION1, modelinfoData.getEmergencyContactRelation1());
        }
        if (emergencyContactStatus.length() > 0) {
            mParam.put(EMERGENCY_CONTACT_STATUS, emergencyContactStatus);
        }
        if (profileCompleteStatus.length() > 0) {
            mParam.put(PROFILE_COMPLETION_STATUS, profileCompleteStatus);
        }

        if (bankApprovalStatus.length() > 0) {
            mParam.put(BANK_APPROVAL_STATUS, bankApprovalStatus);
        }
        if (modelinfoData.getBankName().length() > 0) {
            mParam.put(BANK_NAME, modelinfoData.getBankName());
        }
        if (modelinfoData.getAccountNumber().length() > 0) {
            mParam.put(ACCOUNT_NUMBER, modelinfoData.getAccountNumber());
        }
        if (modelinfoData.getAccHolderName().length() > 0) {
            mParam.put(ACCOUNT_HOLDER_NAME, modelinfoData.getAccHolderName());
        }
        if (modelinfoData.getIFSCCode().length() > 0) {
            mParam.put(IFSC_CODE, modelinfoData.getIFSCCode());
        }

    }

    public void validation() {

        getData();

      /*  if (email.length() <= 0) {
            Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else*/ if (firstName.length() <= 0) {
            Toast.makeText(getActivity(), "First Name cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (lastName.length() <= 0) {
            Toast.makeText(getActivity(), "Last Name cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (streetAddress.length() <= 0) {
            Toast.makeText(getActivity(), "Address cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (city.length() <= 0) {
            Toast.makeText(getActivity(), "City cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (country.length() <= 0) {
            Toast.makeText(getActivity(), "Country cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (dob.length() <= 0) {
            Toast.makeText(getActivity(), "DOB cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (adhaarNumber.length() <= 0) {
            Toast.makeText(getActivity(), "ADHAAR no. cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (adhaarNumber.length() < 12) {
            Toast.makeText(getActivity(), "Input the correct 12 digit Adhaar Number", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);

        }/* else if (phoneNumber.length() < 10) {
            Toast.makeText(getActivity(), "Emergency contact Number one is not correct", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        }  else if (relation.length() <= 0) {
            Toast.makeText(getActivity(), "Relation with contact one cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (bankName.length() <= 0) {
            Toast.makeText(getActivity(), "Bank Name cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);

        } else if (accountNumber.length() <= 0) {
            Toast.makeText(getActivity(), "Account Number Cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        }else if (confirmaccountNumber.length() <= 0) {
            Toast.makeText(getActivity(), "Confirm Account Number Cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        }else if (!confirmaccountNumber.equals(accountNumber)) {
            Toast.makeText(getActivity(), "Confirm account number is not matching", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (namInBankAcct.length() <= 0) {
            Toast.makeText(getActivity(), "Account Holder Name cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        } else if (ifscCode.length() <= 0) {
            Toast.makeText(getActivity(), "IFSC code cannot be empty", Toast.LENGTH_SHORT).show();
            nextButton.setEnabled(true);
        }*/
        else {
         //   personalInformationUpdateApiHit();
            InsetDataInMOdel();
        }
    }

    private void InsetDataInMOdel() {

    modelinfoData.setEmail(email);
    modelinfoData.setFirstName(firstName);
    modelinfoData.setLastName(lastName);
    modelinfoData.setAddress(streetAddress);
    modelinfoData.setCity(city);
    modelinfoData.setCountry(country);
    modelinfoData.setDob(dob);
    modelinfoData.setAadharNo(adhaarNumber);

    Log.i("arp","modelinfoData= "+ new Gson().toJson(modelinfoData));

    FragmentManager fm = requireActivity().getSupportFragmentManager();
    nomineedetails newFragment = new nomineedetails(getActivity());
    newFragment.show(fm, "nominee details");

    }

    private void personalInformationUpdateApiHit() {
        initParam();
        Toast.makeText(getActivity(), "Save Data to DB now...", Toast.LENGTH_SHORT).show();
        Log.i("arp","paramData= "+ new Gson().toJson(mParam));
       /* if (NetworkInfo.hasConnection(requireContext())) {
            //calling the API client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<PersonalInformationModel> call = apiService.personalInformationUpdateApi(mParam);
            call.enqueue(new Callback<PersonalInformationModel>() {
                @Override
                public void onResponse(Call<PersonalInformationModel> call, Response<PersonalInformationModel> response) {
                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {

                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        if (getContext() instanceof EditDataActivity) {
                            startActivity(new Intent(getActivity(), HomeScreenActivity.class));
                            getActivity().finish();
                        } else {
                            editor.putInt(SharedPref.DEFAULT_FRAGMENT_NO_AFTER_OPENING, 3);
                            editor.apply();
                            ((RegisterSlideActivity) getActivity()).nextFragment(3);

                        }


                    } else {
                        nextButton.setEnabled(true);
                        Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<PersonalInformationModel> call, Throwable t) {
                    nextButton.setEnabled(true);
                    Toast.makeText(getActivity(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        } else {
            nextButton.setEnabled(true);

            showAlertDialog();
            Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();

        }*/

    }

    //-------------------show alert dialog for no internet connection-------------------
    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You need to make sure your device is conected to Internet")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().onBackPressed();

                    }
                });
        // Create the AlertDialog object and return it
        builder.create().show();
    }

    private class RetreiveImageTask extends AsyncTask<String, Void, Bitmap> {


        ImageView bmImage;

        public RetreiveImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }


        @Override
        protected Bitmap doInBackground(String... strings) {
            String urlDisplay = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }

}