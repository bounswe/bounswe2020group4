package com.cmpe352group4.buyo.ui.productDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.vo.Comment

class ProductCommentsAdapter(
    var Comments: MutableList<Comment>
): RecyclerView.Adapter<ProductCommentsAdapter.ProductCommentsViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCommentsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_comments_recycler_view, parent, false)

        return ProductCommentsViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProductCommentsViewHolder, position: Int) {
        holder.bind(Comments[position])
    }

    override fun getItemCount(): Int {
        return Comments.size
    }
    fun submitList(list: MutableList<Comment>) {
        this.Comments.clear()
        this.Comments.addAll(list)
        notifyDataSetChanged()
    }

    inner class ProductCommentsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(modal: Comment) {

            // Update the view elements here

        }

    }
}