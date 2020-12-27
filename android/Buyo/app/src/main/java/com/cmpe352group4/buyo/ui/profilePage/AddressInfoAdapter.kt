package com.cmpe352group4.buyo.ui.profilePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.vo.Address
import kotlinx.android.synthetic.main.item_address_info.view.*

class AddressInfoAdapter(
    var addresses: MutableList<Address>
) : RecyclerView.Adapter<AddressInfoAdapter.AddressViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_address_info, parent, false)
        return AddressViewHolder(view)
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addresses[position], position)
    }

    fun submitList(list: MutableList<Address>) {
        this.addresses.clear()
        this.addresses.addAll(list)
        notifyDataSetChanged()
    }

    inner class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView

        fun bind(modal: Address, position: Int) {

            itemView.tv_address_title.text = modal.addressTitle
            itemView.tv_address_name.text = modal.name + " " + modal.surname
            itemView.tv_address_phone.text = modal.phone
            itemView.tv_address_text.text = modal.address
            itemView.tv_address_city.text = modal.province + ", " + modal.city
            itemView.btn_update_address.setOnClickListener {
                TODO()
            }
            itemView.btn_delete_address.setOnClickListener {
                TODO()
            }

        }

    }

}