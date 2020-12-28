import React, { useState } from 'react'
import Valid from 'card-validator'

const PaymentInfoBlock = ({ onPaymentAttempt }) => {
	const [errors, setErrors] = useState({
		isNameValid: true, isMonthValid: true, isYearValid: true, isNumberValid: true, isCVVValid: true
	})

	const onFormSubmit = (e) => {
		e.preventDefault()
		const isNameValid = Valid.cardholderName(e.target[0].value).isPotentiallyValid
		const isMonthValid = Valid.expirationMonth(e.target[1].value).isPotentiallyValid
		const isYearValid = Valid.expirationYear(e.target[2].value).isPotentiallyValid
		const isNumberValid = Valid.number(e.target[3].value).isPotentiallyValid
		const isCVVValid = Valid.cvv(e.target[4].value).isPotentiallyValid

		if(isNumberValid && isNameValid && isMonthValid && isYearValid && isCVVValid) {
			onPaymentAttempt(e.target[0].value, e.target[1].value, e.target[2].value, e.target[3].value, e.target[4].value)
		} else {
			setErrors({ isNameValid, isMonthValid, isYearValid, isNumberValid, isCVVValid })
		}
	}

	return (
		<div className='payment-info-block p-4 border rounded-sm'>
			<h3 className='m-2 mb-4'>Payment Information</h3>
			<div className='form-container'>
				<form onSubmit={onFormSubmit}>
					<div className='form-row'>
						<div className='form-group col-8 '>
							<label htmlFor='nameOnCard'>Name On Card</label>
							<input type='text' className={`form-control ${errors.isNameValid ? '' : 'border border-danger'}`} id='nameOnCard'></input>
						</div>
						<div className='form-group col-2'>
							<label htmlFor='month'>Exp. Month</label>
							<input type='text' className={`form-control ${errors.isMonthValid ? '' : 'border border-danger'}`} id='month'></input>
						</div>
						<div className='form-group col-2'>
							<label htmlFor='year'>Exp. Year</label>
							<input type='text' className={`form-control ${errors.isYearValid ? '' : 'border border-danger'}`} id='year'></input>
						</div>
					</div>
					<div className='form-row'>
						<div className='form-group col-9 '>
							<label htmlFor='cardNo'>Card Number</label>
							<input type='text' className={`form-control ${errors.isNumberValid ? '' : 'border border-danger'}`} id='cardNo'></input>
						</div>
						<div className='form-group col-3'>
							<label htmlFor='ccv'>CVV</label>
							<input type='text' className={`form-control ${errors.isCVVValid ? '' : 'border border-danger'}`} id='ccv'></input>
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