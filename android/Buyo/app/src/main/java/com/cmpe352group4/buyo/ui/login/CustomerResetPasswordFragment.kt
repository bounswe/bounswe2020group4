package com.cmpe352group4.buyo.ui.login

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.ForgotPasswordRequest
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
            if (!ed_reset_password.text.toString().isNullOrEmpty()) {
                profileViewModel.onForgotPassword(
                    ForgotPasswordRequest(
                        email = ed_reset_password.text.toString()
                    )
                )
                profileViewModel.forgotPassword.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null) {
                        tv_Reset_Password.visibility = View.GONE
                        tv_Reset_Password_Email.visibility = View.GONE
                        ed_reset_password.visibility = View.GONE
                        btn_reset_password.visibility = View.GONE
                        iv_email_logo_reset_password.visibility = View.VISIBLE
                        tv_header_reset_password.visibility = View.VISIBLE
                        tv_body_reset_password.visibility = View.VISIBLE
                    } else if (it.status == Status.ERROR) {
                        dispatchLoading()
                        var toastText : String = "E-mail could not be sent"
                        val myToast = Toast.makeText(
                            context,
                            toastText,
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.CENTER, 0, 200)
                        myToast.show()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })
            } else {
                var toastText : String = "Please enter a valid e-mail address"
                val myToast = Toast.makeText(
                    context,
                    toastText,
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.CENTER, 0, 200)
                myToast.show()
            }
        }

        btn_back_reset_password.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}