package com.zimozi.assessment.util.FirebaseAuthenticationHelper

import android.content.Context
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

abstract class PhoneAuthCallbackWrapper(val context: Context?) :
    PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
        // This callback will be invoked in two situations:
        // 1 - Instant verification. In some cases the phone number can be instantly
        //     verified without needing to send or enter a verification code.
        // 2 - Auto-retrieval. On some devices Google Play services can automatically
        //     detect the incoming verification SMS and perform verification without
        //     user action.
        // Log.d(TAG, "onVerificationCompleted:$credential")

        // signInWithPhoneAuthCredential(credential)
    }

    override fun onVerificationFailed(e: FirebaseException) {
        // This callback is invoked in an invalid request for verification is made,
        // for instance if the the phone number format is not valid.
        // Log.w(TAG, "onVerificationFailed", e)

        if (e is FirebaseAuthInvalidCredentialsException) {
            // Invalid request
            // ...
        } else if (e is FirebaseTooManyRequestsException) {
            // The SMS quota for the project has been exceeded
            // ...
        }
    }

    override fun onCodeSent(p0: String, token: PhoneAuthProvider.ForceResendingToken) {
        // The SMS verification code has been sent to the provided phone number, we
        // now need to ask the user to enter the code and then construct a credential
        // by combining the code with a verification ID.

        // Save verification ID and resending token so we can use them later
        // storedVerificationId = verificationId
        // resendToken = token
        // ...
    }

    protected abstract fun onSuccess()
    protected abstract fun onError(messageError: String)
}