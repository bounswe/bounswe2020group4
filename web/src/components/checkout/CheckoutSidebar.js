import React from 'react'
import PreInfoForm from '../../Legal Docs/Preliminary Informaton Form - BUYO.pdf'
import DistSalesAgreement from '../../Legal Docs/distant sales agreement.pdf'

import './CheckoutSidebar.css'

const CheckoutSidebar = ({ setIsContractChecked, isContractChecked, priceInfo }) => {
	return (
		<div>
			<div className='contract-container position-relative d-flex border p-2 rounded-sm mb-4'>
				<input type='checkbox' className='contract-checkbox' checked={isContractChecked} onClick={() => setIsContractChecked(!isContractChecked)}/>
				<div className='contract-text'>
                    I have read and accepted the <a href={PreInfoForm} target="_blank" rel="noopener noreferrer" style={{ textDecoration: 'underline' }}>Preliminary Information Form</a> and the <a href={DistSalesAgreement} target="_blank" rel="noopener noreferrer" style={{ textDecoration: 'underline' }}>Distance Sales Contract</a>
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