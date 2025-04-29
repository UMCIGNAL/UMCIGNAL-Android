package com.daemon.smusignal.data.repository

import com.daemon.smusignal.data.local.CheckSignUpResponse
import com.daemon.smusignal.data.remote.AuthService
import com.daemon.smusignal.data.local.MailCodeRequest
import com.daemon.smusignal.data.local.MailCodeResponse
import com.daemon.smusignal.data.local.MailVerificationRequest
import com.daemon.smusignal.data.local.MailVerificationResponse
import com.daemon.smusignal.data.remote.TokenManager
import retrofit2.Response

class AuthRepository(
    private val authService: AuthService,
    val tokenManager: TokenManager
) {
    suspend fun requestMailCode(mail: String): Response<MailCodeResponse> {
        return authService.postMailCode(MailCodeRequest(mail))
    }

    suspend fun verifyMailCode(mailVerification: String): Response<MailVerificationResponse> {
        return authService.getVerify(MailVerificationRequest(mailVerification))
    }

    suspend fun checkSignUp(): Response<CheckSignUpResponse> {
        return authService.checkSignUp()
    }
}