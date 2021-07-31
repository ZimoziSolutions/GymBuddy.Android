package com.zimozi.assessment.util

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics

object FAUtils {
    fun logScreen(activity: Activity, screenName: String) {
        FirebaseAnalytics.getInstance(activity).setCurrentScreen(activity, screenName, null)
    }

    fun logEvent(context: Context, eventName: String, data: Bundle? = null) {
        FirebaseAnalytics.getInstance(context).logEvent(eventName, data)
    }

    fun setUserId(context: Context, userId: String?) {
        FirebaseAnalytics.getInstance(context).setUserId(userId)
    }

    enum class ScreenName(val value: String) {
        SPLASH("Splash"),
        FORGOT_PASSWORD("ForgotPassword"),
        GOOGLE_VERIFICATION("GoogleVerification"),
        NEW_PASSWORD("NewPassword"),
        ENTER_PHONE_NUMBER("EnterPhoneNumber"),
        OTP_VERIFICATION("OTPVerification"),
        ENABLE_BIOMETRIC("EnableBiometric"),
        SIGNUP_REWARDS("SignupRewards"),
        LOGIN_REWARDS("LoginRewards"),
        GOLD_HOME("GoldHome"),
        SUMMARY("Summary"),
        CANCEL_ORDER("CancelOrder"),
        TRADING_BUY_MARKET("TradingBuyMarket"),
        TRADING_BUY_LIMIT("TradingBuyLimit"),
        TRADING_SELL_MARKET("TradingSellMarket"),
        TRADING_SELL_LIMIT("TradingSellLimit"),
        TRADE_SUCCESS("TradeSuccess"),
        TRADE_FAILED("TradeFailed"),
        NEW_FEEDS("NewFeeds"),
        NEW_FEED_DETAILS("NewFeedDetails"),
        IOE_DETAILS("IEODetails"),
        SUBSCRIBE_IOE_SUCCESS("SubscribeIEOSuccess"),
        SUBSCRIBE_IOE_FAILED("SubscribeIEOFailed"),
        ASSET_LIST("AssetList"),
        ASSET_INFORMATION("AssetInformation"),
        ASSET_DOCUMENT("AssetDocument"),
        MAIN_SETTINGS("MainSettings"),
        GENERAL_SETTINGS("GeneralSettings"),
        SECURITY_SETTINGS("SecuritySettings"),
        RESET_PASSWORD("ResetPassword"),
        BIND_GOOGLE_AUTH("BindGoogleAuth"),
        EDIT_PERSONAL_INFO("EditPersonalInfo"),
        KYC_PROFILE("KYCProfile"),
        KYC_SUCCESS("KYCSuccess"),
        KYC_SUCCESS_NOTICE("KYCSuccessNotice"),
        DEPOSIT_FUNDS("DepositFunds"),
        DEPOSIT_HISTORY("DepositHistory"),
        DIRECT_BANK_TRANSFER("DirectBankTransfer"),
        DEPOSIT_CONFIRM("DepositConfirm"),
        DEPOSIT_SUCCESS("DepositSuccess"),
        WITHDRAW_FUNDS("WithdrawFunds"),
        WITHDRAWAL_HISTORY("WithdrawalHistory"),
        WITHDRAWAL_REQUEST("WithdrawalRequest"),
        ADD_BANK_ACCOUNT("AddBankAccount"),
        ADD_BANK_CONFIRM("AddBankConfirm"),
        ADD_BANK_SUCCESS("AddBankSuccess"),
        ADD_BANK_FAILED("AddBankFailed"),
        WITHDRAWAL_CONFIRM("WithdrawalConfirm"),
        WITHDRAWAL_SUCCESS("WithdrawalSuccess"),
        WITHDRAWAL_FAILED("WithdrawalFailed"),
        REWARDS_HOME("RewardsHome"),
        POINT_HISTORY("PointHistory"),
        INVITE_FRIENDS("InviteFriends"),
        HELP("Help"),
        ABOUT_US("AboutUs"),
        TERM_AND_POLICIES("TermsAndPolicies"),
        USER_AGREEMENT("UserAgreement"),
        END_USER_LICENSE("EndUserLicense"),
        PRIVACY_POLICY("PrivacyPolicy"),
        COOKIE_POLICY("CookiePolicy"),
        NOTIFICATION_LIST("NotificationList"),
        GOLD_STATS("GoldStats"),
        INTRO("Intro"),
        REAL_NAME_AUTH("RealNameAuth"),
        REAL_NAME_UPLOAD_IMAGE("RealNameUploadImage"),
        SUBMIT_REAL_AUTH_SUCCESS("SubmitRealAuthSuccess")
    }

    enum class EventName(val value: String) {
        CHECKING_PRICE("checking_price"),
        SELL_EGT("sell_egt"),
        BUY_EGT("buy_egt"),
        READ_NEWFEEDS("read_newfeeds"),
        EARN_POINTS("earn_points"),
        KEEP_USING_APP("keep_using_app"),
        FINISH_KYC("finish_kyc"),
        MAKE_FIRST_DEPOSIT("make_first_deposit"),
        MAKE_DEPOSITS("make_deposits"),
        MERCHANT_ONBOARDING("merchant_onboarding"),
        BUY_IN_IOE("buy_in_ioe"),
        FIRST_TIME_IOE("first_time_ioe"),
        CANCEL_ORDER("cancel_order"),
        WITHDRAWAL("withdrawal"),
        ADD_BANK("add_bank"),
        LOGIN(FirebaseAnalytics.Event.LOGIN),
        SIGNUP(FirebaseAnalytics.Event.SIGN_UP),
    }

    enum class EventParam(val value: String) {
        TRADE_VOLUME("trade_volume"),
        CURRENCY(FirebaseAnalytics.Param.CURRENCY),
        LOGIN_METHOD(FirebaseAnalytics.Param.METHOD),
        COUNT("count"),
        NEWFEED_ID("new_feed_id"),
        TYPE_OF_EARNING("type_of_earning"),
        NUMBER_OF_POINTS("number_of_points"),
        DEPOSIT_AMOUNT("deposit_amount"),
        ORDER_ID("order_id"),
        BANK_ID("bank_id"),
        WITHDRAW_AMOUNT("withdraw_amount"),
        ACCOUNT_NO("account_no"),
    }

    enum class PointEarningType(val value: String) {
        REGISTER("register"),
        LOGIN("login"),
        BUY("buy"),
        SELL("sell")
    }
}