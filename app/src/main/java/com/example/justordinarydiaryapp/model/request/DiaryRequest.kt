package com.example.justordinarydiaryapp.model.request

import com.google.gson.annotations.SerializedName

data class DiaryRequest (

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("content")
    val content: String? = null

)