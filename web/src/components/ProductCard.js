import React from 'react'
import { Link } from 'react-router-dom'

import DefaultProductImage from '../images/default-product-image.png'

import WishlistButton from './product/WishlistButton'

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
					<div className='pc-price'>{price}₺</div>
					<div className='pc-cart-wishlist'>
						<WishlistButton productId={productId}/>
					</div>
				</div>
			</div>
		</div>
	)
}

export default ProductCard