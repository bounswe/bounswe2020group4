import React, { useEffect, useState } from 'react'
import { useLocation } from "react-router-dom";
import queryString from 'query-string';

import ProductCard from '../components/ProductCard'
import productService from '../services/products'

import './CategoryProducts.css'

const CategoryProducts = () => {
  const location = useLocation()
  const params = queryString.parse(location.search)
  const [products, setProducts] = useState([])

  useEffect(() => {
    if(params.categories) {
      productService
        .getCategoryProducts(params.categories)
        .then(products => setProducts(products))
    }
    else if(params.search) {
      productService
        .searchProducts(params.search)
        .then(products => setProducts(products))
    }
  }, [params.categories, params.search])

  return(
    <div className='category-products-container'>
      <div className='category-title' >
        {params.categories ? "Category: " + params.categories : "Search: " + params.search}
      </div>
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