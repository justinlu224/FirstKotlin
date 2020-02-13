package com.example.kotlindemo.view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.OvershootInterpolator
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.callBack.ButtonClickCallBack
import com.example.kotlindemo.model.GPSTracker2
import com.example.kotlindemo.model.MainActivityViewModel
import com.example.kotlindemo.model.NewTaipeiCityModel
import com.example.kotlindemo.model.Record

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import kotlinx.android.synthetic.main.item.*

/***
 *  權限判斷
 *
 *  是否有開定位
 *
 *  取得使用者位置
 */

class MapsActivity : AppCompatActivity(), ButtonClickCallBack, OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {


    private lateinit var clusterManager: ClusterManager<Record>
    private var mMap: GoogleMap? = null
    private lateinit var viewModel: MainActivityViewModel
    private var uBickList = ArrayList<Record>()
    private lateinit var btnReTry: Button
    private lateinit var actSearch: AutoCompleteTextView
    private var adapter: MapSearchAdapter? = null
    private var clSearch: ConstraintLayout? = null
    var gpsTracker2: GPSTracker2? = null

    private var viewTouch: View? = null

    private var tranlsation = false
    private var isDown = true

//    private lateinit var  recyclerView: RecyclerView
//
//    private var adapter: MainSearchAdapter? = null
//    private var header: MainSearchHeader? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        btnReTry = findViewById(R.id.btnReTry)
//        recyclerView = findViewById(R.id.recyclerView)
        actSearch = findViewById(R.id.actSearch)
        viewTouch = findViewById(R.id.viewTouch)
        clSearch = findViewById(R.id.clSearch)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        observerViewModel()
        btnReTry.setOnClickListener(View.OnClickListener { v ->
            viewModel.reTry()
        })

        gpsTracker2 = GPSTracker2(this)

        initActSearch()


        initViewTouchLin()


//        header = MainSearchHeader(this,this)
//        var layoutManager = LinearLayoutManager(this)
//        layoutManager.orientation = RecyclerView.VERTICAL
//        recyclerView.layoutManager = layoutManager
//        adapter = MainSearchAdapter(uBickList)
//        adapter!!.addHeaderView(header!!.getContentView())
//        recyclerView.adapter = adapter

//        adapter!!.setOnItemClickListener(
//            BaseQuickAdapter.OnItemClickListener(
//                { baseQuickAdapter: BaseQuickAdapter<Any, BaseViewHolder>, view: View, i: Int ->
//
//
//                    Toast.makeText(this,"onClick ${i}", Toast.LENGTH_SHORT).show()
//                }))


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 2) {
            Log.d("MapsActivity", "location: ")
        }

    }

    private fun initAnimation() {

        val animSet = AnimationSet(true)
        animSet.isFillEnabled = true

        val alphAnimation = AlphaAnimation(1.0f, 0.2f)
        alphAnimation.duration = 1000


        val translateAnimation = TranslateAnimation(0f, 0f, 0f, -100f)
        translateAnimation.duration = 1000

        animSet.addAnimation(alphAnimation)
        animSet.addAnimation(translateAnimation)
        clSearch!!.startAnimation(animSet)


    }

//    private fun initDownAnim(){
//        val animSet = AnimationSet(true)
//        animSet.isFillEnabled = true
//
//        val alphAnimation = AlphaAnimation(1.0f, 0.2f)
//        alphAnimation.duration = 1000
//
//
//
//        val translateAnimation = TranslateAnimation(0f,0f,-100f,100f)
//        translateAnimation.duration = 1000
//        translateAnimation.fillAfter = true
//        translateAnimation.interpolator = OvershootInterpolator()
//
//        animSet.addAnimation(alphAnimation)
//        animSet.addAnimation(translateAnimation)
//        clSearch!!.startAnimation(animSet)
//    }

    private fun initDownAnim() {
        val animSet = AnimatorSet()


        val alphAnimation = AlphaAnimation(1.0f, 0.2f)
        alphAnimation.duration = 1000

//        val alph = ObjectAnimator.ofFloat(clSearch,)


        val translateAnimation = TranslateAnimation(0f, 0f, -100f, 100f)
        translateAnimation.duration = 1000

//        animSet.addAnimation(translateAnimation)
        animSet.playTogether(tranlsationY(clSearch!!, -200f), alph(clSearch!!, 1f))
        animSet.start()
        tranlsation = true
    }

    private fun tranlsationY(view: View, y: Float): ObjectAnimator {

        if (tranlsation == false) {
            return ObjectAnimator.ofFloat(view, "TranslationY", 0f, y)
        } else {
            return ObjectAnimator.ofFloat(view, "TranslationY", y, 0f)
        }

    }

    private fun alph(view: View, alph: Float): ObjectAnimator {
        if (tranlsation == false) {
            return ObjectAnimator.ofFloat(view, "alpha", alph, 0f, 0f)
        } else {
            return ObjectAnimator.ofFloat(view, "alpha", 0f, 0f, alph)
        }
    }


    private fun initViewTouchLin() {

        viewTouch!!.setOnTouchListener(object : OnSwipeTouchListener() {


            override fun onSwipeRight() {
                Log.d("viewTouch", "onSwipeRight")
            }

            override fun onSwipeLeft() {
                Log.d("viewTouch", "onSwipeLeft")
            }

            override fun onSwipeTop() {
                Log.d("viewTouch", "onSwipeTop")

                if (isDown == false) {
                    return
                } else {
                    initDownAnim()
                    isDown = false
                }

            }

            override fun onSwipeBottom() {
                Log.d("viewTouch", "onSwipeBottom")
                if (isDown == true) {
                    return
                } else {
                    initDownAnim()
                    tranlsation = false
                    isDown = true
                }

            }
        })

    }

    private fun initActSearch() {
        //這邊要先把adapter 做出來以及搜尋功能
//        actSearch.adapter = MapSearchAdapter(uBickList)
//      val  adapter = MapSearchAdapter(this,R.layout.item_search,uBickList)
        adapter = MapSearchAdapter(this, R.layout.item_search, uBickList)
        actSearch.setAdapter(adapter)

//        actSearch.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
//
////            Log.d("actSearch","onclick: "+position)
//        })

        actSearch.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                actSearch.setText(adapter!!.getListData().get(position).sna)
                Toast.makeText(this, "select: ${position}", Toast.LENGTH_SHORT).show()
            }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.d("onMapReady", "onMapReady")
        // Add a marker in Sydney and move the camera

        val sydney = LatLng(-34.0, 151.0)


//        if (mMap == null)
//            return
//        else
//            setUpClusterManager(mMap!!)
//        mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//        var lat = gpsTracker2?.getLatitude()
//        var lon = gpsTracker2?.getLongitude()
//        Log.d("MapsActivity","location: $lat , $lon")
//
//        mMap!!.setOnMarkerClickListener(this@MapsActivity)
//        mMap!!.setOnInfoWindowClickListener(GoogleMap.OnInfoWindowClickListener { marker ->
//            intent = Intent()
//            intent.setClass(this, MainActivity::class.java)
//            startActivity(intent)
//        })

//        setUpClusterManager(mMap!!)

        // mMap?.let { setUpClusterManager(it) }   判空另一種寫法 it 表示mMap
        // or mMap?.let { googleMap -> setUpClusterManager(googleMap) } 自定義變數
        // mMap?.run{ setUpClusterManager(this)   } 判空另一種寫法 run 內部不需變數 this表示mMap


        // 下面是像swift guard let 的寫法
        val map = mMap ?: run {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show()
            Log.d("MapActivity", "mMap == null")
            return
        }
        val mm = mMap?.run {
            Log.d("MapActivity", "mMap?.run")
            Toast.makeText(this@MapsActivity, "null", Toast.LENGTH_SHORT).show()
        }

        setUpClusterManager(mMap!!)

        map.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        val lat = gpsTracker2?.getLatitude()
        val lon = gpsTracker2?.getLongitude()
        Log.d("MapsActivity", "location: $lat , $lon")

        map.setOnMarkerClickListener(this@MapsActivity)
        map.setOnInfoWindowClickListener {
            intent = Intent()
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        }


    }


    override fun onMarkerClick(p0: Marker?): Boolean {

        p0!!.showInfoWindow()

        return false
    }

//
////    private void setUpClusterManager(GoogleMap googleMap){
//////        ClusterManager<User> clusterManager = new ClusterManager(this, googleMap);  // 3
//////        googleMap.setOnCameraIdleListener(clusterManager);
//////        List<User> items = getItems();
//////        clusterManager.addItems(items);  // 4
//////        clusterManager.cluster();  // 5
//////    }
//


    fun setUpClusterManager(googleMap: GoogleMap) {

        clusterManager = ClusterManager<Record>(this, googleMap)
        clusterManager.renderer = MarkerClusterRender(this, googleMap, clusterManager)
        googleMap.setOnCameraIdleListener(clusterManager)
        clusterManager.addItems(uBickList)
        clusterManager.cluster()

    }

    private fun observerViewModel() {


        viewModel.getMarkerList().observe(this, Observer<List<Record>> { data ->
            Log.d("api", "api1: ${data}")
            uBickList = data as ArrayList<Record>
            if (adapter != null)
                adapter!!.setListData(uBickList)

//            adapter?.let { it.setListData(uBickList) }


            if (mMap != null)
                setUpClusterManager(mMap!!)

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifecycle", "onDestroy: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifecycle", "onStop: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifecycle", "onStart: ")
//        viewModel.reTry()
    }


    override fun buttonCallBckOnClick(status: String?, postion: Int) {

        adapter!!.filter.filter(status)

    }


}
