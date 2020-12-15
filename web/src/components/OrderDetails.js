import React from 'react'

import DefaultProductImage from '../images/default-product-detail-image.png'

import './OrderDetails.css'

const ProductOrder = ({imgUrl, name, brand, price}) => {
  return(
    <div className="product-order">
      <img className="product-image" src={imgUrl || DefaultProductImage} alt='product'/>
      <div className="product-order-info">
        <div>{name}</div>
        <div>{brand}</div>
        <div>{price}</div>
      </div>
    </div>
  )
}


const OrderDetails = () => {
  return(
    <div className="order-details-container">
      <div className="order-details-top">
        <div>
          <ProductOrder name="kazak" brand="mavi" price="200 tl"/>   
          <ProductOrder name="çorap" brand="lcw" price="10 tl" /> 
        </div>  
        <div className="order-button-container">
          <button>Cancel Order</button>
          <button>Message Vendor</button>
        </div>
      </div>
      <div className="address-container"> Address: Rumeli Hisarı, Hisar Üstü Nispetiye Cd No:7, 34342 Sarıyer/İstanbul
      </div>
    </div>
  )
}

export default OrderDetails;