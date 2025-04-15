package com.daemon.smusignal.data

import com.google.gson.annotations.SerializedName

data class MailVerificationResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String?
)
