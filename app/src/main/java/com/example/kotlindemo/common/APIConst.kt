package com.example.kotlindemo.common

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object 單例模式
object APIConst {

    var baseUrl = "http://data.ntpc.gov.tw/"
//    var newTaipeiCity = "http://data.ntpc.gov.tw/api/v1/rest/datastore/382000000A-000352-001"

fun setBaseURL(url: String){
    baseUrl = url
}
    fun getBaseURL():String{
        return baseUrl
    }

 fun getAPIService(): APIService{

        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
     loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
     builder.addInterceptor(loggingInterceptor)

     val okHttpClient = builder.build()
     val retrofit = Retrofit.Builder()
         .baseUrl(baseUrl)
         .addConverterFactory(GsonConverterFactory.create())
         .client(okHttpClient)
         .build()

        //APIService::class.java 取得java類
        return retrofit.create(APIService::class.java)
    }


}