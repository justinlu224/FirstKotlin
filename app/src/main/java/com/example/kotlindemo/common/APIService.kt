package com.example.kotlindemo.common

import com.example.kotlindemo.model.NewTaipeiCityModel
import com.example.kotlindemo.model.Record
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("api/datasets/71CD1490-A2DF-4198-BEF1-318479775E8A/json/preview")
    suspend fun getNewTaipeiCityList(): List<Record>

//     錯誤網段
//    @GET("api/v1/rest/datastore/382000000A-000352-001")
//    suspend fun getNewTaipeiCityList(): List<Record>


    @GET("api/datasets/71CD1490-A2DF-4198-BEF1-318479775E8A/json/preview")
     fun getNewTaipeiCityListWithHandle(): List<Record>
}
