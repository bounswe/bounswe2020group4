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
import com.cmpe352group4.buyo.ui.LegalDocFragment
import com.cmpe352group4.buyo.ui.googlemap.MapsFragment
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.LoginSignupRequest
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_vendor.*
import javax.inject.Inject

// TODO Make kvkk readable
// TODO Reset password functionality
// TODO Google sign up, login functionality
// TODO Sign up e-mail verification


class LoginFragment : BaseFragment() {

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
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addOnTextWatcher()
        loginSignUpButton()
        signUpSwitch()
        userTypeSwitchListener()
        legalDocLinkSet()
    }

    private fun legalDocLinkSet() {
        legal_documents_customer.makeLinks(
            Pair("Terms of Service", View.OnClickListener {
                navigationManager?.onReplace(
                    LegalDocFragment.newInstance("termsOfService"),
                    TransactionType.Replace, true
                )
            }),
            Pair("Privacy Policy", View.OnClickListener {
                navigationManager?.onReplace(
                    LegalDocFragment.newInstance("privacyPolicy"),
                    TransactionType.Replace, true
                )
            }))
    }

    private fun addOnTextWatcher() {
        customer_username.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                userNameCountBool = p0?.length ?: 0 >= 6

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        customer_login_signup_button.isEnabled = true
                        customer_login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        customer_login_signup_button.isEnabled = false
                        customer_login_signup_button.alpha = .6f
                    }
                }
            }
        })

        customer_password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                passwordCountBool = p0?.length ?: 0 >= 6

                // TODO check if passwords are same

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        customer_login_signup_button.isEnabled = true
                        customer_login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        customer_login_signup_button.isEnabled = false
                        customer_login_signup_button.alpha = .6f
                    }
                }
            }
        })

        customer_reenter_password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                passwordCountBool = p0?.length ?: 0 >= 6

                // TODO check if passwords are same

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        customer_login_signup_button.isEnabled = true
                        customer_login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        customer_login_signup_button.isEnabled = false
                        customer_login_signup_button.alpha = .6f
                    }
                }
            }
        })
    }

    private fun loginSignUpButton(){
        customer_login_signup_button.setOnClickListener {
            if(customer_login_signup_button.isEnabled && !customer_signup_switch.isChecked) {
                profileViewModel.onLogin(
                    // TODO Fix backend call format
                    LoginSignupRequest(
                        userType = "customer",
                        email = customer_username.text.toString(),
                        password = customer_password.text.toString()
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
                            "Create an account if you don't have one",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })
            } else if (customer_login_signup_button.isEnabled && customer_signup_switch.isChecked) {
                if (customer_remember_me.isChecked) {
                    // TODO Fix backend call format
                    profileViewModel.onSingup(
                        LoginSignupRequest(
                            userType = "customer",
                            email = customer_username.text.toString(),
                            password = customer_password.text.toString()
                        )
                    )
                    profileViewModel.singup.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {
                            dispatchLoading()
                            val myToast =
                                Toast.makeText(context, "You can login now", Toast.LENGTH_SHORT)
                            myToast.setGravity(Gravity.BOTTOM, 0, 200)
                            myToast.show()
                            customer_signup_switch.isChecked = false

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
                        "Please read and accept KVKK",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                }
            }
        }
    }

    private fun signUpSwitch() {
        customer_signup_switch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked){
                customer_login_signup_button.text = getString(R.string.action_login)
                customer_google_login_signup.text = getString(R.string.google_login)
                customer_signup_switch.text = getString(R.string.sign_up_switch)
                customer_remember_me.text = getString(R.string.remember_me)
                customer_reset_password.visibility = View.VISIBLE
                customer_reenter_password.visibility = View.GONE
            } else {
                customer_login_signup_button.text = getString(R.string.action_sign_up)
                customer_google_login_signup.text = getString(R.string.google_signup)
                customer_signup_switch.text = getString(R.string.login_switch)
                customer_remember_me.text = getString(R.string.kvkk_accept)
                customer_reset_password.visibility = View.GONE
                customer_reenter_password.visibility = View.VISIBLE
            }
        }
    }

    private fun userTypeSwitchListener() {
        customer_switch_to_vendor.setOnClickListener {
            navigationManager?.onReplace(
                LoginFragmentVendor.newInstance(),
                TransactionType.Replace, false
            )
        }
    }
}