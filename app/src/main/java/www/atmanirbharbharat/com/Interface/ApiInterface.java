package www.atmanirbharbharat.com.Interface;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import www.atmanirbharbharat.com.models.AdhaarCardBackModel;
import www.atmanirbharbharat.com.models.AdhaarCardFrontModel;
import www.atmanirbharbharat.com.models.AdhaarCardSelfieModel;
import www.atmanirbharbharat.com.models.AdminBankDetailsModel;
import www.atmanirbharbharat.com.models.ApplyExtensionModel;
import www.atmanirbharbharat.com.models.ApplyLoanModel;
import www.atmanirbharbharat.com.models.CheckPassbookImageModel;
import www.atmanirbharbharat.com.models.CurrentLoanDetailsGetModel;
import www.atmanirbharbharat.com.models.HomeModel;
import www.atmanirbharbharat.com.models.List_Loanpaidhistory;
import www.atmanirbharbharat.com.models.LoanPaymentsGetApiModel;
import www.atmanirbharbharat.com.models.OtpLoginRegistratinModel;
import www.atmanirbharbharat.com.models.OtpVerificationModel;
import www.atmanirbharbharat.com.models.PaySlipImageModel;
import www.atmanirbharbharat.com.models.PersonalInformationModel;
import www.atmanirbharbharat.com.models.PostPaymentApiModel;
import www.atmanirbharbharat.com.models.Res_ROI;
import www.atmanirbharbharat.com.models.SaveContactModel;
import www.atmanirbharbharat.com.models.ShowPersonalInformationModel;
import www.atmanirbharbharat.com.models.UserLoanListModel;

public interface ApiInterface {

    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Login/send_otp")
    @FormUrlEncoded
    Call<OtpLoginRegistratinModel> otpLoginRegistrationApi(@FieldMap Map<String, String> params);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Login/otp_verify")
    @FormUrlEncoded
    Call<OtpVerificationModel> otpVerificationApi(@FieldMap Map<String, String> params);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @Multipart
    @POST("api/user/Details/adhar_card_front_upload")
    Call<AdhaarCardFrontModel> uploadAdhaarFrontImage(@Part("token") RequestBody token, @Part MultipartBody.Part adhar_card_front);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @Multipart
    @POST("api/user/Details/adhar_card_back_upload")
    Call<AdhaarCardBackModel> uploadAdhaarBackImage(@Part("token") RequestBody token, @Part MultipartBody.Part adhar_card_back);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @Multipart
    @POST("api/user/Details/pan_card_image_upload")
    Call<AdhaarCardSelfieModel> uploadPancardimage(@Part("token") RequestBody token, @Part MultipartBody.Part pan_card_image);



    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Details/profile_edit")
    @FormUrlEncoded
    Call<PersonalInformationModel> personalInformationUpdateApi(@FieldMap Map<String, String> params);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @GET("api/user/Details/get_profile_details")
    Call<ShowPersonalInformationModel> showPersonalInformationApi(@Query("token") String token);



    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @Multipart
    @POST("api/user/Details/check_image_upload")
    Call<CheckPassbookImageModel> uploadPassbookOrCheckImageApi(@Part("token") RequestBody token, @Part MultipartBody.Part check_image);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @Multipart
    @POST("api/user/Details/passbook_image_upload")
    Call<PaySlipImageModel> uploadPaySlipImageApi(@Part("token") RequestBody token,@Part MultipartBody.Part passbook_image);

    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @GET("api/user/Home")
    Call<HomeModel> getHomeScreenDataApi(@Query("token") String token);



    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @GET("api/user/Loan/user_loan_list")
    Call<UserLoanListModel> getUserLoanList(@Query("token") String token);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Loan/apply_loan")
    @FormUrlEncoded
    Call<ApplyLoanModel> applyLoanApi(@Field("token") String token, @Field("loan_id") String loan_id, @Field("deduct_lic_amount") String deduct_lic_status);



    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Loan/extension")
    @FormUrlEncoded
    Call<ApplyExtensionModel> applyLoanExtensionApi(@Field("token") String token, @Field("loan_id") String loan_id);



    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
//    @GET("api/user/Loan")
    @GET("api/user/Loan/index")
    Call<CurrentLoanDetailsGetModel> currentLoanDetailsApi(@Query("token") String token, @Query("loan_id") String loan_id);



    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @GET("api/user/Loan/bank_detail")
    Call<AdminBankDetailsModel> adminBankDetailsApi(@Query("token") String token);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Loan/payment")
    @FormUrlEncoded
    Call<PostPaymentApiModel> postPaymentApi(@FieldMap Map<String, String> params);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @GET("api/user/Loan/loan_payments")  // TO BE CALLED
    Call<List_Loanpaidhistory> loanPaymentsGetApi(@Query("token") String token, @Query("loan_id") String loanid);

    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @GET("api/user/Loan/all_payments")
    Call<LoanPaymentsGetApiModel> GetAllPayments(@Query("token") String token);

    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Details/save_contact")
    @FormUrlEncoded
    Call<SaveContactModel> postContactsList(@Field("token") String token,@Field("contact") String contact);


    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Loan/apply_manual_loan")
    @FormUrlEncoded
    Call<ApplyLoanModel> ApplyManualLoan(@Field("token") String token,
                                         @Field("amount") String amount,
                                         @Field("loan_duration") String loan_duration,
                                         @Field("payment_mode") String payment_mode,
                                         @Field("deduct_lic_amount") String licamnt);

    @Headers({
            "x-api-key: R9OH5BHSKP8XELMQGMC6OBAZ",
            "Authorization: Basic T2ZmZXItQWRtaW46b2ZmZXJfYWRtaW5fMTIzKjg5MA==",
            "cache-control: no-cache"
    })
    @POST("api/user/Loan/manual_loan_setting")
    @FormUrlEncoded
    Call<Res_ROI> Get_ROI_ManualAmnt(@Field("token") String token,
                                     @Field("amount") String amount,
                                     @Field("loan_duration") String loan_duration,
                                     @Field("payment_mode") String payment_mode,
                                     @Field("deduct_lic_amount") String licamnt);
}