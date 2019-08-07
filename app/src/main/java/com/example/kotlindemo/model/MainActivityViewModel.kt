package com.example.kotlindemo.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlindemo.common.APIConst
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivityViewModel: ViewModel() {

    private var userList = MutableLiveData<List<User>>()
    private var newTaipeiCity = MutableLiveData<NewTaipeiCityModel>()
    private var list: MutableList<User> = ArrayList()
    private val apiService = APIConst.getAPIService()


    init {
        getUserList()
        getNewTaipeiCityList()
    }

    private fun getNewTaipeiCityList() {
        apiService.getNewTaipeiCityList().enqueue(object: Callback, retrofit2.Callback<NewTaipeiCityModel> {
            override fun onFailure(call: Call<NewTaipeiCityModel>, t: Throwable) {

                Log.d("MvvM","data: ${t.message.toString()}")

            }

            override fun onResponse(call: Call<NewTaipeiCityModel>, response: Response<NewTaipeiCityModel>) {

                val data = response.body()

                if (data != null) {
                    newTaipeiCity.value = data
                    Log.d("MvvM","data: ${data.result.records.get(0).sarea}")
                }else{
                    Log.d("MvvM","data: null")
                }

            }

        })
    }

    fun reTry(){
        getNewTaipeiCityList()
    }


    fun getUserList(): LiveData<List<User>> {

         initData()
         return userList
    }

    fun getNewTaipeiCityLiveData(): LiveData<NewTaipeiCityModel>{
        return newTaipeiCity
    }

    fun initData(){
        val user = User()
        val user1 = User("Jack",18)
        val user2 = User("Mark")
        val user3 = User("Mark",18)
        val user4 = User("TT",20)
        val user5 = User("QQ",18)
        val user6 = User("WW",44)
        val user7 = User("Qwerty",35)
        val user8 = User("二三四",66)
        list.add(user)
        list.add(user1)
        list.add(user2)
        list.add(user3)
        list.add(user4)
        list.add(user5)
        list.add(user6)
        list.add(user7)
        list.add(user8)
        list.add(user2)
        list.add(user3)
        list.add(user4)
        list.add(user4)
        list.add(user5)
        list.add(user6)
        userList.value = list
    }

}