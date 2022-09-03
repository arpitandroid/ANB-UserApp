package www.atmanirbharbharat.com.adapter;

import android.annotation.SuppressLint;
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
import java.util.Locale;
import java.util.Objects;

import www.atmanirbharbharat.com.LoanPaymentDetailActivity;
import www.atmanirbharbharat.com.R;
import www.atmanirbharbharat.com.models.LoanPaymentsGetApiModel;

public class PaymentsListAdapter extends RecyclerView.Adapter<PaymentsListAdapter.ViewHolder> {
    private final LoanPaymentsGetApiModel loanPayments;

    public PaymentsListAdapter(LoanPaymentsGetApiModel loanPayments) {
        this.loanPayments = loanPayments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_paid_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        SimpleDateFormat DateFor ;
//        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
//
//        DateFor = new SimpleDateFormat("E, dd MMM yyyy");

        holder.loanAmountText.setText(loanPayments.getData().getLoanPaidData().get(position).getInitialamount() + " -/  (For Loan ID - "+ loanPayments.getData().getLoanPaidData().get(position).getLoanAppliedId()+" )");

        if(loanPayments.getData().getLoanPaidData().get(position).getAmount_received_by()!=null && loanPayments.getData().getLoanPaidData().get(position).getAmount_received_by().equalsIgnoreCase("MANAGER")){
            holder.paidByTextView.setText(loanPayments.getData().getLoanPaidData().get(position).getManager_name()+" ( Manager )");
        }else{
            holder.paidByTextView.setText("Management team");

        }


        SimpleDateFormat DateFor ;
        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        DateFor = new SimpleDateFormat("E, dd MMM yyyy",Locale.getDefault());

        if (loanPayments.getData().getLoanPaidData().get(position).getAmount_received_at()!=null){
            try {
                holder.paymentDateTextView.setText( DateFor.format(Objects.requireNonNull(fromUser.parse(loanPayments.getData().getLoanPaidData().get(position).getAmount_received_at()))));
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
        private final TextView loanAmountText;
        private final TextView paidByTextView;
        private final TextView paymentDateTextView;
        private final TextView paymentStatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            loanAmountText = itemView.findViewById(R.id.loanAmountText);
            paidByTextView = itemView.findViewById(R.id.paidByTextView);
            paymentDateTextView = itemView.findViewById(R.id.paymentDateTextView);
            paymentStatusTextView = itemView.findViewById(R.id.paymentStatusTextView);
        }

    }



}
