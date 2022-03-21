package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Res_ROI {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("error")
    @Expose
    private Boolean error;

    @SerializedName("data")
    @Expose
    private Data data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {

        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("rate_of_interest")
        @Expose
        private String rateOfInterest;
        @SerializedName("process_fee_percent")
        @Expose
        private String processFeePercent;
        @SerializedName("processing_fee")
        @Expose
        private Integer processingFee;
        @SerializedName("loan_duration")
        @Expose
        private Integer loanDuration;
        @SerializedName("payment_mode")
        @Expose
        private String paymentMode;
        @SerializedName("bouncing_charges_percent")
        @Expose
        private String bouncingChargesPercent;
        @SerializedName("bouncing_charges")
        @Expose
        private Integer bouncingCharges;
        @SerializedName("emi_amount")
        @Expose
        private Integer emiAmount;
        @SerializedName("total_emi_count")
        @Expose
        private Integer totalEmiCount;
        @SerializedName("disbursal_amount")
        @Expose
        private Integer disbursalAmount;
        @SerializedName("loan_closer_amount")
        @Expose
        private Integer loanCloserAmount;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getRateOfInterest() {
            return rateOfInterest;
        }

        public void setRateOfInterest(String rateOfInterest) {
            this.rateOfInterest = rateOfInterest;
        }

        public String getProcessFeePercent() {
            return processFeePercent;
        }

        public void setProcessFeePercent(String processFeePercent) {
            this.processFeePercent = processFeePercent;
        }

        public Integer getProcessingFee() {
            return processingFee;
        }

        public void setProcessingFee(Integer processingFee) {
            this.processingFee = processingFee;
        }

        public Integer getLoanDuration() {
            return loanDuration;
        }

        public void setLoanDuration(Integer loanDuration) {
            this.loanDuration = loanDuration;
        }

        public String getPaymentMode() {
            return paymentMode;
        }

        public void setPaymentMode(String paymentMode) {
            this.paymentMode = paymentMode;
        }

        public String getBouncingChargesPercent() {
            return bouncingChargesPercent;
        }

        public void setBouncingChargesPercent(String bouncingChargesPercent) {
            this.bouncingChargesPercent = bouncingChargesPercent;
        }

        public Integer getBouncingCharges() {
            return bouncingCharges;
        }

        public void setBouncingCharges(Integer bouncingCharges) {
            this.bouncingCharges = bouncingCharges;
        }

        public Integer getEmiAmount() {
            return emiAmount;
        }

        public void setEmiAmount(Integer emiAmount) {
            this.emiAmount = emiAmount;
        }

        public Integer getTotalEmiCount() {
            return totalEmiCount;
        }

        public void setTotalEmiCount(Integer totalEmiCount) {
            this.totalEmiCount = totalEmiCount;
        }

        public Integer getDisbursalAmount() {
            return disbursalAmount;
        }

        public void setDisbursalAmount(Integer disbursalAmount) {
            this.disbursalAmount = disbursalAmount;
        }

        public Integer getLoanCloserAmount() {
            return loanCloserAmount;
        }

        public void setLoanCloserAmount(Integer loanCloserAmount) {
            this.loanCloserAmount = loanCloserAmount;
        }

    }

}
