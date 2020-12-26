package com.cmpe352group4.buyo.ui.productList

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.vo.ParsedAttribute
import kotlinx.android.synthetic.main.fragment_product_list_filter_sort.*


class ListSortFilterFragment: BaseFragment () {

    companion object {
        fun newInstance() = ListSortFilterFragment()
    }

    private val FiltersAdapter by lazy {
        ListFilterAdapter(mutableListOf())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_product_list_filter_sort, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var dummy_parsed_attribute1 = ParsedAttribute(
            att_name = "RAM", att_value = listOf(
                "16gb",
                "8gb",
                "4gb"
            )
        )
        var dummy_parsed_attribute2 = ParsedAttribute(
            att_name = "COLOR", att_value = listOf(
                "RED",
                "GREEN",
                "BLUE"
            )
        )


         var  dummy_attributes = mutableListOf<ParsedAttribute>(
             dummy_parsed_attribute1,
             dummy_parsed_attribute2
         )

        FiltersAdapter.Attributes = dummy_attributes



        // RECYCLER VIEW

        val decorator = DividerItemDecoration(
            rv_productList_filter_sort.context,
            LinearLayoutManager.VERTICAL
        )
        decorator.setDrawable(
            ContextCompat.getDrawable(
                rv_productList_filter_sort.context,
                R.drawable.divider_drawable
            )!!
        )
        rv_productList_filter_sort.addItemDecoration(decorator)

        rv_productList_filter_sort.adapter = FiltersAdapter

        rv_productList_filter_sort.layoutManager = LinearLayoutManager(this.context)


        // SORT

        var SortValues = ArrayList<String>()

        SortValues.add("Price")
        SortValues.add("Rating")
        SortValues.add("Vendor Rating")
        SortValues.add("Discount")

        val spinnerAdapter: ArrayAdapter<String>? =
            this.context?.let { ArrayAdapter<String>(it, android.R.layout.simple_spinner_item, SortValues) }


        spinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp_ProductList_sort_factor.adapter = spinnerAdapter


        sp_ProductList_sort_factor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                adapterView?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }



    }

}