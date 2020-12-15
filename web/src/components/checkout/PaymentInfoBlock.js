import React from 'react'

const PaymentInfoBlock = () => {
	return (
		<div className='payment-info-block p-4 border rounded-sm'>
			<h3 className='m-2 mb-4'>Payment Information</h3>
			<div className='form-container'>
				<form>
					<div className='form-row'>
						<div className='form-group col-8 '>
							<label htmlFor='nameOnCard'>Name On Card</label>
							<input type='text' className='form-control' id='nameOnCard'></input>
						</div>
						<div className='form-group col-2'>
							<label htmlFor='month'>Exp. Month</label>
							<input type='text' className='form-control' id='month'></input>
						</div>
						<div className='form-group col-2'>
							<label htmlFor='year'>Exp. Year</label>
							<input type='text' className='form-control' id='year'></input>
						</div>
					</div>
					<div className='form-row'>
						<div className='form-group col-9 '>
							<label htmlFor='cardNo'>Card Number</label>
							<input type='text' className='form-control' id='cardNo'></input>
						</div>
						<div className='form-group col-3'>
							<label htmlFor='ccv'>CCV</label>
							<input type='text' className='form-control' id='ccv'></input>
						</div>
					</div>
					<hr/>
					<div className='text-center'>
						<button className='btn-lg btn-danger'>BUY!</button>
					</div>
				</form>
			</div>
		</div>
	)
}

export default PaymentInfoBlock