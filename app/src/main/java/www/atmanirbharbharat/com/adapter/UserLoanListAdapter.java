package www.atmanirbharbharat.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.models.LoanBasicDetails;

public class UserLoanListAdapter extends RecyclerView.Adapter<UserLoanListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoanBasicDetails> list;
    private static OnItemClickListener onItemClickLister;

    public UserLoanListAdapter(Context context, ArrayList<LoanBasicDetails> mList) {
        this.context = context;
        this.list = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_loan_item_layout, parent, false);
        ViewHolder cardViewHolder = new ViewHolder(view);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.loanAmountText.setText(String.valueOf(list.get(position).getInitialamount())+" "+"-/");

        holder.tenureTextView.append(" " + list.get(position).getTenure() + " days");
        holder.tenureTextView.setText(getloanrepayDays(list.get(position).getPayment_mode(),list.get(position).getLoan_duration()));

        if (!list.get(position).getLoanStatus().equals("APPROVED")) {
            holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.red));
        } else
            holder.statusTextView.setTextColor(ContextCompat.getColor(context, R.color.green));

        if (list.get(position).getLoanStatus().equals("RUNNING")) {
            holder.statusTextView.setText("Loan Running");

        }else if(list.get(position).getLoanStatus().equals("PAID")){
            holder.statusTextView.setText(holder.itemView.getContext().getString(R.string.loan_closed));

        } else
            holder.statusTextView.setText("Status: " + list.get(position).getLoanStatus());

    }

    public void setOnItemClickLister(OnItemClickListener onItemClickLister) {
        UserLoanListAdapter.onItemClickLister = onItemClickLister;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView loanAmountText;
        private TextView tenureTextView;
        private TextView statusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loanAmountText = itemView.findViewById(R.id.loanAmountText);
            tenureTextView = itemView.findViewById(R.id.tenureTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickLister.onItemClick(v, getAdapterPosition());

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public String getloanrepayDays(String mode, String daysCount){
        String returnvalue = "";
        if(mode.equalsIgnoreCase("Daily")){
            returnvalue = "Daily Repay Loan for "+daysCount+" Days";
        }
        else if(mode.equalsIgnoreCase("Weekly")){
            returnvalue = "Weekly Repay Loan for "+ (Integer.parseInt(daysCount))/7 +" Weeks";
        }else if(mode.equalsIgnoreCase("every-15-days")){
            returnvalue = "Repay in Every-15-days for "+ (Integer.parseInt(daysCount))/15 +" fortnight";
        }else if(mode.equalsIgnoreCase("monthly")){
            returnvalue = "Monthly Repay Loan for "+ (Integer.parseInt(daysCount))/30 +" months";
        }

        return returnvalue;
    }

}
