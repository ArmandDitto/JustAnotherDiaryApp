package com.example.justordinarydiaryapp.model


import com.google.gson.annotations.SerializedName

data class User(

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("username")
    val username: String? = null

)