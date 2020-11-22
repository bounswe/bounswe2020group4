import React, { useEffect, useState } from 'react';

import ProductCard from '../components/ProductCard'

import wishlistService from '../services/wishlist'

import './Wishlist.css'

const Wishlist = () => {
  const [products, setProducts] = useState([])

  //TODO read from redux state
  const customerId = 12341

  useEffect(() => {
    wishlistService
      .getWishlist(customerId)
      .then(prods => {
        setProducts(prods)
      })
  }, [])

  return(
    <div className='wishlist-container'>
      <div className='list-title' >List: Wishlist(All)</div>
      <div className='list-container'>
        <div className='list-links'>
          <h3>Lists</h3>
        </div>
        <div className='product-cards'>
          {products.map(p => 
            <div key={p.id} className='product-card-container'>
              <ProductCard name={p.name} price={p.price} img={p.imageUrl}/>
            </div>          
          )}
        </div>
      </div>
    </div>
  )
}

export default Wishlist