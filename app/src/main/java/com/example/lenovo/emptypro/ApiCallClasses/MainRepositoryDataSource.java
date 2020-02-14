package com.example.lenovo.emptypro.ApiCallClasses;


import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
import com.example.lenovo.emptypro.ModelClasses.CommonRequestParamsJson;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.HeaderMap;

interface MainRepositoryDataSource {
/*

   Observable<LoginResponse> loginApi(String contentType, LoginJson loginJson);
   Observable<CommonResponse> forgotPasswordApi(String contentType, ForgotPasswordJson forgotPasswordJson);
*/

  /* Observable<CityPojo> cityListApi();*/
   Observable<AllApiResponse.CategoryResponse>    allCateListApi();
    Observable<AllApiResponse.OtpResponse> getOtpApi(@HeaderMap Map<String, String> headers, CommonRequestParamsJson requestParamsJson);

/*
   Observable<CityResponse> locationCityListApi(String stateId);  //cityList location list

   Observable<RegisterResponse> registerApi(String contentType, RegisterJson registerJson);

   Observable<JobFilterPojo> jobListPostApi(@HeaderMap Map<String, String> headers, JobListJson jobListJson);
    Observable<JobFilterPojo> jobListGetApi(@HeaderMap Map<String, String> headers, String str);
 Observable<JobCateListResponse> jobCatListGetApi();


   Observable<PostJobPojo> postJobApi(@HeaderMap Map<String, String> headers, PostJobJson postJobJson);

   Observable<NotificationResponse> notificationsApi(@HeaderMap Map<String, String> headers);

   Observable<MyTasksResponse> myTask(String contentType, MyTaskJson myTaskJson);

   Observable<JobBidsResponse> viewJobDetailsApi(@HeaderMap Map<String, String> headers, String choosedJobId);

   Observable<BidResponse> jobBidApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson);

   Observable<MyAllJobResponse> myJobsGetMethod(@HeaderMap Map<String, String> headers, String jobStatus, String pageNum);
    Observable<MyAllJobResponse> onlyMyJobsGetMethod(@HeaderMap Map<String, String> headers, String pageNum);

   Observable<MyAllBidsResponse> myBids(@HeaderMap Map<String, String> headers, String strMyBid, String pageNum);
   ///// new
   Observable<MyAllBidsResponse> jobDuplicateApi(@HeaderMap Map<String, String> headers, BidAndChatJson jobId);
    Observable<CommonResponse> draftToPostedJob(@HeaderMap Map<String, String> headers, BidAndChatJson jobId);


    Observable<CommonResponse> jobDeleteApi(@HeaderMap Map<String, String> headers, BidAndChatJson jobId);
    Observable<CommonResponse> bidAcceptApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson);

    Observable<NotificationResponse> notifiReadApi(@HeaderMap Map<String, String> headers, NotificationResponse notificationResponse);
    Observable<BidResponse> jobBidUpdateApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson);
    Observable<UserProfileResponse> userProfileInfoApi(@HeaderMap Map<String, String> headers);

    Observable<CommonResponse> uploadImageForPostedJob(@HeaderMap Map<String, String> headers, PostJobJson postJobJson);
    Observable<CommonResponse> uploadImageForPostedJobMultipart(@HeaderMap Map<String, String> headers, String jobId, MultipartBody.Part file);

    Observable<ResetPsdResponse> resetPsdApi(@HeaderMap Map<String, String> headers, ResetPsdPojo resetPsdPojo);
    Observable<SkillAndExpResponse> addSkillApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo skillAndExperPojo);
    Observable<SkillAndExpResponse> addEduExperApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo skillAndExperPojo);
    Observable<UpdateProfileResponse>  updateProfileApi(@HeaderMap Map<String, String> headers, UpdateProfilePojo updateProfilePojo);

    Observable<TextBaseCommonResponse>  termsAndInsuranceApi(@HeaderMap Map<String, String> headers);
  Observable<TextBaseCommonResponse>  onlyInsuranceApi(@HeaderMap Map<String, String> headers);
    Observable<TextBaseCommonResponse>  privacyPolicyApi(@HeaderMap Map<String, String> headers);
    Observable<TextBaseCommonResponse>  helpAndContactApi(@HeaderMap Map<String, String> headers);
    Observable<TextBaseCommonResponse>  disclaimerApi(@HeaderMap Map<String, String> headers);
    Observable<TextBaseCommonResponse>  softwareLicenceApi(@HeaderMap Map<String, String> headers);
//removeEduExpItemApi    removeSkillsItemApi   removePortfolioItemApi
Observable<ProfileItemCommonResponse>  removeEduExpItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo commonPojo);
    Observable<ProfileItemCommonResponse>  removeSkillsItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo commonPojo);
    Observable<ProfileItemCommonResponse>  removePortfolioItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo commonPojo);
    Observable<AllSkillsListResponse>  getAllSkillsApi(@HeaderMap Map<String, String> headers);

    Observable<UserProfileResponse>  uploadProfileImageApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file);
    Observable<ConvertTokenResponse>  authConverTokenApi(@HeaderMap Map<String, String> headers, RegisterJson registerJson);
    Observable<ConvertTokenResponse>  registerSocialAccountApi(@HeaderMap Map<String, String> headers, RegisterJson registerJson);

    Observable<BidResponse>  addToWishList(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson);
    Observable<BidResponse>  removeFromWishListApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson);


    Observable<AllLangListResponse> getAllLangApi(@HeaderMap Map<String, String> headers);

    Observable<AddLangResponse>  addLanguageApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo langPojo);
    Observable<AllPortfolioListResponse>  uploadPortfolioApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file);
    Observable<SkillAndExpResponse>  uploadLicenceImageApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file, RequestBody licenseTypeStr);
    Observable<SkillAndExpResponse>  uploadPoliceVeriApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file);
    Observable<ProfileTransportResponse>  addTransApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo transportData);
    Observable<ProfileItemCommonResponse> addTaxDetails(@HeaderMap Map<String, String> headers, SkillAndExperPojo taxDetailsPojo);
    Observable<UserProfileResponse> otherUserProfile(@HeaderMap Map<String, String> headers, String jobId);
    Observable<AddLangResponse> removeAddedLangApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo langIdPojo);
    Observable<ProfileItemCommonResponse> removeTransportApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo transportIdPojo);

    Observable<ProfileItemCommonResponse> otpVerifyApi(@HeaderMap Map<String, String> headers, OTPPojo otpPojo);
    Observable<BidResponse> sendPublicCommentOnJobApi(@HeaderMap Map<String, String> headers, BidAndChatJson sendPublicCommentResp);
    Observable<LoginResponse> fetchProfileWithTokenApi(@HeaderMap Map<String, String> headers);

    Observable<BidResponse> chatP2PSendMsgApi(@HeaderMap Map<String, String> headers, BidAndChatJson sendPersonalMsg);
    Observable<ChatAllGroupsResponse> fetchChatAllGroupsApi(@HeaderMap Map<String, String> headers);
    Observable<ChatAllGroupsResponse> fetchGroupIdWithJobIdApi(@HeaderMap Map<String, String> headers, String jobId);
    Observable<ChatViewWithGroupResponse> chatViewWithGroupIdApi(@HeaderMap Map<String, String> headers, String groupId);

    Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    Observable<CommonResponse> getEmailOTPForPaymentApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo email);
    Observable<CommonResponse> getMobileOTPForPaymentApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo mobile);
    Observable<GetFinanceInfoRes> addCardTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo addCardToken);
    Observable<GetFinanceInfoRes> getAllAddedCardsApi(@HeaderMap Map<String, String> headers);
 Observable<GetFinanceInfoRes> removeCardApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo removeCardId);

    Observable<GetFinanceInfoRes>  addBnkActTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo addBnkTokenPojo);
    Observable<GetAllBnkResponse> getAllAddedBankApi(@HeaderMap Map<String, String> headers);
    Observable <GetFinanceInfoRes> removeBankTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo addedBnkToken);

    Observable<CommonResponse> verifyEmailMobileOTPApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo email);


      Observable<CommonResponse> jobCompleteByBidderApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractId);
    Observable<CommonResponse> reportIssueOnJobApi(@HeaderMap Map<String, String> headers, BidAndChatJson idAndMessage);
     Observable<CommonResponse> bidderRequestIncreasePaymentApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractAndAmount);
    Observable<CommonResponse> cancelJobByPosterApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractAndReson);

      Observable<CommonResponse> posterAcceptingIncreasPayRequestApi(@HeaderMap Map<String, String> headers, BidAndChatJson requestId);

 Observable<CommonResponse> posterNotAcceptIncreasPayRequestApi(@HeaderMap Map<String, String> headers, BidAndChatJson requestIdAndReason);

     Observable<CommonResponse> paymentReleasedByPosterApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_id);

     Observable<CommonResponse> rateToBidderApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idFeedBckRate) ;

     Observable<CommonResponse> bidderCancellingContractApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idAndReason) ;

     Observable<CommonResponse> requestAddOnServiceApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idNameCost);

    Observable<CommonResponse> acceptAddOnServiceApi(@HeaderMap Map<String, String> headers, BidAndChatJson request_id);
     Observable<PosterAddOnServiceJobResponse> posterAddOnServiceListApi(@HeaderMap Map<String, String> headers, String job_id);

     Observable<BidderAddOnServiceJobResponse> bidderAddOnServiceListApi(@HeaderMap Map<String, String> headers, String contract_id) ;
     Observable<CommonResponse> posterRaiseIssueJobCompTmApi(@HeaderMap Map<String, String> headers, BidAndChatJson idAndMessage) ;


       Observable<RaiseIssueReponse> getAllIssueListApi(@HeaderMap Map<String, String> headers, BidAndChatJson issueFor) ;
     Observable<CommonResponse> updateDeviceTokenApi(@HeaderMap Map<String, String> headers, UpdatePhoneTokenJson phoneTokenJson) ;
     Observable<CommonResponse> getFinancePaymntReciptApi(@HeaderMap Map<String, String> headers, BidAndChatJson jobId) ;
    Observable<CommonResponse> reportIssueOnOtherProfileApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo profileItemCommonPojo);
*/

}
