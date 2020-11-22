package com.cmpe352group4.buyo.viewmodel

import androidx.lifecycle.ViewModel
import com.cmpe352group4.buyo.datamanager.repositories.WishListRepository
import javax.inject.Inject


class WishListViewModel @Inject constructor(
    val repository: WishListRepository
): ViewModel() {

    /*private val _wishListProduct = MutableLiveData<Boolean>()

    val wishListProducts: LiveData<Resource<WishListProducts>> =
        Transformations.switchMap(_wishListProduct) { Id ->
            if (Id == null)
                AbsentLiveData.create()
            else
                repository.getWishListPorducts()
        }

    fun onFetchWishListProducts(isFetch: Boolean) {
        if (_wishListProduct.value == null)
            _wishListProduct.value = isFetch
    }*/

}