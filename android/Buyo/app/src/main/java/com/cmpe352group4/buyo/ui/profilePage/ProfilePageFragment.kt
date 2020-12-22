package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.ui.accountInfo.AccountInfoFragment
import com.cmpe352group4.buyo.ui.wishList.WishListFragment
import com.cmpe352group4.buyo.vo.ProfilePageItem
import kotlinx.android.synthetic.main.fragment_profile_page.*
import javax.inject.Inject

class ProfilePageFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //profileViewModel
    companion object {
        fun newInstance() = WishListFragment()
    }

    private var profilePageItems = mutableListOf<ProfilePageItem>(
        ProfilePageItem(context = "Account Information"),
        ProfilePageItem(context = "Address Information"),
        ProfilePageItem(context = "Change Password"),
        ProfilePageItem(context = "Exit")
    )

    private val profilePageAdapter by lazy {
        ProfilePageAdapter(profilePageItems)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_profile_page.adapter = profilePageAdapter
        rv_profile_page.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val decorator = DividerItemDecoration(rv_profile_page.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_profile_page.context, R.drawable.divider_drawable)!!)
        rv_profile_page.addItemDecoration(decorator)

        rv_profile_page.get(0).setOnClickListener {
            AccountInfoFragment.newInstance()
        }
        rv_profile_page.get(1).setOnClickListener { TODO() }
        rv_profile_page.get(2).setOnClickListener { TODO() }
        rv_profile_page.get(3).setOnClickListener { TODO() }
    }



}