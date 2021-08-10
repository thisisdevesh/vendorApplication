package com.gts.coordinator.Retrofit;

import com.gts.coordinator.Model.ContractorData.AddCab.AddCabModel;
import com.gts.coordinator.Model.ContractorData.AddCab.PostCabDetail;
import com.gts.coordinator.Model.ContractorData.AddDriver.Testing.AddDriverModelTesting;
import com.gts.coordinator.Model.ContractorData.AddDriver.Testing.PostDriverDetail;
import com.gts.coordinator.Model.ContractorData.AddVendor.AddVendorModel;
import com.gts.coordinator.Model.ContractorData.AddVendor.PostVendorDetail;
import com.gts.coordinator.Model.ContractorData.AddVendorTesting.AddVendorTesting;
import com.gts.coordinator.Model.ContractorData.AppInfo.VersionCode;
import com.gts.coordinator.Model.ContractorData.BookingActivities.GetBookingActivitiesData;
import com.gts.coordinator.Model.ContractorData.BookingActivities.SetBookingActivitiesData;
import com.gts.coordinator.Model.ContractorData.BookingRequestReport.PostCabNumber;
import com.gts.coordinator.Model.ContractorData.BookingRequestReport.ResponseRequestHistory;
import com.gts.coordinator.Model.ContractorData.CabCategory.CabCategoryModel;
import com.gts.coordinator.Model.ContractorData.CategoryReport.ResponsCategoryReport;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckCabStetus.CheckCabStetus;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckCabStetus.PostCabNo;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverSatusTesting.CheckDriverStetusTesting;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverSatusTesting.PostDriverNoTesting;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetus.CheckDriverStetus;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetusNew.PostDriverNoNew;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetusNew.SetBookingActivities;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckVendorStetus.CheckVendorStetus;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckDriverStetus.PostDriverNo;
import com.gts.coordinator.Model.ContractorData.CheckStetus.CheckVendorStetus.PostVendorNo;
import com.gts.coordinator.Model.ContractorData.ContractorDetail.ContDetail;
import com.gts.coordinator.Model.ContractorData.GetOtp.GetOtpModel;
import com.gts.coordinator.Model.ContractorData.GetOtp.PostMobileNo;
import com.gts.coordinator.Model.ContractorData.GetOtpForgotPasswod.PostUserName;
import com.gts.coordinator.Model.ContractorData.GetOtpForgotPasswod.ResponseResetOtp;
import com.gts.coordinator.Model.ContractorData.Login.LoginModel;
import com.gts.coordinator.Model.ContractorData.Login.PostLogin;
import com.gts.coordinator.Model.ContractorData.ContWalateData.PostContid;
import com.gts.coordinator.Model.ContractorData.LoginDetail.LoginReport;
import com.gts.coordinator.Model.ContractorData.LoginDetail.PostDetail;
import com.gts.coordinator.Model.ContractorData.MaintenanceReport.PostVcabId;
import com.gts.coordinator.Model.ContractorData.MaintenanceReport.ResponsMaintenanceReport;
import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressModel;
import com.gts.coordinator.Model.ContractorData.Bonus.OfferProgressRequestModel;
import com.gts.coordinator.Model.ContractorData.PackageReport.PackageReport;
import com.gts.coordinator.Model.ContractorData.PackageReport.PostPackage;
import com.gts.coordinator.Model.ContractorData.PayUPaymentFail.ResponsePaymentFail;
import com.gts.coordinator.Model.ContractorData.ResetPassword.PostResetPasswod;
import com.gts.coordinator.Model.ContractorData.ResetPassword.ResponseResetPassword;
import com.gts.coordinator.Model.ContractorData.TransferMoney.PostTransferMoneyDetail;
import com.gts.coordinator.Model.ContractorData.TransferMoney.TransferMoneyModel;
import com.gts.coordinator.Model.ContractorData.UnreadNotificationCount.NotificationCountResponce;
import com.gts.coordinator.Model.ContractorData.UnreadNotificationCount.PostData;
import com.gts.coordinator.Model.ContractorData.UpdateCab.PostUpdateCabDetail;
import com.gts.coordinator.Model.ContractorData.UpdateCab.UpdateCabModel;
import com.gts.coordinator.Model.ContractorData.UpdateDeiver.PostUpdateDriverDetail;
import com.gts.coordinator.Model.ContractorData.UpdateDeiver.UpdateDriverModel;
import com.gts.coordinator.Model.ContractorData.UpdateProfile.PostContractorDetail;
import com.gts.coordinator.Model.ContractorData.UpdateProfile.UpdateProfileModel;
import com.gts.coordinator.Model.ContractorData.UpdateVendor.PostUpdateVendorDetail;
import com.gts.coordinator.Model.ContractorData.UpdateVendor.UpdateVendorModel;
import com.gts.coordinator.Model.ContractorData.UploadImage.UplodImageMdel;
import com.gts.coordinator.Model.ContractorData.WallteHistory.HistoryDetailsResponce;
import com.gts.coordinator.Model.ContractorData.WallteHistory.PostTranstionHistory;
import com.gts.coordinator.Model.ContractorData.acceptBooking.AcceptBookingResponse;
import com.gts.coordinator.Model.ContractorData.activeDriver.ActiveDriveDataResponse;
import com.gts.coordinator.Model.ContractorData.activeDriver.PostActiveDriver;
import com.gts.coordinator.Model.ContractorData.assignBooking.BookingDataResponse;
import com.gts.coordinator.Model.ContractorData.assignBooking.PostContData;
import com.gts.coordinator.Model.ContractorData.assignDriver.ReposeAssignDriver;
import com.gts.coordinator.Model.ContractorData.bookingSent.PostSentBooking;
import com.gts.coordinator.Model.ContractorData.bookingSent.ReposeBookingSent;
import com.gts.coordinator.Model.bookingStatus.PostStatus;
import com.gts.coordinator.Model.bookingStatus.StatusRespose;
import com.gts.coordinator.Model.cancelBooking.CancelBookingRespose;
import com.gts.coordinator.Model.cancelBooking.PostCancelBookingData;
import com.gts.coordinator.Model.cityModel.CityRequest;
import com.gts.coordinator.Model.cityModel.CityResponse;
import com.gts.coordinator.Model.driverIncome.DriverIncomeRespose;
import com.gts.coordinator.Model.driverIncome.PostDataDrIncome;
import com.gts.coordinator.Model.en.EnOtpRespose;
import com.gts.coordinator.Model.getAll.ResposeGetAll;
import com.gts.coordinator.Model.income.IncomeRespose;
import com.gts.coordinator.Model.income.PostDataIncome;
import com.gts.coordinator.Model.referralDriver.PostNo;
import com.gts.coordinator.Model.referralDriver.ReferralDriverRespose;
import com.gts.coordinator.Model.referralDriverNew.NewReferralDriverRespose;
import com.gts.coordinator.Notification.Model.Contid;
import com.gts.coordinator.Notification.Model.NotificationResponce;
import com.gts.coordinator.Notification.Model.NotificationStetus;
import com.gts.coordinator.Notification.Model.StetusNotificationModel;
import com.gts.coordinator.Model.ContractorData.PayU.PayuApiResponce;
import com.gts.coordinator.Model.ContractorData.PayU.PostRechargeWalateDetailPayu;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitApiInterface {
    //todo wcm api
    @POST("/contractor.svc/GetContractorDetails")
    Call<ContDetail> contIdcon(@Body PostContid contid);

    @POST("/contractor.svc/ContLogin")
    Call<LoginModel> contLogin(@Body PostLogin postLogin);

    @POST("/contractor.svc/Vendorexist")
    Call<CheckVendorStetus> vendorStetus(@Body PostVendorNo postVendorNo);

    @POST("GetCitiesForTariffType")
    Call<CityResponse> getCities(@Body CityRequest tariffType);

    @POST("/contractor.svc/Cabexist")
    Call<CheckCabStetus> cabStetus(@Body PostCabNo postCabNo);

    @POST("/contractor.svc/Driverdetailsexist")
    Call<CheckDriverStetus> driverStetus(@Body PostDriverNo postDriverNo);

    @POST("/contractor.svc/Driverdetailsexistnew")
    Call<SetBookingActivities> driverStetusnew(@Body PostDriverNoNew postDriverNo);

    @POST("Driverdetailsexist")
    Call<CheckDriverStetusTesting> driverStatusTesting(@Body PostDriverNoTesting postDriverNoTesting);

    @POST("/contractor.svc/AddVendors")
    Call<AddVendorModel> addVendor(@Body PostVendorDetail postVendorDetail);

    @POST("/default/AddVendors")
    Call<AddVendorTesting> addVendorTesting(@Body PostVendorDetail postVendorDetail);


    @POST("/contractor.svc/UpdateVendorInfo")
    Call<UpdateVendorModel> updateVendor(@Body PostUpdateVendorDetail postVendorDetail);

    @POST("/contractor.svc/AddCab")
    Call<AddCabModel> addcCab(@Body PostCabDetail addCabModel);

    @POST("/contractor.svc/UpdateCabInfo")
    Call<UpdateCabModel> updateCab(@Body PostUpdateCabDetail updateCabDetail);

    @POST("/contractor.svc/UpdateDriverInfo")
    Call<UpdateDriverModel> updateDriver(@Body PostUpdateDriverDetail updateDriverDetail);

  /*      @POST("/contractor.svc/AddDriver")
    Call<AddDriverModel> addDriver(@Body PostDriverDetail postDriverDetail);*/
    @POST("AddDriver")
    Call<AddDriverModelTesting> addDriver(@Body PostDriverDetail postDriverDetail);

    @POST("/contractor.svc/ContTransferMoney")
    Call<TransferMoneyModel> transferMoney(@Body PostTransferMoneyDetail transferMoneyDetail);

    @POST("/contractor.svc/GetContHistory")
    Call<HistoryDetailsResponce> transferHistory(@Body PostTranstionHistory postContid);

    @POST("/contractor.svc/GetCabCategory")
    Call<CabCategoryModel> cabCategorydetal();

    @POST("/contractor.svc/UpdateContractorInfo")
    Call<UpdateProfileModel> updateContractor(@Body PostContractorDetail postyContractorDetail);

    @POST("/contractor.svc/GetLoginHistory")
    Call<LoginReport> getLoginHistory(@Body PostDetail postDetail);

    @POST("/contractor.svc/GetServiceReport")
    Call<PackageReport> getPackage(@Body PostPackage postDetail);

    @POST("/contractor.svc/GetRequestHistory")
    Call<ResponseRequestHistory> getRequestHistory(@Body PostCabNumber postCabNumber);

    @POST("/contractor.svc/GetMaintenanceReport")
    Call<ResponsMaintenanceReport> getMaintenanceReport(@Body PostVcabId vcabId);

    @POST("/contractor.svc/GetCategoryReport")
    Call<ResponsCategoryReport> getCategoryReport(@Body PostVcabId vcabId);

    //todo web api
    //http://webapitest.gtscab.com/default/getalldata
    //http://webapitest.gtscab.com/default/Getotp
    @POST("getalldata")
    Call<ResposeGetAll> getData(@Body PostContData postContData);

    @POST("RechargeCont")
    Call<PayuApiResponce> rechargeWalatePayu(@Body PostRechargeWalateDetailPayu postRechargeWalateDetailPayu);

    @POST("initiatepayment")
    Call<ResponsePaymentFail> paymentFail(@Body PostRechargeWalateDetailPayu postRechargeWalateDetailPayu);

    @Multipart
    @POST("Driverfiles")
        //http://webapitest.gtscab.com/default/notify
    Call<UplodImageMdel> uploadImage(@Part MultipartBody.Part file, @Part("vcabid") RequestBody vcabid, @Part("doc_type") RequestBody doc_type, @Part("role") RequestBody role);

    @Multipart
    @POST("Cabfiles")
    Call<UplodImageMdel> uploadImagecab(@Part MultipartBody.Part file, @Part("vcabid") RequestBody vcabid, @Part("doc_type") RequestBody doc_type, @Part("role") RequestBody role);

    @Multipart
    @POST("Vendorfiles")
    Call<UplodImageMdel> uploadImagevendor(@Part MultipartBody.Part file, @Part("vcabid") RequestBody vcabid, @Part("doc_type") RequestBody doc_type, @Part("role") RequestBody role);

    @POST("AppInfo")
    Call<VersionCode> getVersionCode();

    @POST("notify")
    Call<NotificationResponce> contId(@Body Contid contid);

    @POST("GetUnreadNoitify")
    Call<NotificationCountResponce> notificationCount(@Body PostData postData);

    @POST("UpdateNotifyStatus")
    Call<StetusNotificationModel> contStetus(@Body NotificationStetus notificationStetus);

    @POST("Getotp")
    Call<GetOtpModel> postMobileNo(@Body PostMobileNo postMobileNo);

    @POST("sendEncryptedOtp")
    Call<EnOtpRespose> postOtp(@Body PostMobileNo postMobileNo);

    @POST("OTPRequest")
    Call<ResponseResetOtp> postUserName(@Body PostUserName postUserName);

    @POST("resetPassword")
    Call<ResponseResetPassword> resetPassword(@Body PostResetPasswod postUserName);

    @POST("getBookingdataForContractor")
    Call<BookingDataResponse> getAllData(@Body PostContData postContData);

    @POST("getAssignData")
    Call<AcceptBookingResponse> getAcceptBooking(@Body PostContData postContData);

    @POST("getDriverDeliveryStatus")
    Call<GetBookingActivitiesData> getBookingActivities(@Body SetBookingActivitiesData setBookingActivitiesData);

    @POST("replaceAndFree")
    Call<ActiveDriveDataResponse> getDriverDetail(@Body PostActiveDriver postActiveDriver);

    @POST("sendRequest")
    Call<ReposeBookingSent> sentBooking(@Body PostSentBooking postSentBooking);

    @POST("checkStaus")
    Call<StatusRespose> checkStatus(@Body PostStatus postStatus);

    @POST("getPendingBookingFrDriver")
    Call<ReposeAssignDriver> getDriver(@Body com.gts.coordinator.Model.ContractorData.assignDriver.PostCabNo postCabNo);

    @POST("cancelBookingByContractor")
    Call<CancelBookingRespose> cancelBooking(@Body PostCancelBookingData cancelBookingData);

    @POST("getAllReferralDrivers")
    Call<ReferralDriverRespose> getReferralDriver(@Body PostContData postContData);

    @POST("getNewDriverByreferral")
    Call<NewReferralDriverRespose> getNewReferralDriver(@Body PostNo postNo);

    @POST("getTotalVendorIncome")
    Call<IncomeRespose> getVendorIncome(@Body PostDataIncome postDataIncome);

    @POST("getToalDriverIncome")
    Call<DriverIncomeRespose> getDriverIncome(@Body PostDataDrIncome postDataDrIncome);

    @POST("/default/contractorofferdetails")
    Call<OfferProgressModel> findOfferProgress(@Body OfferProgressRequestModel offerProgressRequestModel);


// ok report 24.10.2019
    //UpdateContractorInfo
    //GetCabCategory

}
//"http://webapitest.gtscab.com/default/notify"
//"http://webapitest.gtscab.com/default/cancelBookingByContractor"

/*Dear Ma'am
Good morning .I would like to request you  for  tomorrow 's leave because  i have to go to Delhi in order to  attend my family function so please allow  me to have leave for tomorrow .
Thanks & Regard 
Ravindra Singh Sugra
Android Developer */
/*cancelBookingByContractor
 * bookingId
 * cabNumber*/