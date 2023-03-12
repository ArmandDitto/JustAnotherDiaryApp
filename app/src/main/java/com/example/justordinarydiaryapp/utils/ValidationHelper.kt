package com.example.justordinarydiaryapp.utils

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ValidationHelper {

    companion object {
        fun isValidJson(message: String): Boolean {
            try {
                JSONObject(message)
            } catch (e: JSONException) {
                try {
                    JSONArray(message)
                } catch (ex: JSONException) {
                    return false
                }

            }

            return true
        }
    }

}