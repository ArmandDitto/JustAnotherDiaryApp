package com.example.justordinarydiaryapp.utils

import android.util.Patterns
import java.util.regex.Pattern

class FormValidator {

    fun checkEmail(email: String?): String {
        val pattern = Patterns.EMAIL_ADDRESS
        return if (email.isNullOrEmpty()) {
            "This field is required"
        } else if (!pattern.matcher(email).matches()) {
            "Please input valid email"
        } else ""
    }

    fun checkPassword(password: String?): String {
        val pattern = Pattern.compile("[a-zA-Z0-9]+")
        return if (password.isNullOrEmpty()) {
            "This field is required"
        } else if (!pattern.matcher(password).matches()) {
            "The password should only contain letters and numbers"
        } else ""
    }

    fun checkUsername(username: String?): String {
        val pattern = Pattern.compile("[a-zA-Z0-9]+")
        return if (username.isNullOrEmpty()) {
            "This field is required"
        } else if (!pattern.matcher(username).matches()) {
            "The password should only contain letters and numbers"
        } else ""
    }

}