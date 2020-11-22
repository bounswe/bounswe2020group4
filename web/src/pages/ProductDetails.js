import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

import DefaultProductImage from '../images/default-product-detail-image.png'
import ProductInfo from '../components/ProductInfo'
import ProductPurchase from '../components/ProductPurchase'
import Comments from '../components/Comments'

import productService from '../services/products'

import './ProductDetails.css'


const ProductDetails = ({ img }) => {
  const [product, setProduct] = useState(null)
  const id = useParams().id
  
  useEffect(() => {
    productService.getProduct(id)
      .then(p => {
        setProduct(p)
      })
  }, [id])
  
  if(product === null)
    return(<div></div>)

  return(
    <div className='product-details'>
      <div className='product-container'>
        <div className='product-left-column'>
          <img src={product.imageUrl || DefaultProductImage} alt='product'/>
        </div>
        <div className='product-right-column'>
          <ProductInfo 
            productId={product.id}
            name={product.name} 
            brand={product.brand} 
            price={product.price}
            rating={product.rating}
            vendor={product.vendor.name}
            vendorRating={product.vendor.rating}/>        
            <ProductPurchase price={product.price}/>
        </div>
      </div>
      <Comments />
    </div>
  )
}

export default ProductDetails