package com.cmpe352group4.buyo.dependencyinjection

import com.cmpe352group4.buyo.ui.EmptyFragment
import com.cmpe352group4.buyo.ui.ExampleFragment
import com.cmpe352group4.buyo.ui.homepage.HomepageFragment
import com.cmpe352group4.buyo.ui.login.LoginFragment
import com.cmpe352group4.buyo.ui.navigationtabs.*
import com.cmpe352group4.buyo.ui.wishList.WishListFragment
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailCommentsFragment
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilderModule {

    // AFTER CREATING NEW FRAGMENT YOU NEED TO ADD AN INJECTOR FUNCTION LIKE THE OTHERS
    @ContributesAndroidInjector
    abstract fun contributeExampleFragment(): ExampleFragment

    @ContributesAndroidInjector
    abstract fun contributeEmptyFragment(): EmptyFragment

    @ContributesAndroidInjector
    abstract fun contributeFullScreenContainer(): FullScreenContainer

    @ContributesAndroidInjector
    abstract fun contributeHomeTabContainer(): HomeTabContainer

    @ContributesAndroidInjector
    abstract fun contributeCategoryTabContainer(): CategoryTabContainer

    @ContributesAndroidInjector
    abstract fun contributeWishlistTabContainer(): WishlistTabContainer

    @ContributesAndroidInjector
    abstract fun contributeCartTabContainer(): CartTabContainer

    @ContributesAndroidInjector
    abstract fun contributeProfileTabContainer(): ProfileTabContainer

    @ContributesAndroidInjector
    abstract fun contributeWishListFragment(): WishListFragment

    @ContributesAndroidInjector  
    abstract fun contributeHomepageFragment(): HomepageFragment

    @ContributesAndroidInjector
    abstract  fun contributeProductListFragment(): ProductListFragment

    @ContributesAndroidInjector
    abstract  fun contributeProductDetailContentFragment(): ProductDetailContentFragment

    @ContributesAndroidInjector
    abstract  fun contributeProductDetailCommentsFragment(): ProductDetailCommentsFragment

    @ContributesAndroidInjector
    abstract  fun contributeLoginFragment(): LoginFragment

}