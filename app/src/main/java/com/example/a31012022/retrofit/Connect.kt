package com.example.a31012022.retrofit

import com.google.gson.Gson
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

class Connect {
    companion object{
        val BASE_URL = "http://10.0.2.2:3000"
        val connect = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)
    }
}
interface ApiRequests{
    @POST("/login")
    suspend fun Auth(
        @Body user: user
    ):Response<Void>


}