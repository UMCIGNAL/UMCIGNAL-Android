package com.daemon.smusignal.data.repository

import com.daemon.smusignal.data.local.ReferralCodeRequest
import com.daemon.smusignal.data.local.ReferralCodeResponse
import com.daemon.smusignal.data.local.SerialCodeRequest
import com.daemon.smusignal.data.local.SerialCodeResponse
import com.daemon.smusignal.data.remote.ReferralService
import com.daemon.smusignal.data.remote.TokenManager
import retrofit2.Response

class ReferralRepository(
    private val referralService: ReferralService
) {
    suspend fun insertSerialCode(code: String): Response<SerialCodeResponse> {
        return referralService.postSerialCode(SerialCodeRequest(code))
    }

    suspend fun getMyRerollCount(): Response<SerialCodeResponse> {
        return referralService.getMyRerollCount()
    }

    suspend fun postReferralCode(code: String): Response<ReferralCodeResponse> {
        return referralService.postReferralCode(ReferralCodeRequest(code))
    }

    suspend fun getMyReferralCode(): Response<ReferralCodeResponse> {
        return referralService.getMyReferralCode()
    }
}