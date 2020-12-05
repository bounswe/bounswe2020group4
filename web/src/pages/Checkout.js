import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
import history from '../util/history'
import CheckoutAddressBlock from '../components/checkout/CheckoutAddressBlock'
import PaymentInfoBlock from '../components/checkout/PaymentInfoBlock'

import './Checkout.css'

const AddressDummyData = [
    {
        title: "EV",
        receivingName: "Meric",
        receivingPhone: "0531 932 8388", 
        address: "Etiler Mah, Ucaksavarn Sit", 
        province: "Besiktas", 
        city: "Istanbul"
    }, 
    {
        title: "Ucaksavar",
        receivingName: "Meric",
        receivingPhone: "0531 932 8388", 
        address: "Etiler Mah, Ucaksavarn Sit", 
        province: "Besiktas", 
        city: "Istanbul"
    }
]



const Checkout = ({ isLoggedIn }) => {
    const [selectedAddress, selectAddress]= useState(AddressDummyData[0])

    // useEffect(() => {
    //     //Check if the user is logged in before proceeding to checkout
    //     if(!isLoggedIn) {
    //         history.push('/signin')
    //     }
    // }, [])

    return (
        <div>
            <div className="checkout-header-container px-5 py-3">Checkout</div>
            <div className='address-block-container'>
                <CheckoutAddressBlock addresses={AddressDummyData} selectAddress={selectAddress} selectedAddress={selectedAddress}/>
            </div>
            <div className='payment-info-container'>
                <PaymentInfoBlock />
            </div>
        </div>
    )
}

const mapStateToProps = (state) => {
    return { isLoggedIn: state.signIn.isLoggedIn }
}

export default connect(mapStateToProps)(Checkout);