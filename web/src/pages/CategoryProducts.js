import React, { useEffect, useState } from 'react'
import history from '../util/history';

import ProductCard from '../components/ProductCard'

import productService from '../services/products'

import './CategoryProducts.css'

const CategoryProducts = () => {
  const categoryPath = history.location.state.path 
  const [products, setProducts] = useState([])
  
  useEffect(() => {
    productService
      .getCategoryProducts(categoryPath)
      .then(products => setProducts(products))
  }, [categoryPath])


  return(
    <div className='category-products-container'>
      <div className='category-title' >Category: {categoryPath}</div>
      <div className='products-container'>
        <div className='filters'>
          <h3>Filters</h3>
        </div>
        <div className='product-cards'>
          {products.map(p => 
            <div key={p.id} className='product-card-container'>
              <ProductCard name={p.name} price={p.price} imgUrl={p.imageUrl} productId={p.id}/>
            </div>          
          )}
        </div>
      </div>
    </div>
  )
}

export default CategoryProducts