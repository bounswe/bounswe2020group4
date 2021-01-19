package com.cmpe352group4.buyo.ui.productList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.viewmodel.SearchViewModel
import com.cmpe352group4.buyo.vo.FilterCriterias
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product_list_filter_sort.*
import javax.inject.Inject


class ListSortFilterFragment : BaseFragment() {

    companion object {
        private const val KEYWORD = "search_keyword"
        private const val CATEGORY_PATH = "category_path"
        fun newInstance(keyword: String? = "", path: String? = "") =
            ListSortFilterFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_PATH, path)
                    putString(KEYWORD, keyword)
                }
            }
    }

    private var filter_details: MutableMap<String, String> = mutableMapOf()

    private val FiltersAdapter by lazy {
        ListFilterAdapter(mutableListOf())
        { featureName, featureValue ->
            filter_details[featureName] = featureValue
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productListViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    val gson = Gson()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_product_list_filter_sort, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val keyword = arguments?.getString(ListSortFilterFragment.KEYWORD) ?: ""

        val category = arguments?.getString(ListSortFilterFragment.CATEGORY_PATH) ?: ""

        var filters: List<FilterCriterias>? = null

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



        if (keyword == "") { // Category call

            val categoryList = category.split(",").toList()

            var query_string = "["

            for (cat in categoryList) {
                query_string = query_string + "\"" + cat + "\","
            }

            query_string = query_string.dropLast(1) + "]"


            productListViewModel.onFetchSearchResultbyCategory(query_string, emptyMap())

            productListViewModel.categoryResult.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {
                    Log.v("Products of the keyword", it.data.products.toString())

                    filters = it.data.products.filterCriterias

                    FiltersAdapter.submitList(filters as MutableList<FilterCriterias>)

                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })


        } else if (category == "") { // Keyword call

            productListViewModel.onFetchSearchResultbyKeyword(keyword, emptyMap())

            productListViewModel.searchResult.observe(viewLifecycleOwner, Observer {

                if (it.status == Status.SUCCESS && it.data != null) {
                    Log.v("Products of the keyword", it.data.products.toString())

                    filters = it.data.products.filterCriterias

                    FiltersAdapter.submitList(filters as MutableList<FilterCriterias>)

                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })


        }

        if (filters != null){
            FiltersAdapter.submitList(filters as MutableList<FilterCriterias>)
        }

        // SORT


        // FACTOR
        var sortFactor: String = ""

        var SortFactorNames: MutableList<String> = mutableListOf()

        SortFactorNames.add("")
        SortFactorNames.add("name")
        SortFactorNames.add("rating")
        SortFactorNames.add("price")
        SortFactorNames.add("originalPrice")

        var SortFactorDisps: MutableList<String> = mutableListOf()

        SortFactorDisps.add("-")
        SortFactorDisps.add("Name")
        SortFactorDisps.add("Rating")
        SortFactorDisps.add("Price")
        SortFactorDisps.add("Original Price")



        val spinnerAdapterFactor: ArrayAdapter<String>? =
            this.context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    SortFactorDisps
                )
            }


        spinnerAdapterFactor?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp_ProductList_sort_factor.adapter = spinnerAdapterFactor

        sp_ProductList_sort_factor.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sortFactor =
                        SortFactorNames[SortFactorDisps.indexOf(adapterView?.getItemAtPosition(position).toString())]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    sortFactor = SortFactorNames[0]
                }
            }

        // SORT type

        var sortType: String = ""


        var SortTypeNames: MutableList<String> = mutableListOf()

        SortTypeNames.add("ascending")
        SortTypeNames.add("descending")


        var SortTypeDisps: MutableList<String> = mutableListOf()

        SortTypeDisps.add("Ascending")
        SortTypeDisps.add("Descending")



        val spinnerAdapterType: ArrayAdapter<String>? =
            this.context?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_spinner_item,
                    SortTypeDisps
                )
            }


        spinnerAdapterType?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sp_ProductList_Sort_Type.adapter = spinnerAdapterType

        sp_ProductList_Sort_Type.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    sortType =
                        SortTypeNames[SortTypeDisps.indexOf(adapterView?.getItemAtPosition(position).toString())]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    sortType = SortTypeNames[0]
                }
            }

        // Send Backend Request Here using filter_details and sort options


        btn_search_filter_sort.setOnClickListener {

            var final_opts : MutableMap<String, String> = emptyMap<String, String>().toMutableMap()

            if(sortFactor != ""){
                filter_details["sortingFactor"] = sortFactor
                filter_details["sortingType"] = sortType
            }else{
            }

            for (opt in filter_details){
                if (opt.value != "-"){
                    final_opts[opt.key] = opt.value
                }
            }

            Log.v("ProductListOpts", final_opts.toString())


            if (keyword == "") {
                navigationManager?.onReplace(
                    ProductListFragment.newInstance(path = category, options = gson.toJson(final_opts.toMap())),
                    TransactionType.Replace, true
                )
            } else if (category == "") {

                navigationManager?.onReplace(
                    ProductListFragment.newInstance(keyword = keyword, options = gson.toJson(final_opts.toMap())),
                    TransactionType.Replace, true
                )
            }
        }




    }

}