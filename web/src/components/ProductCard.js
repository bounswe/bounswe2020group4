import React from 'react'

import PCWishlistIcon from '../images/pc-wishlist-icon.png'
import PCCartIcon from '../images/pc-cart-icon.png'
import DefaultProductImage from '../images/default-product-image.png'

//Styling
import './ProductCard.css'

const ProductCard = ({ name, price, img }) => {
    return (
        <div className='pc-container'>
            <div className='pc-img-container'>
                <img className='pc-img' src={img || DefaultProductImage} alt='product image'/>
            </div>
            <div className='pc-info'>
                <div className='pc-name'>{name}</div>
                <div className='pc-info-bottom'>
                    <div className='pc-price'>{price}</div>
                    <div className='pc-cart-wishlist'>
                        <img className='pc-wishlist-icon' src={PCWishlistIcon} alt='wishlist icon'/>
                        <img className='pc-cart-icon' src={PCCartIcon} alt='cart icon'/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default ProductCard