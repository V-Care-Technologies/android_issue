package com.example.recyclermulti.Domain.Repo

import com.example.recyclermulti.Domain.ApiServices.AuthApiService
import com.example.recyclermulti.models.req.Loginreq
import com.example.recyclermulti.models.res.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val authApiService: AuthApiService) {

    suspend fun login(loginreq: Loginreq):Response<LoginResponse>{
        return authApiService.login(loginreq)
    }
}