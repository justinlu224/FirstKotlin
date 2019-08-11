package com.example.kotlindemo.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.model.Record
import java.util.*
import kotlin.collections.ArrayList

class MapSearchAdapter(context: Context, resource: Int, objects: MutableList<Record>) :
    ArrayAdapter<Record>(context, resource, objects), Filterable{

    var records = objects
    private var resultList: MutableList<Record> = ArrayList()

    fun setListData(datas: MutableList<Record>){
        records = datas
    }

    fun getListData():MutableList<Record>{
        return records
    }

    override fun getItem(position: Int): Record? {
        return records.get(position)
    }



    override fun getCount(): Int {
        return records.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        super.getView(position, convertView, parent)
                   val recordData = getItem(position)
        var convertView = convertView

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search, parent, false)
        }
        val tvSna =  convertView!!.findViewById<TextView>(R.id.tvSna)
        tvSna.text = recordData!!.sna
        val tvSbi = convertView.findViewById<TextView>(R.id.tvSbi)
        tvSbi.text = "可借數量：${recordData.sbi}"
        val tvBemp = convertView.findViewById<TextView>(R.id.tvBemp)
        tvBemp.text = "可還數量：${recordData.bemp}"

        return convertView
    }



    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                var charString = constraint.toString().trim()
                val filteredList = ArrayList<Record>()

                if (constraint != null){
                    for (row in records){
                        if (row.sarea!!.toLowerCase().contains(
                                charString.toLowerCase()) ){
                                filteredList.add(row)
                            }
                    }
                    var filterResults =  FilterResults()
                    filterResults.values = filteredList
                    filterResults.count = filteredList.size
                    Log.d("222","filterResults")
                    return filterResults

                } else {
                    Log.d("222","FilterResults")
                return  FilterResults()
            }


//                if (charString.isEmpty()) {
//                    searchListFiltered = searchList;
//                } else {
//                    List<ShopListWithoutPicture> filteredList = new ArrayList<>();
//                    for (ShopListWithoutPicture row : searchList) {
//                        //                        String Category = String.format(getStringWithCategory(row.getCategory()));
//                        // name match condition. this might differ depending on your requirement
//                        // here we are looking for name or phone number match
//
//                        if (row.getStoreName().toLowerCase().contains(charString.toLowerCase())
//                            || row.getMccName().toLowerCase().contains(
//                                charString.toLowerCase()) {
//                            filteredList.add(row)
//                            }
//                    }
//
//                    }
//
//                    searchListFiltered = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = searchListFiltered;
//                LogUtlis.d(TAG,"searchListFiltered: "+searchListFiltered+", filterResults.values: "+filterResults.values);
//                return filterResults;
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    records = results.values as ArrayList<Record>
                    Log.d("222","notifyDataSetChanged")

                    notifyDataSetChanged()
                } else {
                    Log.d("222","notifyDataSetInvalidated")

                    notifyDataSetInvalidated()
                }


            }
        }
    }

}