package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.Address
import com.cmpe352group4.buyo.vo.CustomerInformation
import com.cmpe352group4.buyo.vo.UserInformationRequest
import kotlinx.android.synthetic.main.fragment_address_information.*
import javax.inject.Inject

class AddressInfoFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    private val profileViewModel: ProfileViewModel by viewModels {
        viewModelFactory
    }

    private var newAddresses: MutableList<Address>? = null

    companion object {
        fun newInstance() = AddressInfoFragment()
    }

    private val addressInfoAdapter by lazy {
        AddressInfoAdapter(mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_address_information, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userInfo : CustomerInformation? = null
        var addresses : MutableList<Address>? = null

        val infoReq = UserInformationRequest(sharedPref.getUserId()?: "", "customer")
        profileViewModel.onFetchProfileInfo(infoReq)

        profileViewModel.userInformation.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){

                if (!it.data.result.address.isNullOrEmpty()) {
                    addressInfoAdapter.submitList(it.data.result.address as MutableList<Address>)
                }

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })

        rv_address_info.adapter = addressInfoAdapter
        rv_address_info.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val decorator = DividerItemDecoration(rv_address_info.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_address_info.context, R.drawable.divider_drawable)!!)
        rv_address_info.addItemDecoration(decorator)

        btn_back_address_info.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}