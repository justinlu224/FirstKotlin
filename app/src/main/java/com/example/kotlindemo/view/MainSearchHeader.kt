package com.example.kotlindemo.view

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.kotlindemo.R
import com.example.kotlindemo.callBack.ButtonClickCallBack

class MainSearchHeader(context: Context?, buttonClickCallBack: ButtonClickCallBack) : BaseHeader(context) {

    private  var buttonClickCallBack = buttonClickCallBack
    var view : View? = null
    var etSearch:EditText? = null

    init {
        view = getContentView()
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_search_header
    }

    override fun onCreateView(context: Context?) {
        view = getContentView()
        etSearch = view!!.findViewById(R.id.etSearch)

        etSearch!!.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

                buttonClickCallBack.buttonCallBckOnClick(s.toString(),0)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }


}