import React from 'react'

import './ProductDescription.css'

const ProductDescription = ({ description }) => {
	return(
		<div className='product-description-container'>
			<h2>Product Description</h2>
			<p>{description}</p>
		</div>
	)
}

export default ProductDescription