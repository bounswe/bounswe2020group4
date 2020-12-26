package com.cmpe352group4.buyo.ui.productList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.vo.ParsedAttribute
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.item_product_list_filter_sort_recycler_view.view.*

class ListFilterAdapter (
    var Attributes : MutableList<ParsedAttribute>,
    val attributesCallback : (String, String) -> Unit

) : RecyclerView.Adapter<ListFilterAdapter.ListFilterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListFilterAdapter.ListFilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_list_filter_sort_recycler_view, parent, false)
        return ListFilterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListFilterAdapter.ListFilterViewHolder, position: Int) {
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

    inner class ListFilterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(modal: ParsedAttribute) {
            itemView.tv_ProductList_Filter_Sort_name.text = modal.att_name


            var filterValues = modal.att_value

            val spinnerAdapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, filterValues)

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            itemView.sp_ProductList_Filter_Sort_value.adapter = spinnerAdapter
            spinnerAdapter.notifyDataSetChanged()

            itemView.sp_ProductList_Filter_Sort_value.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    adapterView?.getItemAtPosition(position).toString()

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

            }


        }
    }
}