package com.cmpe352group4.buyo.ui.profilePage

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
import com.cmpe352group4.buyo.vo.AccountInfoRequest
import com.cmpe352group4.buyo.vo.UserInformationRequest
import kotlinx.android.synthetic.main.fragment_account_info.*
import javax.inject.Inject

class AccountInfoFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val profileViewModel: ProfileViewModel by activityViewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance() = AccountInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_account_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // These two arrays are for gender spinner
        val backendGenders = arrayOf("", "female", "male", "other", "noInfo")
        val genders = arrayOf("", "Female", "Male", "Other", "No Info")

        val infoReq = UserInformationRequest(sharedPref.getUserId()?: "", sharedPref.getUserType()?:"")
        profileViewModel.onFetchProfileInfo(infoReq)

        // Send api request and set the response to corresponding fields
        profileViewModel.userInformation.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){

                val nameSurname = it.data.result.name?.split(" ")
                ed_user_name.setText(nameSurname?.get(0))
                ed_user_surname.setText(nameSurname?.get(1))

                ed_user_email.text = it.data.result.email
                ed_user_phone.setText(it.data.result.phoneNumber)

                sp_user_gender.setSelection(backendGenders.indexOf((it.data.result.gender)?.toLowerCase()))

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })

        // When save button is clicked send api request to save the given fields
        btn_user_account_info_save.setOnClickListener {
            profileViewModel.saveAccountInfo(
                AccountInfoRequest(
                    id = sharedPref.getUserId()?:"",
                    userType = sharedPref.getUserType()?:"",
                    name = ed_user_name.text.toString(),
                    surname = ed_user_surname.text.toString(),
                    email = ed_user_email.text.toString(),
                    phoneNumber = ed_user_phone.text.toString(),
                    gender = backendGenders.get(genders.indexOf(sp_user_gender.selectedItem.toString()))
                )
            )

            profileViewModel.saveAccountInfo.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {
                    val myToast = Toast.makeText(
                        context,
                        "Information successfully saved",
                        Toast.LENGTH_SHORT
                    )
                    myToast.setGravity(Gravity.CENTER, 0, 200)
                    myToast.show()
                    profileViewModel.onFetchProfileInfo(infoReq) // TODO LATER
                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })
        }

        // Back button
        btn_back_account_info.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}