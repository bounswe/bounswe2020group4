import React from 'react';

import ProductCard from '../components/ProductCard'

import './Wishlist.css'

const Wishlist = () => {
  return(
    <div className='wishlist-container'>
      <div className='list-title' >List: Wishlist(All)</div>
      <div className='list-container'>
        <div className='list-links'>
          <h3>Lists</h3>
        </div>
        <div className='product-cards'>
          <div className='product-card-container'>
            <ProductCard name='Product' price='$7.99'/>
          </div>          
        </div>
      </div>
    </div>
  )
}

export default Wishlist