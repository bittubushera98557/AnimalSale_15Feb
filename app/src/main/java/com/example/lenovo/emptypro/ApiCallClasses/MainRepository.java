package com.example.lenovo.emptypro.ApiCallClasses;



 import com.example.lenovo.emptypro.ModelClasses.AllApiResponse;
 import com.example.lenovo.emptypro.ModelClasses.CommonRequestParamsJson;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.HeaderMap;
import retrofit2.http.Path;
import retrofit2.http.Url;



public class MainRepository implements MainRepositoryDataSource {

    private final MainRepositoryDataSource remoteGitHubRepository;

    private static MainRepository INSTANCE;


    public static MainRepository getInstance(MainServices gitHubService) {
        if (INSTANCE == null)
            INSTANCE = new MainRepository(gitHubService);
        return INSTANCE;
    }

    private MainRepository(MainServices gitHubService) {
        remoteGitHubRepository = new RemoteMainRepository(gitHubService);
    }


    @Override
    public Observable<AllApiResponse.CategoryResponse> allCateListApi() {
        return remoteGitHubRepository.allCateListApi();
    }
    @Override
    public Observable<AllApiResponse.OtpResponse> getOtpApi(@HeaderMap Map<String, String> headers, CommonRequestParamsJson loginJson) {
        return remoteGitHubRepository.getOtpApi(headers, loginJson);
    }

/*
    @Override
    public Observable<LoginResponse> loginApi(String contentType, LoginJson loginJson) {
        return remoteGitHubRepository.loginApi(contentType, loginJson);
    }
*/

 /*   @Override
    public Observable<CommonResponse> forgotPasswordApi(String contentType, ForgotPasswordJson forgotPasswordJson) {
        return remoteGitHubRepository.forgotPasswordApi(contentType, forgotPasswordJson);

    }


    @Override
    public Observable<StateResponse> stateListApi() {
        return remoteGitHubRepository.stateListApi();
    }

    @Override
    public Observable<CityResponse> locationCityListApi(String stateId) {
        return remoteGitHubRepository.locationCityListApi(stateId);
    }

    @Override
    public Observable<JobCateListResponse> jobCatListGetApi() {
        return remoteGitHubRepository.jobCatListGetApi();
    }

    @Override
    public Observable<RegisterResponse> registerApi(String contentType, RegisterJson registerJson) {
        return remoteGitHubRepository.registerApi(contentType, registerJson);
    }

    @Override
    public Observable<JobFilterPojo> jobListPostApi(@HeaderMap Map<String, String> headers, JobListJson jobListJson) {
        return remoteGitHubRepository.jobListPostApi(headers, jobListJson);
    }


    @Override
    public Observable<JobFilterPojo> jobListGetApi(@HeaderMap Map<String, String> headers, String strPageNumber) {
        return remoteGitHubRepository.jobListGetApi(headers, strPageNumber);
    }

    @Override
    public Observable<PostJobPojo> postJobApi(@HeaderMap Map<String, String> headers, PostJobJson postJobJson) {
        return remoteGitHubRepository.postJobApi(headers, postJobJson);
    }

    @Override
    public Observable<NotificationResponse> notificationsApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.notificationsApi(headers);
    }

    @Override
    public Observable<MyTasksResponse> myTask(String contentType, MyTaskJson myTaskJson) {
        return remoteGitHubRepository.myTask(contentType, myTaskJson);
    }

    @Override
    public Observable<JobBidsResponse> viewJobDetailsApi(@HeaderMap Map<String, String> headers, String choosedJobId) {
        return remoteGitHubRepository.viewJobDetailsApi(headers, choosedJobId);
    }

    @Override
    public Observable<BidResponse> jobBidApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return remoteGitHubRepository.jobBidApi(headers, bidJson);
    }

    @Override
    public Observable<MyAllJobResponse> myJobsGetMethod(@HeaderMap Map<String, String> headers, String jobStatus, String pageNum) {
        return remoteGitHubRepository.myJobsGetMethod(headers, jobStatus, pageNum);
    }

    @Override
    public Observable<MyAllJobResponse> onlyMyJobsGetMethod(@HeaderMap Map<String, String> headers, String pageNum) {

        return remoteGitHubRepository.onlyMyJobsGetMethod(headers, pageNum);
    }

    @Override
    public Observable<MyAllBidsResponse> myBids(@HeaderMap Map<String, String> headers, String bidStatus, String pageNum) {
        return remoteGitHubRepository.myBids(headers, bidStatus, pageNum);
    }

    // new apis
    @Override
    public Observable<MyAllBidsResponse> jobDuplicateApi(@HeaderMap Map<String, String> headers, BidAndChatJson jobId) {
        return remoteGitHubRepository.jobDuplicateApi(headers, jobId);
    }

    @Override
    public Observable<CommonResponse> draftToPostedJob(@HeaderMap Map<String, String> headers, BidAndChatJson jobId) {
        return remoteGitHubRepository.draftToPostedJob(headers, jobId);
    }

    @Override
    public Observable<CommonResponse> jobDeleteApi(@HeaderMap Map<String, String> headers, BidAndChatJson jobId) {
        return remoteGitHubRepository.jobDeleteApi(headers, jobId);
    }

    @Override
    public Observable<CommonResponse> bidAcceptApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return remoteGitHubRepository.bidAcceptApi(headers, bidJson);
    }

    @Override
    public Observable<NotificationResponse> notifiReadApi(@HeaderMap Map<String, String> headers, NotificationResponse notificationResponse) {
        return remoteGitHubRepository.notifiReadApi(headers, notificationResponse);
    }

    @Override
    public Observable<BidResponse> jobBidUpdateApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return remoteGitHubRepository.jobBidUpdateApi(headers, bidJson);
    }

    @Override
    public Observable<UserProfileResponse> userProfileInfoApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.userProfileInfoApi(headers);
    }

    @Override
    public Observable<CommonResponse> uploadImageForPostedJob(@HeaderMap Map<String, String> headers, PostJobJson jobJson) {
        return remoteGitHubRepository.uploadImageForPostedJob(headers, jobJson);
    }

    @Override
    public Observable<CommonResponse> uploadImageForPostedJobMultipart(@HeaderMap Map<String, String> headers, String jobId, MultipartBody.Part file) {
        return remoteGitHubRepository.uploadImageForPostedJobMultipart(headers, jobId, file);
    }

    @Override
    public Observable<ResetPsdResponse> resetPsdApi(@HeaderMap Map<String, String> headers, ResetPsdPojo resetPsdPojo) {
        return remoteGitHubRepository.resetPsdApi(headers, resetPsdPojo);
    }

    @Override
    public Observable<SkillAndExpResponse> addSkillApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo skillAndExperPojo) {
        return remoteGitHubRepository.addSkillApi(headers, skillAndExperPojo);
    }

    @Override
    public Observable<SkillAndExpResponse> addEduExperApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo skillAndExperPojo) {
        return remoteGitHubRepository.addEduExperApi(headers, skillAndExperPojo);
    }

    @Override
    public Observable<UpdateProfileResponse> updateProfileApi(@HeaderMap Map<String, String> headers, UpdateProfilePojo updateProfilePojo) {
        return remoteGitHubRepository.updateProfileApi(headers, updateProfilePojo);
    }


// termsAndInsuranceApi  onlyInsuranceApi  privacyPolicyApi  helpAndContactApi disclaimerApi softwareLicenceApi

    @Override
    public Observable<TextBaseCommonResponse> termsAndInsuranceApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.termsAndInsuranceApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> onlyInsuranceApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.onlyInsuranceApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> privacyPolicyApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.privacyPolicyApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> helpAndContactApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.helpAndContactApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> disclaimerApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.disclaimerApi(headers);
    }

    @Override
    public Observable<TextBaseCommonResponse> softwareLicenceApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.softwareLicenceApi(headers);
    }
//removeEduExpItemApi    removeSkillsItemApi   removePortfolioItemApi

    @Override
    public Observable<ProfileItemCommonResponse> removeEduExpItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo commonPojo) {
        return remoteGitHubRepository.removeEduExpItemApi(headers, commonPojo);
    }

    @Override
    public Observable<ProfileItemCommonResponse> removeSkillsItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo commonPojo) {
        return remoteGitHubRepository.removeSkillsItemApi(headers, commonPojo);
    }

    @Override
    public Observable<ProfileItemCommonResponse> removePortfolioItemApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo commonPojo) {
        return remoteGitHubRepository.removePortfolioItemApi(headers, commonPojo);
    }

    @Override
    public Observable<AllSkillsListResponse> getAllSkillsApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.getAllSkillsApi(headers);
    }

    @Override
    public Observable<UserProfileResponse> uploadProfileImageApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file) {
        return remoteGitHubRepository.uploadProfileImageApi(headers, file);
    }

    @Override
    public Observable<ConvertTokenResponse> authConverTokenApi(@HeaderMap Map<String, String> headers, RegisterJson registerJson) {
        return remoteGitHubRepository.authConverTokenApi(headers, registerJson);
    }


    @Override
    public Observable<ConvertTokenResponse> registerSocialAccountApi(@HeaderMap Map<String, String> headers, RegisterJson registerJson) {
        return remoteGitHubRepository.registerSocialAccountApi(headers, registerJson);
    }

    @Override
    public Observable<BidResponse> addToWishList(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return remoteGitHubRepository.addToWishList(headers, bidJson);
    }

    @Override
    public Observable<BidResponse> removeFromWishListApi(@HeaderMap Map<String, String> headers, BidAndChatJson bidJson) {
        return remoteGitHubRepository.removeFromWishListApi(headers, bidJson);
    }

    @Override
    public Observable<AllLangListResponse> getAllLangApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.getAllLangApi(headers);
    }

    @Override
    public Observable<AddLangResponse> addLanguageApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo langPojo) {
        return remoteGitHubRepository.addLanguageApi(headers, langPojo);
    }

    @Override
    public Observable<AllPortfolioListResponse> uploadPortfolioApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file) {
        return remoteGitHubRepository.uploadPortfolioApi(headers, file);
    }

    @Override
    public Observable<SkillAndExpResponse> uploadLicenceImageApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file, RequestBody licenseTypeStr) {
        return remoteGitHubRepository.uploadLicenceImageApi(headers, file, licenseTypeStr);
    }

    @Override
    public Observable<SkillAndExpResponse> uploadPoliceVeriApi(@HeaderMap Map<String, String> headers, MultipartBody.Part file) {
        return remoteGitHubRepository.uploadPoliceVeriApi(headers, file);
    }

    @Override
    public Observable<ProfileTransportResponse> addTransApi(@HeaderMap Map<String, String> headers, SkillAndExperPojo addTransport) {
        return remoteGitHubRepository.addTransApi(headers, addTransport);
    }

    @Override
    public Observable<ProfileItemCommonResponse> addTaxDetails(@HeaderMap Map<String, String> headers, SkillAndExperPojo taxDetailsPojo) {
        return remoteGitHubRepository.addTaxDetails(headers, taxDetailsPojo);
    }

    @Override
    public Observable<UserProfileResponse> otherUserProfile(@HeaderMap Map<String, String> headers, String userId) {
        return remoteGitHubRepository.otherUserProfile(headers, userId);
    }

    @Override
    public Observable<AddLangResponse> removeAddedLangApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo removeLangId) {
        return remoteGitHubRepository.removeAddedLangApi(headers, removeLangId);
    }

    @Override
    public Observable<ProfileItemCommonResponse> removeTransportApi(@HeaderMap Map<String, String> headers, ProfileItemCommonPojo transportId) {
        return remoteGitHubRepository.removeTransportApi(headers, transportId);
    }

    @Override
    public Observable<ProfileItemCommonResponse> otpVerifyApi(@HeaderMap Map<String, String> headers, OTPPojo otpPojo) {
        return remoteGitHubRepository.otpVerifyApi(headers, otpPojo);
    }

    @Override
    public Observable<BidResponse> sendPublicCommentOnJobApi(@HeaderMap Map<String, String> headers, BidAndChatJson sendPublicCommentResp) {
        return remoteGitHubRepository.sendPublicCommentOnJobApi(headers, sendPublicCommentResp);
    }

    @Override
    public Observable<LoginResponse> fetchProfileWithTokenApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.fetchProfileWithTokenApi(headers);
    }


    @Override
    public Observable<BidResponse> chatP2PSendMsgApi(@HeaderMap Map<String, String> headers, BidAndChatJson sendPersonalMsg) {
        return remoteGitHubRepository.chatP2PSendMsgApi(headers, sendPersonalMsg);
    }

    @Override
    public Observable<ChatAllGroupsResponse> fetchChatAllGroupsApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.fetchChatAllGroupsApi(headers);
    }

    @Override
    public Observable<ChatAllGroupsResponse> fetchGroupIdWithJobIdApi(@HeaderMap Map<String, String> headers, String jobId) {
        return remoteGitHubRepository.fetchGroupIdWithJobIdApi(headers, jobId);
    }

    @Override
    public Observable<ChatViewWithGroupResponse> chatViewWithGroupIdApi(@HeaderMap Map<String, String> headers, String group_id) {
        return remoteGitHubRepository.chatViewWithGroupIdApi(headers, group_id);
    }


    @Override
    public Observable<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl) {
        return remoteGitHubRepository.downloadFileWithDynamicUrlSync(fileUrl);
    }

    @Override
    public Observable<CommonResponse> getEmailOTPForPaymentApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo verifyEmail) {
        return remoteGitHubRepository.getEmailOTPForPaymentApi(headers, verifyEmail);
    }

    @Override
    public Observable<CommonResponse> getMobileOTPForPaymentApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo verifyMobile) {
        return remoteGitHubRepository.getMobileOTPForPaymentApi(headers, verifyMobile);
    }

    @Override
    public Observable<GetFinanceInfoRes> addCardTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo addCardTokenPojo) {
        return remoteGitHubRepository.addCardTokenApi(headers, addCardTokenPojo);
    }

    @Override
    public Observable<GetFinanceInfoRes> getAllAddedCardsApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.getAllAddedCardsApi(headers);
    }

    @Override
    public Observable<GetFinanceInfoRes> removeCardApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo removeCardId) {
        return remoteGitHubRepository.removeCardApi(headers, removeCardId);
    }

    @Override
    public Observable<GetFinanceInfoRes> addBnkActTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo addBnkTokenPojo) {
        return remoteGitHubRepository.addBnkActTokenApi(headers, addBnkTokenPojo);
    }


    @Override
    public Observable<CommonResponse> verifyEmailMobileOTPApi(@HeaderMap Map<String, String> headers, VerifyEmailMobileForPaymentPojo addBnkTokenPojo) {
        return remoteGitHubRepository.verifyEmailMobileOTPApi(headers, addBnkTokenPojo);
    }

    @Override
    public Observable<GetAllBnkResponse> getAllAddedBankApi(@HeaderMap Map<String, String> headers) {
        return remoteGitHubRepository.getAllAddedBankApi(headers);
    }

    @Override
    public Observable<GetFinanceInfoRes> removeBankTokenApi(@HeaderMap Map<String, String> headers, AddRemoveFinancePojo removeBnkToken) {
        return remoteGitHubRepository.removeBankTokenApi(headers, removeBnkToken);
    }

    @Override
    public Observable<CommonResponse> jobCompleteByBidderApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractId) {
        return remoteGitHubRepository.jobCompleteByBidderApi(headers, contractId);
    }


    @Override
    public Observable<CommonResponse> reportIssueOnJobApi(@HeaderMap Map<String, String> headers, BidAndChatJson idAndMessage) {
        return remoteGitHubRepository.reportIssueOnJobApi(headers, idAndMessage);
    }


    @Override
    public Observable<CommonResponse> bidderRequestIncreasePaymentApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractAndAmount) {
        return remoteGitHubRepository.bidderRequestIncreasePaymentApi(headers, contractAndAmount);
    }

    @Override
    public Observable<CommonResponse> cancelJobByPosterApi(@HeaderMap Map<String, String> headers, BidAndChatJson contractAndReson) {
        return remoteGitHubRepository.cancelJobByPosterApi(headers, contractAndReson);
    }

    @Override
    public Observable<CommonResponse> posterAcceptingIncreasPayRequestApi(@HeaderMap Map<String, String> headers, BidAndChatJson requestId) {
        return remoteGitHubRepository.posterAcceptingIncreasPayRequestApi(headers, requestId);
    }

    @Override
    public Observable<CommonResponse> posterNotAcceptIncreasPayRequestApi(@HeaderMap Map<String, String> headers, BidAndChatJson requestIdAndReason) {
        return remoteGitHubRepository.posterNotAcceptIncreasPayRequestApi(headers, requestIdAndReason);
    }


    @Override
    public Observable<CommonResponse> paymentReleasedByPosterApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_id) {
        return remoteGitHubRepository.paymentReleasedByPosterApi(headers, contract_id);
    }

    @Override
    public Observable<CommonResponse> rateToBidderApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idFeedBckRate) {
        return remoteGitHubRepository.rateToBidderApi(headers, contract_idFeedBckRate);
    }


    @Override
    public Observable<CommonResponse> bidderCancellingContractApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idAndReason) {
        return remoteGitHubRepository.bidderCancellingContractApi(headers, contract_idAndReason);
    }


    @Override
    public Observable<CommonResponse> requestAddOnServiceApi(@HeaderMap Map<String, String> headers, BidAndChatJson contract_idNameCost) {
        return remoteGitHubRepository.requestAddOnServiceApi(headers, contract_idNameCost);
    }

    @Override
    public Observable<CommonResponse> acceptAddOnServiceApi(@HeaderMap Map<String, String> headers, BidAndChatJson request_id) {
        return remoteGitHubRepository.acceptAddOnServiceApi(headers, request_id);
    }

    @Override
    public Observable<PosterAddOnServiceJobResponse> posterAddOnServiceListApi(@HeaderMap Map<String, String> headers, String job_id) {
        return remoteGitHubRepository.posterAddOnServiceListApi(headers, job_id);
    }

    @Override
    public Observable<BidderAddOnServiceJobResponse> bidderAddOnServiceListApi(@HeaderMap Map<String, String> headers, String contract_id) {
        return remoteGitHubRepository.bidderAddOnServiceListApi(headers, contract_id);
    }

    @Override
    public Observable<CommonResponse> posterRaiseIssueJobCompTmApi(@HeaderMap Map<String, String> headers, BidAndChatJson idAndMessage) {
        return remoteGitHubRepository.posterRaiseIssueJobCompTmApi(headers, idAndMessage);
    }


    @Override
    public Observable<RaiseIssueReponse> getAllIssueListApi(@HeaderMap Map<String, String> headers, BidAndChatJson issueFor) {
        return remoteGitHubRepository.getAllIssueListApi(headers, issueFor);
    }


    @Override
    public Observable<CommonResponse> updateDeviceTokenApi(@HeaderMap Map<String, String> headers,  UpdatePhoneTokenJson phoneTokenJson){
        return remoteGitHubRepository.updateDeviceTokenApi(headers, phoneTokenJson);
    }

    @Override
    public Observable<CommonResponse> getFinancePaymntReciptApi(@HeaderMap Map<String, String> headers,  BidAndChatJson jobId){
        return remoteGitHubRepository.getFinancePaymntReciptApi(headers, jobId);
    }

    @Override
    public Observable<CommonResponse> reportIssueOnOtherProfileApi(@HeaderMap Map<String, String> headers,  ProfileItemCommonPojo profileItemCommonPojo ){
        return remoteGitHubRepository.reportIssueOnOtherProfileApi(headers, profileItemCommonPojo );
    }



*/
}
