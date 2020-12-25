import React, { useEffect, useState } from 'react'

import getProfileInfo from '../../services/account.js'

import './ProfileInfo.css'

const ProfileInfo = (props) => {

	return (
		<div className='container p-3'>
			<div className='row m-3'>
				<form className="form-container">
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label hmtlFor='name'>Name</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='name' value={firstName}'/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='surname'>Surname</label>
							</div>
							<div className='col'>
								<input type='text' className='form-control form-control-md text-left' id='surname' value='Gazi'/>
							</div>
						</div>
					</div>

					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='email'>Email</label>
							</div>
							<div className='col'>
								<input type='text' readOnly className='form-control-plaintext text-left' id='email' value='battal@hotmail.com'/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='phone'>Phone number</label>
							</div>
							<div className='col'>
								<input type='text' readOnly className='form-control form-control-md text-left' id='phone' value='+901234567890'/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='gender'>Gender</label>
							</div>
							<div className='col'>
								<select className="form-select form-select-lg p-1 mb-1">
									<option selected>Choose</option>
									<option value="female">Female</option>
									<option value="male">Male</option>
									<option value="other">Other</option>
									<option value="noInfo">Prefer not to say</option>
								</select>
							</div>
						</div>
					</div>
					<div className='text-right'>
						<button type='submit' className='btn btn-danger'>Update</button>
					</div>

				</form>
			</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		customerId: state.signIn.userId,
		userType: state.signIn.userType
	}
}

export default connect(mapStateToProps)(ProfileInfo)