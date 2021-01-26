package com.cmpe352group4.buyo.ui.homepage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cmpe352group4.buyo.R
import com.cmpe352group4.buyo.api.Status
import com.cmpe352group4.buyo.base.BaseFragment
import com.cmpe352group4.buyo.base.fragment_ops.TransactionType
import com.cmpe352group4.buyo.imagesearch.ImageClassifier
import com.cmpe352group4.buyo.imagesearch.ImageSearchFragment
import com.cmpe352group4.buyo.imagesearch.Keys
import com.cmpe352group4.buyo.datamanager.shared_pref.SharedPref
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import com.cmpe352group4.buyo.ui.vendor.AddProductCategoryFragment
import com.cmpe352group4.buyo.ui.vendor.AddProductFragment
import com.cmpe352group4.buyo.ui.vendor.VendorProductListFragment
import com.cmpe352group4.buyo.viewmodel.SearchViewModel
import com.cmpe352group4.buyo.vo.Product
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_homepage.*
import javax.inject.Inject

class HomepageFragment : BaseFragment() {

    private val CHOOSE_IMAGE = 1001
    private lateinit var photoImage: Bitmap
    private lateinit var classifier: ImageClassifier

    private var search_keyword = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModelFactory2: ViewModelProvider.Factory

    @Inject
    lateinit var sharedPref: SharedPref

    private val recommendedViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }
    private val discountViewModel: SearchViewModel by viewModels {
        viewModelFactory2
    }

    companion object {
        fun newInstance() = HomepageFragment()
    }

    private val recommendedProductListAdapter by lazy {
        ProductAdapter(mutableListOf()
        ) { product ->
            navigationManager?.onReplace(
                ProductDetailContentFragment.newInstance(product.id),
                TransactionType.Replace, true
            )

        }
    }

    private val discountProductListAdapter by lazy {
        ProductAdapter(mutableListOf()
        ) { product ->
            navigationManager?.onReplace(
                ProductDetailContentFragment.newInstance(product.id),
                TransactionType.Replace, true
            )

        }
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

        classifier = ImageClassifier(requireActivity().getAssets())

        // SEARCH

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

        // Recommendation RV

        var userIdForRec = "placeholder"
        if (!sharedPref.getUserId().isNullOrEmpty()) {
            userIdForRec = sharedPref.getUserId()!!
        }

        recommendedViewModel.onFetchRecommendations(userIdForRec)
        recommendedViewModel.recommendationResult.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS && it.data != null){

                recommendedProductListAdapter.submitList(it.data.productList as MutableList<Product>)

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })

        recommendationsRecyclerView.adapter = recommendedProductListAdapter
        recommendationsRecyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        // Discount RV

        discountViewModel.onFetchSearchResultbyCategory( "[\"Desks\"]", emptyMap())

        discountViewModel.categoryResult.observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS && it.data != null){

                Log.v("Homepage", it.data.toString())

                discountProductListAdapter.submitList(it.data.products.productList as MutableList<Product>)

                dispatchLoading()
            } else if (it.status == Status.ERROR){
                dispatchLoading()
            }else if (it.status == Status.LOADING){
                showLoading()
            }
        })


        discountRecyclerView.adapter = discountProductListAdapter
        discountRecyclerView.layoutManager = LinearLayoutManager(
            this.context,
            LinearLayoutManager.HORIZONTAL, false
        )

        image_search.setOnClickListener {
            checkPermission()
            choosePicture()
        }

    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }
    }

    private fun choosePicture() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 999)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 999) {
            val stream = requireActivity().contentResolver.openInputStream(data!!.getData()!!)
            if (::photoImage.isInitialized) photoImage.recycle()
            photoImage = BitmapFactory.decodeStream(stream)
            photoImage = Bitmap.createScaledBitmap(photoImage,
                Keys.INPUT_SIZE,
                Keys.INPUT_SIZE, false)
            classifier.recognizeImage(photoImage).subscribeBy(
                onSuccess = {
                    search_keyword = it.toString()
                }
            )
        }

        if (!search_keyword.isNullOrEmpty()) {
            navigationManager?.onReplace(
                ProductListFragment.newInstance(keyword = search_keyword.split(":")[0].drop(1)),
                TransactionType.Replace, true
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == 0) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        classifier.close()
    }

    override fun onResume() {
        super.onResume()
        var userIdForRec = "placeholder"
        if (!sharedPref.getUserId().isNullOrEmpty()) {
            userIdForRec = sharedPref.getUserId()!!
        }
        recommendedViewModel.onFetchRecommendations(userIdForRec)
    }
}