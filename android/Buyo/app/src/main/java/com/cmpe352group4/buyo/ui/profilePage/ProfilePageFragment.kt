package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import kotlinx.android.synthetic.main.fragment_profile_page.*
import javax.inject.Inject

class ProfilePageFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    //profileViewModel
    companion object {
        fun newInstance() = ProfilePageFragment()
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

        tv_profile_page_account_info.setOnClickListener() {
            navigationManager?.onReplace(
                AccountInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_profile_page_address_info.setOnClickListener() {
            navigationManager?.onReplace(
                AddressInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_profile_page_change_password.setOnClickListener() {
            navigationManager?.onReplace(
                ChangePasswordFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

    }



}