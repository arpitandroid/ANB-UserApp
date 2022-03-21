package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OtpVerificationModel {
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

    public class Data implements Serializable {

        @SerializedName("userid")
        private String userId;

        @SerializedName("username")
        private String username;

        @SerializedName("emailId")
        private String emailId;

        @SerializedName("password")
        private String password;

        @SerializedName("token")
        private String token;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("profile_image")
        private String profileImage;

        @SerializedName("userInfo")
        private String userInfo;

        @SerializedName("address")
        private String address;

        @SerializedName("web_user_status")
        private String webUserStatus;

        @SerializedName("web_token")
        private String webToken;

        @SerializedName("install_lat")
        private String installLat;

        @SerializedName("install_long")
        private String installLong;

        @SerializedName("install_city")
        private String installCity;

        @SerializedName("install_state")
        private String installState;

        @SerializedName("install_country")
        private String installCountry;

        @SerializedName("install_address")
        private String installAddress;

        @SerializedName("Install_pincode")
        private String InstallPincode;

        @SerializedName("deviceId")
        private String deviceId;

        @SerializedName("dob")
        private String dob;

        @SerializedName("deviceType")
        private String deviceType;

        @SerializedName("fcm_token")
        private String fcmToken;

        @SerializedName("userCreationDate")
        private String userCreationDate;

        @SerializedName("pastModifiedDate")
        private String pastModifiedDate;

        @SerializedName("socialId")
        private String socialId;

        @SerializedName("socialType")
        private String socialType;

        @SerializedName("gender")
        private String gender;

        @SerializedName("p_c_status")
        private String profileCompletionStatus;

        public String getAadharUploadStatus() {
            return aadharUploadStatus;
        }

        @SerializedName("company_upload_status")
        private String companyUploadStatus;


        @SerializedName("company_approve_status")
        private String companyApproveStatus;

        public String getCompanyUploadStatus() {
            return companyUploadStatus;
        }

        public String getCompanyApproveStatus() {
            return companyApproveStatus;
        }

        @SerializedName("aadhar_upload_status")
        private String aadharUploadStatus;

        @SerializedName("status")
        private String status;

        @SerializedName("city")
        private String city;

        @SerializedName("socialStatus")
        private String socialStatus;

        @SerializedName("admin_email_shoot")
        private String adminEmailShoot;

        public String getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getEmailId() {
            return emailId;
        }

        public String getPassword() {
            return password;
        }

        public String getToken() {
            return token;
        }

        public String getMobile() {
            return mobile;
        }

        public String getProfileImage() {
            return profileImage;
        }

        public String getUserInfo() {
            return userInfo;
        }

        public String getAddress() {
            return address;
        }

        public String getWebUserStatus() {
            return webUserStatus;
        }

        public String getWebToken() {
            return webToken;
        }

        public String getInstallLat() {
            return installLat;
        }

        public String getInstallLong() {
            return installLong;
        }

        public String getInstallCity() {
            return installCity;
        }

        public String getInstallState() {
            return installState;
        }

        public String getInstallCountry() {
            return installCountry;
        }

        public String getInstallAddress() {
            return installAddress;
        }

        public String getInstallPincode() {
            return InstallPincode;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public String getDob() {
            return dob;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public String getUserCreationDate() {
            return userCreationDate;
        }

        public String getPastModifiedDate() {
            return pastModifiedDate;
        }

        public String getSocialId() {
            return socialId;
        }

        public String getSocialType() {
            return socialType;
        }

        public String getGender() {
            return gender;
        }

        public String getProfileCompletionStatus() {
            return profileCompletionStatus;
        }

        public String getStatus() {
            return status;
        }

        public String getCity() {
            return city;
        }

        public String getSocialStatus() {
            return socialStatus;
        }

        public String getAdminEmailShoot() {
            return adminEmailShoot;
        }
    }
}
