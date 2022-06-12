package www.atmanirbharbharat.com.LoanExtention;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import www.atmanirbharbharat.com.LoanPaymentDetailActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.models.List_Loanpaidhistory;

public class adapter_ListLoanExtention extends RecyclerView.Adapter<adapter_ListLoanExtention.ViewHolder> {
    private final Model_LoanExtList.ExtensionsList list;
    private Context context;

    public adapter_ListLoanExtention(Activity_ListLoanExtention ctx, Model_LoanExtList.ExtensionsList list) {
        this.context = ctx;
        this.list = list;
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

        holder.amount_received_by.setText("Ext Amount(INR) : "+list.getExtAmount());
       // holder.amount.setText("Amount(INR) : "+list.get(position).getAmount());
        holder.amount_received.setText("Ext Duration : "+list.getExtDuration());
        holder.payment_date.setText("Payment Mode : "+list.getExtPaymentMode());

        String status = list.getExtensionStatus();
        holder.status.setText(status);

//        if(status.equalsIgnoreCase("Active")){
//            holder.status.setText("Status : "+"PAID");
//        }else {
//            holder.status.setText("Status : "+"PENDING");
//        }

      //  holder.manager_name.setText("Manager Name : "+list.get(position).getManagerName());


    }


    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView amount_received_by,amount,amount_received,payment_date,status,manager_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            amount_received_by =  itemView.findViewById(R.id.amount_received_by);
            amount =  itemView.findViewById(R.id.amount);
            amount_received =  itemView.findViewById(R.id.amount_received);
            payment_date =  itemView.findViewById(R.id.payment_date);
            status =  itemView.findViewById(R.id.status);
            manager_name =  itemView.findViewById(R.id.manager_name);

        }
    }

}
