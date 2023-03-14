package com.example.justordinarydiaryapp.network

import com.example.justordinarydiaryapp.model.Diary
import com.example.justordinarydiaryapp.model.User
import com.example.justordinarydiaryapp.model.request.LoginRequest
import com.example.justordinarydiaryapp.model.request.DiaryRequest
import com.example.justordinarydiaryapp.model.request.RegisterRequest
import com.example.justordinarydiaryapp.model.response.LoginResponse
import com.example.justordinarydiaryapp.network.model.PagingWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ) : LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ) : User

    @POST("diary")
    suspend fun crateNewDiary(
        @Body diaryRequest: DiaryRequest
    ) : Diary

    @GET("diary")
    suspend fun getDiariesPaging(
        @Query("page") page: Int,
    ): PagingWrapper<List<Diary>>

    @GET("diary/{diary_id}")
    suspend fun getDiaryDetail(
        @Path("diary_id") diaryId: String,
    ): Diary

    @PUT("diary/{diary_id}")
    suspend fun editDiary(
        @Path("diary_id") diaryId: String,
        @Body diaryRequest: DiaryRequest
    ): Diary

}