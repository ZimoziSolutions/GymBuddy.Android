package com.zimozi.assessment.util.FirebaseAuthenticationHelper

import android.app.Activity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class FirebaseAuthenticationHelper {

    companion object {

        private lateinit var firebaseAuth: FirebaseAuth

        fun signInWithEmailAndPassword(
            email: String,
            password: String,
            callback: CallbackFirebaseWrapper<AuthResult>
        ) {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(callback)
        }

        fun createUserWithEmail(
            email: String,
            password: String,
            callback: CallbackFirebaseWrapper<AuthResult>
        ) {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(callback)
        }

        fun verifyPhoneNumber(
            activity: Activity,
            phoneNumber: String,
            callback: PhoneAuthCallbackWrapper
        ) {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                activity, // Activity (for callback binding)
                callback // OnVerificationStateChangedCallbacks
            )
        }

        fun resetPassword(email: String, callback: CallbackFirebaseWrapper<Void>) {
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(callback)
        }

        fun changePassword(
            activity: Activity,
            email: String,
            oldPassword: String,
            newpassword: String,
            callback: CallbackFirebaseWrapper<Void>
        ) {
            val credential = EmailAuthProvider
                .getCredential(email, oldPassword)
            // Prompt the user to re-provide their sign-in credentials
            FirebaseAuth.getInstance().currentUser?.reauthenticate(credential)
                ?.addOnCompleteListener(object : CallbackFirebaseWrapper<Void>(activity) {
                    override fun onSuccess(task: Task<Void>) {
                        FirebaseAuth.getInstance().currentUser?.updatePassword(newpassword)
                            ?.addOnCompleteListener(callback)
                    }

                    override fun onError(messageError: String) {
                        // (activity as BaseActivity<*>).hideLoading()
                        // activity.showErrorDialog(messageError)
                    }
                })
        }

        fun refreshFirebaseToken(callback: CallbackFirebaseWrapper<GetTokenResult>): Boolean {
            if (FirebaseAuth.getInstance().currentUser != null) {
                FirebaseAuth.getInstance().currentUser?.getIdToken(true)
                    ?.addOnCompleteListener(callback)
                return true
            }
            return false
        }

        fun sendVerificationEmail(callback: CallbackFirebaseWrapper<Void>) {
            FirebaseAuth.getInstance().currentUser?.let {
                it.sendEmailVerification()
                    .addOnCompleteListener(callback)
            }
        }
    }
}