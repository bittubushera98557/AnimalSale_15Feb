package com.example.lenovo.emptypro.ApiCallClasses;



import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
import com.example.lenovo.emptypro.ModelClasses.CommonRequestParamsJson;

import java.util.Map;

import io.reactivex.Observable;



class RemoteMainRepository implements MainRepositoryDataSource {

    private final MainServices mainService;

    public RemoteMainRepository(MainServices mainService) {
        this.mainService = mainService;
    }


    @Override
    public Observable<AllApiResponse.CategoryResponse> allCateListApi() {
        return mainService.allCateListApi();
    }

    @Override
    public Observable<AllApiResponse.OtpResponse> getOtpApi(Map<String, String> headers, CommonRequestParamsJson requestParamsJson) {
        return mainService.getOtpApi(headers, requestParamsJson);

    }

/*
    @Override
    public Observable<CommonResponse> forgotPasswordApi(String contentType, ForgotPasswordJson forgotPasswordJson) {
        return mainService.forgotPasswordApi(contentType, forgotPasswordJson);
    }


    @Override
    public Observable<StateResponse> stateListApi() {
        return mainService.stateListApi();
    }


    */
/*@Override
    public Observable<LocationPojo> locationListApi(String contentType, LocationListJson locationListJson) {
        return mainService.locationListApi(*//*
*/
/*contentType,locationListJson*//*
*/
/*);
    }

    *//*

    @Override
    public Observable<CityResponse> locationCityListApi(String stateId) {
        return mainService.locationCityListApi("" + stateId);
    }


    @Override
    public Observable<RegisterResponse> registerApi(String contentType, RegisterJson registerJson) {
        return mainService.registerApi(contentType, registerJson);
    }

    @Override
    public Observable<JobFilterPojo> jobListPostApi(@HeaderMap Map<String, String> headers, JobListJson jobListJson) {
        return mainService.jobListPostApi(headers, jobListJson);
    }

    @Override
    public Observable<JobFilterPojo> jobListGetApi(@HeaderMap Map<String, String> headers, String strPgNumber) {
        return mainService.jobListGetApi(headers, strPgNumber);
    }

    @Override
    public Observable<PostJobPojo> postJobApi(@HeaderMap Map<String, String> headers, PostJobJson postJobJson) {
        return mainService.postJobApi(headers, postJobJson);
    }

    @Override
    public Observable<NotificationResponse> notificationsApi(@HeaderMap Map<String, String> headers) {
        return mainService.notificationsApi(headers);
    }

    @Override
    public Observable<MyTasksResponse> myTask(String contentType, MyTaskJson myTaskJson) {
        return mainService.myTask(contentType, myTaskJson);
    }

    @Override
    public Observable<JobBidsResponse> viewJobDetailsApi(@HeaderMap Map<String, String> headers, String jobBidsId) {
        return mainService.viewJobDetailsApi(headers, jobBidsId);
    }

    @Override
    public Observable<BidResponse> jobBidApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return mainService.jobBidApi(headers, bidJson);
    }

    @Override
    public Observable<MyAllJobResponse> myJobsGetMethod(@HeaderMap Map<String, String> headers, String jobStatus, String pageNum) {
        return mainService.myJobsGetMethod(headers, jobStatus, pageNum);
    }

    @Override
    public Observable<MyAllJobResponse> onlyMyJobsGetMethod(@HeaderMap Map<String, String> headers, String pageNum) {
        return mainService.onlyMyJobsGetMethod(headers, pageNum);
    }

    @Override
    public Observable<MyAllBidsResponse> myBids(@HeaderMap Map<String, String> headers, String bidStatus, String pageNum) {
        return mainService.myBids(headers, bidStatus, pageNum);
    }

    ///// // / // / / new apis
    @Override
    public Observable<MyAllBidsResponse> jobDuplicateApi(@HeaderMap Map<String, String> headers, BidAndChatJson jobId) {
        return mainService.jobDuplicateApi(headers, jobId);
    }

    @Override
    public Observable<CommonResponse> draftToPostedJob(@HeaderMap Map<String, String> headers, BidAndChatJson jobId) {
        return mainService.draftToPostedJob(headers, jobId);
    }


    @Override
    public Observable<CommonResponse> jobDeleteApi(@HeaderMap Map<String, String> headers, BidAndChatJson jobId) {
        return mainService.jobDeleteApi(headers, jobId);
    }

    @Override
    public Observable<CommonResponse> bidAcceptApi(@HeaderMap Map<String, String> headers, @Body BidAndChatJson bidJson) {
        return mainService.bidAcceptApi(headers, bidJson);
    }

    @Override
    public Observable<NotificationResponse> notifiReadApi(@HeaderMap Map<String, String> headers, @Body NotificationResponse NotiId) {
        return mainService.notifiReadApi(headers, NotiId);
    }

    @Override
    public Observable<BidResponse> jobBidUpdateApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return mainService.jobBidUpdateApi(headers, bidJson);
    }

    @Override
    public Observable<UserProfileResponse> userProfileInfoApi(@HeaderMap Map<String, String> headers) {
        return mainService.userProfileInfoApi(headers);
    }

    @Override
    public Observable<CommonResponse> uploadImageForPostedJob(@HeaderMap Map<String, String> headers, PostJobJson postJobJson) {
        return mainService.uploadImageForPostedJob(headers, postJobJson);
    }

    @Override
    public Observable<CommonResponse> uploadImageForPostedJobMultipart(@HeaderMap Map<String, String> headers, String jobId, MultipartBody.Part file) {
        return mainService.uploadImageForPostedJobMultipart(headers, jobId, file);
    }

    @Override
    public Observable<ResetPsdResponse> resetPsdApi(@HeaderMap Map<String, String> headers, ResetPsdPojo resetPsdPojo) {
        return mainService.resetPsdApi(headers, resetPsdPojo);
    }

    @Override
    public Observable<SkillAndExpResponse> addSkillApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo skillAndExperPojo) {
        return mainService.addSkillApi(headers, skillAndExperPojo);
    }

    @Override
    public Observable<SkillAndExpResponse> addEduExperApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo skillAndExperPojo) {
        return mainService.addEduExperApi(headers, skillAndExperPojo);
    }

    @Override
    public Observable<UpdateProfileResponse> updateProfileApi(@HeaderMap Map<String, String> headers, UpdateProfilePojo updateProfilePojo) {
        return mainService.updateProfileApi(headers, updateProfilePojo);
    }

    // termsAndInsuranceApi  onlyInsuranceApi  privacyPolicyApi  helpAndContactApi disclaimerApi softwareLicenceApi

    @Override
    public Observable<TextBaseCommonResponse> termsAndInsuranceApi(@HeaderMap Map<String, String> headers) {
        return mainService.termsAndInsuranceApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> helpAndContactApi(@HeaderMap Map<String, String> headers) {
        return mainService.helpAndContactApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> privacyPolicyApi(@HeaderMap Map<String, String> headers) {
        return mainService.privacyPolicyApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> onlyInsuranceApi(@HeaderMap Map<String, String> headers) {
        return mainService.onlyInsuranceApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> disclaimerApi(@HeaderMap Map<String, String> headers) {
        return mainService.disclaimerApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> softwareLicenceApi(@HeaderMap Map<String, String> headers) {
        return mainService.softwareLicenceApi(headers);
    }

    //removeEduExpItemApi    removeSkillsItemApi   removePortfolioItemApi
    @Override
    public Observable<ProfileItemCommonResponse> removeEduExpItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo pojo) {
        return mainService.removeEduExpItemApi(headers, pojo);
    }

    @Override
    public Observable<ProfileItemCommonResponse> removeSkillsItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo pojo) {
        return mainService.removeSkillsItemApi(headers, pojo);
    }

    @Override
    public Observable<ProfileItemCommonResponse> removePortfolioItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo pojo) {
        return mainService.removePortfolioItemApi(headers, pojo);
    }

    @Override
    public Observable<AllSkillsListResponse> getAllSkillsApi(@HeaderMap Map<String, String> headers) {
        return mainService.getAllSkillsApi(headers);
    }

    @Override
    public Observable<UserProfileResponse> uploadProfileImageApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file) {
        return mainService.uploadProfileImageApi(headers, file);
    }

    @Override
    public Observable<ConvertTokenResponse> authConverTokenApi(@HeaderMap Map<String, String> headers, RegisterJson registerJson) {
        return mainService.authConverTokenApi(headers, registerJson);
    }

    @Override
    public Observable<ConvertTokenResponse> registerSocialAccountApi(@HeaderMap Map<String, String> headers, RegisterJson registerJson) {
        return mainService.registerSocialAccountApi(headers, registerJson);
    }

    @Override
    public Observable<BidResponse> addToWishList(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return mainService.addToWishList(headers, bidJson);
    }

    @Override
    public Observable<BidResponse> removeFromWishListApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return mainService.removeFromWishListApi(headers, bidJson);
    }

    @Override
    public Observable<AllLangListResponse> getAllLangApi(@HeaderMap Map<String, String> headers) {
        return mainService.getAllLangApi(headers);
    }

    @Override
    public Observable<AddLangResponse> addLanguageApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo addLangPojo) {
        return mainService.addLanguageApi(headers, addLangPojo);
    }

    @Override
    public Observable<AllPortfolioListResponse> uploadPortfolioApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file) {
        return mainService.uploadPortfolioApi(headers, file);
    }

    @Override
    public Observable<SkillAndExpResponse> uploadLicenceImageApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file, RequestBody licenseTypeStr) {
        return mainService.uploadLicenceImageApi(headers, file, licenseTypeStr);
    }

    @Override
    public Observable<SkillAndExpResponse> uploadPoliceVeriApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file) {
        return mainService.uploadPoliceVeriApi(headers, file);
    }

    @Override
    public Observable<ProfileTransportResponse>  addTransApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo transportData) {
        return mainService.addTransApi(headers, transportData);
    }

    @Override
    public Observable<ProfileItemCommonResponse> addTaxDetails(@HeaderMap Map<String, String> headers, SkillAndExperPojo addLangPojo) {
        return mainService.addTaxDetails(headers, addLangPojo);
    }

    @Override
    public Observable<UserProfileResponse> otherUserProfile(@HeaderMap Map<String, String> headers, String id) {
        return mainService.otherUserProfile(headers, id);
    }

    @Override
    public Observable<AddLangResponse> removeAddedLangApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo removeLangId) {
        return mainService.removeAddedLangApi(headers, removeLangId);
    }

    @Override
    public Observable<ProfileItemCommonResponse> removeTransportApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo removeTransportId) {
        return mainService.removeTransportApi(headers, removeTransportId);
    }

    @Override
    public Observable<ProfileItemCommonResponse> otpVerifyApi(@HeaderMap Map<String, String> headers, OTPPojo otpPojo) {
        return mainService.otpVerifyApi(headers, otpPojo);
    }

    @Override
    public Observable<BidResponse> sendPublicCommentOnJobApi(@HeaderMap Map<String, String> headers, BidAndChatJson sendPublicCommentResp) {
        return mainService.sendPublicCommentOnJobApi(headers, sendPublicCommentResp);
    }

    @Override
    public Observable<LoginResponse> fetchProfileWithTokenApi(@HeaderMap Map<String, String> headers) {
        return mainService.fetchProfileWithTokenApi(headers);
    }

    @Override
    public Observable<BidResponse> chatP2PSendMsgApi(@HeaderMap Map<String, String> headers, BidAndChatJson sendPersonalMsg) {
        return mainService.chatP2PSendMsgApi(headers, sendPersonalMsg);
    }

    @Override
    public Observable<ChatAllGroupsResponse> fetchChatAllGroupsApi(@HeaderMap Map<String, String> headers) {
        return mainService.fetchChatAllGroupsApi(headers);
    }

    @Override
    public Observable<ChatAllGroupsResponse> fetchGroupIdWithJobIdApi(@HeaderMap Map<String, String> headers, String jobId) {
        return mainService.fetchGroupIdWithJobIdApi(headers, jobId);
    }

    @Override
    public Observable<ChatViewWithGroupResponse> chatViewWithGroupIdApi(@HeaderMap Map<String, String> headers, String group_id) {
        return mainService.chatViewWithGroupIdApi(headers, group_id);
    }

    @Override
    public Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl) {
        return mainService.downloadFileWithDynamicUrlSync(fileUrl);
    }

    @Override
    public Observable<CommonResponse> getEmailOTPForPaymentApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo verifyEmail) {
        return mainService.getEmailOTPForPaymentApi(headers, verifyEmail);
    }

    @Override
    public Observable<CommonResponse> getMobileOTPForPaymentApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo verifyMobile) {
        return mainService.getMobileOTPForPaymentApi(headers, verifyMobile);
    }

    @Override
    public Observable<GetFinanceInfoRes> addCardTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo addCardTokenPojo) {
        return mainService.addCardTokenApi(headers, addCardTokenPojo);
    }

    @Override
    public Observable<GetFinanceInfoRes> getAllAddedCardsApi(@HeaderMap Map<String, String> headers) {
        return mainService.getAllAddedCardsApi(headers);
    }

    @Override
    public Observable<GetFinanceInfoRes> removeCardApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo removeCardId) {
        return mainService.removeCardApi(headers, removeCardId);
    }

    @Override
    public Observable<GetFinanceInfoRes> addBnkActTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo addBnkTokenPojo) {
        return mainService.addBnkActTokenApi(headers, addBnkTokenPojo);
    }


    @Override
    public Observable<CommonResponse> verifyEmailMobileOTPApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo addBnkTokenPojo) {
        return mainService.verifyEmailMobileOTPApi(headers, addBnkTokenPojo);
    }

    @Override
    public Observable<GetAllBnkResponse> getAllAddedBankApi(@HeaderMap Map<String, String> headers) {
        return mainService.getAllAddedBankApi(headers);
    }

    @Override
    public Observable<GetFinanceInfoRes> removeBankTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo removeBnkToken) {
        return mainService.removeBankTokenApi(headers, removeBnkToken);
    }

    @Override
    public Observable<CommonResponse> jobCompleteByBidderApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractId) {
        return mainService.jobCompleteByBidderApi(headers, contractId);
    }

    @Override
    public Observable<CommonResponse> reportIssueOnJobApi(@HeaderMap Map<String, String> headers, BidAndChatJson idAndMessage) {
        return mainService.reportIssueOnJobApi(headers, idAndMessage);
    }

    @Override
    public Observable<CommonResponse> bidderRequestIncreasePaymentApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractAndAmount) {
        return mainService.bidderRequestIncreasePaymentApi(headers, contractAndAmount);
    }

    @Override
    public Observable<CommonResponse> cancelJobByPosterApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractAndReson) {
        return mainService.cancelJobByPosterApi(headers, contractAndReson);
    }

    @Override
    public Observable<CommonResponse> posterAcceptingIncreasPayRequestApi(@HeaderMap Map<String, String> headers, BidAndChatJson requestId) {
        return mainService.posterAcceptingIncreasPayRequestApi(headers, requestId);
    }

    @Override
    public Observable<CommonResponse> posterNotAcceptIncreasPayRequestApi(@HeaderMap Map<String, String> headers, BidAndChatJson requestIdAndReason) {
        return mainService.posterNotAcceptIncreasPayRequestApi(headers, requestIdAndReason);
    }

    @Override
    public Observable<CommonResponse> paymentReleasedByPosterApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_id) {
        return mainService.paymentReleasedByPosterApi(headers, contract_id);
    }

    @Override
    public Observable<CommonResponse> rateToBidderApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idFeedBckRate) {
        return mainService.rateToBidderApi(headers, contract_idFeedBckRate);
    }

    @Override
    public Observable<CommonResponse> bidderCancellingContractApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idAndReason) {
        return mainService.bidderCancellingContractApi(headers, contract_idAndReason);
    }

    @Override
    public Observable<CommonResponse> requestAddOnServiceApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idNameCost) {
        return mainService.requestAddOnServiceApi(headers, contract_idNameCost);
    }

    @Override
    public Observable<CommonResponse> acceptAddOnServiceApi(@HeaderMap Map<String, String> headers, BidAndChatJson request_id) {
        return mainService.acceptAddOnServiceApi(headers, request_id);
    }

    @Override
    public Observable<PosterAddOnServiceJobResponse> posterAddOnServiceListApi(@HeaderMap Map<String, String> headers, String job_id) {
        return mainService.posterAddOnServiceListApi(headers, job_id);
    }

    @Override
    public Observable<BidderAddOnServiceJobResponse> bidderAddOnServiceListApi(@HeaderMap Map<String, String> headers, String contract_id) {
        return mainService.bidderAddOnServiceListApi(headers, contract_id);
    }

    @Override
    public   Observable<CommonResponse> posterRaiseIssueJobCompTmApi(@HeaderMap Map<String, String> headers,  BidAndChatJson idAndMessage) {
        return mainService.posterRaiseIssueJobCompTmApi(headers, idAndMessage);
    }


    @Override
    public   Observable<RaiseIssueReponse> getAllIssueListApi(@HeaderMap Map<String, String> headers, BidAndChatJson  issueFor){
        return mainService.getAllIssueListApi(headers, issueFor);
    }

    @Override
    public Observable<CommonResponse> updateDeviceTokenApi(@HeaderMap Map<String, String> headers,  UpdatePhoneTokenJson phoneTokenJson){
        return mainService.updateDeviceTokenApi(headers, phoneTokenJson);
    }

    @Override
    public Observable<CommonResponse> getFinancePaymntReciptApi(@HeaderMap Map<String, String> headers,  BidAndChatJson jobId){
        return mainService.getFinancePaymntReciptApi(headers, jobId);
    }

    @Override
    public Observable<CommonResponse> reportIssueOnOtherProfileApi(@HeaderMap Map<String, String> headers,  ProfileItemCommonPojo profileItemCommonPojo){
        return mainService.reportIssueOnOtherProfileApi(headers, profileItemCommonPojo);
    }
*/
}
