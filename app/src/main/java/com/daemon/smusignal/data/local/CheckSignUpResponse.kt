package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class CheckSignUpResponse(
    @SerializedName("signUpStatus")
    val signUpStatus: Boolean?,

    @SerializedName("idleTypeStatus")
    val idleTypeStatus: Boolean?,

    @SerializedName("message")
    val message: String
)
