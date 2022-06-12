package www.atmanirbharbharat.com.LoanExtention;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_LoanExtList {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("data")
    @Expose
    private ListData data;

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

    public ListData getData() {
        return data;
    }

    public void setData(ListData data) {
        this.data = data;
    }

    public class ListData {

        @SerializedName("extensions_list")
        @Expose
        private ExtensionsList extensionsList;

        public ExtensionsList getExtensionsList() {
            return extensionsList;
        }

        public void setExtensionsList(ExtensionsList extensionsList) {
            this.extensionsList = extensionsList;
        }

    }

    public class ExtensionsList {

        @SerializedName("le_id")
        @Expose
        private String leId;
        @SerializedName("la_id")
        @Expose
        private String laId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("ext_amount")
        @Expose
        private String extAmount;
        @SerializedName("extension_status")
        @Expose
        private String extensionStatus;
        @SerializedName("le_doc")
        @Expose
        private String leDoc;
        @SerializedName("le_dom")
        @Expose
        private String leDom;
        @SerializedName("reject_comment")
        @Expose
        private String rejectComment;
        @SerializedName("ext_duration")
        @Expose
        private String extDuration;
        @SerializedName("ext_payment_mode")
        @Expose
        private String extPaymentMode;
        @SerializedName("new_la_id")
        @Expose
        private String newLaId;

        public String getLeId() {
            return leId;
        }

        public void setLeId(String leId) {
            this.leId = leId;
        }

        public String getLaId() {
            return laId;
        }

        public void setLaId(String laId) {
            this.laId = laId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getExtAmount() {
            return extAmount;
        }

        public void setExtAmount(String extAmount) {
            this.extAmount = extAmount;
        }

        public String getExtensionStatus() {
            return extensionStatus;
        }

        public void setExtensionStatus(String extensionStatus) {
            this.extensionStatus = extensionStatus;
        }

        public String getLeDoc() {
            return leDoc;
        }

        public void setLeDoc(String leDoc) {
            this.leDoc = leDoc;
        }

        public String getLeDom() {
            return leDom;
        }

        public void setLeDom(String leDom) {
            this.leDom = leDom;
        }

        public String getRejectComment() {
            return rejectComment;
        }

        public void setRejectComment(String rejectComment) {
            this.rejectComment = rejectComment;
        }

        public String getExtDuration() {
            return extDuration;
        }

        public void setExtDuration(String extDuration) {
            this.extDuration = extDuration;
        }

        public String getExtPaymentMode() {
            return extPaymentMode;
        }

        public void setExtPaymentMode(String extPaymentMode) {
            this.extPaymentMode = extPaymentMode;
        }

        public String getNewLaId() {
            return newLaId;
        }

        public void setNewLaId(String newLaId) {
            this.newLaId = newLaId;
        }

    }
}
