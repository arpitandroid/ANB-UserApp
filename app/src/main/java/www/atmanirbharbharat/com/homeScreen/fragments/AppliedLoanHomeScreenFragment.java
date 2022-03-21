package www.atmanirbharbharat.com.homeScreen.fragments;

import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.UserLoanDetailActivity;
import www.atmanirbharbharat.com.adapter.UserLoanListAdapter;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.homeScreen.fragments.loansFragment.AppliedLoanDetailFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.loansFragment.PayLoanFragment;
import www.atmanirbharbharat.com.models.LoanBasicDetails;
import www.atmanirbharbharat.com.models.UserLoanListModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;

public class AppliedLoanHomeScreenFragment extends Fragment {


    String token;
    ImageView noDataFoundImageView;

    TextView currentLoanTextView;

    private RecyclerView loansRecyclerView;

    private ArrayList<LoanBasicDetails> arrayList;
    private UserLoanListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    SwipeRefreshLayout swiperefresh;

    public AppliedLoanHomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList = new ArrayList<>();
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        token = sharedPreferences.getString(SharedPref.TOKEN, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_applied_loan_home_screen, container, false);
        init(viewGroup);
        showLoanDataApi();
        return viewGroup;
    }

    private void showLoanDataApi() {
        if (NetworkInfo.hasConnection(requireContext())) {
            //calling the api client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<UserLoanListModel> call = apiService.getUserLoanList(token);
            call.enqueue(new Callback<UserLoanListModel>() {
                @Override
                public void onResponse(Call<UserLoanListModel> call, Response<UserLoanListModel> response) {

                    UserLoanListModel res = response.body();

                    if (res != null) {
                        if (res.getStatus()==200 && res.getData() != null) {

                            arrayList = res.getData();
                            currentLoanTextView.setVisibility(View.VISIBLE);

                            setAdapter();

                        } else {
                            noDataFoundImageView.setVisibility(View.VISIBLE);
                            currentLoanTextView.setVisibility(View.GONE);
                            loansRecyclerView.setVisibility(View.GONE);

                        }

                    }else {  // res null --
                        noDataFoundImageView.setVisibility(View.VISIBLE);
                        currentLoanTextView.setVisibility(View.GONE);
                        loansRecyclerView.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onFailure(Call<UserLoanListModel> call, Throwable t) {
                    Toast.makeText(getContext(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(getContext());
        loansRecyclerView.setLayoutManager(layoutManager);
        loansRecyclerView.setHasFixedSize(true);
        adapter = new UserLoanListAdapter(getActivity(), arrayList);
        loansRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLister(new UserLoanListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                Intent intent = new Intent(getActivity(), UserLoanDetailActivity.class);
                intent.putExtra(AppliedLoanDetailFragment.LOANID,  arrayList.get(position).getLoanAppliedId());
                intent.putExtra(PayLoanFragment.LOANAMOUNNT,  arrayList.get(position).getLoanCloserAmount());
                startActivity(intent);
            }
        });
    }


    private void init(ViewGroup viewGroup) {
        loansRecyclerView = viewGroup.findViewById(R.id.loansRecyclerView);
        noDataFoundImageView = viewGroup.findViewById(R.id.noDataFoundImageView);
        currentLoanTextView = viewGroup.findViewById(R.id.currentLoanTextView);
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