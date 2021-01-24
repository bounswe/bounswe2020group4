package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.VendorRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.UploadImageResponse
import com.cmpe352group4.buyo.vo.VendorProductResponseResult
import javax.inject.Inject

class VendorViewModel @Inject constructor(
    val repository: VendorRepository
) : ViewModel() {

    private val _image =  MutableLiveData<ByteArray>()

    private val _vendorID = MutableLiveData<String>()

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

    fun onUploadImage(image : ByteArray?){
        _image.value = image
    }

    fun onFetchVendorProducts(vendorID : String){
        _vendorID.value = vendorID
    }
}