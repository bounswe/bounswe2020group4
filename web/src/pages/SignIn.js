import React, { useState, useEffect } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import 'bootstrap/dist/css/bootstrap.min.css'
import './SignIn.css'
import logo from '../logo-buyo.png'
import { connect } from 'react-redux'
import { hideHeader } from '../redux/actions'
import { showHeader } from '../redux/actions'
import { setLoginState } from '../redux/actions'
import history from '../util/history'
import accountService from '../services/account'
import { Link } from 'react-router-dom'

const SignIn = ({hideHeader, showHeader, setLoginState}) => {

	//This function corresponds to componentDidMount
	//The return function corresponds to componentDidUnmount
	useEffect(() => {
		hideHeader()
		return () => showHeader()
	}, [])

	//Setup google sign in button
	useEffect(() => {
		window.gapi.signin2.render('g-signin2', {
			'scope': 'https://www.googleapis.com/auth/plus.login',
			'onsuccess': onGoogleSignIn
		})
	}, [])

	const [email, setEmail] = useState('')
	const [password, setPassword] = useState('')

	const handleEmailChange = function(e) {
		setEmail(e.target.value)
	}

	const handlePasswordChange = function(e) {
		setPassword(e.target.value)
	}

	const handleClick = async function(e) {
		e.preventDefault()
		if(email == '' | password == ''){
			alert('Enter your credentials')
		} else {
			const response = await accountService.login({ 'email': email, 'password': password})
			if (!response?.userId){
				alert('Wrong credentials')
			} else if (response.banned) {
				alert('Your account has been suspended. Please check your e-mail for further information.')
			} else {
				setLoginState({ userId: response.userId, userType: 'customer'})
				history.push('/')
			}
		}
	}
	const redirectToSignup = function(e) {
		e.preventDefault()
		history.push('/signup')
	}

	const onGoogleSignIn = async (googleUser) => {
		const id_token = await googleUser.getAuthResponse().id_token
		const email = await googleUser.getBasicProfile().getEmail()
		const response = await accountService.googleSignIn(id_token, email)
		if(response?.id == null) {
			alert("Something went wrong while trying to sign in with google")
		} else if (response.banned) {
			alert("Your account has been suspended. Please check your e-mail for further information.")
		} else {
			setLoginState({ userId: response.id, userType: 'customer' })
			history.push('/')
		}
	}

	return (
		<div className="signInModal">
			<div className="formContainer">
				<Link to='/'>
					<img className="logo" src={logo} alt="Buyo logo"/>
				</Link>
				<Form>
					<Form.Group controlId="formBasicEmail">
						<Form.Control
							className="formInputBox"
							type="email"
							placeholder="Email"
							value = {email}
							onChange={handleEmailChange}
						/>
					</Form.Group>

					<Form.Group controlId="formBasicPassword">
						<Form.Control
							className="formInputBox"
							type="password"
							placeholder="Password"
							value = {password}
							onChange={handlePasswordChange}
						/>
						<div className="col text-center">
							<a href="/vendorsignin">Are you a vendor?</a>
						</div>
					</Form.Group>
					<Button
						className="submitButton"
						variant="primary"
						type="submit"
						onClick = {handleClick}
					>
						SIGN IN
					</Button>
					<div className='google-signin-button-container pt-3'>
						<div id="g-signin2" className='google-signin-button'/>
					</div>
					<Button
						className="submitButtonTransparent"
						variant="primary"
						type="submit"
						onClick = {redirectToSignup}
					>
							SIGN UP
					</Button>
				</Form>
			</div>
		</div>

	)

}


export default connect(null, {showHeader, hideHeader, setLoginState})(SignIn)