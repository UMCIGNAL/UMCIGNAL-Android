package com.daemon.smusignal.data.remote

import com.daemon.smusignal.data.local.ReferralCodeRequest
import com.daemon.smusignal.data.local.ReferralCodeResponse
import com.daemon.smusignal.data.local.SerialCodeRequest
import com.daemon.smusignal.data.local.SerialCodeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface ReferralService {
    @PATCH("serialCode/insertCode")
    suspend fun postSerialCode(
        @Body request: SerialCodeRequest
    ): Response<SerialCodeResponse>

    @GET("serialCode/myReroll")
    suspend fun getMyRerollCount(): Response<SerialCodeResponse>

    @PATCH("referral/findReferralCode")
    suspend fun postReferralCode(
        @Body request: ReferralCodeRequest
    ): Response<ReferralCodeResponse>

    @GET("referral/getMyReferralCode")
    suspend fun getMyReferralCode(): Response<ReferralCodeResponse>
}