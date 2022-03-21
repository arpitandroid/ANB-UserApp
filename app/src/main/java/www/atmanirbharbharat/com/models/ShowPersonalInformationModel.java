package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShowPersonalInformationModel {
    @SerializedName("status")
    private int status;

    @SerializedName("error")
    private String error;

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public Data getData() {
        return data;
    }

    @SerializedName("data")
    private Data data;

    public class Data implements Serializable{
        @SerializedName("userid")
        private String userId;

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("email")
        private String email;

        @SerializedName("password")
        private String password;

        @SerializedName("token")
        private String token;

        @SerializedName("mobile")
        private String mobile;

        @SerializedName("profile_image")
        private String profileImage;

        public String getAvatarImage() {
            return avatarImage;
        }

        @SerializedName("avatar_img")
        private String avatarImage;

        @SerializedName("adhar_card_front")
        private String adharCardFront;

        @SerializedName("adhar_card_back")
        private String adharCardBack;
        @SerializedName("address")
        private String address;

        @SerializedName("web_user_status")
        private String webUserStatus;

        @SerializedName("deviceId")
        private String deviceId;

        @SerializedName("dob")
        private String dob;

        @SerializedName("deviceType")
        private String deviceType;

        @SerializedName("fcm_token")
        private String fcm_token;

        @SerializedName("userCreationDate")
        private String userCreationDate;

        @SerializedName("pastModifiedDate")
        private String pastModifiedDate;

        @SerializedName("p_c_status")
        private String p_c_status;

        @SerializedName("status")
        private String status;

        @SerializedName("city")
        private String city;

        @SerializedName("country")
        private String country;

        @SerializedName("ecr1")
        private String emergencyContactRelation1;

        @SerializedName("aadhar_no")
        private String aadharNo;

        @SerializedName("ecr2")
        private String emergencyContactRelation2;

        @SerializedName("ec1")
        private String emergencyContact1;

        public String getUserId() {
            return userId;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
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

        public String getAdharCardFront() {
            return adharCardFront;
        }

        public String getAdharCardBack() {
            return adharCardBack;
        }

        public String getAddress() {
            return address;
        }

        public String getWebUserStatus() {
            return webUserStatus;
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

        public String getFcm_token() {
            return fcm_token;
        }

        public String getUserCreationDate() {
            return userCreationDate;
        }

        public String getPastModifiedDate() {
            return pastModifiedDate;
        }

        public String getP_c_status() {
            return p_c_status;
        }

        public String getStatus() {
            return status;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getEmergencyContactRelation1() {
            return emergencyContactRelation1;
        }

        public String getAadharNo() {
            return aadharNo;
        }

        public String getEmergencyContactRelation2() {
            return emergencyContactRelation2;
        }

        public String getEmergencyContact1() {
            return emergencyContact1;
        }

        public String getEmergencyContact2() {
            return emergencyContact2;
        }

        public String getSocialStatus() {
            return socialStatus;
        }

        public String getAdminEmailShoot() {
            return adminEmailShoot;
        }

        @SerializedName("ec2")
        private String emergencyContact2;

        @SerializedName("socialStatus")
        private String socialStatus;

        @SerializedName("admin_email_shoot")
        private String adminEmailShoot;
    }

}
