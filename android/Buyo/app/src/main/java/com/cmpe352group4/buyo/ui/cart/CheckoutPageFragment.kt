package com.cmpe352group4.buyo.ui.cart

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import com.cmpe352group4.buyo.ui.profilePage.AddUpdateAddressFragment
import com.cmpe352group4.buyo.ui.profilePage.AddressInfoFragment
import com.cmpe352group4.buyo.viewmodel.CartViewModel
import com.cmpe352group4.buyo.viewmodel.ProfileViewModel
import com.cmpe352group4.buyo.vo.Address
import com.cmpe352group4.buyo.vo.CheckoutRequest
import com.cmpe352group4.buyo.vo.CreditCard
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_checkout.*
import javax.inject.Inject


class CheckoutPageFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val cartViewModel: CartViewModel by activityViewModels {
        viewModelFactory
    }

    private val profileViewModel: ProfileViewModel by activityViewModels {
        viewModelFactory
    }

    @Inject
    lateinit var sharedPref: SharedPref

    private val gson = Gson()
    private var selectedAddress = ""
    companion object {
        fun newInstance() = CheckoutPageFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        observeData()
        getProfileInfoandAddress()

        tv_final_price.text = ("Total: " + cartViewModel.cartInfo.value?.data?.products?.discountedPrice?.plus(20).toString() + "₺")
    }

    override fun onResume() {
        super.onResume()
        getProfileInfoandAddress()
    }

    private fun setListeners() {
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        tv_add_or_choose.setOnClickListener {
            navigationManager?.onReplace(
                AddressInfoFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        btnCheckout.setOnClickListener {
            if(checkFields()){
                // Send request to backend
                cartViewModel.onCheckout(
                    CheckoutRequest(
                    customerId = sharedPref.getUserId()?: "",
                    creditCard = gson.toJson(CreditCard(
                        name = et_card_name.text.toString(),
                        number = et_card_number.text.toString().replace(" ", ""),
                        expirationMonth = et_card_exp_month.text.toString(),
                        expirationYear = et_card_exp_year.text.toString(),
                        cvc = et_card_cvv.text.toString()
                        )),
                        address = selectedAddress
                    )
                )
            }
        }
        // gson.toJson(profileViewModel.selectedAddress)
    }

    private fun observeData() {
        cartViewModel.checkoutResponse.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null) {
                Toast.makeText(context, "You successfully order your cart!", Toast.LENGTH_SHORT).show()

                dispatchLoading()
            } else if (it.status == Status.ERROR) {
                dispatchLoading()
            } else if (it.status == Status.LOADING) {
                showLoading()
            }
        })
    }

    private fun getProfileInfoandAddress() {
        var addresses = mutableListOf<String>()
        if(profileViewModel.userInformation.value?.data?.result?.address?.isEmpty() != false){
            Toast.makeText(context, "You need to add an address before order something", Toast.LENGTH_SHORT).show()
        }else{
            var addresses = mutableListOf<String>()
            profileViewModel.userInformation.value?.data?.result?.address?.forEach {
                var showedAddress = it.addressTitle + " (" + it.street + "/" + it.city + "/" + it.province + ")"
                addresses.add(showedAddress)
            }
            val spinnerAdapter = context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_spinner_item,
                    addresses
                )
            }

            spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            sp_address.adapter = spinnerAdapter
            spinnerAdapter?.notifyDataSetChanged()

            sp_address.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedAddress = adapterView?.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    selectedAddress = adapterView?.getItemAtPosition(0).toString()
                }

            }
        }

    }

    private fun checkFields() : Boolean {

        val creditCardName = et_card_name.text
        val creditCardNo = et_card_number.text
        val creditCardExpMonth = et_card_exp_month.text
        val creditCardExpYear = et_card_exp_year.text
        val creditCardCvv = et_card_cvv.text
        var toastText = ""

        if(profileViewModel.userInformation.value?.data?.result?.name?.isEmpty() != false) {
            toastText = "Please, fill your profile information"
        }
        if(selectedAddress.isNullOrEmpty()){
            toastText = "Please, add an address"
        } else if(creditCardName.isNullOrEmpty()) {
            toastText = "Please, enter 'Name on Card Field'!"
        } else if (creditCardNo.isNullOrEmpty()|| (creditCardNo.toString().replace(" ", "")).length < 16) {
            toastText = "Please, enter valid credit card number!"
        } else if (creditCardExpMonth.isNullOrEmpty() || creditCardExpMonth.length > 2) {
            toastText = "Please, enter valid expiration month!"
        } else if (creditCardExpYear.isNullOrEmpty() || !(creditCardExpYear.length == 2 || creditCardExpYear.length == 4)) {
            toastText = "Please, enter valid expiration year!"
        } else if (creditCardCvv.isNullOrEmpty() || creditCardCvv.length != 3) {
            toastText = "Please, enter valid CVV!"
        }else if (!cb_accept.isChecked) {
            toastText = "Please, confirm 'Distant Sales Agreement And Pre-Information Form'!"
        }

        return if(toastText.isEmpty()){
            true
        }else{
            val myToast = Toast.makeText(
                context,
                toastText,
                Toast.LENGTH_SHORT
            )
            myToast.setGravity(Gravity.BOTTOM, 0, 200)
            myToast.show()
            false
        }
    }

}