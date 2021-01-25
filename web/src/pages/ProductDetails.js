import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

import DefaultProductImage from '../images/default-product-detail-image.png'
import ProductInfo from '../components/product/ProductInfo'
import ProductDescription from '../components/product/ProductDescription'
import ProductPurchase from '../components/product/ProductPurchase'
import Comments from '../components/product/Comments'

import productService from '../services/products'

import './ProductDetails.css'


const ProductDetails = () => {
	const [product, setProduct] = useState(null)
	const id = useParams().id

	useEffect(() => {
		const getProduct = async () => {
			const p = await productService.getProduct(id)
			if(Object.keys(p).length !== 0 || p.constructor !== Object) {
				setProduct(p)
			}
		}

		getProduct()
	}, [id])

	if(product === null)
		return(<div></div>)

	return(
		<div className='product-details'>
			<div className='product-container'>
				<div className='product-left-column'>
					<img src={product.imageUrl || DefaultProductImage} alt='product'/>
				</div>
				<div className='product-right-column'>
					<ProductInfo
						productId={product.id}
						name={product.name}
						brand={product.brand}
						price={product.price}
						rating={typeof product.rating !== 'undefined' ? product.rating : 0}
						vendor={product.vendor.name}
						vendorRating={typeof product.vendor.rating !== 'undefined' ? product.vendor.rating : 0}/>
					<ProductPurchase productId={product.id} price={product.price} originalPrice={product.originalPrice} productInfos={product.productInfos}/>
				</div>
			</div>
			<ProductDescription description={product.description} />
			<Comments comments={product.comments} />
		</div>
	)
}



export default ProductDetails