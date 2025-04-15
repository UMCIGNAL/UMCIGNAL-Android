package com.daemon.smusignal.presentation.onboarding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daemon.smusignal.data.MailCodeResponse
import com.daemon.smusignal.data.MailVerificationResponse
import com.daemon.smusignal.domain.AuthRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    val mailCodeResponse = MutableLiveData<MailCodeResponse>()
    val mailVerificationResponse = MutableLiveData<MailVerificationResponse>()

    fun requestMailCode(mail: String) {
        viewModelScope.launch {
            val response = repository.requestMailCode(mail)
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    Timber.d("requestMailCode success: $result")
                    mailCodeResponse.value = result
                } ?: run {
                    Timber.e("requestMailCode: 응답 본문이 null 입니다")
                }
            } else {
                Timber.e("requestMailCode 실패: ${response.errorBody()?.string()}")
                // 에러 응답 처리 (예: 에러 메시지를 담은 객체 생성 후 전달)
            }
        }
    }

    fun verifyMailCode(mailVerification: String) {
        viewModelScope.launch {
            val response = repository.verifyMailCode(mailVerification)
            if (response.isSuccessful) {
                response.body()?.let { res ->
                    res.token?.let { token ->
                        repository.tokenManager.saveTokens(token)
                        Timber.d("verifyMailCode success - 토큰 저장: $token")
                    } ?: Timber.e("verifyMailCode: token 값이 null 입니다")
                    Timber.d("verifyMailCode success: $res")
                    mailVerificationResponse.value = res
                } ?: run {
                    Timber.e("verifyMailCode: 응답 본문이 null 입니다")
                }
            } else {
                Timber.e("verifyMailCode 실패: ${response.errorBody()?.string()}")
                // 에러 응답 처리
            }
        }
    }
}