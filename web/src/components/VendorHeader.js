import React from 'react'
import { Link } from 'react-router-dom'
import Icon from '../images/buyo-icon.png'

import './VendorHeader.css'

const VendorHeader = () => {
    return (
        <div className='vendor-header-container container-fluid d-flex justify-content-center p-4'>
            <div className='mr-5'>
                <img className='header-icon vertical-align-middle' src={Icon} alt='site icon'/>
            </div>
            <Link to='/vendor/products' className='vendor-header-category px-4 py-3 mx-3 rounded'>
                Products
            </Link>
            <Link to='/vendor/orders' className='vendor-header-category px-4 py-3 mx-3 rounded'>
                Orders
            </Link>
            <Link to='/vendor/profile' className='vendor-header-category px-4 py-3 mx-3 rounded'>
                Profile
            </Link>
        </div>
    )
}

export default VendorHeader