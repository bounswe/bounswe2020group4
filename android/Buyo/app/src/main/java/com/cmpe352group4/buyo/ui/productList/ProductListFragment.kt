package com.cmpe352group4.buyo.ui.productList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_list.*
import com.cmpe352group4.buyo.vo.Product
import com.cmpe352group4.buyo.vo.Vendor
import java.util.ArrayList
import javax.inject.Inject


class ProductListFragment : BaseFragment() {
    /*
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val productListViewModel: ProductViewModel by viewModels {
        viewModelFactory
    }
    */
    companion object {
        private const val KEYWORD = "search_keyword"
        private const val CATEGORY_PATH = "category_path"
        fun newInstance(keyword: String? = "", path: String? = "") = ProductListFragment().apply {
            arguments = Bundle().apply {
                putString(CATEGORY_PATH, path)
                putString(KEYWORD, keyword)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        val keyword = arguments?.getString(KEYWORD) ?: "NULL SEARCH"

        // TODO SEARCH API CALL

        productListViewModel.onFetchProductListbyKeyword(keyword)

        // Request Backend and fill the xml.
        // TODO

        productListViewModel.productList.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS && it.data != null){
                Log.v("Products of the keyword", it.data.products.toString())

                //dispatchLoading()
            } else if (it.status == Status.ERROR){
                //dispatchLoading()
            }else if (it.status == Status.LOADING){
                //showLoading()
            }

        })

         */



        tv_productListCategoryName.text = arguments?.getString(KEYWORD) ?: "NULL SEARCH"

        // BACK BUTTON

        btnProductListBack.setOnClickListener {
            activity?.onBackPressed()
        }


        sv_productListSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword == "") {
                    return false
                } else {
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


        // TODO Backend request with keyword and fill the recycler view

        // RECYCLER VIEW
        var dummyComment =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempus sem tortor, id efficitur nisi feugiat eget. In ac odio sed nisl dapibus consequat. Praesent eu nulla at ipsum elementum varius et suscipit metus."

        var productsList = mutableListOf(
            Product(
                category = listOf("Bebek", "Kiz Bebek", "Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet", 3.22)
            ),
            Product(
                category = listOf("Bebek", "Kiz Bebek", "Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet", 3.22)
            ),
            Product(
                category = listOf("Bebek", "Kiz Bebek", "Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet", 3.22)
            ),
            Product(
                category = listOf("Bebek", "Kiz Bebek", "Takim"),
                sizes = null,
                colors = listOf(),
                name = "Kiz Bebek Takim 2'li",
                id = 10000,
                imageUrl = "https://img-lcwaikiki.mncdn.com/mnresize/230/-/pim/productimages/20202/4353680/l_20202-0w9038z1-qca_a.jpg",
                rating = 1.95,
                price = 35.99,
                originalPrice = 35.99,
                brand = "Koton",
                vendor = Vendor("Ahmet", 3.22)
            )
        )

        val productListAdapter by lazy {
            ProductListAdapter(productsList) { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.id),
                    TransactionType.Replace, true
                )
            }
        }

        rv_ProductList.adapter = productListAdapter

        rv_ProductList.layoutManager = LinearLayoutManager(this.context)


        // ORDER SPINNER

        sp_ProductListSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (adapterView?.getItemAtPosition(position).toString() == "Name") {
                    productsList.sortBy { product -> product.name }
                    productListAdapter.notifyDataSetChanged()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }


            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        // FILTER SPINNER

        sp_ProductListFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (adapterView?.getItemAtPosition(position).toString() == "Brand") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Category") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Vendor") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position).toString() == "Price Range") {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (adapterView?.getItemAtPosition(position)
                        .toString() == "Category Specific"
                ) {
                    Toast.makeText(
                        context,
                        adapterView?.getItemAtPosition(position).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }
}