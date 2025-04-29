package com.daemon.smusignal.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.daemon.smusignal.data.local.ApiError
import com.daemon.smusignal.data.repository.AuthRepository
import com.daemon.smusignal.data.repository.ReferralRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

class ReferralViewModel(
    private val repository: ReferralRepository
) : ViewModel() {

    val rerollCount = MutableLiveData<Int>()
    val referralCode = MutableLiveData<String>()
    val message = MutableLiveData<String>()

    fun getMyRerollCount() {
        viewModelScope.launch {
            val response = repository.getMyRerollCount()
            if (response.isSuccessful) {
                response.body()?.result?.let { count ->
                    Timber.d("getMyRerollCount success: $count")
                    rerollCount.value = count
                } ?: Timber.e("getMyRerollCount: 응답에 reroll 필드가 없습니다.")
            } else {
                Timber.e("getMyRerollCount 실패: ${response.errorBody()?.string()}")
            }
        }
    }

    fun postReferralCode(code: String) {
        viewModelScope.launch {
            val response = repository.postReferralCode(code)
            if (response.isSuccessful) {
                response.body()?.message?.let {
                    Timber.d("postReferralCode success: $message")
                    message.value = message.toString()
                } ?: Timber.e("postReferralCode: 응답에 reroll 필드가 없습니다.")
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMsg  = errorJson?.let {
                    runCatching { Gson().fromJson(it, ApiError::class.java).message }.getOrNull()
                } ?: "알 수 없는 오류"

                Timber.e("postReferralCode 실패: $errorMsg")
                message.value = errorMsg
            }
        }
    }

    fun getMyReferralCode() {
        viewModelScope.launch {
            val response = repository.getMyReferralCode()
            if (response.isSuccessful) {
                response.body()?.result?.let { code ->
                    Timber.d("getMyReferralCode success: $code")
                    referralCode.value = code
                } ?: Timber.e("getMyReferralCode: 응답에 reroll 필드가 없습니다.")
            } else {
                Timber.e("getMyReferralCode 실패: ${response.errorBody()?.string()}")
            }
        }
    }
}