import React, { useEffect } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'

import VendorProfileInfo from '../components/vendor-profile/VendorProfileInfo'
import UpdatePassword from '../components/customer-profile/UpdatePassword'

const VendorProfile = (props) => {

	useEffect(() => {
		props.hideHeader()
		props.showVendorHeader()
		return () => props.showHeader()
	}, [])

	return (
		<div>
			<div className="checkout-header-container px-5 py-2">Profile Information</div>
			<div className="profile-info-container">
				<div className="row">
					<div className="col">
						<VendorProfileInfo/>
					</div>
					<div className="col">
						<UpdatePassword/>
					</div>
				</div>
			</div>
		</div>
	)

}

export default connect(null, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(VendorProfile)
