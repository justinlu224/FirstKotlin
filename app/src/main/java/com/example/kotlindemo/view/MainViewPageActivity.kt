package com.example.kotlindemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlindemo.R
import com.example.kotlindemo.adapter.MainViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_main_view_page.*

class MainViewPageActivity : AppCompatActivity() {

    val tabTitle = arrayListOf<String>("總覽","個別")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view_page)

        initView()
    }

    private fun initView() {

        viewpager.adapter = MainViewPagerAdapter(this,tabTitle)

        TabLayoutMediator(tablayout,viewpager){ tab,position ->

            tab.text = tabTitle[position]

        }.attach()


    }


}
