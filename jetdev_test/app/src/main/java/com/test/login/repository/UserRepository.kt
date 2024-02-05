package com.test.login.repository

import com.test.login.data.api.methods.UserApi
import com.test.login.data.api.request.LoginRequest
import com.test.login.data.api.response.LoginResponse
import retrofit2.Response

class UserRepository {

   suspend fun loginUser(loginRequest:LoginRequest): Response<LoginResponse>? {
      return  UserApi.getApi()?.loginUser(loginRequest = loginRequest)
    }
}