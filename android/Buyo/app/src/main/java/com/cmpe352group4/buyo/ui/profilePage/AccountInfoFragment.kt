package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.UserInformationRequest
import kotlinx.android.synthetic.main.fragment_account_info.*
import javax.inject.Inject

class AccountInfoFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val accountInfoViewModel: ProfileViewModel by viewModels {
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

        val infoReq = UserInformationRequest(sharedPref.getUserId()?: "", "customer")
        accountInfoViewModel.onFetchProfileInfo(infoReq)

        accountInfoViewModel.userInformation.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){

                ed_user_name.setText(it.data.result.name)
                ed_user_surname.setText(it.data.result.surname)
                ed_user_email.text = it.data.result.email
                ed_user_phone.setText(it.data.result.phone)

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })

        btn_user_account_info_save.setOnClickListener {
            TODO()
        }

        btn_back_account_info.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}