package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdminBankDetailsModel {
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

    public class Data implements Serializable{
        @SerializedName("id")
        private String id;

        @SerializedName("Email")
        private String email;

        @SerializedName("Password")
        private String password;

        @SerializedName("bank_name")
        private String bankName;

        @SerializedName("account_no")
        private String accountNo;

        @SerializedName("account_holder_name")
        private String accountHolderName;

        @SerializedName("ifsc_code")
        private String IFSCCode;

        @SerializedName("upi")
        private String upi;

        @SerializedName("mobile")
        private String mobile;

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }

        public String getBankName() {
            return bankName;
        }

        public String getAccountNo() {
            return accountNo;
        }

        public String getIFSCCode() {
            return IFSCCode;
        }

        public String getUpi() {
            return upi;
        }

        public String getMobile() {
            return mobile;
        }
    }
}
