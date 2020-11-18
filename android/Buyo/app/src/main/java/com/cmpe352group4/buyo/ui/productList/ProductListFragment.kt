package com.cmpe352group4.buyo.ui.productList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.ui.EmptyFragment
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import kotlinx.android.synthetic.main.fragment_product_list.*
import com.cmpe352group4.buyo.vo.Product


class ProductListFragment : BaseFragment(){

    companion object {
        fun newInstance() = ProductListFragment()
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

        btnProductListBack.setOnClickListener {
            // TODO
        }

        sv_productListSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                TODO("Not yet implemented")
            }
        })


        // RECYCLER VIEW
        var dummyComment = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus tempus sem tortor, id efficitur nisi feugiat eget. In ac odio sed nisl dapibus consequat. Praesent eu nulla at ipsum elementum varius et suscipit metus."

        var productsList = mutableListOf(
            Product(productImage = "drawable/ic_launcher_background.xml", productInfo = dummyComment, productName = "MyItemName6", productID = 6, productNumComments = 0, productRate = 1.1, productPrice = "0.0", productReleaseDate = "01.01.2020"),
            Product(productImage = "drawable/ic_launcher_background.xml", productInfo = dummyComment, productName = "MyItemName5", productID = 5, productNumComments = 0, productRate = 1.1, productPrice = "0.0", productReleaseDate = "01.01.2020" ),
            Product(productImage = "drawable/ic_launcher_background.xml", productInfo = dummyComment, productName = "MyItemName4", productID = 4, productNumComments = 0, productRate = 1.1, productPrice = "0.0", productReleaseDate = "01.01.2020" ),
            Product(productImage = "drawable/ic_launcher_background.xml", productInfo = dummyComment, productName = "MyItemName3", productID = 3, productNumComments = 0, productRate = 1.1, productPrice = "0.0", productReleaseDate = "01.01.2020" ),
            Product(productImage = "drawable/ic_launcher_background.xml", productInfo = dummyComment, productName = "MyItemName2", productID = 2, productNumComments = 0, productRate = 1.1, productPrice = "0.0", productReleaseDate = "01.01.2020" ),
            Product(productImage = "drawable/ic_launcher_background.xml", productInfo = dummyComment, productName = "MyItemName1", productID = 1, productNumComments = 0, productRate = 1.1, productPrice = "0.0", productReleaseDate = "01.01.2020")
        )

        val productListAdapter by lazy {
            ProductListAdapter(productsList) { product ->
                navigationManager?.onReplace(
                    ProductDetailContentFragment.newInstance(product.productID),
                    TransactionType.Replace, true
                )
            }
        }

        rv_ProductList.adapter = productListAdapter

        rv_ProductList.layoutManager = LinearLayoutManager(this.context)


        // ORDER SPINNER

        sp_ProductListSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(adapterView?.getItemAtPosition(position).toString() == "Name"){
                    productsList.sortBy { product -> product.productName }
                    productListAdapter.notifyDataSetChanged()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Date (Latest)"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        // FILTER SPINNER

        sp_ProductListFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (adapterView?.getItemAtPosition(position).toString() == "Brand"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Category"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Vendor"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Price Range"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }else if (adapterView?.getItemAtPosition(position).toString() == "Category Specific"){
                    Toast.makeText(context, adapterView?.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

}