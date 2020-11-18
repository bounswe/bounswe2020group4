package com.cmpe352group4.buyo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.homepage.ProductAdapter
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import com.cmpe352group4.buyo.vo.Product
import kotlinx.android.synthetic.main.fragment_homepage.*

class HomepageFragment : BaseFragment() {

    companion object {
        fun newInstance() = HomepageFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SEARCH

        searchBarSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(keyword: String?): Boolean {
                if (keyword == ""){
                    return false
                }
                else {
                    navigationManager?.onReplace(
                        ProductListFragment.newInstance(keyword),
                        TransactionType.Replace, true
                    )
                    return true
                }

            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })


        // Recommendation RV
        var recommendedProductList = mutableListOf(
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName6",
                productID = 6, productNumComments = 0, productRate = 1.1,
                productPrice = "0.0", productReleaseDate = "01.01.2020"
            ),

            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName5",
                productID = 5, productNumComments = 0, productRate = 1.1,
                productPrice = "0.0", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName4",
                productID = 4, productNumComments = 0, productRate = 1.1,
                productPrice = "0.0", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName3",
                productID = 3, productNumComments = 0, productRate = 1.1,
                productPrice = "0.0", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName2",
                productID = 2, productNumComments = 0, productRate = 1.1,
                productPrice = "0.0", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName1",
                productID = 1, productNumComments = 0, productRate = 1.1,
                productPrice = "0.0", productReleaseDate = "01.01.2020"
            )
        )

        val recommendedProductListAdapter by lazy {
            ProductAdapter(recommendedProductList) { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.productID),
                    TransactionType.Replace, true
                )
            }
        }

        recommendationsRecyclerView.adapter = recommendedProductListAdapter
        recommendationsRecyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        // Discount RV
        var discountProductList = mutableListOf(
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName1",
                productID = 0, productNumComments = 0, productRate = 1.1,
                productPrice = "10.0", productReleaseDate = "01.01.2020"
            ),

            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName2",
                productID = 0, productNumComments = 0, productRate = 1.1,
                productPrice = "20.0", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName3",
                productID = 0, productNumComments = 0, productRate = 1.1,
                productPrice = "999.99", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName4",
                productID = 0, productNumComments = 0, productRate = 1.1,
                productPrice = "123.2", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName5",
                productID = 0, productNumComments = 0, productRate = 1.1,
                productPrice = "0.50", productReleaseDate = "01.01.2020"
            ),
            Product(
                productImage = "drawable/ic_launcher_background.xml",
                productInfo = "Product Info", productName = "MyItemName6",
                productID = 0, productNumComments = 0, productRate = 1.1,
                productPrice = "0.99", productReleaseDate = "01.01.2020"
            )
        )

        val discountProductListAdapter by lazy {
            ProductAdapter(discountProductList) { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.productID),
                    TransactionType.Replace, true
                )
            }
        }

        discountRecyclerView.adapter = discountProductListAdapter
        discountRecyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )
    }
}