package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class ReferralCodeRequest(
    @SerializedName("referralCode")
    val referralCode: String
)
