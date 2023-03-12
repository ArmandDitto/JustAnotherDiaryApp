package com.example.justordinarydiaryapp.network.model

import com.example.justordinarydiaryapp.utils.ValidationHelper
import com.google.gson.annotations.SerializedName
import org.json.JSONException
import org.json.JSONObject

open class ErrorResponse() {

    @SerializedName("message")
    var message: String? = null

    constructor(response: String) : this() {
        var isValid = true
        if (ValidationHelper.isValidJson(response)) {
            try {
                val json = JSONObject(response)
                message = if (json.has("error")) {
                    json.optString("error")
                } else {
                    json.optString("message")
                }
            } catch (e: JSONException) {
                isValid = false
            }
        } else {
            isValid = false
        }

        if (!isValid) message = ""
    }

}