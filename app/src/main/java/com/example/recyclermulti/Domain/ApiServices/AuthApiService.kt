package com.example.recyclermulti.Domain.ApiServices

import com.example.recyclermulti.models.req.Loginreq
import com.example.recyclermulti.models.res.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login")
    suspend fun login(@Body loginreq: Loginreq): Response<LoginResponse>

}