import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
import history from '../util/history'

import ProfileInfo from '../components/customer-profile/ProfileInfo'

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

                    </div>
                </div>
                
            </div>
        </div>
    )

}

export default CustomerProfile