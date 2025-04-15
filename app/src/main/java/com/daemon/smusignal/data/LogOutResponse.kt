package com.daemon.smusignal.data

import com.google.gson.annotations.SerializedName

data class LogOutResponse(
    @SerializedName("message")
    val message: String,
)
