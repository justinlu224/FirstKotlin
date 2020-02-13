package com.example.kotlindemo.model

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem


/***
 * sno(站點代號)、sna(中文場站名稱)
 * 、tot(場站總停車格)、sbi(可借車位數)
 * 、sarea(中文場站區域)、mday(資料更新時間)
 * 、lat(緯度)、lng(經度)、ar(中文地址)
 * 、sareaen(英文場站區域)、snaen(英文場站名稱)
 * 、aren(英文地址)、bemp(可還空位數)、act(場站是否暫停營運)
 */

data class NewTaipeiCityModel(
    val result: Result,
    val success: Boolean
)

data class Result(
    val fields: List<Field>,
    var limit: Int,
    val records: List<Record>,
    val resource_id: String,
    var total: Int
)

data class Field(
    val id: String,
    val type: String
)


 class Record: ClusterItem {
     override fun getSnippet(): String {

         return ""
     }

     override fun getTitle(): String {

         return sarea!!

     }

     override fun getPosition(): LatLng? {

         if (lat != null && lng != null){
             return LatLng(lat.toDouble(), lng.toDouble())
         }else{
          return null
         }

     }

     val act: String? = null
     val ar: String? = null
     val aren: String? = null
     val bemp: String? = null
     val lat: String? = null
     val lng: String? = null
     val mday: String? = null
     val sarea: String? = null
     val sareaen: String? = null
     val sbi: String? = null
     val sna: String? = null
     val snaen: String? = null
     val sno: String? = null
     val tot: String? = null

 }