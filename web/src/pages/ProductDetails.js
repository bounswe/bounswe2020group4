import React from 'react'

import DefaultProductImage from '../images/default-product-image.png'
import ProductInfo from '../components/ProductInfo'
import ProductPurchase from '../components/ProductPurchase'
import Comments from '../components/Comments'
import ProductDetailInfo from '../components/ProductDetailInfo'

import './ProductDetails.css'


const ProductDetails = ({ img }) => {
  return(
    <div className='product-details'>
      <div className='product-container'>
        <div className='product-left-column'>
          <img src={img || DefaultProductImage} alt='product'/>
          <ProductPurchase />
        </div>
        <div className='product-right-column'>
          <ProductInfo />        
          <ProductDetailInfo />
        </div>
      </div>
      <Comments />
    </div>
  )
}

export default ProductDetails