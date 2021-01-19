package com.cmpe352group4.buyo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import kotlinx.android.synthetic.main.fragment_legal_doc.*
import kotlinx.android.synthetic.main.fragment_maps.*

class LegalDocFragment: BaseFragment() {
    companion object {
        private const val DOC_TYPE = "doc_type"
        fun newInstance(docTypeP: String) = LegalDocFragment().apply {
            arguments = Bundle().apply {
                putString(DOC_TYPE, docTypeP)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_legal_doc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val docType = arguments?.getString(LegalDocFragment.DOC_TYPE)

        if (docType == "privacyPolicy"){
            legal_text.text = getString(R.string.privacy_policy_string)
        } else if (docType == "termsOfService") {
            legal_text.text = getString(R.string.terms_of_service_string)
        }
        okButtonListener()
    }

    private fun okButtonListener() {
        docs_ok_button.setOnClickListener {
            activity?.onBackPressed()
        }
    }

}