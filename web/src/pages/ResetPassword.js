import React, { useState, useEffect } from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import { connect } from 'react-redux'
import { hideHeader, hideVendorHeader } from '../redux/actions'
import { showHeader } from '../redux/actions'
import { setLoginState } from '../redux/actions'
import { Link } from 'react-router-dom'
import history from '../util/history'

import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'

import accountService from '../services/account'
import logo from '../logo-buyo.png'
import './SignIn.css'

const ResetPassword = ({ hideHeader, showHeader, setLoginState }) => {

	useEffect(() => {
		hideHeader()
		hideVendorHeader()
		return () => showHeader()
	}, [])

	const [email, setEmail] = useState('')

	const handleEmailChange = function (e) {
		setEmail(e.target.value)
	}

	const handleClick = async function (e) {
		e.preventDefault()
		if (!email) {
			alert('Email cannot be empty.')
		} else {
			const response = await accountService.forgotPassword(email)
			if (response) {
				alert('A link has been sent to your e-mail to reset your password.')
			} else {
				alert('Something went wrong. Please try again later.')
			}
		}
	}

	const redirectToSignin = (e) => {
		e.preventDefault()
		history.push('/signin')
	}

	const redirectToVendorSignin = (e) => {
		e.preventDefault()
		history.push('/vendorsignin')
	}

	return (
		<div className="signInModal">
			<div className="formContainer">
				<Link to='/'>
					<img className="logo" src={logo} alt="Buyo logo" />
				</Link>
				<Form>
					<Form.Group controlId="formBasicEmail">
						<Form.Control
							className="formInputBox"
							type="email"
							placeholder="Email"
							value={email}
							onChange={handleEmailChange}
						/>
					</Form.Group>
					<Button
						className="submitButton"
						variant="primary"
						type="submit"
						onClick={handleClick}
					>
						RESET PASSWORD
					</Button>
					<Button
						className="submitButtonTransparent"
						variant="primary"
						type="submit"
						onClick={redirectToSignin}
					>
						BACK TO SIGN IN
					</Button>
					<Button
						className="submitButtonTransparent"
						variant="primary"
						type="submit"
						onClick={redirectToVendorSignin}
					>
						BACK TO VENDOR SIGN IN
					</Button>
				</Form>
			</div>
		</div>
	)
}

export default connect(null, { showHeader, hideHeader, setLoginState })(ResetPassword)
