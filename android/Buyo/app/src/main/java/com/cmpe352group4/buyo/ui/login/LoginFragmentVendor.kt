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
import com.cmpe352group4.buyo.ui.homepage.HomepageFragment
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.LoginSignupRequest
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.login_signup_button
import kotlinx.android.synthetic.main.fragment_login.password
import kotlinx.android.synthetic.main.fragment_login.remember_me
import kotlinx.android.synthetic.main.fragment_login.signup_switch
import kotlinx.android.synthetic.main.fragment_login.username
import kotlinx.android.synthetic.main.fragment_login_vendor.*
import javax.inject.Inject

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
        username.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                userNameCountBool = p0?.length ?: 0 >= 6

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        login_signup_button.isEnabled = true
                        login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        login_signup_button.isEnabled = false
                        login_signup_button.alpha = .6f
                    }
                }
            }
        })

        password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                passwordCountBool = p0?.length ?: 0 >= 6

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        login_signup_button.isEnabled = true
                        login_signup_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        login_signup_button.isEnabled = false
                        login_signup_button.alpha = .6f
                    }
                }
            }
        })
    }

    private fun loginSignUpButton(){
        login_signup_button.setOnClickListener {
            if(login_signup_button.isEnabled && !signup_switch.isChecked) {
                profileViewModel.onLogin(
                    LoginSignupRequest(
                        userType = "vendor",
                        email = username.text.toString(),
                        password = password.text.toString()
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
            } else if (login_signup_button.isEnabled && signup_switch.isChecked) {
                if (remember_me.isChecked) {
                    profileViewModel.onSingup(
                        LoginSignupRequest(
                            userType = "vendor",
                            email = username.text.toString(),
                            password = password.text.toString()
                        )
                    )
                    profileViewModel.singup.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {
                            dispatchLoading()

                            val myToast =
                                Toast.makeText(context, "You can login now", Toast.LENGTH_SHORT)
                            myToast.setGravity(Gravity.BOTTOM, 0, 200)
                            myToast.show()
                            signup_switch.isChecked = false

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
        signup_switch.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked){
                login_signup_button.text = getString(R.string.action_login)
                signup_switch.text = getString(R.string.sign_up_switch)
                remember_me.text = getString(R.string.reset_password)
            } else {
                login_signup_button.text = getString(R.string.action_sign_up)
                signup_switch.text = getString(R.string.login_switch)
                remember_me.text = getString(R.string.kvkk_accept)
            }
        }
    }

    private fun userTypeSwitchListener() {
        switch_to_customer.setOnClickListener {
            navigationManager?.onReplace(
                LoginFragment.newInstance(),
                TransactionType.Replace, false
            )
        }
    }
}