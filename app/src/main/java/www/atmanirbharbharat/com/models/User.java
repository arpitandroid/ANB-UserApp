package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("userid")
    private String userId;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("pan_card_image")
    private String pan_card_image;

    @SerializedName("pan_card_approved_status")
    private String pan_card_approved_status;

    @SerializedName("passbook_approved_status")
    private String passbook_approved_status;

    @SerializedName("pan_card_approved_status_comment")
    private String pan_card_approved_status_comment;

    public String getPassbook_approved_status() {
        return passbook_approved_status;
    }

    public String getPan_card_approved_status() {
        return pan_card_approved_status;
    }

    public String getPan_card_approved_status_comment() {
        return pan_card_approved_status_comment;
    }

    public String getPan_card_image() {
        return pan_card_image;
    }

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

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("mobile")
    private String mobile;

    @SerializedName("profile_image")
    private String profileImage;

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

    @SerializedName("ec2")
    private String emergencyContact2;

    @SerializedName("socialStatus")
    private String socialStatus;

    @SerializedName("admin_email_shoot")
    private String adminEmailShoot;

    @SerializedName("bda_status")
    private String basicDetailsApprovalStatus;

    @SerializedName("bda_status_comment")
    private String basicDetailsApprovalComment;

    @SerializedName("ecv_status")
    private String emergencyContactStatus;

    @SerializedName("ecv_status_comment")
    private String emergencyContactComment;

    @SerializedName("ba_status")
    private String bankApprovalStatus;

    @SerializedName("ba_status_comment")
    private String bankApprovalComment;

    @SerializedName("pa_status")
    private String paySlipApproval;

    @SerializedName("pa_status_comment")
    private String paySlipComment;

    @SerializedName("docv_status")
    private String documentVerificationStatus;

    @SerializedName("docv_status_comment")
    private String documentVerificationComment;

    @SerializedName("sa_status")
    private String selfieApprovalStatus;

    @SerializedName("sa_status_comment")
    private String selfieApprovalComment;

    @SerializedName("cpa_status")
    private String completeProfileApprovalStatus;

    @SerializedName("job_type")
    private String jobType;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("designation")
    private String designation;

    @SerializedName("salary")
    private String salary;

    @SerializedName("pay_slip_image")
    private String paySlipImage;

    @SerializedName("bank_name")
    private String bankName;

    @SerializedName("acc_holder_name")
    private String accHolderName;

    @SerializedName("ifcs_code")
    private String IFSCCode;

    @SerializedName("acc_no")
    private String accountNumber;

    public String getBasicDetailsApprovalStatus() {
        return basicDetailsApprovalStatus;
    }

    public String getBasicDetailsApprovalComment() {
        return basicDetailsApprovalComment;
    }

    public String getEmergencyContactStatus() {
        return emergencyContactStatus;
    }

    public String getEmergencyContactComment() {
        return emergencyContactComment;
    }

    public String getBankApprovalStatus() {
        return bankApprovalStatus;
    }

    public String getBankApprovalComment() {
        return bankApprovalComment;
    }

    public String getPaySlipApproval() {
        return paySlipApproval;
    }

    public String getPaySlipComment() {
        return paySlipComment;
    }

    public String getDocumentVerificationStatus() {
        return documentVerificationStatus;
    }

    public String getDocumentVerificationComment() {
        return documentVerificationComment;
    }

    public String getSelfieApprovalStatus() {
        return selfieApprovalStatus;
    }

    public String getSelfieApprovalComment() {
        return selfieApprovalComment;
    }

    public String getCompleteProfileApprovalStatus() {
        return completeProfileApprovalStatus;
    }

    public String getJobType() {
        return jobType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getSalary() {
        return salary;
    }

    public String getPaySlipImage() {
        return paySlipImage;
    }

    public String getBankName() {
        return bankName;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public String getIFSCCode() {
        return IFSCCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCheckImage() {
        return checkImage;
    }

    @SerializedName("check_image")
    private String checkImage;

    @SerializedName("office_telephone")
    private String officeTelephone;

    @SerializedName("industry")
    private String industry;

    @SerializedName("years_of_working")
    private String yearsOfWorking;

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public String getIndustry() {
        return industry;
    }

    public String getYearsOfWorking() {
        return yearsOfWorking;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    @SerializedName("company_address")
    private String companyAddress;

}
