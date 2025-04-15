package com.daemon.smusignal.domain

import com.daemon.smusignal.data.AuthService
import com.daemon.smusignal.data.MailCodeRequest
import com.daemon.smusignal.data.MailCodeResponse
import com.daemon.smusignal.data.MailVerificationRequest
import com.daemon.smusignal.data.MailVerificationResponse
import com.daemon.smusignal.network.TokenManager
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
}