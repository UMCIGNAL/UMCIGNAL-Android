package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class SerialCodeResponse(
    @SerializedName("result")
    val result: Int?,
    @SerializedName("message")
    val message: String
)
