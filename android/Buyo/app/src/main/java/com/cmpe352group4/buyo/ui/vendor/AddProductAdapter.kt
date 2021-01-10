package com.cmpe352group4.buyo.ui.vendor

import android.text.Editable
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

    inner class AddProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(modal : ParsedAttribute){

            /*
            val att_name = itemView.et_vendorAddProductAttributeName.text.toString()

            val att_options = itemView.et_vendorAddProductAttributeOptions.text.toString()

            val myParsedAttribute = ParsedAttribute(att_name = att_name, att_value = att_options.split("-").toList())

            callbackAddAttribute.invoke(myParsedAttribute)
            */


        }

    }

}