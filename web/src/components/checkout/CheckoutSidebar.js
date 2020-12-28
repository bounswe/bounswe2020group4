import React from 'react'

import './CheckoutSidebar.css'

const CheckoutSidebar = ({ setIsContractChecked, isContractChecked, priceInfo }) => {
	return (
		<div>
			<div className='contract-container position-relative d-flex border p-2 rounded-sm mb-4'>
				<input type='checkbox' className='contract-checkbox' checked={isContractChecked} onClick={() => setIsContractChecked(!isContractChecked)}/>
				<div className='contract-text'>
                    I have read and accepted the Preliminary Information Form and the Distance Sales Contract
				</div>
			</div>
			<div className='purchase-information p-4 border rounded-sm'>
				<h4>Summary</h4>
				<div className='d-flex justify-content-between mb-1'>
					<div>Product Total</div>
					<div className='font-weight-light'>{priceInfo.totalPrice}</div>
				</div>
				<div className='d-flex justify-content-between'>
					<div>Discount</div>
					<div className='font-weight-light red-text'>-{priceInfo.totalPrice - priceInfo.discountedPrice}</div>
				</div>
				<hr/>
				<div className='d-flex justify-content-between'>
					<div>Total</div>
					<div className='font-weight-bold red-text'>{priceInfo.discountedPrice}</div>
				</div>
			</div>
		</div>
	)
}

export default CheckoutSidebar