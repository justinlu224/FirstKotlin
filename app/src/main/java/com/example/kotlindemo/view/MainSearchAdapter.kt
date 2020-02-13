package com.example.kotlindemo.view

import android.util.Log
import android.widget.Filter
import android.widget.Filterable
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.kotlindemo.R
import com.example.kotlindemo.model.Record

class MainSearchAdapter(data: MutableList<Record>?) : BaseQuickAdapter<Record, BaseViewHolder>(R.layout.item_search,data), Filterable {

    var records = data
    private var resultList: MutableList<Record> = ArrayList()

    override fun convert(helper: BaseViewHolder, item: Record) {
        helper.setText(R.id.tvSna, item.sna)
    }
    fun setListData(datas: MutableList<Record>){
        records = datas
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val filterResults = FilterResults()
//                if (constraint != null) {
//                    val PeoplesList =
//                    // Assign the data to the FilterResults
//                    filterResults.values = PeoplesList
//                    filterResults.count = PeoplesList.size
//                }
//                return filterResults


                var charString = constraint.toString().trim()
                val filteredList = ArrayList<Record>()

                if (constraint != null){


                    for (row in records!!){
                        if (row.sna!!.toLowerCase().contains(charString.toLowerCase())
                            || row.sarea!!.toLowerCase().contains(
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
                    resultList = results.values as ArrayList<Record>
                    Log.d("222","notifyDataSetChanged")
                    setNewData(resultList)
//                    notifyDataSetChanged()
                } else {
                    Log.d("222","notifyDataSetInvalidated")

                    resultList.clear()
                    setNewData(resultList)
//                    notifyDataSetChanged()
                }


            }
        }
    }
}