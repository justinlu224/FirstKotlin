package com.example.kotlindemo.view

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.model.Record

class MainAdapter(data: MutableList<Record>?) : BaseQuickAdapter<Record, BaseViewHolder>(R.layout.item,data) {
    override fun convert(helper: BaseViewHolder, item: Record) {
        helper.setText(R.id.tvName, item.sna)
    }

}