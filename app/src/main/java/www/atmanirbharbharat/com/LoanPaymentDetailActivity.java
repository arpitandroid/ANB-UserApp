package www.atmanirbharbharat.com;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.adapter.PaymentsListAdapter;
import www.atmanirbharbharat.com.adapter.adapter_LoanPaidhistory;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.models.List_Loanpaidhistory;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

import static www.atmanirbharbharat.com.util.AppConstant.hideLoading;
import static www.atmanirbharbharat.com.util.AppConstant.showLoading;

import com.google.gson.Gson;

public class LoanPaymentDetailActivity extends AppCompatActivity {

    String token;
    ImageView noDataFoundImageView;

    private RecyclerView loanPaidRecyclerView;

    private List_Loanpaidhistory res;
    private adapter_LoanPaidhistory adapter;
    private RecyclerView.LayoutManager layoutManager;

    private SharedPreferences sharedPreferences;
    SwipeRefreshLayout swiperefresh;
    private String LoanAppliedId ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadpaidhistory);

        // Intent from DiscoverHomeScreenFragment ----
        LoanAppliedId = getIntent().getStringExtra("LoanAppliedId");
        Log.i("arp", "LoanAppliedId= "+LoanAppliedId);
        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREFS, MODE_PRIVATE);

        token = sharedPreferences.getString(SharedPref.TOKEN, null);
        
        init();

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void showLoanPaidDataApi() {
        if (NetworkInfo.hasConnection(this)) {

            showLoading(LoanPaymentDetailActivity.this);
            //calling the api client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<List_Loanpaidhistory> call = apiService.loanPaymentsGetApi(token,LoanAppliedId);
            call.enqueue(new Callback<List_Loanpaidhistory>() {
                @Override
                public void onResponse(Call<List_Loanpaidhistory> call, Response<List_Loanpaidhistory> response) {
                    hideLoading();

                    res  =  response.body();
                    if(res!=null){
                        Log.i("arp","res== "+new Gson().toJson(response.body()));
                        if(res.getStatus()==200 && res.getData()!=null){

                            setAdapter();
                            
                        }else if(res.getStatus()==201) {
                            Toast.makeText(LoanPaymentDetailActivity.this, res.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        loanPaidRecyclerView.setVisibility(View.GONE);
                        noDataFoundImageView.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<List_Loanpaidhistory> call, Throwable t) {
                    Toast.makeText(LoanPaymentDetailActivity.this, "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            });
        }

    }

    private void setAdapter() {
        layoutManager = new LinearLayoutManager(LoanPaymentDetailActivity.this);
        loanPaidRecyclerView.setLayoutManager(layoutManager);
        loanPaidRecyclerView.setHasFixedSize(true);
        adapter = new adapter_LoanPaidhistory(LoanPaymentDetailActivity.this, res.getData());
        loanPaidRecyclerView.setAdapter(adapter);
        
    }


    private void init() {

        loanPaidRecyclerView = findViewById(R.id.loanPaidRecyclerView);
        noDataFoundImageView = findViewById(R.id.noDataFoundImageView);
       Button btn = findViewById(R.id.Btn_applyforloanext);
       btn.setVisibility(View.GONE);

        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showLoanPaidDataApi();
                swiperefresh.setRefreshing(false);
            }
        });

        showLoanPaidDataApi();

    }
}