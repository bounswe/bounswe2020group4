import React from 'react'

import './CheckoutSidebar.css'

const CheckoutSidebar = ({ setIsContractChecked, isContractChecked }) => {
	return (
		<div>
			<div className='contract-container position-relative d-flex border p-2 rounded-sm mb-4'>
				<input type='checkbox' className='contract-checkbox' checked={isContractChecked} onClick={() => setIsContractChecked(!isContractChecked)}/>
				<div className='contract-text'>
                    Ön Bilgilendirme Koşulları'nı ve Mesafeli Satış Sözleşmesi'ni okudum, onaylıyorum.
				</div>
			</div>
			<div className='purchase-information p-4 border rounded-sm'>
				<h4>Summary</h4>
				<div className='d-flex justify-content-between mb-1'>
					<div>Product Total</div>
					<div className='font-weight-light'>209,99₺</div>
				</div>
				<div className='d-flex justify-content-between mb-1'>
					<div>Cargo Total</div>
					<div className='font-weight-light'>69,98₺</div>
				</div>
				<div className='d-flex justify-content-between'>
					<div>Discount</div>
					<div className='font-weight-light red-text'>-29,99₺</div>
				</div>
				<hr/>
				<div className='d-flex justify-content-between'>
					<div>Total</div>
					<div className='font-weight-bold red-text'>249,98₺</div>
				</div>
			</div>
		</div>
	)
}

export default CheckoutSidebar