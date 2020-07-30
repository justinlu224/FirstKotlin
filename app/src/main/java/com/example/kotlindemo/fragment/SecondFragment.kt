package com.example.kotlindemo.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.example.kotlindemo.R
import com.example.kotlindemo.model.MainActivityViewModel
import com.example.kotlindemo.model.Record
import com.example.kotlindemo.view.SecondFragmentViewModel
import kotlinx.android.synthetic.main.fragment_second.*

class SecondFragment : Fragment() {

//    lateinit var viewModel: SecondFragmentViewModel
    var records:MutableList<Record> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val viewModel : SecondFragmentViewModel by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(MainActivityViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.getMarkData().observe(this, Observer {
//            tvSenond.text = it.first().sarea?: kotlin.run { "null" }
            records.addAll(it)
            if (records.isNullOrEmpty()){
                Log.d("record","null")
            }else{
                Log.d("record","not null")

            }
            tvSenond.text = records.first().sarea?: kotlin.run { "null" }

        })
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        tvSenond.text = viewModel.getMarkList().first().sarea?:run{"null"}
    }

    companion object {

        fun newInstance() =  SecondFragment()

    }
}
