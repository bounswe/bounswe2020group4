package com.cmpe352group4.buyo.ui.notification

import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.util.extensions.inflate
import com.cmpe352group4.buyo.vo.NotificationRV
import kotlinx.android.synthetic.main.item_notification.view.*
import kotlinx.android.synthetic.main.item_orders.view.*

class NotificationAdapter (
    private val notificationList: MutableList<NotificationRV>,
    val clickCallback: (NotificationRV) -> Unit
): RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_notification))
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    fun submitList(list: MutableList<NotificationRV>) {
        this.notificationList.clear()
        this.notificationList.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(modal: NotificationRV) {

            itemView.tv_notification_type.text = modal.name
            itemView.tv_time.text = modal.time.split("T")[0] + " " + modal.time.split("T")[1].split(".")[0]
            itemView.tv_notification_message.text = modal.summary

            itemView.setOnClickListener { clickCallback.invoke(modal) }

        }
    }
}