import React from 'react'
import './ProductPurchase.css'

const ProductPurchase = ({price}) => {
  return(
    <div className='product-purchase-container'>
      <div className='product-amount-container' >
        <label className='amount-label'>Amount</label>
        <input type="number" min="1" value="1"/>
      </div>
      <div className='product-add-to-cart-container'>
        <div className='product-price'>
          {price} &#8378;
        </div>
        <button>Add to <br/> Cart</button>
      </div>
    </div>
  )
}

export default ProductPurchase