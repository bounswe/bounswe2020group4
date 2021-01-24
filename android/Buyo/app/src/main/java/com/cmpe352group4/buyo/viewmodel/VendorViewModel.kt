package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.ProductRepository
import com.cmpe352group4.buyo.datamanager.repositories.VendorRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.ProductResult
import com.cmpe352group4.buyo.vo.UploadImageResponse
import javax.inject.Inject

class VendorViewModel @Inject constructor(
    val repository: VendorRepository
) : ViewModel() {

    private val _image =  MutableLiveData<ByteArray>()

    val imageUrl: LiveData<Resource<UploadImageResponse>> =
        Transformations.switchMap(_image) { image ->
            if (image == null)
                AbsentLiveData.create()
            else
                repository.uploadImage(image)
        }

    fun onUploadImage(image : ByteArray?){
        _image.value = image
    }
}