package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.api.Resource
import com.cmpe352group4.buyo.datamanager.repositories.ProductRepository
import com.cmpe352group4.buyo.util.livedata.AbsentLiveData
import com.cmpe352group4.buyo.vo.BaseResponsePostRequest
//import com.cmpe352group4.buyo.vo.ProductResponse
import com.cmpe352group4.buyo.vo.ProductResult
import com.cmpe352group4.buyo.vo.ReportRequest
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    val repository: ProductRepository
) : ViewModel() {

    // Things we need to conduct a request

    private val _productId =  MutableLiveData<String>()

    private val _commentReport = MutableLiveData<ReportRequest>()

    private val _productReport = MutableLiveData<ReportRequest>()

    //private val _searchKeyword = MutableLiveData<String>()

    // Things we get as a response
    val productDetail: LiveData<Resource<ProductResult>> =
        Transformations.switchMap(_productId) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getProductById(Id)
        }

    val reportComment: LiveData<Resource<BaseResponsePostRequest>> =
        Transformations.switchMap(_commentReport) { it
            if (it == null)
                AbsentLiveData.create()
            else
                repository.reportComment(it.id, it.message)
        }

    val reportProduct: LiveData<Resource<BaseResponsePostRequest>> =
        Transformations.switchMap(_productReport) { it
            if (it == null)
                AbsentLiveData.create()
            else
                repository.reportComment(it.id, it.message)
        }

    //val productList: LiveData<Resource<ProductResponse>> =
    //    Transformations.switchMap(_searchKeyword) { keyword ->
    //        if (keyword == null)
    //            AbsentLiveData.create()
    //        else
    //            repository.getProductListbyKeyword(keyword)
    //    }


    // Triggers to make a http request

    fun onFetchProductById(productId: String) {
        _productId.value = productId
    }

    fun onReportComment(reportRequest: ReportRequest){
        _commentReport.value = reportRequest
    }

    fun onReportProduct(reportRequest: ReportRequest){
        _productReport.value = reportRequest
    }

    //fun onFetchProductListbyKeyword(keyword: String) {
    //    _searchKeyword.value = keyword
    //}

}