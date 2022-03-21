package www.atmanirbharbharat.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import www.atmanirbharbharat.com.LoanPaymentDetailActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.models.LoanPaymentsGetApiModel;

public class PaymentsListAdapter extends RecyclerView.Adapter<PaymentsListAdapter.ViewHolder> {
    private Context context;
    private LoanPaymentsGetApiModel loanPayments;

    public PaymentsListAdapter(Context context, LoanPaymentsGetApiModel loanPayments) {
        this.context = context;
        this.loanPayments = loanPayments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_paid_item_layout, parent, false);
        ViewHolder cardViewHolder = new ViewHolder(view);
        return cardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        SimpleDateFormat DateFor ;
//        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//
//        DateFor = new SimpleDateFormat("E, dd MMM yyyy");

        holder.loanAmountText.setText(String.valueOf(loanPayments.getData().getLoanPaidData().get(position).getAmount_received()) + " -/  (For Loan ID - "+ loanPayments.getData().getLoanPaidData().get(position).getLoanAppliedId()+" )");

        if(loanPayments.getData().getLoanPaidData().get(position).getAmount_received_by().equalsIgnoreCase("MANAGER")){
            holder.paidByTextView.setText(loanPayments.getData().getLoanPaidData().get(position).getManager_name()+" ( Manager )");
        }else{
            holder.paidByTextView.setText("Management team");

        }


        SimpleDateFormat DateFor ;
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd");

        DateFor = new SimpleDateFormat("E, dd MMM yyyy");

        if (loanPayments.getData().getLoanPaidData().get(position).getAmount_received_at()!=null){
            try {
                holder.paymentDateTextView.setText( DateFor.format(fromUser.parse(loanPayments.getData().getLoanPaidData().get(position).getAmount_received_at())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        holder.paymentStatusTextView.setText("Payment Recived");

    }


    @Override
    public int getItemCount() {
        return loanPayments.getData().getLoanPaidData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView loanAmountText;
        private TextView paidByTextView;
        private TextView paymentDateTextView;
        private TextView paymentStatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loanAmountText = itemView.findViewById(R.id.loanAmountText);
            paidByTextView = itemView.findViewById(R.id.paidByTextView);
            paymentDateTextView = itemView.findViewById(R.id.paymentDateTextView);
            paymentStatusTextView = itemView.findViewById(R.id.paymentStatusTextView);
        }

    }



}
