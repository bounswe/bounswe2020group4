import React, { useState, useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { connect } from 'react-redux'
import { hideHeader, hideVendorHeader } from '../redux/actions'
import { showHeader } from '../redux/actions'
import { setLoginState } from '../redux/actions'
import { Link, useLocation } from "react-router-dom";
import history from '../util/history'

import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'

import accountService from '../services/account'
import logo from '../logo-buyo.png'
import './SignIn.css'

function useQuery() {
	return new URLSearchParams(useLocation().search);
}

const ForgotPassword = ({ hideHeader, showHeader, setLoginState }) => {
	const query = useQuery();
	
	const [userId, setUserId] = useState('')
	const [userType, setUserType] = useState('')
	const [password, setPassword] = useState('')
	const [password2, setPassword2] = useState('')

	useEffect(() => {
		hideHeader()
		hideVendorHeader()

		const queryUserId = query.get('userId')
		setUserId(queryUserId)
		const queryUserType = query.get('userType')
		setUserType(queryUserType)
		
		// alerts may trigger before the header can be hidden, so alerts are delayed by a short amount
		setTimeout(async () => {
			if (!queryUserId || !queryUserType) {
				alert('Corrupted link.')
				history.push('/')
			}
		}, 500)

		return () => showHeader()
	}, [])

	const handlePasswordChange = function (e) {
		setPassword(e.target.value)
	}

	const handlePassword2Change = function (e) {
		setPassword2(e.target.value)
	}

	const handleClick = async function (e) {
		e.preventDefault()

		if (!password) {
			alert('Please enter a new password.')
		} else if (!password2) {
			alert('Please confirm your new password.')
		} else if (password != password2) {
			alert('Password fields do not match.')
		} else {
			const response = await accountService.updatePassword(userType, userId, { 'newPassword': password })
			if (response == 200) {
				alert('Your password has been successfully changed. Logging in...')
				setLoginState({ userId: userId, userType: userType })
				if (userType == 'vendor') {
					history.push('/vendorprofile')
				} else {
					history.push('/')
				}
			} else {
				alert('Something went wrong. Please try again later.')
			}
		}
	}

	return (
		<div className="signInModal">
			<div className="formContainer">
				<Link to='/'>
					<img className="logo" src={logo} alt="Buyo logo" />
				</Link>
				<Form>
					<Form.Group controlId="formBasicPassword">
						<Form.Control
							className="formInputBox"
							type="password"
							placeholder="Password"
							value={password}
							onChange={handlePasswordChange}
						/>
					</Form.Group>
					<Form.Group controlId="formBasicPassword2">
						<Form.Control
							className="formInputBox"
							type="password"
							placeholder="Re-enter Password"
							value={password2}
							onChange={handlePassword2Change}
						/>
					</Form.Group>
					<Button
						className="submitButton"
						variant="primary"
						type="submit"
						onClick={handleClick}
					>
						UPDATE PASSWORD
					</Button>
				</Form>
			</div>
		</div>
	)
}

export default connect(null, { showHeader, hideHeader, setLoginState })(ForgotPassword)
