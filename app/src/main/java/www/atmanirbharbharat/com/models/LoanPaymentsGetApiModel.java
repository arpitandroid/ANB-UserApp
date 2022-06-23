package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class LoanPaymentsGetApiModel {
    @SerializedName("status")
    private int status;

    @SerializedName("error")
    private String error;

    @SerializedName("data")
    private Data data;

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    public class Data implements Serializable {
        @SerializedName("payed_payments")
        private ArrayList<paidPaymentData> loanPaidData;

        @SerializedName("upcoming_payment")
        private ArrayList<paymentData> upcommingPayment;

        public ArrayList<paidPaymentData> getLoanPaidData() {
            return loanPaidData;
        }

        public ArrayList<paymentData> getUpcommingPayment() {
            return upcommingPayment;
        }
    }

    public class paidPaymentData implements Serializable {

        @SerializedName("id")
        private String loanPaidId;

        @SerializedName("loan_apply_id")
        private String loanAppliedId;

        @SerializedName("user_id")
        private String userId;

        @SerializedName("amount_received")
        private String amount_received;

        @SerializedName("amount_received_by")
        private String amount_received_by;

        @SerializedName("manager_name")
        private String manager_name;

        @SerializedName("payment_date")
        private String amount_received_at;

        @SerializedName("bounce_charges")
        private String bounce_charges;

        @SerializedName("initial_amount")
        private String initialamount;

        public String getInitialamount() {
            return initialamount;
        }

        public void setInitialamount(String initialamount) {
            this.initialamount = initialamount;
        }

        public String getLoanPaidId() {
            return loanPaidId;
        }

        public String getLoanAppliedId() {
            return loanAppliedId;
        }

        public String getUserId() {
            return userId;
        }

        public String getAmount_received() {
            return amount_received;
        }

        public String getAmount_received_by() {
            return amount_received_by;
        }

        public String getManager_name() {
            return manager_name;
        }

        public String getAmount_received_at() {
            return amount_received_at;
        }

        public String getBounce_charges() {
            return bounce_charges;
        }
    }

    public class paymentData implements Serializable {
        @SerializedName("loan_apply_id")
        private String loan_apply_id;

        @SerializedName("amount")
        private String amount;

        @SerializedName("payment_date")
        private String payment_date;

        @SerializedName("initial_amount")
        private String initialamount;

        public String getInitialamount() {
            return initialamount;
        }

        public void setInitialamount(String initialamount) {
            this.initialamount = initialamount;
        }

        public String getLoan_apply_id() {
            return loan_apply_id;
        }

        public String getAmount() {
            return amount;
        }

        public String getPayment_date() {
            return payment_date;
        }
    }
}
