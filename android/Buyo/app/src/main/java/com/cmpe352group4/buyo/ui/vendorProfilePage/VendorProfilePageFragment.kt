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
import com.cmpe352group4.buyo.ui.login.LoginFragmentVendor
import com.cmpe352group4.buyo.ui.messaging.MessagesFragment
import com.cmpe352group4.buyo.ui.notification.NotificationFragment
import com.cmpe352group4.buyo.ui.orderpage.OrderPageFragmentVendor
import com.cmpe352group4.buyo.ui.vendor.AddProductCategoryFragment
import com.cmpe352group4.buyo.ui.vendor.AddProductFragment
import com.cmpe352group4.buyo.ui.vendor.VendorProductListFragment
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

        vendorLogoAccount.setOnClickListener {
            navigationManager?.onReplace(
                VendorAccountInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        vendorLogoAdd.setOnClickListener {
            navigationManager?.onReplace(
                AddProductCategoryFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        vendorLogoProduct.setOnClickListener {
            navigationManager?.onReplace(
                VendorProductListFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        vendorLogoOrder.setOnClickListener{
            navigationManager?.onReplace(
                OrderPageFragmentVendor.newInstance(),
                TransactionType.Replace, true
            )
        }

        vendorLogoMessage.setOnClickListener {
            // Later
        }

        vendorLogoNotification.setOnClickListener {
            navigationManager?.onReplace(
                NotificationFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        vendorLogoPassword.setOnClickListener {
            navigationManager?.onReplace(
                VendorChangePasswordFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        vendorLogoLogout.setOnClickListener {
            sharedPref.saveUserId("")
            sharedPref.saveUserType("")
            sharedPref.saveVendorAddress("")
            sharedPref.saveVendorLat("")
            sharedPref.saveVendorLon("")
            sharedPref.saveRememberMe(false)
            navigationManager?.onReplace(
                LoginFragmentVendor.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_profile_add_product.setOnClickListener {
            navigationManager?.onReplace(
                AddProductCategoryFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_profile_page_messages.setOnClickListener {
            navigationManager?.onReplace(
                MessagesFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_my_products.setOnClickListener {
            navigationManager?.onReplace(
                VendorProductListFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_profile_my_orders.setOnClickListener {
            navigationManager?.onReplace(
                OrderPageFragmentVendor.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_profile_page_account_info.setOnClickListener {
            navigationManager?.onReplace(
                VendorAccountInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_vendor_profile_page_notification.setOnClickListener {
            navigationManager?.onReplace(
                NotificationFragment.newInstance(),
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
            sharedPref.saveUserId("")
            sharedPref.saveUserType("")
            sharedPref.saveVendorAddress("")
            sharedPref.saveVendorLat("")
            sharedPref.saveVendorLon("")
            sharedPref.saveRememberMe(false)
            navigationManager?.onReplace(
                LoginFragmentVendor.newInstance(),
                TransactionType.Replace, true
            )
        }
    }
}