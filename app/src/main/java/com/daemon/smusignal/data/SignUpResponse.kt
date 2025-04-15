package com.daemon.smusignal.data

import com.google.gson.annotations.SerializedName

data class SignUpResponse(
    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: SignUpData? = null,

    @SerializedName("missingFields")
    val missingFields: List<String>? = null
)
