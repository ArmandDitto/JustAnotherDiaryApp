package com.example.justordinarydiaryapp.model


import com.google.gson.annotations.SerializedName

data class Diary (

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("is_archieved")
    val isArchieved: Boolean? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    val updatedAt: String? = null

)