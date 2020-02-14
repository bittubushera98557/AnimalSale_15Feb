package com.example.lenovo.emptypro.ApiCallClasses;



import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
 import com.example.lenovo.emptypro.ModelClasses.CommonRequestParamsJson;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface MainServices {


    @GET("category/")
    Observable<AllApiResponse.CategoryResponse> allCateListApi();

    @POST("generateOTP/")
    Observable<AllApiResponse.OtpResponse> getOtpApi(
            @HeaderMap Map<String, String> headers,
            @Body CommonRequestParamsJson commonRequestParamsJson);

    /*
    @GET("states/list/all")
        ///states/list/all
    Observable<StateResponse> stateListApi();


    @GET("cityByState/ID/{stateId}")
        //     cityByState/ID/{id}/
    Observable<CityResponse> locationCityListApi(
            @Path("stateId") String stateId);

    @POST("register-one/")
        //register-one
    Observable<RegisterResponse> registerApi(
            @Header("Content-Type") String contentType,
            @Body RegisterJson registerJson);

    @POST("jobs/all/list/")
        // all jobs list post method
    Observable<JobFilterPojo> jobListPostApi(@HeaderMap Map<String, String> headers,
                                             @Body JobListJson jobListJson);

    @GET("jobs/all/list/")
        // all jobs list get method
    Observable<JobFilterPojo> jobListGetApi(@HeaderMap Map<String, String> headers, @Query("page") String pageNumber_str);

    @POST("jobs/add/")
    Observable<PostJobPojo> postJobApi(
            @HeaderMap Map<String, String> headers, @Body PostJobJson postJobJson);

    @GET("notification/list")
    Observable<NotificationResponse> notificationsApi(@HeaderMap Map<String, String> headers);

    @POST("job/mytask/list")
    Observable<MyTasksResponse> myTask(
            @Header("Content-Type") String contentType,
            @Body MyTaskJson myTaskJson);

    @GET("jobs/my/")
        //   jobs/my/
    Observable<MyAllJobResponse> onlyMyJobsGetMethod(
            @HeaderMap Map<String, String> headers, @Query("page") String pageNumber_str);

    @GET("all-categories/")
        //   job category list
    Observable<JobCateListResponse> jobCatListGetApi(
    );

    @GET("jobs/my/{jobs_status}/")
        //   jobs/my/active,draft, completed,pending
    Observable<MyAllJobResponse> myJobsGetMethod(
            @HeaderMap Map<String, String> headers, @Path("jobs_status") String jobs_status, @Query("page") String pageNumber_str);

    @GET("jobs/mybids/{bid_status}")
// my bids api  with all status also
    Observable<MyAllBidsResponse> myBids(@HeaderMap Map<String, String> headers, @Path(value = "bid_status", encoded = true) String bid_status, @Query("page") String pageNumber_str);

    @GET("jobs/view/{jobs_id}/")
        //job details
    Observable<JobBidsResponse> viewJobDetailsApi(
            @HeaderMap Map<String, String> headers, @Path("jobs_id") String jobs_id);

    @POST("jobs/bid-on-job/")
        ///jobs/bid-on-job             new bid on job
    Observable<BidResponse> jobBidApi(
            @HeaderMap Map<String, String> headers,
            @Body BidAndChatJson bidJson);

    ///////////////new
    @POST("jobs/bid/accept/")
    /// bid accept api
    Observable<CommonResponse> bidAcceptApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson bidJson);

    @POST("jobs/delete/")
        /// delete job          delete a job
    Observable<CommonResponse> jobDeleteApi(
            @HeaderMap Map<String, String> headers
            , @Body BidAndChatJson bidJson);

    @POST("jobs/create/duplicate/")
        ///duplicate job
    Observable<MyAllBidsResponse> jobDuplicateApi(
            @HeaderMap Map<String, String> headers, @Body BidAndChatJson bidJson);

    @POST("/jobs/convert/draft/to/posted/")
        ///draftToPosted job
    Observable<CommonResponse> draftToPostedJob(
            @HeaderMap Map<String, String> headers, @Body BidAndChatJson bidJson);


    @POST("/jobs/contract/complete/")
        ////jobs/contract/complete job             new bid on job
    Observable<BidResponse> jobCompleteApi(
            @HeaderMap Map<String, String> headers, @Query("job_id") String jobs_id);

    @POST("/notification/read/")
        //// set notification status as read
    Observable<NotificationResponse> notifiReadApi(
            @HeaderMap Map<String, String> headers, @Body NotificationResponse notificationResponse);

    @POST("/jobs/bid/update/")
        ////jobs/bid/update/            update bid on job
    Observable<BidResponse> jobBidUpdateApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson bidJson);


    @GET("/profile-fetch/")
    Observable<UserProfileResponse> userProfileInfoApi(@HeaderMap Map<String, String> headers);

    @POST("/jobs/add/images/")
        /// upload images for job posting time
    Observable<CommonResponse> uploadImageForPostedJob(@HeaderMap Map<String, String> headers, @Body PostJobJson postJobJson);

    @Multipart
    @POST("/jobs/upload/images/{job_id}/")
        /// upload images for job posting time
    Observable<CommonResponse> uploadImageForPostedJobMultipart(@HeaderMap Map<String, String> headers, @Path("job_id") String job_id, @Part MultipartBody.Part file);


    @POST("/change/password/")
        /// upload images for job posting time
    Observable<ResetPsdResponse> resetPsdApi(@HeaderMap Map<String, String> headers, @Body ResetPsdPojo resetPsdPojo);


    @POST("/add-skill/")
        /// upload images for job posting time
    Observable<SkillAndExpResponse> addSkillApi(@HeaderMap Map<String, String> headers, @Body SkillAndExperPojo skillAndExperPojo);

    @POST("/add-education/")
        /// upload images for job posting time
    Observable<SkillAndExpResponse> addEduExperApi(@HeaderMap Map<String, String> headers, @Body SkillAndExperPojo skillAndExperPojo);

    @POST("/profile-update/")
        /// upload images for job posting time
    Observable<UpdateProfileResponse> updateProfileApi(@HeaderMap Map<String, String> headers, @Body UpdateProfilePojo updateProfilePojo);

    @Multipart
    @POST("/profile-image-upload/")
        /// upload images for job posting time
    Observable<UserProfileResponse> uploadProfileImageApi(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);


    @GET("/documents/terms/and/insurance/?format=json")
        //http://13.127.231.62/documents/terms/and/insurance/?format=json
    Observable<TextBaseCommonResponse> termsAndInsuranceApi(@HeaderMap Map<String, String> headers);

    @GET("/documents/support/?format=json")
        //http://13.127.231.62/documents/support/?format=json
    Observable<TextBaseCommonResponse> helpAndContactApi(@HeaderMap Map<String, String> headers);

    @GET("/documents/insurance/?format=json")
        //documents/insurance/?format=json
    Observable<TextBaseCommonResponse> onlyInsuranceApi(@HeaderMap Map<String, String> headers);

    @GET("/documents/software/licence/?format=json")
        ///documents/software/licence/?format=json
    Observable<TextBaseCommonResponse> softwareLicenceApi(@HeaderMap Map<String, String> headers);

    @GET("/documents/privacy/and/policy/?format=json")
        //documents/privacy/and/policy/?format=json
    Observable<TextBaseCommonResponse> privacyPolicyApi(@HeaderMap Map<String, String> headers);

    @GET("/documents/disclaimer/?format=json")
        //documents/disclaimer/?format=json
    Observable<TextBaseCommonResponse> disclaimerApi(@HeaderMap Map<String, String> headers);

    @POST("/remove/education/")
        // remove/education/   item from profile
    Observable<ProfileItemCommonResponse> removeEduExpItemApi(@HeaderMap Map<String, String> headers, @Body ProfileItemCommonPojo commonPojo);

    @POST("/remove/portfolio/")
        /// /remove/portfolio/   item from profile
    Observable<ProfileItemCommonResponse> removePortfolioItemApi(@HeaderMap Map<String, String> headers, @Body ProfileItemCommonPojo commonPojo);

    @POST("/remove/skills/profile/")
        //  /remove/skills/profile/ item from profile
    Observable<ProfileItemCommonResponse> removeSkillsItemApi(@HeaderMap Map<String, String> headers, @Body ProfileItemCommonPojo commonPojo);

    @GET("/skills/")
        //   /skills/  get all skills list
    Observable<AllSkillsListResponse> getAllSkillsApi(@HeaderMap Map<String, String> headers);

    @POST("/auth/convert-token/")
        // /auth/convert-token/  for facebook
    Observable<ConvertTokenResponse> authConverTokenApi(@HeaderMap Map<String, String> headers, @Body RegisterJson registerJson);

    @POST("/register/social/accounts/")
        // /auth/convert-token/  for facebook
    Observable<ConvertTokenResponse> registerSocialAccountApi(@HeaderMap Map<String, String> headers, @Body RegisterJson registerJson);


    @POST("/jobs/wishlist/add/")
        // /auth/convert-token/  for facebook
    Observable<BidResponse> addToWishList(@HeaderMap Map<String, String> headers, @Body BidAndChatJson registerJson);

    @POST("/jobs/wishlist/remove/")
        // /auth/convert-token/  for facebook
    Observable<BidResponse> removeFromWishListApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson registerJson);


    @GET("/languges/list/all/")
        //   /skills/  get all skills list
    Observable<AllLangListResponse> getAllLangApi(@HeaderMap Map<String, String> headers);

    @POST("/language/add/")
        /// upload images for job posting time
    Observable<AddLangResponse> addLanguageApi(@HeaderMap Map<String, String> headers, @Body SkillAndExperPojo skillAndExperPojo);

    @POST("/language/remove/profile/")
        /// remove added language
    Observable<AddLangResponse> removeAddedLangApi(@HeaderMap Map<String, String> headers, @Body ProfileItemCommonPojo removeLangId);

    @Multipart
    @POST("/add-portfolio/")
        /// upload images for job posting time
    Observable<AllPortfolioListResponse> uploadPortfolioApi(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);

    @Multipart
    @POST("/add/licence/request/")
        /// upload images for license -- licenceType, licenceImage
    Observable<SkillAndExpResponse> uploadLicenceImageApi(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part file, @Part("licenceType") RequestBody licenceType);

    @Multipart
    @POST("/submit/police/verification/document/")
        /// upload images for policeVerificationDocument -- policeVerificationDocument
    Observable<SkillAndExpResponse> uploadPoliceVeriApi(@HeaderMap Map<String, String> headers, @Part MultipartBody.Part file);

    @POST("/transpotation/remove/profile/")
        /// remove added language
    Observable<ProfileItemCommonResponse> removeTransportApi(@HeaderMap Map<String, String> headers, @Body ProfileItemCommonPojo removeLangId);

    @POST("/add/tax/details/")
        //  /add/tax/details/  edit profile
    Observable<ProfileItemCommonResponse> addTaxDetails(@HeaderMap Map<String, String> headers, @Body SkillAndExperPojo skillAndExperPojo);

    @POST("/transpotation/add/")
        /// upload images for policeVerificationDocument -- policeVerificationDocument
    Observable<ProfileTransportResponse>  addTransApi(@HeaderMap Map<String, String> headers, @Body SkillAndExperPojo skillAndExperPojo);


    @GET("/public/profile/fetch/{id}/")
        ///public/profile/fetch/{id}/
    Observable<UserProfileResponse> otherUserProfile(@HeaderMap Map<String, String> headers, @Path("id") String id);

    @POST("/verify/otp/complete/registration/")
        /// upload images for policeVerificationDocument -- policeVerificationDocument
    Observable<ProfileItemCommonResponse> otpVerifyApi(@HeaderMap Map<String, String> headers, @Body OTPPojo otpPojo);


    @POST("/jobs/comment/create/")
        /// upload images for policeVerificationDocument -- policeVerificationDocument
    Observable<BidResponse> sendPublicCommentOnJobApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson sendPublicCommentResp);

    @GET("/profile-fetch/")
        /// fetch profile with token
    Observable<LoginResponse> fetchProfileWithTokenApi(@HeaderMap Map<String, String> headers);

    @POST("/chat/send/messege/")
        /// send a message to bidder who working on my job
    Observable<BidResponse> chatP2PSendMsgApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson sendPublicCommentResp);

    @GET("/chat/group/id/by/job/{job_id}/")
        /// fetch all chat for bidded job (bidder-job poster)
    Observable<ChatAllGroupsResponse> fetchGroupIdWithJobIdApi(@HeaderMap Map<String, String> headers, @Path("job_id") String id);

    @GET("/chat/all/groups/")
        /// fetch all groups of chat
    Observable<ChatAllGroupsResponse> fetchChatAllGroupsApi(@HeaderMap Map<String, String> headers);

    @GET("/chat/view/{group_id}/")
        /// fetch all groups of chat
    Observable<ChatViewWithGroupResponse> chatViewWithGroupIdApi(@HeaderMap Map<String, String> headers, @Path("group_id") String group_id);

    @Streaming
    @GET
    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    *//*Financial API  *//*
    @POST("/update/verify/email/")
    ///  /update/verify/email/
    Observable<CommonResponse> getEmailOTPForPaymentApi(@HeaderMap Map<String, String> headers, @Body VerifyEmailMobileForPaymentPojo verifyEmailForPaymentPojo);

    @POST("/update/verify/phone/number/")
        /// update/verify/phone/number/
    Observable<CommonResponse> getMobileOTPForPaymentApi(@HeaderMap Map<String, String> headers, @Body VerifyEmailMobileForPaymentPojo verifyEmailForPaymentPojo);

    @POST("/verify/with/otp/")
        ///   verify/with/otp/
    Observable<CommonResponse> verifyEmailMobileOTPApi(@HeaderMap Map<String, String> headers, @Body VerifyEmailMobileForPaymentPojo verifyEmailForPaymentPojo);

    @POST("/finances/add/credit/card/")
        //   finances/add/credit/card/
    Observable<GetFinanceInfoRes> addCardTokenApi(@HeaderMap Map<String, String> headers, @Body AddRemoveFinancePojo addCardTokenPojo);

    @GET("/finances/get/all/cards/")
        /// /finances/get/all/cards/
    Observable<GetFinanceInfoRes> getAllAddedCardsApi(@HeaderMap Map<String, String> headers);

    @POST("/finances/remove/card/")
        /// finances/remove/card/
    Observable<GetFinanceInfoRes> removeCardApi(@HeaderMap Map<String, String> headers, @Body AddRemoveFinancePojo removeCardId);

    @POST("/finances/system/add/bank/account/")
        /// finances/get/all/bank/accounts/
    Observable<GetFinanceInfoRes> addBnkActTokenApi(@HeaderMap Map<String, String> headers, @Body AddRemoveFinancePojo addBnkToken);

    @POST("/finances/system/delete/bank/account/")
        /// finances/get/all/bank/accounts/
    Observable<GetFinanceInfoRes> removeBankTokenApi(@HeaderMap Map<String, String> headers, @Body AddRemoveFinancePojo addedBnkToken);

    @GET("finances/system/get/all/bank/accounts/")
        /// /finances/get/all/cards/
    Observable<GetAllBnkResponse> getAllAddedBankApi(@HeaderMap Map<String, String> headers);

    @POST("/jobs/request/job/completed/bidder/")
    Observable<CommonResponse> jobCompleteByBidderApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson contractId);

    @POST("/jobs/report-job/")
    Observable<CommonResponse> reportIssueOnJobApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson idAndMessage);

    @POST("/jobs/bidder/requested/to/increase/payments/")
    Observable<CommonResponse> bidderRequestIncreasePaymentApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson contractAndAmount);

    @POST("/jobs/poster/cancel/contract/")
    Observable<CommonResponse> cancelJobByPosterApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson contractAndReson);

    @POST("/jobs/poster/accept/increase/payment/request/")
    Observable<CommonResponse> posterAcceptingIncreasPayRequestApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson requestId);

    @POST("/jobs/poster/rejected/increase/payment/request/")
    Observable<CommonResponse> posterNotAcceptIncreasPayRequestApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson requestId);

    @POST("/jobs/contract/complete/")
    Observable<CommonResponse> paymentReleasedByPosterApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson contract_id);

    @POST("/jobs/seeker/provide/feedback/")
    Observable<CommonResponse> rateToBidderApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson contract_id);

    @POST("/jobs/bidder/cancel/contract/")
    Observable<CommonResponse> bidderCancellingContractApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson contract_idAndReason);

    @POST("/jobs/request/add/on/service/")
    Observable<CommonResponse> requestAddOnServiceApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson contract_idNameCost);

    @POST("/jobs/accept/add/on/service/")
    Observable<CommonResponse> acceptAddOnServiceApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson request_id);

     @POST("/jobs/bidder/add/on/service/requested/list/{contract_id}/")
    Observable<BidderAddOnServiceJobResponse> bidderAddOnServiceListApi(@HeaderMap Map<String, String> headers, @Path("contract_id") String contract_id);

    @POST("/jobs/poster/add/on/service/requested/list/{job_id}")
    Observable<PosterAddOnServiceJobResponse> posterAddOnServiceListApi(@HeaderMap Map<String, String> headers, @Path("job_id") String job_id);


    @POST("/jobs/poster/raise/issue/job/complete/")
    Observable<CommonResponse> posterRaiseIssueJobCompTmApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson idAndMessage);

    @POST("/jobs/list/of/issues/")
    Observable<RaiseIssueReponse> getAllIssueListApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson issueFor);

    @POST("/update/device/key/")
    Observable<CommonResponse> updateDeviceTokenApi(@HeaderMap Map<String, String> headers, @Body UpdatePhoneTokenJson phoneTokenJson);

    @POST("/finances/payment/reciepts/by/job/")
    Observable<CommonResponse> getFinancePaymntReciptApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson jobId);

    @POST("/report/user/")
     Observable<CommonResponse> reportIssueOnOtherProfileApi(@HeaderMap Map<String, String> headers, @Body ProfileItemCommonPojo profileItemCommonPojo);
*/

}
