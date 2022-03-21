package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class  LoanList implements Serializable {

    @SerializedName("loan_id")
    private String loanId;

    @SerializedName("amount")
    private int amount;


    @SerializedName("loan_name")
    private String loanName;

    @SerializedName("loan_duration")
    private String loan_duration;

    @SerializedName("emi_amount")
    private String emi_amount;

    public String getLoan_duration() {
        return loan_duration;
    }

    public String getEmi_amount() {
        return emi_amount;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    @SerializedName("payment_mode")
    private String payment_mode;

    @SerializedName("rate_of_interest")
    private Float ROI;

    @SerializedName("process_fee_percent")
    private String processFeePercent;

    @SerializedName("processing_fee")
    private int processingFee;

    @SerializedName("gst")
    private int gst;

    @SerializedName("tenure")
    private String tenure;

    @SerializedName("penalty")
    private String penalty;

    @SerializedName("extension_charges")
    private String extensionCharges;

    @SerializedName("extension_days")
    private String extensionDays;

    @SerializedName("penalty_days")
    private String penaltyDays;


    public String getLoanId() {
        return loanId;
    }

    public int getAmount() {
        return amount;
    }

    public String getProcessFeePercent() {
        return processFeePercent;
    }

    public int getProcessingFee() {
        return processingFee;
    }

    public int getGst() {
        return gst;
    }

    public String getLoanName() {
        return loanName;
    }

    public Float getROI() {
        return ROI;
    }

    public String getTenure() {
        return tenure;
    }

    public String getPenalty() {
        return penalty;
    }

    public String getExtensionCharges() {
        return extensionCharges;
    }

    public String getExtensionDays() {
        return extensionDays;
    }

    public String getPenaltyDays() {
        return penaltyDays;
    }
}

