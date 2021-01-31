import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'

import './VendorOrders.css'

import VendorOrderDetails from '../components/VendorOrderDetails'
import orderService from '../services/orders'
import history from '../util/history'

import Accordion from '@material-ui/core/Accordion'
import AccordionDetails from '@material-ui/core/AccordionDetails'
import AccordionSummary from '@material-ui/core/AccordionSummary'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'

const TabPanel = (props) => {
	const { children, value, index, ...other } = props
	return (
		<div
			role="tabpanel"
			hidden={value !== index}
			id={`tabpanel-${index}`}
			aria-labelledby={`tab-${index}`}
			{...other}
		>
			{value === index && (
				<div className="tabpanel-content">
					{children}
				</div>
			)}
		</div>
	)
}

const VendorOrders = ({showHeader, hideHeader, showVendorHeader, isLoggedIn, userId, userType}) => {
	if(!isLoggedIn | userType != 'vendor') {
		history.push('/vendorsignin')
		return
	}

	const [expanded, setExpanded] = useState(false)
	const [orders, setOrders] = useState([])
	const [totalEarnings, setTotalEarnings] = useState(0)

	useEffect(async () => {
		const ordersObj = await orderService.getOrders(userId, 'vendor')
		/*const ordersList = []
		let key
		let sumOfDeliveredPrices = 0
		if (orders) {
			for(key of Object.keys(orders)) {
				ordersList.push({id: key, data: orders[key]})
				if (orders[key].products) {
					orders[key].products.forEach(product => {
						if (product.status == 'Delivered') sumOfDeliveredPrices += product.price;
					});
				}
			}
			setOrders(ordersList)
			setTotalEarnings(sumOfDeliveredPrices)
		}
		*/
		if (ordersObj?.orders) {
			const ordersList = []
			for (const key of Object.keys(ordersObj.orders)) {
				ordersList.push({id: key, data: ordersObj.orders[key]})
			}
			setOrders(ordersList)
		}
		if (ordersObj?.totalEarnings) setTotalEarnings(ordersObj.totalEarnings)

		hideHeader()
		showVendorHeader()
		return () => showHeader()
	}, [])

	const handleExpand = (panel) => (event, isExpanded) => {
		setExpanded(isExpanded ? panel : false)
	}

	return(
		<div className="orders-container">
			<div className='title px-5 py-3' >Orders</div>
			<TabPanel value={0} index={0}>
				{orders?.length ? orders.map(o => (
					<Accordion key={o.id} expanded={expanded === o.id} onChange={handleExpand(o.id)}>
						<AccordionSummary
							expandIcon={<ExpandMoreIcon />}
							aria-controls={o.id + 'bh-content'}
							id={o.id + 'bh-header'}
						>
							<div className="order-heading">
								<div>Order No: {o.id}</div>
								<div>Date: {new Date(Date.parse(o.data.date)).toLocaleString()}</div>
								<div>Shipping: {o.data.shippingPrice + '₺'}</div>
							</div>
						</AccordionSummary>
						<AccordionDetails>
							<VendorOrderDetails orderId={o.id} products={o.data.products} address={o.data.address}/>
						</AccordionDetails>
					</Accordion>
				)) : <div className='no-orders'>There is nothing here.</div>}
				<div className='total-earnings'>Total Earnings: {totalEarnings.toFixed(2)}₺</div>
			</TabPanel>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		userId: state.signIn.userId,
		userType: state.signIn.userType,
	}
}

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(VendorOrders)
