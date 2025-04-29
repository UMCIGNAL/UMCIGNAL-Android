package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class ReferralCodeResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: String?
)
