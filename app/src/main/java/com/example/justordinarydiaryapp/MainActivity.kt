package com.example.justordinarydiaryapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.example.justordinarydiaryapp.presentation.login.LoginViewModel
import com.example.justordinarydiaryapp.utils.extension.showDefaultToast
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModel()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpObserver()
        val valid = findViewById<TextView>(R.id.txt_valid_data)
        val invalid = findViewById<TextView>(R.id.txt_invalid_data)
        valid.setOnClickListener {
            sendValidCred()
        }
        invalid.setOnClickListener {
            sendInvalidCred()
        }
    }

    private fun setUpObserver() {
        viewModel.loginLiveData.observe(this) {
            when (it) {
                is ResultWrapper.Loading -> {

                }

                is ResultWrapper.Success -> {
                    this.showDefaultToast(GsonBuilder().create().toJson(it.value))
                }

                is ResultWrapper.Error -> {
                    this.showDefaultToast(GsonBuilder().create().toJson(it.message))
                }
            }
        }
    }

    private fun sendValidCred() {
        viewModel.login(LoginRequest("amogus111@xmail.com", "123"))
    }

    private fun sendInvalidCred() {
        viewModel.login(LoginRequest("amogus111@xmail.com", "321"))
    }
}