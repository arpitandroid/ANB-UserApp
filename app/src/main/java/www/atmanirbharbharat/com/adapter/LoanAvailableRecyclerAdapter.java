package www.atmanirbharbharat.com.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.models.LoanList;

public class LoanAvailableRecyclerAdapter extends RecyclerView.Adapter<LoanAvailableRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<LoanList> list;
    private static OnItemClickListener onItemClickLister;

    public LoanAvailableRecyclerAdapter(Context context, ArrayList<LoanList> mList) {
        this.context = context;
        this.list = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_available_item_layout, parent, false);
        ViewHolder cardViewHolder = new ViewHolder(view);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.loanAmountText.setText(String.valueOf(list.get(position).getAmount())+" "+"-/");
        holder.tenureTextView.setText(getloanrepayDays(list.get(position).getPayment_mode(),list.get(position).getLoan_duration()));
//        if (!list.get(position).getEligibility()){
//            holder.rupeesImageView.setColorFilter(ContextCompat.getColor(context, R.color.grey
//            ), android.graphics.PorterDuff.Mode.MULTIPLY);
//        }

    }

    public void setOnItemClickLister(OnItemClickListener onItemClickLister) {
        LoanAvailableRecyclerAdapter.onItemClickLister = onItemClickLister;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView loanAmountText;
        private TextView tenureTextView;
        private ImageView rupeesImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loanAmountText = itemView.findViewById(R.id.loanAmountText);
            tenureTextView = itemView.findViewById(R.id.tenureTextView);
            rupeesImageView = itemView.findViewById(R.id.rupeesImageView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemClickLister.onItemClick(v, getAdapterPosition());

        }
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

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
