package com.cmpe352group4.buyo.ui.cart

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.profilePage.AddUpdateAddressFragment
import com.cmpe352group4.buyo.viewmodel.CartViewModel
import kotlinx.android.synthetic.main.fragment_checkout.*
import javax.inject.Inject


class CheckoutPageFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val cartViewModel: CartViewModel by activityViewModels {
        viewModelFactory
    }

    @Inject
    lateinit var sharedPref: SharedPref


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


        // tv_final_price.text = cartViewModel.cartInfo.value?.data?.totalPrice?.plus(20).toString()


    }

    private fun setListeners() {
        btnBack.setOnClickListener {
            activity?.onBackPressed()
        }

        tv_add_or_choose.setOnClickListener {
            navigationManager?.onReplace(
                AddUpdateAddressFragment.newInstance(),
                TransactionType.Replace, true
            )
        }

        btnCheckout.setOnClickListener {
            if(checkFields()){
//                // Send request to backend
//                cartViewModel.onCheckout(CheckoutRequest(
//                    customerId = sharedPref.getUserId()?: "",
//                    creditCard = CreditCard(
//                        name = et_card_name.text.toString(),
//                        number = et_card_number.text.toString().replace(" ", "").toInt()),
//                        expirationMonth = et_card_exp_month.text.toString().toInt(),
//
//
//                    ))
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


        if (creditCardName.isNullOrEmpty()) {
            toastText = "Please, enter 'Name on Card Field'!"
        } else if (creditCardNo.isNullOrEmpty()|| (creditCardNo.toString().replace(" ", "")).length < 16) {
            toastText = "Please, enter valid credit card number!"
        } else if (creditCardExpMonth.isNullOrEmpty() || creditCardExpMonth.length > 2) {
            toastText = "Please, enter valid expiration month!"
        } else if (creditCardExpYear.isNullOrEmpty() || creditCardExpYear.length != 2 || creditCardExpYear.length != 4) {
            toastText = "Please, enter valid expiration year!"
        } else if (creditCardCvv.isNullOrEmpty() || creditCardCvv.length != 3) {
            toastText = "Please, enter valid CVV!"
        }else if (!cb_accept.isChecked) {
            toastText = "Please, confirm 'Distant Sales Agreement And Pre-Information Form'!"
        }

        if(tv_chosen_address.text.isNullOrEmpty()){
            toastText = "Upps something went wrong please try again!"
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