package com.cmpe352group4.buyo.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_reset_password.*
import javax.inject.Inject

class CustomerResetPasswordFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = CustomerResetPasswordFragment()
    }

    private val profileViewModel: ProfileViewModel by activityViewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_reset_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_email_logo_reset_password.visibility = View.GONE
        tv_header_reset_password.visibility = View.GONE
        tv_body_reset_password.visibility = View.GONE

        btn_reset_password.setOnClickListener {
            tv_Reset_Password.visibility = View.GONE
            tv_Reset_Password_Email.visibility = View.GONE
            ed_reset_password.visibility = View.GONE
            btn_reset_password.visibility = View.GONE
            iv_email_logo_reset_password.visibility = View.VISIBLE
            tv_header_reset_password.visibility = View.VISIBLE
            tv_body_reset_password.visibility = View.VISIBLE

            // api connection
        }

        btn_back_reset_password.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}