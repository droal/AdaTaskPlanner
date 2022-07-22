package com.example.adataskplanner.data.api

import com.example.adataskplanner.data.model.request.AuthenticationBodyDto
import com.example.adataskplanner.data.model.response.AuthenticationResponseDto
import com.example.adataskplanner.data.model.response.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AdaTaskPlannerApi {

    @POST("auth")
    suspend fun authentication(@Body authBody: AuthenticationBodyDto): Response<AuthenticationResponseDto>

    @GET("api/user/all")
    suspend fun getAllUsers(): Response<List<UserDto>>
}