package com.cmpe352group4.buyo.ui.vendorProfilePage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.UserInformationRequest
import com.cmpe352group4.buyo.vo.VendorAccountInfoRequest
import kotlinx.android.synthetic.main.fragment_vendor_account_info.*
import javax.inject.Inject

class VendorAccountInfoFragment: BaseFragment() {

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val profileViewModel: ProfileViewModel by activityViewModels {
        viewModelFactory
    }

    companion object {
        fun newInstance() = VendorAccountInfoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_vendor_account_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoReq = UserInformationRequest(sharedPref.getUserId()?: "", sharedPref.getUserType()?:"")
        profileViewModel.onFetchVendorProfileInfo(infoReq)

        // Get the current account information from db
        profileViewModel.vendorInformation.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){

                ed_vendor_user_email.text = it.data.result.email
                ed_vendor_company.setText(it.data.result.company)
                ed_vendor_website.setText(it.data.result.website)

            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })

        // if save button is clicked
        btn_vendor_account_info_save.setOnClickListener {
            profileViewModel.saveVendorAccountInfo(
                VendorAccountInfoRequest(
                    id = sharedPref.getUserId()?:"",
                    userType = sharedPref.getUserType()?:"",
                    email = ed_vendor_user_email.text.toString(),
                    longitude = sharedPref.getVendorLon()?:"",
                    latitude = sharedPref.getVendorLat()?:"",
                    website = ed_vendor_website.text.toString(),
                    company = ed_vendor_company.text.toString()
                )
            )

            // Send updated account information as API request
            profileViewModel.saveVendorAccountInfo.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {
                    profileViewModel.onFetchVendorProfileInfo(infoReq)
                    Log.v("Vendor account info", "saved")
                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })
        }

        btn_back_vendor_account_info.setOnClickListener{
            activity?.onBackPressed()
        }

    }
}