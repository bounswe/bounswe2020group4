package com.cmpe352group4.buyo.ui.wishList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.item_wish_list_recycler_view.view.*

class WishListAdapter (
    var wishListProducts: MutableList<Product>,
    val deleteCallback: (Int) -> Unit,
    val clickProductCallBack: (Int) -> Unit
) : RecyclerView.Adapter<WishListAdapter.WishListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_wish_list_recycler_view, parent, false)
        return WishListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wishListProducts.size
    }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        holder.bind(wishListProducts[position], position)
    }

    fun submitList(list: MutableList<Product>) {
        this.wishListProducts.clear()
        this.wishListProducts.addAll(list)
        notifyDataSetChanged()
    }

    inner class WishListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val view = itemView

        fun bind(modal: Product, position: Int) {

            itemView.tvWishListRecyclerView_Name.text = modal.name
            itemView.tvWishListRecyclerView_Rate.text = modal.rating.toString()
            itemView.tvWishListRecyclerView_Price.text = modal.price.toString()
            Glide.with(itemView.context)
                .load(modal.imageUrl).centerCrop()
                .into(itemView.ivWishListRecyclerView_Image)
            itemView.setOnClickListener {
                clickProductCallBack.invoke(modal.id)
            }
            itemView.btnRemoveProductFromWishList.setOnClickListener {
                deleteCallback.invoke(modal.id)
                wishListProducts.removeAt(position)
                notifyDataSetChanged()

            }
        }
    }
}