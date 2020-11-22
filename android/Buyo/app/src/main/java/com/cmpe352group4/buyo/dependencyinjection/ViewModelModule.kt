package com.cmpe352group4.buyo.dependencyinjection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cmpe352group4.buyo.viewmodel.CategoryViewModel
import com.cmpe352group4.buyo.viewmodel.ExampleViewModel
import com.cmpe352group4.buyo.viewmodel.WishListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    // AFTER CREATING NEW VIEWMODEL YOU NEED TO ADD A BIND FUNCTION LIKE THE FOLLOWING ONE

    @Binds
    @IntoMap
    @ViewModelKey(ExampleViewModel::class)
    abstract fun bindExampleViewModel(viewModel: ExampleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoryViewModel::class)
    abstract fun bindCategoryViewModel(viewModel: CategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WishListViewModel::class)
    abstract fun bindWishListViewModel(viewModel: WishListViewModel): ViewModel


    @Binds
    abstract fun bindViewModelFactory(factory: ApplicationViewModelFactory): ViewModelProvider.Factory

}