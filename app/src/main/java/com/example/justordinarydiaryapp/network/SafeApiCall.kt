package com.example.justordinarydiaryapp.network

import com.example.justordinarydiaryapp.network.model.ErrorResponse
import com.example.justordinarydiaryapp.network.model.HttpStatusCode
import com.example.justordinarydiaryapp.network.model.ResultWrapper
import com.squareup.moshi.JsonDataException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.BufferedReader
import java.io.IOException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.Error(
                    null,
                    throwable.localizedMessage ?: "Connection Problem"
                )

                is HttpException -> ResultWrapper.Error(
                    throwable.code(),
                    parseRetrofitException(throwable)
                )

                is JsonDataException -> ResultWrapper.Error(null, "Parsing data error")
                else -> ResultWrapper.Error(null, throwable.message)
            }
        }
    }
}

fun parseRetrofitException(exception: HttpException): String {
    var errorMessage = ""
    val httpStatusCode = HttpStatusCode.values().firstOrNull { it.code == exception.code() }
        ?: HttpStatusCode.Unknown
    val errorCode = exception.code()
    val stream = exception.response()?.errorBody()?.byteStream()
    val errorJson = stream?.bufferedReader()?.use(BufferedReader::readText)

    errorJson?.let {
        errorMessage = ErrorResponse(it).message ?: ""
    }

    errorMessage =
        if (errorMessage.isBlank() || errorMessage.equals(null) || errorMessage == "null") {
            "$errorCode - ${httpStatusCode.statusName}"
        } else {
            errorMessage
//            "$errorCode - $errorMessage" //TODO enable this instead L:55 to show the error code
        }

    return errorMessage
}