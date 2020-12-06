package com.cmpe352group4.buyo.ui.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.EmptyFragment
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.LoginSignupRequest
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_vendor.*
import javax.inject.Inject


// TODO Make kvkk readable

class LoginFragmentVendor : BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val profileViewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    private var userNameCountBool: Boolean = false
    private var passwordCountBool: Boolean = false

    companion object {
        fun newInstance() = LoginFragmentVendor()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_vendor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addOnTextWatcher()
        loginSignUpButton()
        signUpSwitch()
        userTypeSwitchListener()
    }

    private fun addOnTextWatcher() {
        vendor_username.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                userNameCountBool = p0?.length ?: 0 >= 6

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        vendor_login_signup_button.isEnabled = true
                        vendor_login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        vendor_login_signup_button.isEnabled = false
                        vendor_login_signup_button.alpha = .6f
                    }
                }
            }
        })

        vendor_password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                passwordCountBool = p0?.length ?: 0 >= 6

                // TODO check if passwords are same

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        vendor_login_signup_button.isEnabled = true
                        vendor_login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        vendor_login_signup_button.isEnabled = false
                        vendor_login_signup_button.alpha = .6f
                    }
                }
            }
        })

        vendor_reenter_password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                passwordCountBool = p0?.length ?: 0 >= 6

                // TODO check if passwords are same

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        vendor_login_signup_button.isEnabled = true
                        vendor_login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        vendor_login_signup_button.isEnabled = false
                        vendor_login_signup_button.alpha = .6f
                    }
                }
            }
        })
    }

    private fun loginSignUpButton(){
        vendor_login_signup_button.setOnClickListener {
            if(vendor_login_signup_button.isEnabled && !vendor_signup_switch.isChecked) {

                // TODO Fix backend call format
                profileViewModel.onLogin(
                    LoginSignupRequest(
                        userType = "vendor",
                        email = vendor_username.text.toString(),
                        password = vendor_password.text.toString()
                    )
                )
                profileViewModel.login.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null) {
                        sharedPref.saveUserId(it.data.userId)
                        dispatchLoading()

                        // TODO Go to profile page here
                        navigationManager?.onReplace(
                            EmptyFragment.newInstance(),
                            TransactionType.Replace, true
                        )

                    } else if (it.status == Status.ERROR) {
                        dispatchLoading()
                        val myToast = Toast.makeText(
                            context,
                            "Create an account if you don't have one!",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })
            } else if (vendor_login_signup_button.isEnabled && vendor_signup_switch.isChecked) {
                if (vendor_remember_me.isChecked) {
                    // TODO Fix backend call format
                    profileViewModel.onSingup(
                        LoginSignupRequest(
                            userType = "vendor",
                            email = vendor_username.text.toString(),
                            password = vendor_password.text.toString()
                        )
                    )
                    profileViewModel.singup.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {
                            dispatchLoading()
                            val myToast =
                                Toast.makeText(context, "You can login now", Toast.LENGTH_SHORT)
                            myToast.setGravity(Gravity.BOTTOM, 0, 200)
                            myToast.show()
                            vendor_signup_switch.isChecked = false

                        } else if (it.status == Status.ERROR) {
                            dispatchLoading()
                            val myToast = Toast.makeText(
                                context,
                                "An error occurred during sign up!",
                                Toast.LENGTH_SHORT
                            )
                            myToast.setGravity(Gravity.BOTTOM, 0, 200)
                            myToast.show()
                        } else if (it.status == Status.LOADING) {
                            showLoading()
                        }
                    })
                } else {
                    val myToast = Toast.makeText(
                        context,
                        "Please accept KVKK!",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                }
            }
        }
    }

    private fun signUpSwitch() {
        vendor_signup_switch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked){
                vendor_login_signup_button.text = getString(R.string.action_login)
                vendor_signup_switch.text = getString(R.string.sign_up_switch)
                vendor_remember_me.text = getString(R.string.remember_me)
                vendor_reenter_password.visibility = View.GONE
                vendor_reset_password.visibility = View.VISIBLE
                vendor_tax_id.visibility = View.GONE
            } else {
                vendor_login_signup_button.text = getString(R.string.action_sign_up)
                vendor_signup_switch.text = getString(R.string.login_switch)
                vendor_remember_me.text = getString(R.string.kvkk_accept)
                vendor_reenter_password.visibility = View.VISIBLE
                vendor_reset_password.visibility = View.GONE
                vendor_tax_id.visibility = View.VISIBLE
            }
        }
    }

    private fun userTypeSwitchListener() {
        vendor_switch_to_customer.setOnClickListener {
            navigationManager?.onReplace(
                LoginFragment.newInstance(),
                TransactionType.Replace, false
            )
        }
    }
}