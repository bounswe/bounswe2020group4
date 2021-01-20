package com.cmpe352group4.buyo.ui.vendorProfilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import kotlinx.android.synthetic.main.fragment_vendor_profile_page.*
import javax.inject.Inject

class VendorProfilePageFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    //profileViewModel
    companion object {
        fun newInstance() = VendorProfilePageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_vendor_profile_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_vendor_profile_page_account_info.setOnClickListener {
            navigationManager?.onReplace(
                VendorAccountInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_profile_page_change_password.setOnClickListener {
            navigationManager?.onReplace(
                VendorChangePasswordFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_profile_page_logout.setOnClickListener {
            // it will be implemented
        }
    }
}