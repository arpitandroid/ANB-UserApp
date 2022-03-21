package www.atmanirbharbharat.com.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PersonalInformationModel {
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

    @SerializedName("status")
    private int status;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Data data;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable{
        @SerializedName("userid")
        private String userId;

        @SerializedName("first_name")
        private String firstName;

        @SerializedName("last_name")
        private String lastName;

        @SerializedName("email")
        private String email;

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }

        public void setAdharCardFront(String adharCardFront) {
            this.adharCardFront = adharCardFront;
        }

        public void setAdharCardBack(String adharCardBack) {
            this.adharCardBack = adharCardBack;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setWebUserStatus(String webUserStatus) {
            this.webUserStatus = webUserStatus;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public void setFcm_token(String fcm_token) {
            this.fcm_token = fcm_token;
        }

        public void setUserCreationDate(String userCreationDate) {
            this.userCreationDate = userCreationDate;
        }

        public void setPastModifiedDate(String pastModifiedDate) {
            this.pastModifiedDate = pastModifiedDate;
        }

        public void setP_c_status(String p_c_status) {
            this.p_c_status = p_c_status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setEmergencyContactRelation1(String emergencyContactRelation1) {
            this.emergencyContactRelation1 = emergencyContactRelation1;
        }

        public void setAadharNo(String aadharNo) {
            this.aadharNo = aadharNo;
        }

        public void setEmergencyContactRelation2(String emergencyContactRelation2) {
            this.emergencyContactRelation2 = emergencyContactRelation2;
        }

        public void setEmergencyContact1(String emergencyContact1) {
            this.emergencyContact1 = emergencyContact1;
        }

        public void setEmergencyContact2(String emergencyContact2) {
            this.emergencyContact2 = emergencyContact2;
        }

        public void setSocialStatus(String socialStatus) {
            this.socialStatus = socialStatus;
        }

        public void setAdminEmailShoot(String adminEmailShoot) {
            this.adminEmailShoot = adminEmailShoot;
        }

        public void setBasicDetailsApprovalStatus(String basicDetailsApprovalStatus) {
            this.basicDetailsApprovalStatus = basicDetailsApprovalStatus;
        }

        public void setBasicDetailsApprovalComment(String basicDetailsApprovalComment) {
            this.basicDetailsApprovalComment = basicDetailsApprovalComment;
        }

        public void setEmergencyContactStatus(String emergencyContactStatus) {
            this.emergencyContactStatus = emergencyContactStatus;
        }

        public void setEmergencyContactComment(String emergencyContactComment) {
            this.emergencyContactComment = emergencyContactComment;
        }

        public void setBankApprovalStatus(String bankApprovalStatus) {
            this.bankApprovalStatus = bankApprovalStatus;
        }

        public void setBankApprovalComment(String bankApprovalComment) {
            this.bankApprovalComment = bankApprovalComment;
        }

        public void setPaySlipApproval(String paySlipApproval) {
            this.paySlipApproval = paySlipApproval;
        }

        public void setPaySlipComment(String paySlipComment) {
            this.paySlipComment = paySlipComment;
        }

        public void setDocumentVerificationStatus(String documentVerificationStatus) {
            this.documentVerificationStatus = documentVerificationStatus;
        }

        public void setDocumentVerificationComment(String documentVerificationComment) {
            this.documentVerificationComment = documentVerificationComment;
        }

        public void setSelfieApprovalStatus(String selfieApprovalStatus) {
            this.selfieApprovalStatus = selfieApprovalStatus;
        }

        public void setSelfieApprovalComment(String selfieApprovalComment) {
            this.selfieApprovalComment = selfieApprovalComment;
        }

        public void setCompleteProfileApprovalStatus(String completeProfileApprovalStatus) {
            this.completeProfileApprovalStatus = completeProfileApprovalStatus;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

        public void setPaySlipImage(String paySlipImage) {
            this.paySlipImage = paySlipImage;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public void setAccHolderName(String accHolderName) {
            this.accHolderName = accHolderName;
        }

        public void setIFSCCode(String IFSCCode) {
            this.IFSCCode = IFSCCode;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public void setCheckImage(String checkImage) {
            this.checkImage = checkImage;
        }

        public void setOfficeTelephone(String officeTelephone) {
            this.officeTelephone = officeTelephone;
        }

        public void setIndustry(String industry) {
            this.industry = industry;
        }

        public void setYearsOfWorking(String yearsOfWorking) {
            this.yearsOfWorking = yearsOfWorking;
        }

        public void setCompanyAddress(String companyAddress) {
            this.companyAddress = companyAddress;
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

        @SerializedName("bda_comment")
        private String basicDetailsApprovalComment;

        @SerializedName("ecv_status")
        private String emergencyContactStatus;

        @SerializedName("ecv_comment")
        private String emergencyContactComment;

        @SerializedName("ba_status")
        private String bankApprovalStatus;

        @SerializedName("ba_comment")
        private String bankApprovalComment;

        @SerializedName("pa_status")
        private String paySlipApproval;

        @SerializedName("pa_comment")
        private String paySlipComment;

        @SerializedName("docv_status")
        private String documentVerificationStatus;

        @SerializedName("docv_comment")
        private String documentVerificationComment;

        @SerializedName("sa_status")
        private String selfieApprovalStatus;

        @SerializedName("sa_comment")
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
}
