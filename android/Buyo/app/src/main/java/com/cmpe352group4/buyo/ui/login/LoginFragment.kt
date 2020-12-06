package com.cmpe352group4.buyo.ui.login

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import javax.inject.Inject

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
    private var boolLoginButton: Boolean = false

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
        loginButton()
        //signUpButton()
    }

    private fun addOnTextWatcher() {
        login_username.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                userNameCountBool = p0?.length ?: 0 >= 6

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        login_button.isEnabled = true
                        login_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        login_button.isEnabled = false
                        login_button.alpha = .6f
                    }
                }
            }
        })

        login_password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                passwordCountBool = p0?.length ?: 0 >= 6

                if (userNameCountBool && passwordCountBool) {
                    context?.run {
                        login_button.isEnabled = true
                        login_button.alpha = 1f
                    }
                } else {
                    context?.run {
                        login_button.isEnabled = false
                        login_button.alpha = .6f
                    }
                }
            }
        })
    }

    private fun loginButton(){
        login_button.setOnClickListener {
            if(login_button.isEnabled) {
                profileViewModel.onLogin(
                    LoginSignupRequest(
                        userType = "customer",
                        email = login_username.text.toString(),
                        password = login_password.text.toString()
                    )
                )
                profileViewModel.login.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null) {
                        sharedPref.saveUserId(it.data.userId)
                        dispatchLoading()

                        // TODO: Go homepage or profile page here
                        navigationManager?.onReplace(
                            EmptyFragment.newInstance(),
                            TransactionType.Replace, true
                        )

                    } else if (it.status == Status.ERROR) {
                        dispatchLoading()
                        val myToast = Toast.makeText(
                            context,
                            "An error occurred during signing up!",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })
            }
        }
    }
    /*
    private fun signUpButton(){
        btn_singUp.setOnClickListener {
            if(boolLoginButton){
                profileViewModel.onSingup(
                    LoginSignupRequest(
                        userType = "customer",
                        email = login_username.text.toString(),
                        password = login_password.text.toString()
                    )
                )
                profileViewModel.singup.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null) {

                        //sharedPref.saveUserId(it.data.userId)

                        dispatchLoading()

                        val myToast = Toast.makeText(context,"You succesfully signed up",Toast.LENGTH_SHORT)
//                        myToast.setGravity(Gravity.LEFT,200,200)
                        myToast.show()
                        //navigationManager?.onReplace(
                        //    EmptyFragment.newInstance(),
                        //    TransactionType.Replace, true
                        //)
                    } else if (it.status == Status.ERROR) {
                        dispatchLoading()
                        val myToast = Toast.makeText(context,it.message,Toast.LENGTH_SHORT)
                        myToast.setGravity(Gravity.LEFT,200,200)
                        myToast.show()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })
            }else{
                val myToast = Toast.makeText(context,"enter your username and password please",Toast.LENGTH_SHORT)
                myToast.setGravity(Gravity.LEFT,200,200)
                myToast.show()
            }
        }
    }
     */

    private fun signUpSwitch() {
        signup_switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){

            } else {
                
            }
        }
    }
}