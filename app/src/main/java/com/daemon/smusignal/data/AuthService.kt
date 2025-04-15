package com.daemon.smusignal.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @POST("user/mailcode")
    suspend fun postMailCode(
        @Body request: MailCodeRequest
    ): Response<MailCodeResponse>

    @POST("user/verify")
    suspend fun getVerify(
        @Body request: MailVerificationRequest
    ): Response<MailVerificationResponse>

    @PATCH("user/signup")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<SignUpResponse>

    @PATCH("user/logOut")
    suspend fun logOut(
        @Body body: EmptyRequest = EmptyRequest
    ): Response<LogOutResponse>

    @PATCH("user/signOut")
    suspend fun signOut(
        @Body body: EmptyRequest = EmptyRequest
    ): Response<LogOutResponse>

    // TODO
    // 유저 정보 변경 API

    @GET("operating/checkSignUp")
    suspend fun checkSignUp(): Response<LogOutResponse>
}