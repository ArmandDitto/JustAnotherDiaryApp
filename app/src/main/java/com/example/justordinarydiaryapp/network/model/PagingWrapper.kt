package com.example.justordinarydiaryapp.network.model

import com.google.gson.annotations.SerializedName

data class PagingWrapper<T>(

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("data")
    val data: T? = null,

    @SerializedName("limit")
    val limit: Int? = null,

    @SerializedName("total_data")
    val totalData: Int? = null

)