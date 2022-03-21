package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class List_Loanpaidhistory {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private String message;
    @SerializedName("message")
    @Expose
    private Boolean error;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

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

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }


    public class Datum {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("loan_apply_id")
        @Expose
        private String loanApplyId;
        @SerializedName("amount_received_by")
        @Expose
        private String amountReceivedBy;
        @SerializedName("manager_id")
        @Expose
        private String managerId;
        @SerializedName("amount")
        @Expose
        private String amount;
        @SerializedName("amount_received")
        @Expose
        private String amountReceived;
        @SerializedName("amount_received_at")
        @Expose
        private String amountReceivedAt;
        @SerializedName("payment_date")
        @Expose
        private String paymentDate;
        @SerializedName("bounce_charges")
        @Expose
        private Object bounceCharges;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("manager_name")
        @Expose
        private String managerName;
        @SerializedName("lp_doc")
        @Expose
        private String lpDoc;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getLoanApplyId() {
            return loanApplyId;
        }

        public void setLoanApplyId(String loanApplyId) {
            this.loanApplyId = loanApplyId;
        }

        public String getAmountReceivedBy() {
            return amountReceivedBy;
        }

        public void setAmountReceivedBy(String amountReceivedBy) {
            this.amountReceivedBy = amountReceivedBy;
        }

        public String getManagerId() {
            return managerId;
        }

        public void setManagerId(String managerId) {
            this.managerId = managerId;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getAmountReceived() {
            return amountReceived;
        }

        public void setAmountReceived(String amountReceived) {
            this.amountReceived = amountReceived;
        }

        public String getAmountReceivedAt() {
            return amountReceivedAt;
        }

        public void setAmountReceivedAt(String amountReceivedAt) {
            this.amountReceivedAt = amountReceivedAt;
        }

        public String getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(String paymentDate) {
            this.paymentDate = paymentDate;
        }

        public Object getBounceCharges() {
            return bounceCharges;
        }

        public void setBounceCharges(Object bounceCharges) {
            this.bounceCharges = bounceCharges;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getManagerName() {
            return managerName;
        }

        public void setManagerName(String managerName) {
            this.managerName = managerName;
        }

        public String getLpDoc() {
            return lpDoc;
        }

        public void setLpDoc(String lpDoc) {
            this.lpDoc = lpDoc;
        }

    }
}
