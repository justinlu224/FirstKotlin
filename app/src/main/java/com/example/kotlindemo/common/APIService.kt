package com.example.kotlindemo.common

import com.example.kotlindemo.model.NewTaipeiCityModel
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("api/v1/rest/datastore/382000000A-000352-001")
    fun getNewTaipeiCityList(): Call<NewTaipeiCityModel>
}