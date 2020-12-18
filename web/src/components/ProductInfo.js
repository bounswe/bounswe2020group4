import React from 'react'

import WishlistButton from '../components/WishlistButton'
import RatingStar from '../components/RatingStar'

import FreeShippingImage from '../images/free-shipping.png'

import './ProductInfo.css'


const ProductInfo = ({ productId, name, brand, price, rating, vendor, vendorRating}) => {
	return(
		<div className='product-info-container'>
			<div>
				<h2 className='product-name' >{name}</h2>
				<div className='wishlist-button-container' >
					<WishlistButton productId={productId} />
				</div>
			</div>
			<h3 className='product-brand'>{brand}</h3>
			<RatingStar rating={rating} />
			<div className='product-price'>{price}&#8378;</div>
			<div>
				<div className='shipping-container'>
					<img src={FreeShippingImage} alt='free shipping'/>
				</div>
				<div className='company-detail-container'>
					<div>Seller: {vendor}</div>
					<RatingStar rating={vendorRating}/>
				</div>
			</div>
		</div>
	)
}

export default ProductInfo