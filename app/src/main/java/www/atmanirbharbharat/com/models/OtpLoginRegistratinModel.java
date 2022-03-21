package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtpLoginRegistratinModel {
    @SerializedName("status")
    private int status;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }


    public class Data implements Serializable{
        @SerializedName("otp_id")
        private String otpId;

        @SerializedName("mobile_number")
        private String mobileNumber;

        @SerializedName("otp")
        private String otp;

        @SerializedName("created_date")
        private String createdDate;

        @SerializedName("past_modified_date")
        private String pastModifiedDate;

        @SerializedName("status")
        private String status;

        @SerializedName("usertype")
        private String userType;

        public String getOtpId() {
            return otpId;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public String getOtp() {
            return otp;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public String getPastModifiedDate() {
            return pastModifiedDate;
        }

        public String getStatus() {
            return status;
        }

        public String getUserType() {
            return userType;
        }
    }

}
