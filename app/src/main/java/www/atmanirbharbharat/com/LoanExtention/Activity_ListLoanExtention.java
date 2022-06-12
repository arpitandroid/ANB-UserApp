package www.atmanirbharbharat.com.LoanExtention;

import static www.atmanirbharbharat.com.util.AppConstant.hideLoading;
import static www.atmanirbharbharat.com.util.AppConstant.showLoading;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import www.atmanirbharbharat.com.Interface.ApiInterface;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.common.SharedPref;
import www.atmanirbharbharat.com.util.ApiClient;
import www.atmanirbharbharat.com.util.NetworkInfo;

public class Activity_ListLoanExtention extends AppCompatActivity {

    String token;
    ImageView noDataFoundImageView;

    private RecyclerView loanPaidRecyclerView;

    private Model_LoanExtList res;
    private adapter_ListLoanExtention adapter;
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

    private void GetExtloanList() {
        if (NetworkInfo.hasConnection(this)) {

            showLoading(Activity_ListLoanExtention.this);
            //calling the api client
            ApiInterface apiService = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);

            Call<Model_LoanExtList> call = apiService.GetLoanExtentionList(token,LoanAppliedId);
            call.enqueue(new Callback<Model_LoanExtList>() {
                @Override
                public void onResponse(@NonNull Call<Model_LoanExtList> call, @NonNull Response<Model_LoanExtList> response) {
                    hideLoading();

                    res  =  response.body();
                    Log.i("arp", "res: GetExtloanList= "+new Gson().toJson(response.body()));
                    if(res!=null && res.getData().getExtensionsList()!=null){
                        if(res.getStatus()==200){
                            Model_LoanExtList.ExtensionsList list = res.getData().getExtensionsList();
                            setAdapter(list);
                            
                        }else if(res.getStatus()==201) {
                            Toast.makeText(Activity_ListLoanExtention.this, res.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        loanPaidRecyclerView.setVisibility(View.GONE);
                        noDataFoundImageView.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onFailure(Call<Model_LoanExtList> call, Throwable t) {
                    Toast.makeText(Activity_ListLoanExtention.this, "OOpss Something went wrong!", Toast.LENGTH_SHORT).show();
                    hideLoading();
                }
            });
        }

    }

    private void setAdapter(Model_LoanExtList.ExtensionsList list) {
        layoutManager = new LinearLayoutManager(Activity_ListLoanExtention.this);
        loanPaidRecyclerView.setLayoutManager(layoutManager);
        loanPaidRecyclerView.setHasFixedSize(true);
        adapter = new adapter_ListLoanExtention(Activity_ListLoanExtention.this,list);
        loanPaidRecyclerView.setAdapter(adapter);
        
    }


    private void init() {

        loanPaidRecyclerView = findViewById(R.id.loanPaidRecyclerView);
        noDataFoundImageView = findViewById(R.id.noDataFoundImageView);
        TextView tvtitle = findViewById(R.id.textView6);
        tvtitle.setText("Extension Loan");

        swiperefresh = findViewById(R.id.swiperefresh);
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetExtloanList();
                swiperefresh.setRefreshing(false);
            }
        });

        GetExtloanList();

    }
}