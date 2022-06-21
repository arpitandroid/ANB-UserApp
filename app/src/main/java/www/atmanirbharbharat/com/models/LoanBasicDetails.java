package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoanBasicDetails implements Serializable {
    @SerializedName("la_id")
    private String loanAppliedId;

    @SerializedName("loan_id")
    private String  loanId;

    @SerializedName("user_id")
    private String  userId;

    @SerializedName("loan_status")
    private String  loanStatus;

    @SerializedName("payable_amt")
    private String  payableAmount;

    @SerializedName("loan_start_date")
    private String  loanStartDate;

    @SerializedName("loan_end_date")
    private String  loanEndDate;

    @SerializedName("reject_comment")
    private String  rejectComment;

    @SerializedName("ext_status")
    private String  extStatus;

    @SerializedName("ext_count")
    private String  extCount;

    @SerializedName("panelty_status")
    private String  paneltyStatus;

    @SerializedName("loan_panelty_days")
    private String  loanPaneltyDays;

    @SerializedName("loan_panelty_amount")
    private String  loanPaneltyAmount;

    @SerializedName("la_doc")
    private String  loanAppliedDateOfCreation;

    @SerializedName("la_dom")
    private String  loanAppliedDateOfModification;

    @SerializedName("la_status")
    private String  loanAppliedStatus;

    @SerializedName("money_paid")
    private String  moneyPaid;

    @SerializedName("money_return")
    private String  moneyReturn;

    @SerializedName("lsid")
    private String  lsid;

    @SerializedName("amount")
    private int  amount;

    @SerializedName("process_fee_percent")
    private String  processingFeePercent;

    @SerializedName("processing_fee")
    private int  processingFee;

    @SerializedName("rate_of_interest")
    private Float ROI;

    @SerializedName("tenure")
    private String  tenure;

    @SerializedName("penalty")
    private String  penalty;

    @SerializedName("extension_charges")
    private String  extensionCharges;

    @SerializedName("ext_days")
    private String  totalExtensionDays;

    @SerializedName("ext_charges")
    private String  totalExtensionCharges;

    @SerializedName("extension_days")
    private String  extensionDays;

    @SerializedName("penalty_days")
    private String  penaltyDays;

    @SerializedName("ls_doc")
    private String  ls_doc;

    @SerializedName("ls_dom")
    private String  ls_dom;

    @SerializedName("ls_status")
    private String  ls_status;

    @SerializedName("extension_percent")
    private String  extensionPercent;

    @SerializedName("loan_closer_amount")
    private String  loanCloserAmount;

    @SerializedName("remaining_balance")
    private String  remaining_balance;

    @SerializedName("payment_mode")
    private String payment_mode;

    @SerializedName("emi_amount")
    private String emi_amount;

    @SerializedName("extension_of")
    @Expose
    private String extensionOf;
    @SerializedName("child_la_id")
    @Expose
    private String childLaId;
    @SerializedName("initial_amount")
    @Expose
    private String initialamount;

    public String getInitialamount() {
        return initialamount;
    }

    public void setInitialamount(String initialamount) {
        this.initialamount = initialamount;
    }

    public void setLoanAppliedId(String loanAppliedId) {
        this.loanAppliedId = loanAppliedId;
    }

    public String getExtensionOf() {
        return extensionOf;
    }

    public void setExtensionOf(String extensionOf) {
        this.extensionOf = extensionOf;
    }

    public String getChildLaId() {
        return childLaId;
    }

    public void setChildLaId(String childLaId) {
        this.childLaId = childLaId;
    }

    public String getEmi_amount() {
        return emi_amount;
    }

    public Float getROI() {
        return ROI;
    }

    public String getRemaining_balance() {
        return remaining_balance;
    }

    @SerializedName("loan_duration")
    private String loan_duration;


    public String getTotalExtensionDays() {
        return totalExtensionDays;
    }

    public String getTotalExtensionCharges() {
        return totalExtensionCharges;
    }

    public String getLoanCloserAmount() {
        return loanCloserAmount;
    }

    public String getExtStatus() {
        return extStatus;
    }

    public String getExtCount() {
        return extCount;
    }

    public String getPaneltyStatus() {
        return paneltyStatus;
    }

    public String getLoanPaneltyDays() {
        return loanPaneltyDays;
    }

    public String getLoanPaneltyAmount() {
        return loanPaneltyAmount;
    }

    public String getExtensionPercent() {
        return extensionPercent;
    }

    public String getLoanAppliedId() {
        return loanAppliedId;
    }

    public String getLoanId() {
        return loanId;
    }

    public String getUserId() {
        return userId;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public String getLoanStartDate() {
        return loanStartDate;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public String getRejectComment() {
        return rejectComment;
    }

    public String getLoanAppliedDateOfCreation() {
        return loanAppliedDateOfCreation;
    }

    public String getLoanAppliedDateOfModification() {
        return loanAppliedDateOfModification;
    }

    public String getLoanAppliedStatus() {
        return loanAppliedStatus;
    }

    public String getMoneyPaid() {
        return moneyPaid;
    }

    public String getMoneyReturn() {
        return moneyReturn;
    }

    public String getLsid() {
        return lsid;
    }

    public int getAmount() {
        return amount;
    }

    public String getProcessingFeePercent() {
        return processingFeePercent;
    }

    public int getProcessingFee() {
        return processingFee;
    }



    public String getPayment_mode() {
        return payment_mode;
    }

    public String getLoan_duration() {
        return loan_duration;
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

    public String getLs_doc() {
        return ls_doc;
    }

    public String getLs_dom() {
        return ls_dom;
    }

    public String getLs_status() {
        return ls_status;
    }


}
