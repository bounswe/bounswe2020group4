package com.cmpe352group4.buyo.ui.vendor

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.vo.ParsedAttribute
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.item_vendor_add_product_attributes_recycler_view.view.*

class AddProductAdapter(
    var Attributes: MutableList<ParsedAttribute>,
    val callbackAddAttribute: (ParsedAttribute) -> Unit
) : RecyclerView.Adapter<AddProductAdapter.AddProductViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vendor_add_product_attributes_recycler_view, parent, false)

        return AddProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddProductViewHolder, position: Int) {
        holder.bind(Attributes[position])
    }

    override fun getItemCount(): Int {
        return Attributes.size
    }

    fun submitList(list: MutableList<ParsedAttribute>) {
        this.Attributes.clear()
        this.Attributes.addAll(list)
        notifyDataSetChanged()
        Log.v("VendorAddProduct", this.Attributes.toString())
    }

    fun getList(): List<String> {
        return this.Attributes.map{it.att_name}
    }

    inner class AddProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(modal : ParsedAttribute){

            if (modal.att_name != ""){
                itemView.et_vendorAddProductAttributeName.setText(modal.att_name)
                itemView.et_vendorAddProductAttributeOptions.setText(modal.att_value.joinToString("-"))
                callbackAddAttribute.invoke(modal)
            }


            itemView.et_vendorAddProductAttributeName.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.v("VendorAddProductAdapter", "beforeTextChanged: $text")
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.v("VendorAddProductAdapter", "onTextChanged: $text")
                    modal.att_name = text.toString()
                }

                override fun afterTextChanged(editable: Editable?) {
                    Log.v("VendorAddProductAdapter", "afterTextChanged: "+editable.toString())
                }

            })


            itemView.et_vendorAddProductAttributeOptions.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
                    Log.v("VendorAddProductAdapter", "beforeTextChanged: $text")
                }

                override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.v("VendorAddProductAdapter", "onTextChanged: $text")
                    modal.att_value = text.toString().split("-")
                    if (modal.att_name != ""){
                        callbackAddAttribute.invoke(modal)
                    }
                }

                override fun afterTextChanged(editable: Editable?) {
                    Log.v("VendorAddProductAdapter", "afterTextChanged: "+editable.toString())
                }
            })

        }

    }

}