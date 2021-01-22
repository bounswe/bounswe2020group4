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
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.LegalDocFragment
import com.cmpe352group4.buyo.ui.googlemap.MapsFragment
import com.cmpe352group4.buyo.ui.vendorProfilePage.VendorProfilePageFragment
import com.cmpe352group4.buyo.util.extensions.makeLinks
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.LoginRequestVendor
import com.cmpe352group4.buyo.vo.SignupRequestVendor
import kotlinx.android.synthetic.main.fragment_login_vendor.*
import javax.inject.Inject


// TODO Reset password functionality
// TODO Sign up e-mail verification

// Vendor must not use an e-mail domain if its domain is in the list below.
val badDomainList : List<String> = listOf<String>("gmail", "windowslive", "hotmail", "outlook",
                                                    "yahoo", "msn", "aol", "yandex")

class LoginFragmentVendor : BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val profileViewModel: ProfileViewModel by activityViewModels {
        viewModelFactory
    }

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
            vendor_choose_location?.text = getString(R.string.reselect_location)
            given_address?.text = sharedPref.getVendorAddress()
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
            })
        )
    }


    private fun checkCredentials(): Boolean {

        val companyEmail: String = vendor_username.text?.toString() ?: ""
        val password1: String = vendor_password.text?.toString() ?: ""
        val password2: String = vendor_reenter_password.text?.toString() ?: ""
        val companyWebsite: String = vendor_company_website.text?.toString() ?: ""
        val nameSurname: String = vendor_name_surname.text?.toString() ?: ""
        val companyName: String = vendor_company_name.text?.toString() ?: ""

        var isValidEmail: Boolean = false
        var isValidPassword: Boolean = false
        var isValidWebsite: Boolean = false
        var isValidNameSurname: Boolean = false
        var isValidCompanyName: Boolean = false

        var passwordSame: Boolean = false
        var companyEmailMatch: Boolean = false

        // Validity Checks
        isValidEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(companyEmail).matches()
        isValidPassword = password1.length >= 6
        isValidWebsite = android.util.Patterns.WEB_URL.matcher(companyWebsite).matches()
        isValidCompanyName = companyName.isNotEmpty()

        if (nameSurname.split(" ").size > 1){
            isValidNameSurname = nameSurname.split(" ")[0].length > 1 &&
                                 nameSurname.split(" ")[1].length > 1
        }

        // Other Checks
        passwordSame = password1 == password2
        if (isValidWebsite && isValidEmail) {
            var websiteList : List <String> = companyWebsite.split("www.")
            var websiteDomain : String = websiteList[websiteList.lastIndex].split(".")[0]
            var emailList : List<String> = companyEmail.split("@")
            var emailDomain : String = emailList[emailList.lastIndex].split(".")[0]
            companyEmailMatch = (websiteDomain == emailDomain) && (emailDomain !in badDomainList)
        }

        if (vendor_reenter_password.visibility == View.VISIBLE) {
            // Sign up checks
            if (isValidEmail && isValidPassword && isValidWebsite && isValidNameSurname &&
                isValidCompanyName && passwordSame && companyEmailMatch) {
                return true
            } else {

                var toastText : String = ""

                if (!isValidEmail){
                    toastText = "Please check your e-mail"
                } else if (!isValidPassword) {
                    toastText = "Your password cannot be shorter than 6 characters"
                } else if (!isValidWebsite) {
                    toastText = "Please check your company website"
                } else if (!isValidNameSurname) {
                    toastText = "You should write both your name and surname"
                } else if (!isValidCompanyName) {
                    toastText = "Please write your company name"
                } else if (!passwordSame) {
                    toastText = "Given passwords are not the same"
                } else if (!companyEmailMatch) {
                    toastText = "You should use your company e-mail which has the same domain" +
                            " with your company website"
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

        vendor_login_signup_button.setOnClickListener {
            val check : Boolean = checkCredentials()
            if (check) {
                if (vendor_login_signup_button.isEnabled && !vendor_signup_switch.isChecked) {

                    profileViewModel.onLoginVendor(
                        LoginRequestVendor(
                            userType = "vendor",
                            email = vendor_username.text.toString(),
                            password = vendor_password.text.toString()
                        )
                    )
                    profileViewModel.loginVendor.observe(viewLifecycleOwner, Observer {
                        if (it.status == Status.SUCCESS && it.data != null) {
                            sharedPref.saveUserId(it.data.userId)
                            sharedPref.saveUserType("vendor")
                            sharedPref.saveRememberMe(vendor_remember_me.isChecked)

                            dispatchLoading()

                        navigationManager?.onReplace(
                            VendorProfilePageFragment.newInstance(),
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
                } else if (vendor_login_signup_button.isEnabled && vendor_signup_switch.isChecked) {
                    val lat = sharedPref.getVendorLat()
                    val lon = sharedPref.getVendorLon()
                    if (vendor_remember_me.isChecked && lat != "" && lon != "") {
                        profileViewModel.onSingupVendor(
                            SignupRequestVendor(
                                userType = "vendor",
                                email = vendor_username.text.toString(),
                                password = vendor_password.text.toString(),
                                longitude = lon!!,
                                latitude = lat!!,
                                website = vendor_company_website.text.toString(),
                                company = vendor_company_name.text.toString()
                            )
                        )
                        profileViewModel.singupVendor.observe(viewLifecycleOwner, Observer {
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
                vendor_company_name.visibility = View.GONE
                vendor_choose_location.visibility = View.GONE
                given_address.visibility = View.GONE
            } else {
                vendor_login_signup_button.text = getString(R.string.action_sign_up)
                vendor_signup_switch.text = getString(R.string.login_switch)
                vendor_remember_me.text = getString(R.string.kvkk_accept)
                vendor_reenter_password.visibility = View.VISIBLE
                vendor_reset_password.visibility = View.GONE
                vendor_company_website.visibility = View.VISIBLE
                vendor_name_surname.visibility = View.VISIBLE
                vendor_company_name.visibility = View.VISIBLE
                vendor_choose_location.visibility = View.VISIBLE
                given_address.visibility = View.VISIBLE
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
