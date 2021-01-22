package com.cmpe352group4.buyo.ui.vendor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.CategoryViewModel
import com.cmpe352group4.buyo.vo.Category
import kotlinx.android.synthetic.main.fragment_vendor_add_product_categories.*
import javax.inject.Inject

class AddProductCategoryFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val categoryViewModel: CategoryViewModel by activityViewModels {
        viewModelFactory
    }

    private lateinit var pathIndex: ArrayList<Int>
    private var newCategoryList: List<Category>? = null


    companion object {
        private const val CATEGORY_INDEX_PATH = "category_index_path"
        fun newInstance(pathIndex: ArrayList<Int> = ArrayList()) = AddProductCategoryFragment().apply {
            arguments = Bundle().apply {
                putIntegerArrayList(CATEGORY_INDEX_PATH, pathIndex)
            }
        }
    }

    private val categoryAdapter by lazy {
        AddProductCategoryAdapter(mutableListOf(),
            { path -> // Click callback
                navigationManager?.onReplace(
                    AddProductFragment.newInstance(mode = "add", product = "", categories = path),
                    TransactionType.Replace, true
                )
            },
            { index -> // click subcategory callback
                pathIndex.add(index)
                navigationManager?.onReplace(
                    AddProductCategoryFragment.newInstance(pathIndex = pathIndex),
                    TransactionType.Replace, true
                )
            })

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        pathIndex = arguments?.getIntegerArrayList(CATEGORY_INDEX_PATH) ?: arrayListOf()

        return inflater.inflate(R.layout.fragment_vendor_add_product_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(pathIndex.isEmpty()){
            btn_vendorAddProductCategories_Back.visible = false

            categoryViewModel.onFetchCategories(true)

            categoryViewModel.categories.observe(viewLifecycleOwner, Observer {
                if (it.status == Status.SUCCESS && it.data != null) {

                    categoryAdapter.submitList(it.data.categories as MutableList<Category>)

                    dispatchLoading()
                } else if (it.status == Status.ERROR) {
                    dispatchLoading()
                } else if (it.status == Status.LOADING) {
                    showLoading()
                }
            })
            //categoryAdapter.submitList(initialCategoryList)

        }else{
            btn_vendorAddProductCategories_Back.visible = true

            newCategoryList = categoryViewModel.categories.value?.data?.categories as MutableList<Category>
            pathIndex.forEach {
//                newCategoryList = categoryViewModel.categories.value?.data?.get(it)?.subCategories
                newCategoryList = newCategoryList!![it].subcategories
            }
            categoryAdapter.submitList(newCategoryList as MutableList<Category>)
        }

        val decorator = DividerItemDecoration(rv_vendorAddProductCategories.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_vendorAddProductCategories.context, R.drawable.divider_drawable)!!)
        rv_vendorAddProductCategories.addItemDecoration(decorator)
        rv_vendorAddProductCategories.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_vendorAddProductCategories.adapter = categoryAdapter

        btn_vendorAddProductCategories_Back.setOnClickListener {
            pathIndex.removeAt(pathIndex.size - 1)
            activity?.onBackPressed()
        }

    }
}