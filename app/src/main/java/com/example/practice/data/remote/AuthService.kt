package com.example.practice.data.remote
import android.util.Log
import com.example.practice.LoginView
import com.example.practice.SignUpView
import com.example.practice.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView : SignUpView
    private lateinit var loginView: LoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
    fun signUp(user : User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("Signup/success",response.toString())
                val resp : AuthResponse = response.body()!!
                when(resp.code){
                    1000->signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("Signup/success",t.message.toString())
            }

        })
        Log.d("Signup/success","HELLO")
    }


    fun login(user : User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(user).enqueue(object: Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("Login/success",response.toString())
                val resp : AuthResponse = response.body()!!
                when(val code = resp.code){
                   1000-> loginView.onLoginUpSuccess(code,resp.result!!)
                    else -> loginView.onLoginUpFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("Signup/success",t.message.toString())
            }

        })
        Log.d("Signup/success","HELLO")
    }
}