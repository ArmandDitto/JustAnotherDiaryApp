package com.example.justordinarydiaryapp.utils

import android.view.View
import android.widget.TextView
import androidx.core.util.PatternsCompat
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.regex.Pattern

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

        fun isBlank(textView: TextView?): Boolean {
            if ((textView?.visibility ?: View.GONE) != View.VISIBLE) return true
            return isBlank(textView?.text.toString())
        }

        fun isBlank(text: String): Boolean {
            return text.isBlank()
        }


        fun isValidEmail(textView: TextView?): Boolean {
            return if (textView?.visibility == View.VISIBLE) isValidEmail(textView.text.toString())
            else true
        }

        fun isValidEmail(text: String?): Boolean {
            if (text.isNullOrEmpty()) return false
            return PatternsCompat.EMAIL_ADDRESS.matcher(text).matches()
        }

        fun isValidPassword(textView: TextView?): Boolean {
            return if (textView?.visibility == View.VISIBLE) isValidPassword(textView.text.toString())
            else true
        }

        fun isValidPassword(text: String?): Boolean {
            if (text.isNullOrEmpty()) return false
            val pattern = Pattern.compile("[a-zA-Z0-9]+")
            return pattern.matcher(text).matches()
        }
    }
}