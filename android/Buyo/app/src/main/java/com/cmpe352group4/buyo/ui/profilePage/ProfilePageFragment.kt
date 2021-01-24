package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.login.LoginFragment
import com.cmpe352group4.buyo.ui.notification.NotificationFragment
import com.cmpe352group4.buyo.ui.orderpage.OrderPageFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_profile_page.*
import javax.inject.Inject

class ProfilePageFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

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

        tv_profile_page_account_info.setOnClickListener {
            navigationManager?.onReplace(
                AccountInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_profile_page_address_info.setOnClickListener {
            navigationManager?.onReplace(
                AddressInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_profile_page_orders.setOnClickListener {
            navigationManager?.onReplace(
                OrderPageFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_profile_page_messages.setOnClickListener {
            // To do
        }

        tv_profile_page_notification.setOnClickListener {
            navigationManager?.onReplace(
                NotificationFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_profile_page_change_password.setOnClickListener {
            navigationManager?.onReplace(
                ChangePasswordFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        tv_profile_page_logout.setOnClickListener {
            sharedPref.saveUserId("")
            sharedPref.saveUserType("")
            sharedPref.saveVendorAddress("")
            sharedPref.saveRememberMe(false)
            if (sharedPref.isGoogleSignin()) {
                val gso =
                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build()
                val mGoogleSignInClient = GoogleSignIn.getClient(this.requireContext(), gso)

                mGoogleSignInClient.signOut()
            }
            navigationManager?.onReplace(
                LoginFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

    }



}