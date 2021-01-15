package com.cmpe352group4.buyo.ui.vendor

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.vo.ParsedAttribute
import kotlinx.android.synthetic.main.item_vendor_add_stock_value.view.*


class AddStockValuesAdapter(
    var Combinations: MutableList<List<String>>,
    val callbackAddStockValues : (String, Int) -> Unit
) : RecyclerView.Adapter<AddStockValuesAdapter.AddStockValuesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddStockValuesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vendor_add_stock_value, parent, false)

        return AddStockValuesViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddStockValuesViewHolder, position: Int) {
        holder.bind(Combinations[position])
    }

    override fun getItemCount(): Int {
        return Combinations.size
    }

    fun submitList(list: MutableList<List<String>>) {
        this.Combinations.clear()
        this.Combinations.addAll(list)
        notifyDataSetChanged()
        Log.v("VendorAddStockValuesAdp", this.Combinations.toString())
    }

    inner class AddStockValuesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(modal : List<String>){

            var stockName = modal.joinToString("-")

            itemView.tv_vendorAddStockValue.text = stockName

            itemView.et_vendorAddStockValue.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.v("VendorAddStockValuesAdp", "beforeTextChanged: $text")
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.v("VendorAddStockValuesAdp", "onTextChanged: $text")
                    callbackAddStockValues.invoke(stockName, text.toString().toInt())
                }

                override fun afterTextChanged(editable: Editable?) {
                    Log.v("VendorAddStockValuesAdp", "afterTextChanged: "+editable.toString())
                    callbackAddStockValues.invoke(stockName, editable.toString().toInt())
                }
            })

        }

    }

}