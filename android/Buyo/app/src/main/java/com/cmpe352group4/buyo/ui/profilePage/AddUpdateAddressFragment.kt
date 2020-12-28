package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.MainActivity
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.AddAddress
import com.cmpe352group4.buyo.vo.AddAddressRequest
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_update_address.*
import javax.inject.Inject


class AddUpdateAddressFragment: BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val profileViewModel: ProfileViewModel by activityViewModels {
        viewModelFactory
    }

    @Inject
    lateinit var sharedPref: SharedPref

    private val gson = Gson()

    companion object {
        fun newInstance() =
            AddUpdateAddressFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_add_update_address, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Update address version will be implemented later

        setListeners()

        profileViewModel.addAddress.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {

                Toast.makeText(activity as MainActivity, "New address successfully added", Toast.LENGTH_SHORT).show()
                //activity?.onBackPressed()

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })

    }

    private fun setListeners() {
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        btnAdd.setOnClickListener {
            // If needed, we can add some validation to each field and according to that
            // we can make disabled and enabled states for Add button
            // Using profile viewmodel send a request to add-address endpoint via using Address modal and user ID
            profileViewModel.addAddress(
                AddAddressRequest(id = sharedPref.getUserId() ?: "",
                    address = gson.toJson(AddAddress(
                        name = etName.text.toString(),
                        surname = etSurname.text.toString(),
                        phone = etPhone.text.toString(),
                        province = etProvince.text.toString(),
                        city = etCity.text.toString(),
                        street = etStreet.text.toString(),
                        addressTitle = etAddressTitle.text.toString(),
                        address = etAddress.text.toString()
                    ))
                )
            )
        }
    }
}