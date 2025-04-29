package com.daemon.smusignal.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daemon.smusignal.data.repository.AuthRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    val messageRequestMailCode = MutableLiveData<String>()
    val messageVerifyMailCode = MutableLiveData<String>()
    val stateRequestMailCode = MutableLiveData<Boolean>()
    val stateVerifyMailCode = MutableLiveData<Boolean>()

    val messageCheckSignUp = MutableLiveData<Boolean>()

    fun requestMailCode(mail: String) {
        viewModelScope.launch {
            val response = repository.requestMailCode(mail)
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    Timber.d("requestMailCode success: $result")
                    messageRequestMailCode.value = result.message
                    stateRequestMailCode.value = true
                } ?: run {
                    Timber.e("requestMailCode: 응답 본문이 null 입니다")
                    messageRequestMailCode.value = response.errorBody()?.string() ?: "Unknown error"
                    stateRequestMailCode.value = false
                }
            } else {
                Timber.e("requestMailCode 실패: ${response.body()?.message}")
                messageRequestMailCode.value = response.errorBody()?.string() ?: "Unknown error"
                stateRequestMailCode.value = false
            }
        }
    }

    fun verifyMailCode(mailVerification: String) {
        viewModelScope.launch {
            val response = repository.verifyMailCode(mailVerification)
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    result.token?.let { token ->
                        repository.tokenManager.saveTokens(token)
                        Timber.d("verifyMailCode success - 토큰 저장: $token")
                    } ?: Timber.e("verifyMailCode: token 값이 null 입니다")
                    Timber.d("verifyMailCode success: $result")
                    messageVerifyMailCode.value = result.message
                    stateVerifyMailCode.value = true
                } ?: run {
                    Timber.e("verifyMailCode: 응답 본문이 null 입니다")
                    messageVerifyMailCode.value = response.errorBody()?.string() ?: "Unknown error"
                    stateVerifyMailCode.value = false
                }
            } else {
                Timber.e("verifyMailCode 실패: ${response.errorBody()?.string()}")
                messageVerifyMailCode.value = response.errorBody()?.string() ?: "Unknown error"
                stateVerifyMailCode.value = false
            }
        }
    }

    fun checkSignUp() {
        viewModelScope.launch {
            val response = repository.checkSignUp()

            if (response.isSuccessful) {
                response.body()?.let { result ->
                    Timber.d("checkSignUp success: $result")
                    messageCheckSignUp.value = true
                } ?: run {
                    Timber.e("checkSignUp: 응답 본문이 null 입니다")
                    messageCheckSignUp.value   = false
                }
            } else {
                Timber.e("checkSignUp 실패")
                messageCheckSignUp.value = false
            }
        }
    }
}