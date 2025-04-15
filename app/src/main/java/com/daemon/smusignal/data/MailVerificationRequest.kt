package com.daemon.smusignal.data

import com.google.gson.annotations.SerializedName

data class MailVerificationRequest(
    @SerializedName("mailVerification")
    val mailVerification: String
)
