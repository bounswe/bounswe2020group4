package com.cmpe352group4.buyo.ui.categoriesPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import com.cmpe352group4.buyo.util.extensions.visible
import com.cmpe352group4.buyo.viewmodel.CategoryViewModel
import com.cmpe352group4.buyo.vo.Category
import kotlinx.android.synthetic.main.fragment_categories.*
import javax.inject.Inject


class CategoriesPageFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val categoryViewModel: CategoryViewModel by activityViewModels {
        viewModelFactory
    }

    private lateinit var pathIndex: ArrayList<Int>
    private var newCategoryList: List<Category>? = null
//    var initialCategoryList = mutableListOf(
//        Category(name = "Bebek", path = "bebek", subCategories = mutableListOf(Category(name = "Erkek Bebek", path = "Bebek,Erkek Bebek", subCategories = mutableListOf(Category(name = "Tulum", path = "Bebek,Erkek Bebek,Tulum", subCategories = mutableListOf()))))),
//        Category(name = "Erkek", path = "Erkek", subCategories = mutableListOf(Category(name = "Pijama", path = "Erkek,Pijama", subCategories = mutableListOf()))),
//        Category(name = "Kadin", path = "Kadin", subCategories = mutableListOf(Category(name = "Corap", path = "Kadin,Corap", subCategories = mutableListOf())))
//        )

//    private var initialCategoryList: MutableList<Category>? = null


    companion object {
        private const val CATEGORY_INDEX_PATH = "category_index_path"
        fun newInstance(pathIndex: ArrayList<Int> = ArrayList()) = CategoriesPageFragment().apply {
            arguments = Bundle().apply {
                putIntegerArrayList(CATEGORY_INDEX_PATH, pathIndex)
            }
        }
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(mutableListOf(),
            { path ->
                navigationManager?.onReplace(
                    ProductListFragment.newInstance(path = path),
                    TransactionType.Replace, true
                )
            },
            { index ->
                pathIndex.add(index)
                navigationManager?.onReplace(
                    CategoriesPageFragment.newInstance(pathIndex = pathIndex),
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

        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(pathIndex.isEmpty()){
            btnCategoriesBack.visible = false
            logoImageView.visible = true

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
            btnCategoriesBack.visible = true
            logoImageView.visible = false

            newCategoryList = categoryViewModel.categories.value?.data?.categories as MutableList<Category>
            pathIndex.forEach {
//                newCategoryList = categoryViewModel.categories.value?.data?.get(it)?.subCategories
                newCategoryList = newCategoryList!![it].subcategories
            }
            categoryAdapter.submitList(newCategoryList as MutableList<Category>)
        }

        val decorator = DividerItemDecoration(rv_categories.context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(ContextCompat.getDrawable(rv_categories.context, R.drawable.divider_drawable)!!)
        rv_categories.addItemDecoration(decorator)
        rv_categories.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rv_categories.adapter = categoryAdapter

        btnCategoriesBack.setOnClickListener {
            pathIndex.removeAt(pathIndex.size - 1)
            activity?.onBackPressed()
        }

        searchBarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword == ""){
                    return false
                }
                else {
                    navigationManager?.onReplace(
                        ProductListFragment.newInstance(keyword = keyword),
                        TransactionType.Replace, true
                    )
                    return true
                }

            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }



}