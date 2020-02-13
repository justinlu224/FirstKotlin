package com.example.kotlindemo.common

import com.example.kotlindemo.model.NewTaipeiCityModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

typealias DoneHandle = (data: Any?, error: Throwable?) -> Unit

class APIManager {
    private var apiClient: APIService? = null

    init {
        apiClient = JobAPI.client.create(APIService::class.java)
    }

    fun <T> handle(call: Call<T>?, completion: DoneHandle) {
        call?.enqueue(object : Callback<T> {

            override fun onFailure(call: Call<T>, t: Throwable) {
                completion(null, t)
            }

            override fun onResponse(call: Call<T>?, response: Response<T>?) {
                response?.body()?.also { data ->
                    data?.let { data ->
                        completion(data, null)
                    } ?: run {
                        // data是空的
                        completion(null, null)
                    }
                }
            }
        })
    }

    fun callGetNewTaipeiCityList(completion: DoneHandle) {
        val cell = apiClient?.getNewTaipeiCityList()
        handle(cell, completion)
    }

//    fun callGetNewTaipeiCityList(completion: GetNewTaipeiCityListCompletion) {
//        doGetNewTaipeiCityList { isSuccess, data, error ->
//            if (isSuccess) {
//                val data = data as? NewTaipeiCityModel
//                data?.let { data ->
//                    completion(data, error)
//                } ?: run {
//                    // data是空的
//                    completion(null, null)
//                }
//            } else {
//                // 發生錯誤
//                completion(null, error)
//            }
//        }
//    }


}