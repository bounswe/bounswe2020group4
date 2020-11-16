import React from 'react'

import DefaultProductImage from '../images/default-product-image.png'
import ProductInfo from '../components/ProductInfo'

import './ProductDetails.css'


const ProductDetails = ({ img }) => {
  return(
    <div>
      <div className='product-left-column'>
        <img src={img || DefaultProductImage} alt='product'/>
      </div>
      <div className='product-right-column'>
        <ProductInfo />        
      </div>
    </div>
  )
}

export default ProductDetails