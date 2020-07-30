package com.example.kotlindemo.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlindemo.common.APIConst
import com.example.kotlindemo.common.APIManager
import com.example.kotlindemo.view.FirstFragmentViewModel
import com.example.kotlindemo.view.SecondFragmentViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivityViewModel : ViewModel(), FirstFragmentViewModel, SecondFragmentViewModel {
    override fun getCityData(): LiveData<List<Record>> {
        return newTaipeiCity
    }

    override fun getMarkData(): LiveData<List<Record>> {
        return markerList
    }

    override fun getMarkList(): List<Record> {
        return markers
    }

    private var userList = MutableLiveData<List<User>>()
    private var newTaipeiCity = MutableLiveData<List<Record>>()
    private var list: MutableList<User> = ArrayList()
    private val apiService = APIConst.getAPIService()
    private var markerList = MutableLiveData<List<Record>>()
    private var markers = mutableListOf<Record>()

    init {
        getUserList()
        getNewTaipeiCityList()
    }

//    private fun getNewTaipeiCityList() {
//        apiService.getNewTaipeiCityList()
//            .enqueue(object : Callback, retrofit2.Callback<List<Record>> {
//                override fun onFailure(call: Call<List<Record>>, t: Throwable) {
//
//                    Log.d("MvvM", "data: ${t.message.toString()}")
//
//                }
//
//                override fun onResponse(
//                    call: Call<List<Record>>,
//                    response: Response<List<Record>>
//                ) {
//
//                    val data = response.body()
//
//                    if (data != null) {
//                        newTaipeiCity.value = data
//                        markerList.value = data
//                        markers.addAll(data)
//                        Log.d("MvvM", "data: ${data.get(0).sarea}")
//                    } else {
//                        Log.d("MvvM", "data: null")
//                    }
//                }
//            })
//    }


    private fun getNewTaipeiCityList() {
        viewModelScope.launch {
            try {
                val data = withContext(Dispatchers.Main) {
                    apiService.getNewTaipeiCityList()
                }
                newTaipeiCity.value = data
                markerList.value = data
                markers.addAll(data)
                Log.d("MvvM", "data: ${data.get(0).sarea}")
            } catch (e: Exception) {
                Log.d("MvvM", "Exception: ${e.message}")
            }
        }
    }

//    private fun getNewTaipeiCityListWithHandle() {
//        APIManager().callGetNewTaipeiCityList { data, error ->
//            var data = data as? List<Record>
//            // 有錯誤訊息
//            if (error != null) {
//                Log.d("Testing", "error: ${error.message.toString()}")
//                return@callGetNewTaipeiCityList
//            }
//
//            // 資料為 null
//            if (data == null) {
//                Log.d("Testing", "data IS NULL!")
//                return@callGetNewTaipeiCityList
//            }
//
//            // 成功取得資料
//            newTaipeiCity.value = data
//            markerList.value = data
//            Log.d("Testing", "data: ${data.get(0).sarea}")
//
//        }
//
//    }

    fun reTry() {
        getNewTaipeiCityList()
    }

    fun getMarkerList(): LiveData<List<Record>> {
        return markerList
    }

    fun getUserList(): LiveData<List<User>> {

        initData()
        return userList
    }

    fun getNewTaipeiCityLiveData(): LiveData<List<Record>> {
        return newTaipeiCity
    }

    fun initData() {
        val user = User()
        val user1 = User("Jack", 18)
        val user2 = User("Mark")
        val user3 = User("Mark", 18)
        val user4 = User("TT", 20)
        val user5 = User("QQ", 18)
        val user6 = User("WW", 44)
        val user7 = User("Qwerty", 35)
        val user8 = User("二三四", 66)
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