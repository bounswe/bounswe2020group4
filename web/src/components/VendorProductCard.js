import React from 'react'
import { Link } from 'react-router-dom'

import PCWishlistIcon from '../images/wishlist-icon.png'
import PCCartIcon from '../images/cart-icon.png'
import DefaultProductImage from '../images/default-product-image.png'

import './ProductCard.css'
import '../pages/VendorProducts.css'

const VendorProductCard = ({ name, price, imgUrl, productId, brand}) => {
	return (
		<div className='pc-container container-fluid justify-content-center'>
			<Link to={`/product/${productId}`} >
				<div className='pc-img-container'>
					<img className='pc-img' src={imgUrl || DefaultProductImage} alt='product image'/>
				</div>
			</Link>
			<div className='pc-info'>
				<div className='row'>
					<div className='col'>
						<Link to={`/product/${productId}`} >
							<div className='pc-name'>{name}</div>
							<div className='pc-name'>{brand}</div>
						</Link>
					</div>
					<div className='col text-center'>
						<button type='button' className='btn btn-danger'>Edit</button>
					</div>
				</div>
				<div className='row'>
					<div className='pc-info-bottom'>
						<div className='pc-price col'>{price}₺</div>
						<div className='col text-center'>
							<button type='button' className='btn btn-light'>Delete</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}

export default VendorProductCard