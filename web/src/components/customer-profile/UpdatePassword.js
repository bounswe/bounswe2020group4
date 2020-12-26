import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'

import history from '../../util/history'
import accountService from '../../services/account.js'

import './ProfileInfo.css'

const UpdatePassword = (props) => {

	const [oldPassword, setOldPassword] = useState('')
	const [newPassword1, setNewPassword1] = useState('')
	const [newPassword2, setNewPassword2] = useState('')

	const handleOldPasswordChange = function(e) {
		setOldPassword(e.target.value)
	}

	const handleNewPassword1Change = function(e) {
		setNewPassword1(e.target.value)
	}

	const handleNewPassword2Change = function(e) {
		setNewPassword2(e.target.value)
	}

	const handleClick = async function(e){
		e.preventDefault()

		if(!oldPassword){
			alert('Enter your current password.')
			return
		}
		if(!newPassword1){
			alert('Enter your new password')
			return
		}

		if(newPassword1 !== newPassword2){
			alert('Enter the same new password for both fields.')
		} else {
			const passwordInfo = {
				'oldPassword': oldPassword,
				'newPassword': newPassword1
			}
			const passwordUpdated = await accountService.updatePassword('customer', props.customerId, passwordInfo)
			console.log(passwordUpdated)
			if(!passwordUpdated){
				alert('You have successfully changed your password.')
				//FIXME: history.push('/customerinfo') does nothing
				history.push('/customerprofile')
			}
			//TODO: Use backend data when endpoint is ready
		}
	}

	return(
		<div className='container-main p-3 mt-3 mb-3 mr-3 ml-1 rounded'>
			<div className='row m-3'>
				<div className='row pb-3'>
					<h3 className='text-container font-weight-bold'>Change password</h3>
				</div>
				<form className="form-container">
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='oldPassword'>Current password</label>
							</div>
							<div className='col'>
								<input type='password' className='form-control form-control-md text-left' id='oldPassword' value={oldPassword} onChange={handleOldPasswordChange}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='newPassword1'>New password</label>
							</div>
							<div className='col'>
								<input type='password' className='form-control form-control-md text-left' id='newPassword1' value={newPassword1} onChange={handleNewPassword1Change}/>
							</div>
						</div>
					</div>
					<div className='form-group'>
						<div className='row'>
							<div className='col-3'>
								<label htmlFor='newPassword2'>New password (again)</label>
							</div>
							<div className='col'>
								<input type='password' className='form-control form-control-md text-left' id='newPassword2' value={newPassword2} onChange={handleNewPassword2Change}/>
							</div>
						</div>
					</div>

					<div className='text-right'>
						<button type='submit' className='btn btn-danger' onSubmit={handleClick} onClick={handleClick}>Update your password</button>
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

export default connect(mapStateToProps)(UpdatePassword)