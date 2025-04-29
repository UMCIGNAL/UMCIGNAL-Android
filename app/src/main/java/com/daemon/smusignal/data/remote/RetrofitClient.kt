package com.daemon.smusignal.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var _tokenManager: TokenManager? = null

    private val tokenManager: TokenManager
        get() = _tokenManager ?: throw IllegalStateException("TokenManager is not initialized.")

    fun init(tokenManager: TokenManager) {
        _tokenManager = tokenManager
    }

    fun clearTokens() {
        tokenManager.clearTokens()
    }

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()

                // 요청에 커스텀 헤더가 있으면 해당 플래그에 따라 토큰을 다르게 처리
                tokenManager.getAccessToken()?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://smuumc.kro.kr/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val referralService: ReferralService by lazy {
        retrofit.create(ReferralService::class.java)
    }
}