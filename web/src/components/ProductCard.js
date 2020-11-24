import React from 'react'
import { Link } from 'react-router-dom'

import PCWishlistIcon from '../images/wishlist-icon.png'
import PCCartIcon from '../images/cart-icon.png'
import DefaultProductImage from '../images/default-product-image.png'

//Styling
import './ProductCard.css'

const ProductCard = ({ name, price, imgUrl, productId }) => {
    return (
        <div className='pc-container'>
            <Link to={`/product/${productId}`} >
                <div className='pc-img-container'>
                    <img className='pc-img' src={imgUrl || DefaultProductImage} alt='product image'/>
                </div>
            </Link>
            <div className='pc-info'>
                <Link to={`/product/${productId}`} >
                    <div className='pc-name'>{name}</div>
                </Link>
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