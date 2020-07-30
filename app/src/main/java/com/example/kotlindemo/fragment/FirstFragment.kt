package com.example.kotlindemo.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.kotlindemo.R
import com.example.kotlindemo.model.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    lateinit var viewModel:MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getNewTaipeiCityLiveData().observe(activity!!, Observer {
            tvFirst.text = it.first().sarea
        })
    }

    companion object {

        fun newInstance() = FirstFragment()

    }
}
