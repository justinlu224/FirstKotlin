package com.example.kotlindemo.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kotlindemo.fragment.FirstFragment
import com.example.kotlindemo.fragment.SecondFragment
import java.util.*
import kotlin.collections.ArrayList

class MainViewPagerAdapter(fragmentActivity: FragmentActivity, private val titles: ArrayList<String>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {

        return if (position == 0){
            FirstFragment.newInstance()
        }else{
            SecondFragment.newInstance()
        }

    }
}