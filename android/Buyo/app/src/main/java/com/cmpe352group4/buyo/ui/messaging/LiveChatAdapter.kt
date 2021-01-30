package com.cmpe352group4.buyo.ui.messaging

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.vo.LiveChatMessage
import kotlinx.android.synthetic.main.item_received_message.view.*
import kotlinx.android.synthetic.main.item_sent_message.view.*


class LiveChatAdapter (
    private val liveChatMessageList: MutableList<LiveChatMessage>,
    private val userID: String
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_SENT = 1
        const val VIEW_TYPE_RECEIVE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_SENT) {
            return SentViewHolder(parent.inflate(R.layout.item_sent_message))
        }
        return ReceiveViewHolder(parent.inflate(R.layout.item_received_message))

    }

    override fun getItemCount(): Int {
        return liveChatMessageList.size
    }


    override fun getItemViewType(position: Int): Int {
        return if (liveChatMessageList[position].user.id == userID) {
            VIEW_TYPE_SENT
        } else {
            VIEW_TYPE_RECEIVE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (liveChatMessageList[position].user.id == userID) {
            (holder as SentViewHolder).bind(liveChatMessageList[position])
        } else {
            (holder as ReceiveViewHolder).bind(liveChatMessageList[position])
        }
    }

    fun submitList(list: MutableList<LiveChatMessage>) {
        this.liveChatMessageList.clear()
        this.liveChatMessageList.addAll(list)
        notifyDataSetChanged()
    }

    fun addMessage(message: LiveChatMessage){
        liveChatMessageList.add(message)
        notifyDataSetChanged()
    }

    inner class SentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val view = view

        fun bind(modal: LiveChatMessage) {
            itemView.tv_sent_message.text = modal.message
//            val date = modal.date.substring(11, 16) + " " + modal.date.substring(0, 10)
//            itemView.tv_sent_date.text = date
        }

    }

    inner class ReceiveViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val view = view

        fun bind(modal: LiveChatMessage) {
            itemView.tv_receive_message.text = modal.message
//            val date = modal.date.substring(11, 16) + " " + modal.date.substring(0, 10)
//            itemView.tv_receive_date.text = date
        }

    }

}