package com.cmpe352group4.buyo.dependencyinjection

import com.cmpe352group4.buyo.ui.EmptyFragment
import com.cmpe352group4.buyo.ui.LegalDocFragment
import com.cmpe352group4.buyo.ui.cart.CartPageFragment
import com.cmpe352group4.buyo.ui.cart.CheckoutPageFragment
import com.cmpe352group4.buyo.ui.categories.CategoriesPageFragment
import com.cmpe352group4.buyo.ui.googlemap.MapsFragment
import com.cmpe352group4.buyo.ui.homepage.HomepageFragment
import com.cmpe352group4.buyo.ui.image_utils.ImageUploadFragment
import com.cmpe352group4.buyo.ui.login.LoginFragment
import com.cmpe352group4.buyo.ui.login.LoginFragmentVendor
import com.cmpe352group4.buyo.ui.navigationtabs.*
import com.cmpe352group4.buyo.ui.orderpage.OrderPageFragment
import com.cmpe352group4.buyo.ui.productDetail.AddCartFragment
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailCommentsFragment
import com.cmpe352group4.buyo.ui.productDetail.ProductDetailContentFragment
import com.cmpe352group4.buyo.ui.productList.ListSortFilterFragment
import com.cmpe352group4.buyo.ui.productList.ProductListFragment
import com.cmpe352group4.buyo.ui.profilePage.*
import com.cmpe352group4.buyo.ui.wishList.WishListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsBuilderModule {

    // AFTER CREATING NEW FRAGMENT YOU NEED TO ADD AN INJECTOR FUNCTION LIKE THE OTHERS

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
    abstract  fun contributeCategoriesPageFragment(): CategoriesPageFragment
    
    @ContributesAndroidInjector
    abstract  fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract  fun contributeLoginFragmentVendor(): LoginFragmentVendor

    @ContributesAndroidInjector
    abstract  fun contributeMapsFragment(): MapsFragment

    @ContributesAndroidInjector
    abstract  fun contributeCartPageFragment(): CartPageFragment

    @ContributesAndroidInjector
    abstract  fun contributeAddUpdateAddressFragment(): AddUpdateAddressFragment

    @ContributesAndroidInjector
    abstract  fun contributeCheckoutPageFragment(): CheckoutPageFragment
    
    @ContributesAndroidInjector
    abstract  fun contributeLegalDocFragment(): LegalDocFragment

    @ContributesAndroidInjector
    abstract  fun contributeProfilePageFragment(): ProfilePageFragment

    @ContributesAndroidInjector
    abstract  fun contributeChangePasswordFragment(): ChangePasswordFragment

    @ContributesAndroidInjector
    abstract  fun contributeAccountInfoFragment(): AccountInfoFragment

    @ContributesAndroidInjector
    abstract  fun contributeAddressInfoFragment(): AddressInfoFragment

    @ContributesAndroidInjector
    abstract  fun contributeListSortFilterFragment(): ListSortFilterFragment

    @ContributesAndroidInjector
    abstract  fun contributeAddCartFragment(): AddCartFragment
    
    @ContributesAndroidInjector
    abstract  fun contributeOrderPageFragment(): OrderPageFragment

    @ContributesAndroidInjector
    abstract  fun contributeImageUploadFragment(): ImageUploadFragment

}