import React from 'react'
import { Link } from 'react-router-dom'
import Icon from '../images/buyo-icon.png'

import { connect } from 'react-redux'
import history from '../util/history'
import { setLogoutState } from '../redux/actions'

import './VendorHeader.css'

const VendorHeader = ({setLogoutState}) => {
    const signOut = () => {
		setLogoutState()
		history.push('/')
    }
    
    return (
        <div className='vendor-header-container container-fluid d-flex justify-content-center p-4'>
            <div className='mr-5'>
                <img className='header-icon vertical-align-middle' src={Icon} alt='site icon'/>
            </div>
            <Link to='/vendorproducts' className='vendor-header-category px-4 py-3 mx-3 rounded'>
                Products
            </Link>
            <Link to='/vendororders' className='vendor-header-category px-4 py-3 mx-3 rounded'>
                Orders
            </Link>
            <Link to='/vendorprofile' className='vendor-header-category px-4 py-3 mx-3 rounded'>
                Profile
            </Link>
            <Link to='/addproduct' className='vendor-header-category px-4 py-3 mx-3 rounded'>
                Add Product
            </Link>
            <div className='vendor-header-category px-4 py-3 mx-3 rounded'>
                <span className='sign-out-button cursor-pointer' onClick={signOut}>Sign Out</span>
            </div>
        </div>
    )
}

export default connect(null, {setLogoutState})(VendorHeader)
