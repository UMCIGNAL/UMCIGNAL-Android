package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class SignUpData(
    @SerializedName("user_id")
    val userId: Int,

    @SerializedName("student_id")
    val studentId: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("is_smoking")
    val isSmoking: Int,

    @SerializedName("is_drinking")
    val isDrinking: String,

    @SerializedName("MBTI")
    val mbti: String,

    @SerializedName("student_major")
    val studentMajor: String,

    @SerializedName("instagram_id")
    val instagramId: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("deleted_at")
    val deletedAt: String?, // null 가능성 있음

    @SerializedName("valid_key")
    val validKey: String,

    @SerializedName("reroll")
    val reroll: Int,

    @SerializedName("instagram_reject")
    val instagramReject: Int,

    @SerializedName("age")
    val age: Int,

    @SerializedName("Token")
    val token: String,

    @SerializedName("referralCode")
    val referralCode: String,

    @SerializedName("referralIndex")
    val referralIndex: Int,

    @SerializedName("signUpComplete")
    val signUpComplete: Int,

    @SerializedName("idleComplete")
    val idleComplete: Int
)
