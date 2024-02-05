package com.test.login.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.test.login.data.api.request.LoginRequest
import com.test.login.data.api.response.BaseResponse
import com.test.login.data.api.response.LoginResponse
import com.test.login.repository.UserRepository
import com.test.login.utils.PREFCONST
import com.test.login.utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    val userRepo = UserRepository()
    val loginResult: MutableLiveData<BaseResponse<LoginResponse>> = MutableLiveData()

    fun loginUser(username: String, pwd: String,context : Context) {

        loginResult.value = BaseResponse.Loading()
        viewModelScope.launch {
            try {

                val loginRequest = LoginRequest(
                    password = pwd,
                    username = username
                )
                val response = userRepo.loginUser(loginRequest = loginRequest)

                if (response?.code() == 200) {

                   var data= BaseResponse.Success(response.body());
                    if(data.data?.errorCode == "00")
                    {
                        response.raw().headers["X-acc"]?.let {
                            SessionManager.saveString(context, PREFCONST.PREF_USER_X_ACC,
                                it
                            )
                        }
                        loginResult.value = BaseResponse.Success(response.body())

                    }
                    else{
                        loginResult.value = BaseResponse.Error((data.data?.errorMessage))

                    }
                } else {
                    loginResult.value = BaseResponse.Error(response?.message())
                }

            } catch (ex: Exception) {
                loginResult.value = BaseResponse.Error(ex.message)
            }
        }
    }
}