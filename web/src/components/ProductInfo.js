import React from 'react'

import WishlistButton from '../components/WishlistButton'

import './ProductInfo.css'


const ProductInfo = () => {
  return(
    <div className='product-info-container'>
      <div>          
        <h2 className='product-name' >Bluetooth Kulaklık</h2>
        <div className='wishlist-button-container' >
          <WishlistButton />
        </div>          
      </div>
      <h3 className='product-brand'>Xiaomi</h3>
    </div>
  )
}

export default ProductInfo