package com.cmpe352group4.buyo.ui.productDetail

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.vo.FilterCriterias
import com.cmpe352group4.buyo.vo.ParsedAttribute
import com.cmpe352group4.buyo.vo.ProductInfo
import kotlinx.android.synthetic.main.item_add_cart_combination_recycler_view.view.*
import kotlinx.android.synthetic.main.item_product_list_filter_sort_recycler_view.view.*

class AddCartAdapter(
    var Criterias : MutableList<FilterCriterias>,
    val sharedPref : SharedPref,
    val selectFeatureCallback : (String, String) -> Unit

    ): RecyclerView.Adapter<AddCartAdapter.AddCartViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddCartAdapter.AddCartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list_filter_sort_recycler_view, parent, false)
        return AddCartViewHolder(view)    }

    override fun onBindViewHolder(holder: AddCartAdapter.AddCartViewHolder, position: Int) {
        holder.bind(Criterias[position])
    }

    override fun getItemCount(): Int {
        return Criterias.size
    }

    fun submitList(list: MutableList<FilterCriterias>) {
        this.Criterias.clear()
        this.Criterias.addAll(list)
        notifyDataSetChanged()
    }

    inner class AddCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(modal: FilterCriterias) {

            itemView.tv_ProductList_Filter_Sort_name.text = modal.displayName

            // Set the options of the spinner

            var filterValues = modal.possibleValues

            val spinnerAdapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, filterValues)

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            itemView.sp_ProductList_Filter_Sort_value.adapter = spinnerAdapter
            spinnerAdapter.notifyDataSetChanged()

            itemView.sp_ProductList_Filter_Sort_value.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectFeatureCallback(modal.name, adapterView?.getItemAtPosition(position).toString())
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) { // select zeroth element for default
                    selectFeatureCallback(modal.name, adapterView?.getItemAtPosition(0).toString())
                }

            }






            /*var tuple = ""

            for (attribute in modal.attributes){
                tuple += attribute.name.toUpperCase() + ":" + attribute.value + " \n"
            }

            itemView.ctv_addCartItem.text = tuple.dropLast(1) + "(" + modal.stockValue + ")"

            itemView.ctv_addCartItem.isChecked = true*/


        }
    }
}