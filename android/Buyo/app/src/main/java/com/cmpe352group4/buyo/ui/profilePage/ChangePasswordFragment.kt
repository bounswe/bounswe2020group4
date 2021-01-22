package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.cmpe352group4.buyo.vo.ChangePasswordRequest
import com.cmpe352group4.buyo.vo.UserInformationRequest
import kotlinx.android.synthetic.main.fragment_change_password.*
import javax.inject.Inject

class ChangePasswordFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = ChangePasswordFragment()
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
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_change_password.isEnabled = false
        ed_previous_password.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isEmpty()) {
                    btn_change_password.isEnabled = false
                } else {
                    btn_change_password.isEnabled = true
                }
            }
        })

        ed_new_password_again.isEnabled = false
        ed_new_password.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().isEmpty()) {
                    ed_new_password_again.isEnabled = false
                } else {
                    ed_new_password_again.isEnabled = true
                }
            }
        })

        btn_change_password.setOnClickListener {
            val check : Boolean = checkCredentials()
            if(check) {
                val infoReq = UserInformationRequest(sharedPref.getUserId()?: "", sharedPref.getUserType()?:"")
                profileViewModel.onFetchProfileInfo(infoReq)

                profileViewModel.userInformation.observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS && it.data != null){

                        if (ed_previous_password.text?.toString() ?: "" == it.data.result.password){
                            profileViewModel.changePassword(
                                ChangePasswordRequest(
                                    id = sharedPref.getUserId()?:"",
                                    userType = sharedPref.getUserType()?:"",
                                    password = ed_new_password.text.toString()
                                )
                            )

                            profileViewModel.changePassword.observe(viewLifecycleOwner, Observer {
                                if (it.status == Status.SUCCESS && it.data != null) {

                                    var toastText : String = "Password successfully changed"
                                    val myToast = Toast.makeText(
                                        context,
                                        toastText,
                                        Toast.LENGTH_SHORT
                                    )
                                    myToast.setGravity(Gravity.CENTER, 0, 200)
                                    myToast.show()

                                    dispatchLoading()
                                } else if (it.status == Status.ERROR) {
                                    dispatchLoading()
                                } else if (it.status == Status.LOADING) {
                                    showLoading()
                                }
                            })
                        } else {
                            var toastText : String = "Previous password is not correct"
                            val myToast = Toast.makeText(
                                context,
                                toastText,
                                Toast.LENGTH_SHORT
                            )
                            myToast.setGravity(Gravity.CENTER, 0, 200)
                            myToast.show()
                        }

                        dispatchLoading()
                    } else if (it.status == Status.ERROR){
                        dispatchLoading()
                    }else if (it.status == Status.LOADING){
                        showLoading()
                    }
                })
            }
        }

        btn_back_change_password.setOnClickListener {
            activity?.onBackPressed()
        }
    }

    private fun checkCredentials(): Boolean {
        val previousPassword: String = ed_previous_password.text?.toString() ?: ""
        val newPassword: String = ed_new_password.text?.toString() ?: ""
        val newPasswordAgain: String = ed_new_password_again.text?.toString() ?: ""

        var isValidPassword: Boolean = false
        var passwordSame: Boolean = false
        var isPasswordDifferent: Boolean = false

        // Validity Checks
        isValidPassword = newPassword.length >= 6

        // Other Checks
        passwordSame = newPassword == newPasswordAgain
        isPasswordDifferent = previousPassword != newPassword

        if (ed_new_password_again.isEnabled) {
            // Sign up checks
            if (isValidPassword && passwordSame && isPasswordDifferent) {
                return true
            } else {

                var toastText : String = ""

                if (!isValidPassword) {
                    toastText = "Your password cannot be shorter than 6 characters"
                } else if (!passwordSame) {
                    toastText = "Given passwords are not the same"
                } else {
                    toastText = "New password can not be same with the old password"
                }

                val myToast = Toast.makeText(
                    context,
                    toastText,
                    Toast.LENGTH_SHORT
                )
                myToast.setGravity(Gravity.CENTER, 0, 200)
                myToast.show()
                return false
            }
        }
        return false
    }
}