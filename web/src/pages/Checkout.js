import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
import history from '../util/history'
import CheckoutAddressBlock from '../components/checkout/AddressBlock'
import PaymentInfoBlock from '../components/checkout/PaymentInfoBlock'
import CheckoutSidebar from '../components/checkout/CheckoutSidebar'

import './Checkout.css'

const Checkout = ({ isLoggedIn }) => {
	const [isContractChecked, setIsContractChecked] = useState(false)
	const [selectedAddress, selectAddress]= useState({})

	useEffect(() => {
		//Check if the user is logged in before proceeding to checkout
		if(!isLoggedIn) {
			history.push('/signin')
		}
	}, [])

	return (
		<div>
			<div className="checkout-header-container px-5 py-3">Checkout</div>
			<div className='container-fluid p-3 p-sm-5'>
				<div className='row'>
					<div className='col-sm-8'>
						<div className='address-block-container'>
							<CheckoutAddressBlock title='Shipping Address' onCheckout selectAddress={selectAddress} selectedAddress={selectedAddress}/>
						</div>
						<div className='payment-info-container'>
							<PaymentInfoBlock />
						</div>
					</div>
					<div className='col-sm-4'>
						<CheckoutSidebar setIsContractChecked={setIsContractChecked} isContractChecked={isContractChecked}/>
					</div>
				</div>
			</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return { isLoggedIn: state.signIn.isLoggedIn }
}

export default connect(mapStateToProps)(Checkout)