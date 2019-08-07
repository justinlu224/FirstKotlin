package com.example.kotlindemo.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.kotlindemo.R
import com.example.kotlindemo.model.Record
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.google.maps.android.ui.IconGenerator

class MarkerClusterRender(context: Context?, map: GoogleMap?, clusterManager: ClusterManager<Record>?) :
    DefaultClusterRenderer<Record>(context, map, clusterManager) {

    var  iconGenerator: IconGenerator
    var  markerImageView: ImageView

init {
    //這裡是設定圖標的外框
    iconGenerator =  IconGenerator(context);
    markerImageView = ImageView(context);
    markerImageView.setLayoutParams( ViewGroup.LayoutParams(60 , 40))
    iconGenerator.setContentView(markerImageView);
}

    override fun onBeforeClusterItemRendered(item: Record?, markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)


        //這邊設判斷一可用數量來做圖標選擇  使用外框的寫法
      if (item!!.sarea.equals("板橋區")) {
            markerImageView.setImageResource(R.drawable.location_vector_icon2)
            val icon = iconGenerator.makeIcon()
            markerOptions!!.icon(BitmapDescriptorFactory.fromBitmap(icon))
            markerOptions.title(item!!.getTitle())

        } else if (item!!.sarea.equals("土城區")) {
            markerImageView.setImageResource(R.drawable.location_vector_icon)
            val icon = iconGenerator.makeIcon()
            markerOptions!!.icon(BitmapDescriptorFactory.fromBitmap(icon))
            markerOptions.title(item!!.getTitle())
        }

        //不使用外框寫法
//        if (markerOptions.getTitle().equals("CodingInfinite2")){
//
//            markerOptions.icon(bitmapDescriptorFromVector(context,R.drawable.location_vector_icon2));
//            markerOptions.title(item.getTitle());
//        }else if (item.getTitle().equals("CodingInfinite6")){
//
//            markerOptions.title(item.getTitle());
//
//        }else{
//
//            markerOptions.icon(bitmapDescriptorFromVector(context,R.drawable.location_vector_icon));
//            markerOptions.title(item.getTitle());
//        }


    }

    //轉bitmap
    fun  bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor {
        var vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
       val bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
       val canvas =  Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}