package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.VendorRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.*
import javax.inject.Inject

class VendorViewModel @Inject constructor(
    val repository: VendorRepository
) : ViewModel() {

    private val _image =  MutableLiveData<ByteArray>()

    private val _vendorID = MutableLiveData<String>()

    private val _addRequest = MutableLiveData<AddProductRequest>()

    val imageUrl: LiveData<Resource<UploadImageResponse>> =
        Transformations.switchMap(_image) { image ->
            if (image == null)
                AbsentLiveData.create()
            else
                repository.uploadImage(image)
        }

    val vendorProducts: LiveData<Resource<VendorProductResponseResult>> =
        Transformations.switchMap(_vendorID) { vendorID ->
            if (vendorID == null)
                AbsentLiveData.create()
            else
                repository.getProducts(vendorID)
        }

    val addProduct: LiveData<Resource<AddProductResponseResult>> =
        Transformations.switchMap(_addRequest) { it ->
            if (it == null)
                AbsentLiveData.create()
            else
                repository.addProduct(it.vendorID, it.products)
        }

    fun onUploadImage(image : ByteArray?){
        _image.value = image
    }

    fun onFetchVendorProducts(vendorID : String){
        _vendorID.value = vendorID
    }

    fun onAddProduct(vendorID : String, product : AddProduct){
        _addRequest.value = AddProductRequest(vendorID = vendorID, products = listOf(product))
    }
}