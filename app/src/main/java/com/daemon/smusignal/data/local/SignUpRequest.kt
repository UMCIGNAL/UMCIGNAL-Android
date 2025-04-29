package com.daemon.smusignal.data.local

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("MBTI")
    val mbti: String, // INTJ

    @SerializedName("age")
    val age: String, // 2000-10-06

    @SerializedName("gender")
    val gender: String, // male

    @SerializedName("instagram_id")
    val instagramId: String, // aemond_daily

    @SerializedName("is_drinking")
    val isDrinking: Int, // 1

    @SerializedName("is_smoking")
    val isSmoking: Boolean, // true

    @SerializedName("name")
    val name: String, // 정승원

    @SerializedName("nickname")
    val nickname: String, // daemon

    @SerializedName("student_major")
    val studentMajor: String // 컴퓨터과학과
)