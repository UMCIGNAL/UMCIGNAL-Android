package com.daemon.smusignal.data

import com.google.gson.annotations.SerializedName

data class MailCodeResponse(
    @SerializedName("userId")
    val userId: String?,

    @SerializedName("message")
    val message: String
)
