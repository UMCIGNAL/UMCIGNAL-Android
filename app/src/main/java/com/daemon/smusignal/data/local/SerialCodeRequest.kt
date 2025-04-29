package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class SerialCodeRequest(
    @SerializedName("serialCode")
    val serialCode: String
)
