package www.atmanirbharbharat.com.homeScreen.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Activity_UpdateProfile;
import www.atmanirbharbharat.com.EditDataActivity;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.LoanActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.SettingsActivity;
import www.atmanirbharbharat.com.WebViewActivity;
import www.atmanirbharbharat.com.adapter.LoanAvailableRecyclerAdapter;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.homeScreen.activity.Activity_CustomLoan;
import www.atmanirbharbharat.com.homeScreen.fragments.homeFragment.SelectLoanFragment;
import www.atmanirbharbharat.com.models.HomeModel;
import www.atmanirbharbharat.com.models.LoanList;
import www.atmanirbharbharat.com.models.User;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;
import static www.atmanirbharbharat.com.common.SharedPref.Isprofileupdate;

public class MainHomeScreenFragment extends Fragment implements View.OnClickListener {

    Button feedbackButton;
    Button helpButton;
    RecyclerView loansAvailableRecycler;
    private LoanAvailableRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<LoanList> arrayList;

    Button editButton;
    Button cancelButton;
    TextView headingTextView;
    TextView bodyTextView;

    ImageButton settingsImageButton;
    CircleImageView refreshImageButton;
    CircleImageView CIV_profile;

    CardView basicDetailsCardView;
    CardView profilepictureCardView;
    CardView pancardCardView;
    CardView documentVerificationCardView;
    CardView checkbookverificationCardView;

    TextView nameTextView;
    TextView phoneTextView;
    TextView currentStatusTextView;
    TextView currentStatusDescriptionTextView;
    ImageView waitingOrApprovedImageView;


    String name;
    String phoneNumber;
    String token;
    String documentVerificationMessage;
    String basicDetailsVerificationMessage;
    String selfieVerificationMessage;
    String payslipVerificationMessage;

    String basicDetailsApprovalStatus;
    String nomineeVerificationStatus;
    String panCardVerificationStatus;
    String documentVerificationStatus;
    String passbookVerificationStatus;
    String profileUrl;

    FragmentManager fragmentManager;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    TextView basicDetailsApprovalStatusTextView;
    TextView panCardApprovalStatusTextView;
    TextView documentVerificationApprovalStatusTextView;
    TextView checkbookVerificationApprovalTextView;
    TextView tv_statusProfile;

    ProgressBar progress_circular;
    ConstraintLayout mainConstraintLayout;
    SwipeRefreshLayout swiperefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        token = sharedPreferences.getString(SharedPref.TOKEN, null);
        Log.i("arp","token= "+token);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main_home_screen, container, false);
        init(viewGroup);
        showHomeScreenDataApi();

        return viewGroup;
    }

    private void setStatus() {
        if (basicDetailsApprovalStatus == "APPROVED") {
            basicDetailsApprovalStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            basicDetailsCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_green));
        } else {
            basicDetailsApprovalStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            basicDetailsCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_red));
        }

        basicDetailsApprovalStatusTextView.setText(basicDetailsApprovalStatus);

        if (profileUrl.startsWith("https")) {
            tv_statusProfile.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            tv_statusProfile.setText("Uploaded");
            profilepictureCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_green));
            Glide.with(Objects.requireNonNull(getActivity())).load(profileUrl).placeholder(R.mipmap.ic_launcher).into(CIV_profile);
        } else {
            tv_statusProfile.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            tv_statusProfile.setText("Not Available");
            profilepictureCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_red));
        }

        // pancard approval text status
        if (panCardVerificationStatus == "APPROVED") {
            panCardApprovalStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            pancardCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_green));

        } else {
            panCardApprovalStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            pancardCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_red));
        }
        panCardApprovalStatusTextView.setText(panCardVerificationStatus);

        if (documentVerificationStatus == "APPROVED") {
            documentVerificationApprovalStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            documentVerificationCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_green));

        } else {
            documentVerificationApprovalStatusTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            documentVerificationCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_red));

        }
        documentVerificationApprovalStatusTextView.setText(documentVerificationStatus);

        if (passbookVerificationStatus == "APPROVED") {
            checkbookVerificationApprovalTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            checkbookverificationCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_green));

        } else {
            checkbookVerificationApprovalTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            checkbookverificationCardView.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gradient_red));

        }
        checkbookVerificationApprovalTextView.setText(passbookVerificationStatus);
    }

    private void setTopCardDetails() {
        nameTextView.setText(name);
        phoneTextView.setText(phoneNumber);

        if (basicDetailsApprovalStatus.equals("REJECTED") ||
                documentVerificationStatus.equals("REJECTED") || passbookVerificationStatus.equals("REJECTED")
                || panCardVerificationStatus.equals("REJECTED")) {
            waitingOrApprovedImageView.setVisibility(View.VISIBLE);

        }


        if (basicDetailsApprovalStatus.equals("NOT_AVAILABLE") ||
                documentVerificationStatus.equals("NOT_AVAILABLE") || passbookVerificationStatus.equals("NOT_AVAILABLE")
                || panCardVerificationStatus.equals("NOT_AVAILABLE")) {
            currentStatusTextView.setText(getString(R.string.your_profile_is_incomplete));
            currentStatusDescriptionTextView.setText(getString(R.string.please_complete_your_profile));
        } else if (basicDetailsApprovalStatus.equals("APPROVED") &&
                documentVerificationStatus.equals("APPROVED") && passbookVerificationStatus.equals("APPROVED")
                && panCardVerificationStatus.equals("APPROVED")) {
            currentStatusTextView.setText(getString(R.string.profile_is_approved));
            currentStatusDescriptionTextView.setText(getString(R.string.elegibile_now));
        } else {
            currentStatusTextView.setText(getString(R.string.profile_verification_in_process));
            currentStatusDescriptionTextView.setText(getString(R.string.please_wait_till_we_complete_verification));
        }
    }

    private void init(ViewGroup viewGroup) {
        fragmentManager = getActivity().getSupportFragmentManager();
        progress_circular = viewGroup.findViewById(R.id.progress_circular);
        mainConstraintLayout = viewGroup.findViewById(R.id.mainConstraintLayout);
        feedbackButton = viewGroup.findViewById(R.id.feedbackButton);
        helpButton = viewGroup.findViewById(R.id.helpButton);
        loansAvailableRecycler = viewGroup.findViewById(R.id.loansAvailableRecycler);
        basicDetailsCardView = viewGroup.findViewById(R.id.basicDetailsCardView);
        pancardCardView = viewGroup.findViewById(R.id.pancardCardView);
        documentVerificationCardView = viewGroup.findViewById(R.id.AdharCardCardView);
        checkbookverificationCardView = viewGroup.findViewById(R.id.checkbookCardView);
        profilepictureCardView = viewGroup.findViewById(R.id.profileCardView);
        settingsImageButton = viewGroup.findViewById(R.id.settingsImageButton);
        refreshImageButton = viewGroup.findViewById(R.id.refreshImageButton);
        CIV_profile = viewGroup.findViewById(R.id.CIV_profile);


        nameTextView = viewGroup.findViewById(R.id.nameTextView);
        phoneTextView = viewGroup.findViewById(R.id.phoneTextView);
        currentStatusTextView = viewGroup.findViewById(R.id.currentStatusTextView);
        currentStatusDescriptionTextView = viewGroup.findViewById(R.id.currentStatusDescriptionTextView);
        waitingOrApprovedImageView = viewGroup.findViewById(R.id.waitingOrApprovedImageView);

        basicDetailsApprovalStatusTextView = viewGroup.findViewById(R.id.basicDetailsApprovalStatusTextView);
        panCardApprovalStatusTextView = viewGroup.findViewById(R.id.paySlipApprovalStatusTextView);
        documentVerificationApprovalStatusTextView = viewGroup.findViewById(R.id.documentVerificationApprovalStatusTextView);
        checkbookVerificationApprovalTextView = viewGroup.findViewById(R.id.scheckbookVerificationApprovalTextView);
        tv_statusProfile = viewGroup.findViewById(R.id.tv_statusProfile);


        feedbackButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        basicDetailsCardView.setOnClickListener(this);
        pancardCardView.setOnClickListener(this);
        documentVerificationCardView.setOnClickListener(this);
        checkbookverificationCardView.setOnClickListener(this);
        settingsImageButton.setOnClickListener(this);
        refreshImageButton.setOnClickListener(this);
        waitingOrApprovedImageView.setOnClickListener(this);
        profilepictureCardView.setOnClickListener(this);

//        swiperefresh = viewGroup.findViewById(R.id.swiperefresh);
//        scrollView = viewGroup.findViewById(R.id.scrollView);
//
//        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                int scrollY = scrollView.getScrollY();
//                if(scrollY == 0) swiperefresh.setEnabled(true);
//                else swiperefresh.setEnabled(false);
//
//            }
//        });

//        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                reload();
//                swiperefresh.setRefreshing(false);
//            }
//        });

//        swiperefresh.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
//            @Override
//            public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
//                if (scrollView != null)
//                    return scrollView.canScrollVertically(-1);
//
//                return false;            }
//        });
//

         viewGroup.findViewById(R.id.Tv_manualamnt).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(requireActivity(), Activity_CustomLoan.class));
             }
         });


    }

/*    private void reload() {
        getFragmentManager().beginTransaction().detach(this).attach(this).commit();

    }*/

    @Override
    public void onResume() {

        Log.i("arp","====== onresume:MainHomeScreen =======");
        Log.i("arp","====== Isprofileupdate =======" + Isprofileupdate);

        if(Isprofileupdate){
            showHomeScreenDataApi();
            Isprofileupdate = false;
        }

        super.onResume();
    }

    private void setAdapter() {

        layoutManager = new LinearLayoutManager(getContext());
        loansAvailableRecycler.setLayoutManager(layoutManager);
        loansAvailableRecycler.setHasFixedSize(true);
        adapter = new LoanAvailableRecyclerAdapter(getActivity(), arrayList);
        loansAvailableRecycler.setAdapter(adapter);
        adapter.setOnItemClickLister(new LoanAvailableRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                Intent intent = new Intent(getActivity(), LoanActivity.class);
                intent.putExtra(SelectLoanFragment.LOANDETAILS, arrayList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.feedbackButton) {
            String url = ApiClient.BASE_URL+"webviews/feedback?token="+token;
            Intent i = new Intent(getActivity(), WebViewActivity.class);
            i.putExtra(WebViewActivity.WEB_URL, url);
            startActivity(i);

        }
        if (view.getId() == R.id.helpButton) {
            String url = ApiClient.BASE_URL+"webviews/help?token="+token;

            Intent i = new Intent(getActivity(), WebViewActivity.class);
            i.putExtra(WebViewActivity.WEB_URL, url);
            startActivity(i);

        }
        if (view.getId() == R.id.basicDetailsCardView) {
            openDialog(3, basicDetailsApprovalStatus);
        }
        if (view.getId() == R.id.waitingOrApprovedImageView) {

            openNotificatinDialog();
        }
        if (view.getId() == R.id.pancardCardView) {
            if (panCardVerificationStatus.equals("APPROVED")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout.home_screen_dialog1, viewGroup, false);
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                editButton = dialogView.findViewById(R.id.editButton);
                cancelButton = dialogView.findViewById(R.id.cancelButton);
                headingTextView = dialogView.findViewById(R.id.headingTextView);
                bodyTextView = dialogView.findViewById(R.id.bodyTextView);

                headingTextView.setText(getString(R.string.oops_you_cannot_edit_now));
                bodyTextView.setText(getString(R.string.details_have_been_approved));
                editButton.setVisibility(View.GONE);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            } else {

                Intent intent = new Intent(getActivity(), EditDataActivity.class);
                intent.putExtra("fragmentNo", 0);
                startActivity(intent);
            }
        }

        if (view.getId() == R.id.AdharCardCardView) {
            openDialog(2, documentVerificationStatus);
        }
        if (view.getId() == R.id.checkbookCardView) {
            if (passbookVerificationStatus.equals("APPROVED")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                ViewGroup viewGroup = getView().findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout
                        .home_screen_dialog1, viewGroup, false);
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();

                editButton = dialogView.findViewById(R.id.editButton);
                cancelButton = dialogView.findViewById(R.id.cancelButton);
                headingTextView = dialogView.findViewById(R.id.headingTextView);
                bodyTextView = dialogView.findViewById(R.id.bodyTextView);

                headingTextView.setText(getString(R.string.oops_you_cannot_edit_now));
                bodyTextView.setText(getString(R.string.details_have_been_approved));
                editButton.setVisibility(View.GONE);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
            } else {

                Intent intent = new Intent(getActivity(), EditDataActivity.class);
                intent.putExtra("fragmentNo", 1);
                startActivity(intent);
            }
        }
        if (view.getId() == R.id.settingsImageButton) {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        }
        /*if (view.getId() == R.id.refreshImageButton) {
            //reload();
            Intent intent = new Intent(getActivity(), Activity_UpdateProfile.class);
            startActivity(intent);
        }
*/
        if (view.getId() == R.id.profileCardView) {
            if(!profileUrl.startsWith("https")){
                Intent intent = new Intent(getActivity(), Activity_UpdateProfile.class);
                startActivity(intent);
            }else {
                OpenDialog();
            }
        }
    }


    private void OpenDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        ViewGroup viewGroup = Objects.requireNonNull(getView()).findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout
                .home_screen_dialog1, viewGroup, false);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        editButton = dialogView.findViewById(R.id.editButton);
        cancelButton = dialogView.findViewById(R.id.cancelButton);
        headingTextView = dialogView.findViewById(R.id.headingTextView);
        bodyTextView = dialogView.findViewById(R.id.bodyTextView);

        headingTextView.setText(getString(R.string.oops_you_cannot_edit_now));
        bodyTextView.setText(getString(R.string.details_have_been_approved));
        editButton.setVisibility(View.GONE);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

    }


    private void openDialog(final int fragmentNo, String status) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getView().getContext()).inflate(R.layout
                .home_screen_dialog1, viewGroup, false);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        editButton = dialogView.findViewById(R.id.editButton);
        cancelButton = dialogView.findViewById(R.id.cancelButton);
        headingTextView = dialogView.findViewById(R.id.headingTextView);
        bodyTextView = dialogView.findViewById(R.id.bodyTextView);


        if (status.equals("APPROVED")) {
            headingTextView.setText(getString(R.string.oops_you_cannot_edit_now));
            bodyTextView.setText(getString(R.string.details_have_been_approved));
            editButton.setVisibility(View.GONE);
        } else {
            headingTextView.setText(getString(R.string.we_gave_you_choice));
            bodyTextView.setText(getString(R.string.edit_the_data_you_enterd));
            editButton.setVisibility(View.VISIBLE);

        }


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditDataActivity.class);
                intent.putExtra("fragmentNo", fragmentNo);
                intent.putExtra("cameFromEdit", true);
                startActivity(intent);
                alertDialog.dismiss();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void showHomeScreenDataApi() {
        if (NetworkInfo.hasConnection(requireActivity())) {
            //calling the api client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL)
                    .create(ApiInterface.class);

            Call<HomeModel> call = apiService.getHomeScreenDataApi(token);
            call.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(@NotNull Call<HomeModel> call, @NotNull Response<HomeModel> response) {

                    Log.i("tag","res= " + new Gson().toJson(response.body()));

                    if (response.body() != null && response.body().getStatus() == 200 && response.body().getData() != null) {
                        progress_circular.setVisibility(View.GONE);
                        mainConstraintLayout.setVisibility(View.VISIBLE);

                        Gson gson = new Gson();
                        User user = new User();
                        user = response.body().getData().getUser();
                        String json = gson.toJson(user);
                        editor.putString(SharedPref.HOMESCREENDATA, json);
                        editor.apply();

                        basicDetailsApprovalStatus = response.body().getData().getUser().getBasicDetailsApprovalStatus();

                       /* if(response.body().getData().getUser().getProfileImage()!=null){
                            profileUrl = response.body().getData().getUser().getProfileImage();
                        }else {
                            profileUrl = "";
                            editor = sharedPreferences.edit();
                            editor.putString(SharedPref.PROFILEIMAGE_URL, "");
                            editor.apply();
                        }*/

                        nomineeVerificationStatus = response.body().getData().getUser().getEmergencyContactStatus();
                        panCardVerificationStatus = response.body().getData().getUser().getPan_card_approved_status();
                        documentVerificationStatus = response.body().getData().getUser().getDocumentVerificationStatus();
                        passbookVerificationStatus = response.body().getData().getUser().getPassbook_approved_status();
                        name = response.body().getData().getUser().getFirstName() + " " + response.body().getData().getUser().getLastName();
                        phoneNumber = response.body().getData().getUser().getMobile();
                        if(response.body().getData().getUser().getProfileImage()!=null){
                            editor = sharedPreferences.edit();
                            editor.putString(SharedPref.PROFILEIMAGE_URL, response.body().getData().getUser().getProfileImage());
                            editor.apply();

                            Glide.with(Objects.requireNonNull(getActivity())).load(response.body().getData().getUser().getProfileImage())
                                    .placeholder(R.mipmap.ic_launcher).into(refreshImageButton);

                            profileUrl = response.body().getData().getUser().getProfileImage();
                        }else {
                            Glide.with(Objects.requireNonNull(getActivity())).load(R.mipmap.ic_launcher).into(refreshImageButton);
                            profileUrl = "";
                            editor = sharedPreferences.edit();
                            editor.putString(SharedPref.PROFILEIMAGE_URL, response.body().getData().getUser().getProfileImage());
                            editor.apply();
                        }

                        documentVerificationMessage = response.body().getData().getUser().getDocumentVerificationComment();
                        basicDetailsVerificationMessage = response.body().getData().getUser().getBasicDetailsApprovalComment();
                        selfieVerificationMessage = response.body().getData().getUser().getSelfieApprovalComment();
                        payslipVerificationMessage = response.body().getData().getUser().getPaySlipComment();

                        arrayList = response.body().getData().getLoanList();
                        editor.putString(SharedPref.ADHAR_FRONT_URL, response.body().getData().getUser().getAdharCardFront());
                        editor.putString(SharedPref.ADHAR_BACK_URL, response.body().getData().getUser().getAdharCardBack());

                        if (response.body().getData().getUser().getPan_card_image() != null) {
                            editor.putString(SharedPref.PANCARD_URL, response.body().getData().getUser().getPan_card_image());

                        }
                        editor.apply();
                        setTopCardDetails();
                        setStatus();
                        setAdapter();


                    } else {
                     //  Toast.makeText(getContext(), "Some Error Occurred ====>> ", Toast.LENGTH_SHORT).show();
                        Log.i("tag","Some Error Occurred" + Objects.requireNonNull(getActivity()).getPackageName().getClass().getSimpleName());
                    }

                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {
                    Toast.makeText(getContext(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void openNotificatinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ViewGroup viewGroup = getView().findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getView().getContext())
                .inflate(R.layout.notification_dialog, viewGroup, false);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView basicDetailsMessageTextView;
        TextView documentVerificationMessageTextView;
        TextView selfieVereificationMessageTextView;
        TextView payslipVerificationMessageTextView;


        TextView basicDetailsTextView;
        TextView documentVerificationTextView;
        TextView selfieVereificationTextView;
        TextView payslipVerificationTextView;

        Button okButton;

        basicDetailsMessageTextView = dialogView.findViewById(R.id.basicDetailsMessageTextView);
        documentVerificationMessageTextView = dialogView.findViewById(R.id.documentVerificationMessageTextView);
        selfieVereificationMessageTextView = dialogView.findViewById(R.id.selfieVereificationMessageTextView);
        payslipVerificationMessageTextView = dialogView.findViewById(R.id.payslipVerificationMessageTextView);

        basicDetailsTextView = dialogView.findViewById(R.id.basicDetailsTextView);
        documentVerificationTextView = dialogView.findViewById(R.id.documentVerificationTextView);
        selfieVereificationTextView = dialogView.findViewById(R.id.selfieVereificationTextView);
        payslipVerificationTextView = dialogView.findViewById(R.id.payslipVerificationTextView);

        okButton = dialogView.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });


        if (basicDetailsApprovalStatus.equals("REJECTED")) {
            basicDetailsTextView.setVisibility(View.VISIBLE);
            basicDetailsMessageTextView.setVisibility(View.VISIBLE);
            basicDetailsMessageTextView.setText(basicDetailsVerificationMessage);
        }

        if (documentVerificationStatus.equals("REJECTED")) {
            documentVerificationTextView.setVisibility(View.VISIBLE);
            documentVerificationMessageTextView.setVisibility(View.VISIBLE);
            documentVerificationMessageTextView.setText(documentVerificationMessage);
        }

        if (passbookVerificationStatus.equals("REJECTED")) {
            selfieVereificationTextView.setVisibility(View.VISIBLE);
            selfieVereificationMessageTextView.setVisibility(View.VISIBLE);

            selfieVereificationMessageTextView.setText(selfieVerificationMessage);
        }

        if (panCardVerificationStatus.equals("REJECTED")) {
            payslipVerificationTextView.setVisibility(View.VISIBLE);
            payslipVerificationMessageTextView.setVisibility(View.VISIBLE);

            payslipVerificationMessageTextView.setText(payslipVerificationMessage);
        }
    }
}
