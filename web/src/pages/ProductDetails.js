import React from 'react'

import DefaultProductImage from '../images/default-product-image.png'
import ProductInfo from '../components/ProductInfo'
import ProductPurchase from '../components/ProductPurchase'

import './ProductDetails.css'


const ProductDetails = ({ img }) => {
  return(
    <div className='product-details'>
      <div className='product-left-column'>
        <img src={img || DefaultProductImage} alt='product'/>
        <ProductPurchase />
      </div>
      <div className='product-right-column'>
        <ProductInfo />        
      </div>
    </div>
  )
}

export default ProductDetails