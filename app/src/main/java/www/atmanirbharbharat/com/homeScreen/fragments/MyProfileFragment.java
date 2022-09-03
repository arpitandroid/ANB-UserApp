package www.atmanirbharbharat.com.homeScreen.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.ShowPersonalInformationModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;


public class MyProfileFragment extends Fragment {

    TextView nameTextView;
    TextView locationTextView;
    TextView languageTextView;
    TextView emailTextView;
    TextView phoneTextView;
    TextView dobTextView;
    TextView adhaarTextView;

    ImageView profileImageView;

    private SharedPreferences sharedPreferences;
    private String token;


    String nameText;
    String locationText;
    String languageText;
    String emailText;
    String phoneText;
    String dobText;
    String adhaarText;
    String profileImageURL;
    String avatarImageURL;

    SwipeRefreshLayout swiperefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_me_home_screen, container, false);
        init(viewGroup);
        showProfileDataApiHit();
        return viewGroup;
    }

    private void showProfileDataApiHit() {
        if (NetworkInfo.hasConnection(getContext())) {
            //calling the api client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<ShowPersonalInformationModel> call = apiService.showPersonalInformationApi(token);
            call.enqueue(new Callback<ShowPersonalInformationModel>() {
                @Override
                public void onResponse(Call<ShowPersonalInformationModel> call, Response<ShowPersonalInformationModel> response) {

                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {

                        nameText = response.body().getData().getFirstName() + " " + response.body().getData().getLastName();
                        locationText = response.body().getData().getCity() + ", " + response.body().getData().getCountry();
                        emailText = response.body().getData().getEmail();
                        phoneText = response.body().getData().getMobile();
                        dobText = response.body().getData().getDob();
                        adhaarText = response.body().getData().getAadharNo();
                        profileImageURL = response.body().getData().getProfileImage();
                        avatarImageURL = response.body().getData().getAvatarImage();

                        setText();

                    } else {
                       // Toast.makeText(getContext(), "Some Error Occurred", Toast.LENGTH_SHORT).show();
                        Log.i("tag","Some Error Occurred" + Objects.requireNonNull(getActivity()).getPackageName().getClass().getSimpleName());
                    }

                }

                @Override
                public void onFailure(Call<ShowPersonalInformationModel> call, Throwable t) {
                    Toast.makeText(getContext(), "OOpss Something went wrong!", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    private void setText() {
        nameTextView.setText(nameText);
        locationTextView.setText(locationText);
        emailTextView.setText(emailText);
        phoneTextView.setText(phoneText);
        dobTextView.setText(dobText);
        adhaarTextView.setText(adhaarText);

        Glide.with(requireActivity())
                .load(profileImageURL)
                .circleCrop()
                .error(R.drawable.elmlogoimg)
                .placeholder(R.drawable.elmlogoimg)
                .into(profileImageView);

       // Glide.with(requireActivity()).load(profileImageURL).error(R.drawable.logo).apply(new RequestOptions().circleCrop()).into(profileImageView);

    }

    private void init(ViewGroup viewGroup) {
        nameTextView = viewGroup.findViewById(R.id.nameTextView);
        locationTextView = viewGroup.findViewById(R.id.locationTextView);
        languageTextView = viewGroup.findViewById(R.id.languageTextView);
        emailTextView = viewGroup.findViewById(R.id.emailTextView);
        phoneTextView = viewGroup.findViewById(R.id.phoneTextView);
        dobTextView = viewGroup.findViewById(R.id.dobTextView);
        adhaarTextView = viewGroup.findViewById(R.id.adhaarTextView);
        profileImageView = viewGroup.findViewById(R.id.profileImageView);

        sharedPreferences = getContext().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
        token = sharedPreferences.getString(SharedPref.TOKEN, null);
        swiperefresh = viewGroup.findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reload();
                swiperefresh.setRefreshing(false);
            }
        });
    }

    private void reload() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }



}