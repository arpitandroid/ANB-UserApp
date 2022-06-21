package www.atmanirbharbharat.com.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import www.atmanirbharbharat.com.LoanPaymentDetailActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.models.List_Loanpaidhistory;
import www.atmanirbharbharat.com.models.LoanBasicDetails;

public class adapter_LoanPaidhistory extends RecyclerView.Adapter<adapter_LoanPaidhistory.ViewHolder> {
    private Context context;
    private List<List_Loanpaidhistory.Datum> list;

    public adapter_LoanPaidhistory(LoanPaymentDetailActivity loanPaymentDetailActivity, List<List_Loanpaidhistory.Datum> mlIst) {
        this.context = loanPaymentDetailActivity;
        this.list = mlIst;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_loadpaidhistory, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

      //  holder.amount_received_by.setText("Amount Received by : "+list.get(position).getAmountReceivedBy());
        holder.amount.setText("Amount (INR) : "+list.get(position).getAmount());
        holder.amount_received.setText("Loan apply id : "+list.get(position).getLoanApplyId());
        holder.payment_date.setText("Payment Date : "+list.get(position).getPaymentDate());

        String status = list.get(position).getStatus();
        if(status.equalsIgnoreCase("Active")){
            holder.status.setText("Status : "+"PAID");
        }else {
            holder.status.setText("Status : "+"PENDING");
        }

       // holder.manager_name.setText("Manager Name : "+list.get(position).getManagerName());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount_received_by,amount,amount_received,payment_date,status,manager_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amount_received_by =  itemView.findViewById(R.id.amount_received_by);
            amount_received_by.setVisibility(View.GONE);
            amount =  itemView.findViewById(R.id.amount);
            amount_received =  itemView.findViewById(R.id.amount_received);
            payment_date =  itemView.findViewById(R.id.payment_date);
            status =  itemView.findViewById(R.id.status);
            manager_name =  itemView.findViewById(R.id.manager_name);
            manager_name.setVisibility(View.GONE);

        }
    }

}
