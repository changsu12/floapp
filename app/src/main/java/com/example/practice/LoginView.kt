package com.example.practice

import com.example.practice.data.remote.Result

interface LoginView {
    fun onLoginUpSuccess(code : Int, result : Result)
    fun onLoginUpFailure()
}