import React from 'react'
import { Link } from 'react-router-dom'
import history from '../util/history'

import DefaultProductImage from '../images/default-product-image.png'

import './ProductCard.css'
import '../pages/VendorProducts.css'
import vendorService from '../services/vendor'

const VendorProductCard = ({ vendorId, name, originalPrice, price, imgUrl, productId, brand}) => {

	const handleDeleteButton = async function(){
		const response = await vendorService.deleteProduct(vendorId, productId)
		if(response == 200){
			alert('Product has been deleted successfully.')
		} else {
			alert('Something went wrong, please try again')
		}
		history.push('/vendorproducts')
	}

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
						<Link to={`/updateproduct/${productId}`}>
							<button type='button' className='btn btn-danger'>Edit</button>
						</Link>
					</div>
				</div>
				<div className='row'>
					<div className='pc-info-bottom'>
						{originalPrice==price ?
							<div className='pc-price col'>{originalPrice}₺</div>
							:
							<div>
								<div className='pc-price col' style={{textDecorationLine: 'line-through'}}>{originalPrice}₺</div>
								<div className='pc-price col'>{price}₺</div>
							</div>
						}
						<div className='col text-center'>
							<button onClick={handleDeleteButton} type='button' className='btn btn-light'>Delete</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	)
}

export default VendorProductCard