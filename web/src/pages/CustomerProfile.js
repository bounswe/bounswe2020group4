import React from 'react'
import { connect } from 'react-redux'

import ProfileInfo from '../components/customer-profile/ProfileInfo'
import UpdatePassword from '../components/customer-profile/UpdatePassword'

import './CustomerProfile.css'

const CustomerProfile = ({ isGoogleUser }) => {
	console.log("Is Google User? ", isGoogleUser)
	return (
		<div>
			<div className="checkout-header-container px-5 py-2">Profile Information</div>
			<div className="profile-info-container">
				<div className="row">
					<div className="col">
						<ProfileInfo/>
					</div>
					{	// Dont show update password part if the user is a google user
						isGoogleUser ? null : 
						<div className="col">
							<UpdatePassword/>
						</div>
					}
				</div>
			</div>
		</div>
	)

}

const mapStateToProps = (state) => {
	return { isGoogleUser: state.signIn.isGoogleUser }
}

export default connect(mapStateToProps, {})(CustomerProfile)