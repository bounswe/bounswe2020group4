package com.cmpe352group4.buyo.ui.login

import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import com.google.android.gms.maps.MapFragment
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login_vendor.*
import kotlinx.android.synthetic.main.fragment_maps.*
import javax.inject.Inject


// TODO Make kvkk readable
// TODO Reset password functionality
// TODO Sign up e-mail verification


// https://stackoverflow.com/a/45727769
fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {

            override fun updateDrawState(textPaint: TextPaint) {
                // use this to change the link color
                textPaint.color = textPaint.linkColor
                // toggle below value to enable/disable
                // the underline shown below the clickable text
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        val startIndexOfLink = this.text.toString().indexOf(link.first)
        spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    this.movementMethod = LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}


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
        googleMapButtonListener()
        legalDocLinkSet()
    }

    override fun onResume() {
        super.onResume()

        val lat: String? = sharedPref.getVendorLat()
        val lon: String? = sharedPref.getVendorLon()
        if (lat != "" && lon != ""){
            vendor_choose_location?.text = getString(R.string.reselect_location) // TODO SHOW SELECTED ADDRESS
        }
    }


    private fun legalDocLinkSet() {
        legal_documents_vendor.makeLinks(
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
                            "Create an account if you don't have one",
                            Toast.LENGTH_SHORT
                        )
                        myToast.setGravity(Gravity.BOTTOM, 0, 200)
                        myToast.show()
                    } else if (it.status == Status.LOADING) {
                        showLoading()
                    }
                })
            } else if (vendor_login_signup_button.isEnabled && vendor_signup_switch.isChecked) {
                val lat = sharedPref.getVendorLat()
                val lon = sharedPref.getVendorLon()
                if (vendor_remember_me.isChecked && lat != "" && lon != "") {
                    // TODO Fix backend call format (lat, lon are also available)
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
                } else if (lat == "" || lon == "") {
                    val myToast = Toast.makeText(
                        context,
                        "You should select your company location",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                } else {
                    val myToast = Toast.makeText(
                        context,
                        "Please read and accept KVKK!",
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
                vendor_company_website.visibility = View.GONE
                vendor_name_surname.visibility = View.GONE
                vendor_choose_location.visibility = View.GONE
            } else {
                vendor_login_signup_button.text = getString(R.string.action_sign_up)
                vendor_signup_switch.text = getString(R.string.login_switch)
                vendor_remember_me.text = getString(R.string.kvkk_accept)
                vendor_reenter_password.visibility = View.VISIBLE
                vendor_reset_password.visibility = View.GONE
                vendor_company_website.visibility = View.VISIBLE
                vendor_name_surname.visibility = View.VISIBLE
                vendor_choose_location.visibility = View.VISIBLE
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

    private fun googleMapButtonListener() {
        vendor_choose_location.setOnClickListener {
            navigationManager?.onReplace(
                MapsFragment.newInstance(),
                TransactionType.Replace, true
            )
        }
    }
}
