package com.test.login.ui

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NO_HISTORY
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.test.login.data.api.response.BaseResponse
import com.test.login.data.api.response.LoginResponse
import com.test.login.databinding.ActivityMainBinding
import com.test.login.utils.PREFCONST
import com.test.login.utils.SessionManager
import com.test.login.viewmodel.LoginViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.loginResult.observe(this) {
            when (it) {
                is BaseResponse.Loading -> {
                    showLoading()
                }

                is BaseResponse.Success -> {
                    stopLoading()
                    processLogin(it.data)
                }

                is BaseResponse.Error -> {
                    processError(it.msg)
                }
                else -> {
                    stopLoading()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            doLogin()

        }


    }



    fun doLogin() {

        val userName = binding.txtUsername.text.toString()
        val pwd = binding.txtPass.text.toString()
        viewModel.loginUser(username = userName, pwd = pwd, context = this)

    }

    fun doSignup() {

    }

    fun showLoading() {
        binding.prgbar.visibility = View.VISIBLE
    }

    fun stopLoading() {
        binding.prgbar.visibility = View.GONE
    }

    fun processLogin(data: LoginResponse?) {
        showToast("Success:" + data?.data?.userName)
        data?.data?.userName?.let {
            SessionManager.saveString(this, PREFCONST.PREF_USER_NAME,it);
        }
        data?.data?.userId?.let {
            SessionManager.saveString(this,PREFCONST.PREF_USER_ID,it);
        }
        data?.data?.isDeleted?.let {
            SessionManager.saveBoolean(this,PREFCONST.PREF_USER_DELETED,it);
        }
    }

    fun processError(msg: String?) {
        showToast("Error:" + msg)
    }

    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


}
