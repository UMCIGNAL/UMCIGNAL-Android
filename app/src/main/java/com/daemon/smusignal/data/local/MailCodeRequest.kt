package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class MailCodeRequest(
    @SerializedName("mail")
    val mail: String
)
