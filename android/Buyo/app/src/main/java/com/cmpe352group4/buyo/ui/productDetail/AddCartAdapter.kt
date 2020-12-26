package com.cmpe352group4.buyo.ui.productDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.vo.ParsedAttribute
import kotlinx.android.synthetic.main.item_product_list_filter_sort_recycler_view.view.*

class AddCartAdapter(
    var Attributes : MutableList<ParsedAttribute>,
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
        holder.bind(Attributes[position])
    }

    override fun getItemCount(): Int {
        return Attributes.size
    }

    fun submitList(list: MutableList<ParsedAttribute>) {
        this.Attributes.clear()
        this.Attributes.addAll(list)
        notifyDataSetChanged()
    }

    inner class AddCartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(modal: ParsedAttribute) {
            itemView.tv_ProductList_Filter_Sort_name.text = modal.att_name


            var filterValues = modal.att_value

            val spinnerAdapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, filterValues)

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            itemView.sp_ProductList_Filter_Sort_value.adapter = spinnerAdapter
            spinnerAdapter.notifyDataSetChanged()

            itemView.sp_ProductList_Filter_Sort_value.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectFeatureCallback(modal.att_name, adapterView?.getItemAtPosition(position).toString())
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }


        }
    }
}