package www.atmanirbharbharat.com.homeScreen.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.LoanPaymentDetailActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.UserLoanDetailActivity;
import www.atmanirbharbharat.com.adapter.PaymentsListAdapter;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.homeScreen.fragments.loansFragment.AppliedLoanDetailFragment;
import www.atmanirbharbharat.com.homeScreen.fragments.loansFragment.PayLoanFragment;
import www.atmanirbharbharat.com.models.LoanPaymentsGetApiModel;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static android.content.Context.MODE_PRIVATE;

import com.google.gson.Gson;

public class LoanPayments_Fragment extends Fragment {

    String token;
    ImageView noDataFoundImageView;

    private RecyclerView loanPaidRecyclerView;

    private LoanPaymentsGetApiModel loanPayments;
    private PaymentsListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences sharedPreferences;
    SwipeRefreshLayout swiperefresh;
    private ConstraintLayout upcommingPaymentcard;
    private TextView loanAmountText;
    private TextView paymentDateTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);

        token = sharedPreferences.getString(SharedPref.TOKEN, null);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_discover_home_screen, container, false);
        init(viewGroup);
        showLoanPaidDataApi();
        return viewGroup;

    }
    private void showLoanPaidDataApi() {
        if (NetworkInfo.hasConnection(requireActivity())) {
            //calling the api client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<LoanPaymentsGetApiModel> call = apiService.GetAllPayments(token);
            call.enqueue(new Callback<LoanPaymentsGetApiModel>() {
                @Override
                public void onResponse(Call<LoanPaymentsGetApiModel> call, Response<LoanPaymentsGetApiModel> response) {

                    LoanPaymentsGetApiModel res  =  response.body();
                    if (res != null) {
                        Log.i("arp","res: paylist=> "+new Gson().toJson(res));
                        if (res.getStatus() == 200 && res.getData() != null) {
                            loanPayments = response.body();

                            if (loanPayments.getData().getLoanPaidData().isEmpty() && response.body().getData().getUpcommingPayment().isEmpty()){
                                noDataFoundImageView.setVisibility(View.VISIBLE);
                            }

                            if(!response.body().getData().getUpcommingPayment().isEmpty()){
                                upcommingPaymentcard.setVisibility(View.VISIBLE);
                                loanAmountText.setText("EMI - "+String.valueOf(response.body().getData().getUpcommingPayment().get(0).getInitialamount()) + " -/  (For Loan ID - "+ response.body().getData().getUpcommingPayment().get(0).getLoan_apply_id()+" )");

                                SimpleDateFormat DateFor ;
                                SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd");

                                DateFor = new SimpleDateFormat("E, dd MMM yyyy");

                                if (res.getData().getUpcommingPayment().get(0).getPayment_date()!=null){
                                    try {
                                        paymentDateTextView.setText( DateFor.format(fromUser.parse(response.body().getData().getUpcommingPayment().get(0).getPayment_date())));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }


                            }else{
                                upcommingPaymentcard.setVisibility(View.GONE);
                            }

                            setAdapter();

                        } else {
                            noDataFoundImageView.setVisibility(View.VISIBLE);
                        }
                    }
                    else {
                        noDataFoundImageView.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<LoanPaymentsGetApiModel> call, Throwable t) {
                    Toast.makeText(getContext(), "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(getContext());
        loanPaidRecyclerView.setLayoutManager(layoutManager);
        loanPaidRecyclerView.setHasFixedSize(true);
        adapter = new PaymentsListAdapter(loanPayments);
        loanPaidRecyclerView.setAdapter(adapter);
      /*  adapter.setOnItemClickLister(new PaymentsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                String LoanAppliedId  =  loanPayments.getData().getLoanPaidData().get(position).getLoanAppliedId();
                Intent intent = new Intent(getActivity(), LoanPaymentDetailActivity.class);
                intent.putExtra("LoanAppliedId",LoanAppliedId);
                startActivity(intent);

            }
        });*/
    }


    private void init(ViewGroup viewGroup) {
        loanPaidRecyclerView = viewGroup.findViewById(R.id.loanPaidRecyclerView);
        noDataFoundImageView = viewGroup.findViewById(R.id.noDataFoundImageView);
        upcommingPaymentcard = viewGroup.findViewById(R.id.upcommingPaymentcard);
        loanAmountText = viewGroup.findViewById(R.id.loanAmountText);
        paymentDateTextView = viewGroup.findViewById(R.id.paymentDateTextView);

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