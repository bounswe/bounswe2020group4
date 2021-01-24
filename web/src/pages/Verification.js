import React, { useEffect } from 'react'
import { Link, useLocation } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css'
import { connect } from 'react-redux'
import { hideHeader, hideVendorHeader } from '../redux/actions'
import { showHeader } from '../redux/actions'
import { setLoginState } from '../redux/actions'
import history from '../util/history'
import accountService from '../services/account'

import logo from '../logo-buyo.png'
import './Verification.css'

function useQuery() {
	return new URLSearchParams(useLocation().search);
}

const Verification = ({ hideHeader, hideVendorHeader, showHeader, setLoginState }) => {
	let query = useQuery();

	useEffect(() => {
		hideHeader()
		hideVendorHeader()
		
		// alerts may trigger before the header can be hidden, so alerts are delayed by a short amount
		setTimeout(async () => {
			const userType = query.get('userType')
			const userId = query.get('id')
			if (!userType || !userId) {
				alert('Corrupted link.')
				history.push('/')
			} else {
				const verified = await accountService.verify(userType, userId)
				if (verified) {
					alert('Your account has been verified. Logging in...')
					setLoginState({ userId: userId, userType: userType })
					if (userType == 'vendor') {
						history.push('/vendorprofile')
					} else {
						history.push('/')
					}
				} else {
					alert('Your account verification failed. Try again later.')
					history.push('/')
				}
			}
		}, 500)

		return () => showHeader()
	}, [])

	return (
		<div className="verification-modal">
			<div className="message-container">
				<Link to='/'>
					<img className="logo" src={logo} alt="Buyo logo" />
				</Link>
				<div className="message">
					Your account is being verified. Please wait.
				</div>
			</div>
		</div>

	)
}

export default connect(null, { showHeader, hideHeader, hideVendorHeader, setLoginState })(Verification)
