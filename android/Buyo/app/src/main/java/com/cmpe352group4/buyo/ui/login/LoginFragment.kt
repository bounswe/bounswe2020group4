package com.cmpe352group4.buyo.ui.login

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.LegalDocFragment
import com.cmpe352group4.buyo.ui.profilePage.ProfilePageFragment
import com.cmpe352group4.buyo.ui.orderpage.OrderPageFragment
import com.cmpe352group4.buyo.ui.vendorProfilePage.VendorProfilePageFragment
import com.cmpe352group4.buyo.util.extensions.makeLinks
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.LoginRequestCustomer
import com.cmpe352group4.buyo.vo.SignupRequestCustomer
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val profileViewModel: ProfileViewModel by activityViewModels {
        viewModelFactory
    }

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
        if(sharedPref.getRememberMe() && !sharedPref.getUserId().isNullOrEmpty()){
            if(sharedPref.getUserType().equals("customer")){
                navigationManager?.onReplace(
                    ProfilePageFragment.newInstance(),
                    TransactionType.Replace, false
                )
            }else{
                navigationManager?.onReplace(
                    VendorProfilePageFragment.newInstance(),
                    TransactionType.Replace, false
                )
            }

        }else{
            loginSignUpButton()
            signUpSwitch()
            userTypeSwitchListener()
            legalDocLinkSet()
            resetPassword()
        }

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

    private fun checkCredentials(): Boolean {

        val customerEmail: String = customer_username.text?.toString() ?: ""
        val password1: String = customer_password.text?.toString() ?: ""
        val password2: String = customer_reenter_password.text?.toString() ?: ""

        var isValidEmail: Boolean = false
        var isValidPassword: Boolean = false
        var passwordSame: Boolean = false

        // Validity Checks
        isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(customerEmail).matches()
        isValidPassword = password1.length >= 6

        // Other Checks
        passwordSame = password1 == password2

        if (customer_reenter_password.visibility == View.VISIBLE) {
            // Sign up checks
            if (isValidEmail && isValidPassword && passwordSame ) {
                return true
            } else {

                var toastText : String = ""

                if (!isValidEmail){
                    toastText = "Please check your e-mail"
                } else if (!isValidPassword) {
                    toastText = "Your password cannot be shorter than 6 characters"
                } else if (!passwordSame) {
                    toastText = "Given passwords are not the same"
                }

                val myToast = Toast.makeText(
                    context,
                    toastText,
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.BOTTOM, 0, 200)
                myToast.show()
                return false
            }

        } else {
            // Login checks
            if (isValidEmail && isValidPassword) {
                return true
            } else {
                if (!isValidEmail) {
                    val myToast = Toast.makeText(
                        context,
                        "Please check your e-mail",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                } else {
                    val myToast = Toast.makeText(
                        context,
                        "Your password cannot be shorter than 6 characters",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.BOTTOM, 0, 200)
                    myToast.show()
                }
                return false
            }
        }
    }

    private fun loginSignUpButton(){
        customer_login_signup_button.setOnClickListener {
            val check : Boolean = checkCredentials()
            if (check) {
                if (customer_login_signup_button.isEnabled && !customer_signup_switch.isChecked) {
                    profileViewModel.onLoginCustomer(
                        LoginRequestCustomer(
                            userType = "customer",
                            email = customer_username.text.toString(),
                            password = customer_password.text.toString()
                        )
                    )
                    profileViewModel.loginCustomer.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {
                            sharedPref.saveUserId(it.data.userId)
                            sharedPref.saveUserType("customer")
                            sharedPref.saveRememberMe(customer_remember_me.isChecked)
                            dispatchLoading()

                            navigationManager?.onReplace(
                                ProfilePageFragment.newInstance(),
                                TransactionType.Replace, false
                            )

                        } else if (it.status == Status.ERROR) {
                            dispatchLoading()
                            val myToast = Toast.makeText(
                                context,
                                "Given e-mail or password is wrong",
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
                        profileViewModel.onSingupCustomer(
                            SignupRequestCustomer(
                                userType = "customer",
                                email = customer_username.text.toString(),
                                password = customer_password.text.toString()
                            )
                        )
                        profileViewModel.singupCustomer.observe(viewLifecycleOwner, Observer {
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

    private fun resetPassword() {
        customer_reset_password.setOnClickListener {
            navigationManager?.onReplace(
                CustomerResetPasswordFragment.newInstance(),
                TransactionType.Replace, true
            )
        }
    }

}