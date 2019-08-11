package com.example.kotlindemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.common.APIConst
import com.example.kotlindemo.model.MainActivityViewModel
import com.example.kotlindemo.model.NewTaipeiCityModel
import com.example.kotlindemo.model.Record


class MainActivity : AppCompatActivity() {

    private lateinit var  tvTitleBar: TextView
    private lateinit var  recyclerView:RecyclerView
    private var uBickList = ArrayList<Record>()
    private var adapter: MainAdapter? = null

    private lateinit var model:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val api = APIConst
        Log.d("api", "api1: ${api}")
         tvTitleBar = findViewById(R.id.tvTitleBar)
        recyclerView = findViewById(R.id.recyclerView)

        tvTitleBar.text = "KotlinRecyclerViewDemo"
        model = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
        initView()
        observerViewModel()
    }

    private fun initView() {
        val api2 = APIConst

        Log.d("api", "api2: ${api2}")
        var layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        recyclerView.layoutManager = layoutManager
        adapter = MainAdapter(uBickList)
        recyclerView.adapter = adapter

        adapter!!.onItemClickListener = BaseQuickAdapter.OnItemClickListener(
            { baseQuickAdapter: BaseQuickAdapter<Any, BaseViewHolder>, view: View, i: Int ->


                Toast.makeText(this,"onClick ${i}",Toast.LENGTH_SHORT).show()
            })

    }

    private fun observerViewModel() {

        model.getNewTaipeiCityLiveData().observe(this,Observer<NewTaipeiCityModel>{data ->
            Log.d("api", "api1: ${data}")
            uBickList.clear()
            uBickList.addAll(data.result.records)
            adapter!!.notifyDataSetChanged()
        })
    }
}
