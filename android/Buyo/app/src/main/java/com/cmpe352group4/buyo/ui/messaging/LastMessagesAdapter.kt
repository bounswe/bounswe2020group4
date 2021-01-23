package com.cmpe352group4.buyo.ui.messaging

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.vo.LastMessages
import com.cmpe352group4.buyo.vo.MessageUserInfo
import kotlinx.android.synthetic.main.item_last_message.view.*


class LastMessagesAdapter (
    private val lastMessageList: MutableList<LastMessages>,
    val clickCallback: (MessageUserInfo) -> Unit
): RecyclerView.Adapter<LastMessagesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_last_message))
    }

    override fun getItemCount(): Int {
        return lastMessageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lastMessageList[position], position)
    }

    fun submitList(list: MutableList<LastMessages>) {
        this.lastMessageList.clear()
        this.lastMessageList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val view = view

        fun bind(modal: LastMessages, position: Int) {
            itemView.setOnClickListener { clickCallback.invoke(modal.user) }
            itemView.tv_message_date.text = modal.date
            itemView.tv_message_detail.text = modal.lastMessage
            itemView.tv_message_from.text = modal.user.name
        }

    }

}