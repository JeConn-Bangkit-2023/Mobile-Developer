package com.capstone.jeconn.repository

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.capstone.jeconn.R
import com.capstone.jeconn.data.entities.AuthEntity
import com.capstone.jeconn.state.UiState
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepository(
    private val context: Context
) {

    private val auth = Firebase.auth

    val registerState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Empty)

    val loginState: MutableStateFlow<UiState<String>> = MutableStateFlow(UiState.Empty)

    val sendEmailVerificationState: MutableState<UiState<String>> = mutableStateOf(UiState.Empty)

    val isEmailVerifiedState: MutableState<UiState<String>> = mutableStateOf(UiState.Empty)


    fun registerUser(user: AuthEntity) {
        registerState.value = UiState.Loading
        auth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser

                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(user.username)
                        .build()

                    firebaseUser?.updateProfile(profileUpdates)
                        ?.addOnCompleteListener { profileUpdateTask ->
                            if (profileUpdateTask.isSuccessful) {
                                sendEmailVerification()
                                registerState.value =
                                    UiState.Success(context.getString(R.string.success_regis))
                            } else {
                                registerState.value =
                                    UiState.Error(profileUpdateTask.exception?.message.toString())
                                Log.e(
                                    "AuthRepository",
                                    "Failed to update user profile: ${profileUpdateTask.exception}"
                                )
                            }
                        }
                } else {
                    registerState.value = UiState.Error(task.exception?.message.toString())
                    Log.e("AuthRepository", "Register failed: ${task.exception}")
                }
            }
    }

    fun loginUser(user: AuthEntity) {
        loginState.value = UiState.Loading
        auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    loginState.value = UiState.Success(context.getString(R.string.success_login))
                    // Sign in success, update UI with the signed-in user's information
                } else {
                    // If sign in fails, display a message to the user.
                    loginState.value = UiState.Error(task.exception?.message.toString())
                }
            }
    }

    fun sendEmailVerification() {
        sendEmailVerificationState.value = UiState.Loading
        try {
            auth.currentUser!!.reload()
        } catch (e: Exception) {
            sendEmailVerificationState.value = UiState.Error(e.message.toString())
        } finally {
            if (!auth.currentUser!!.isEmailVerified) {
                auth.currentUser!!.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            sendEmailVerificationState.value =
                                UiState.Success(context.getString(R.string.email_verification_sent))
                        } else {
                            sendEmailVerificationState.value =
                                UiState.Error(context.getString(R.string.email_verification_not_sent))
                        }
                    }
                    .addOnFailureListener { exception ->
                        sendEmailVerificationState.value =
                            UiState.Error(exception.message.toString())
                    }
            } else {
                Log.e("AuthRepo", "99")
                sendEmailVerificationState.value =
                    UiState.Error(context.getString(R.string.already_verified))
            }
        }
    }

    fun isEmailVerified() {
        isEmailVerifiedState.value = UiState.Loading
        try {
            auth.currentUser!!.reload()
        } catch (e: Exception) {
            Log.e("AuthRepository", "Failed Reload")
            isEmailVerifiedState.value = UiState.Error(e.message.toString())
        } finally {
            if (auth.currentUser!!.isEmailVerified) {
                Log.e("AuthRepo", "91")
                isEmailVerifiedState.value =
                    UiState.Success(context.getString(R.string.email_successfully_verified))
            } else {
                isEmailVerifiedState.value =
                    UiState.Error(context.getString(R.string.email_has_not_been_verified))
            }
        }
    }
}