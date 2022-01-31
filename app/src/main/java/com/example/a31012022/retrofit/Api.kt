package com.example.a31012022.retrofit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class Api:ViewModel(){
    fun Auth(user: user,onGet:(Response<Void>)->Unit){
        viewModelScope.launch {
            try {
                val connect = Connect.connect.Auth(user)
                if (connect.code() == 201){

                    onGet(connect)
                }
            }catch (e:Exception){

            }
        }

    }
}