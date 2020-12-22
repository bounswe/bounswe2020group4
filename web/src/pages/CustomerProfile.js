import React from 'react'

import ProfileInfo from '../components/customer-profile/ProfileInfo'
import UpdatePassword from '../components/customer-profile/UpdatePassword'

import './CustomerProfile.css'

const CustomerProfile = () => {

	return (
		<div>
			<div className="checkout-header-container px-5 py-2">Profile Information</div>
			<div className="profile-info-container">
				<div className="row">
					<div className="col">
						<ProfileInfo/>
					</div>
					<div className="col">
						<UpdatePassword/>
					</div>
				</div>
			</div>
		</div>
	)

}

export default CustomerProfile