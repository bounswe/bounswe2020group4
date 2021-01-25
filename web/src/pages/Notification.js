import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'

import history from '../util/history'
import notificationService from '../services/notifications'

import Card from 'react-bootstrap/Card'
import './Notification.css'

const Notification = ({ showHeader, hideHeader, showVendorHeader, isLoggedIn, userId, userType }) => {
    const [notifs, setNotifs] = useState([])

    const getNotifs = async () => {
        const notifList = await notificationService.getNotifications(userType, userId)
        setNotifs(notifList)
    }

    useEffect(async () => {
        if (userType == 'vendor') {
            hideHeader()
            showVendorHeader()
        } else {
            showHeader();
            hideVendorHeader();
        }

        await getNotifs()

        return () => showHeader()
    }, [])

    const redirect = (e, notif) => {
        e.preventDefault()
        if (notif.name == 'Discount') {
            history.push(`/product/${notif.target}`);
        } else if (notif.name == 'Cancel Order') {
            history.push('/vendororders');
        }
    }

    return (
        <React.Fragment>
            <div className="checkout-header-container px-5 py-2 centered-header">Notifications</div>
            <div className="notification-container">
                {notifs?.length > 0 ? notifs.map((notif, index) => (
                    <Card key={index} style={{ width: '400px', cursor: 'pointer', 'margin-top': '0.5em', 'background-color': '#FFFAEF' }} onClick={(e) => redirect(e, notif)}>
                        <Card.Body>
                            <Card.Title>
                                {new Date(Date.parse(notif.startTime)).toLocaleString()}
                            </Card.Title>
                            <Card.Text>
                                {notif.summary}
                            </Card.Text>
                        </Card.Body>
                    </Card>
                )) : <div className='no-notifs'>You don't have any notifications!</div>}
            </div>
        </React.Fragment>
    )
}

const mapStateToProps = (state) => {
    return {
        isLoggedIn: state.signIn.isLoggedIn,
        userId: state.signIn.userId,
        userType: state.signIn.userType,
    }
}

export default connect(mapStateToProps, { showHeader, hideHeader, showVendorHeader, hideVendorHeader })(Notification)
