package com.cmpe352group4.buyo.ui.profilePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_add_update_address.*


class AddUpdateAddressFragment: BaseFragment() {
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

    }

    private fun setListeners() {
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        btnAdd.setOnClickListener {
            // If needed, we can add some validation to each field and according to that
            // we can make disabled and enabled states for Add button
            // Using profile viewmodel send a request to add-address endpoint via using Address modal and user ID
//            Address(
//                name = etName.text.toString(),
//                surname = etSurname.text.toString(),
//                phone = etPhone.text.toString(),
//                city = etCity.text.toString(),
//                addressTitle = etAddressTitle.text.toString(),
//                address = etAddress.text.toString()
//            )
        }
    }


}