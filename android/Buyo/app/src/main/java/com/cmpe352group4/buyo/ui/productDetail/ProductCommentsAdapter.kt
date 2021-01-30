package com.cmpe352group4.buyo.ui.productDetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.vo.Comment
import com.google.gson.Gson
import kotlinx.android.synthetic.main.item_product_comments_recycler_view.view.*
import java.io.Serializable

class ProductCommentsAdapter(
    var Comments: MutableList<Comment>,
    val callbackReportComment: (String) ->  Unit
): RecyclerView.Adapter<ProductCommentsAdapter.ProductCommentsViewHolder>(){

    val gson = Gson()

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

            itemView.tv_productCommentsRecyclerView_user.text = modal.owner.username
            itemView.tv_productCommentsRecyclerView_rating.text = modal.rating
            itemView.tv_productCommentsRecyclerView_text.text = modal.text

            if (modal.id == "-1"){ // If there is no comment for that product disable the report button of the no comment text
                itemView.btn_productCommentsRecyclerView_report.isEnabled = false
                itemView.btn_productCommentsRecyclerView_report.visibility = View.INVISIBLE
            }
            else {
                itemView.btn_productCommentsRecyclerView_report.isEnabled = true
                itemView.btn_productCommentsRecyclerView_report.visibility = View.VISIBLE
            }

            itemView.btn_productCommentsRecyclerView_report.setOnClickListener {
                callbackReportComment(gson.toJson(modal))
            }

        }

    }
}