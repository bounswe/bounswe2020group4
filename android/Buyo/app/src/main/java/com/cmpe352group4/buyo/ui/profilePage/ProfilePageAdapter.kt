package com.cmpe352group4.buyo.ui.profilePage

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.vo.ProfilePageItem

class ProfilePageAdapter (
    var profilePageItems: MutableList<ProfilePageItem>
) : RecyclerView.Adapter<ProfilePageAdapter.ProfilePageViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfilePageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_page, parent, false)
        return ProfilePageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return profilePageItems.size
    }

    override fun onBindViewHolder(holder: ProfilePageViewHolder, position: Int) {
        holder.bind(profilePageItems[position], position)
    }

    inner class ProfilePageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view = itemView

        fun bind(modal: ProfilePageItem, position: Int) {
            itemView.tv_profile_page.text = modal.context
        }
    }
}