package com.example.kotlindemo.view

import androidx.lifecycle.LiveData
import com.example.kotlindemo.model.NewTaipeiCityModel
import com.example.kotlindemo.model.Record

interface SecondFragmentViewModel {

        fun getMarkData():LiveData<List<Record>>
        fun getMarkList():List<Record>

}