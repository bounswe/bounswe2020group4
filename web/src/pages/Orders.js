import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'


import OrderDetails from '../components/OrderDetails'

import Accordion from '@material-ui/core/Accordion'
import AccordionDetails from '@material-ui/core/AccordionDetails'
import AccordionSummary from '@material-ui/core/AccordionSummary'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'

import history from '../util/history'
import orderService from '../services/orders'

import './Orders.css'


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


const Orders = ({isLoggedIn, userId}) => {
	if(!isLoggedIn) {
		history.push('/signin')
		return
	}

	const [expanded, setExpanded] = useState(false)
	const [orders, setOrders] = useState([])

	useEffect(async () => {
		const orders = await orderService.getOrders(userId, 'customer')
		const ordersList = []
		let key
		for(key of Object.keys(orders.orders)) {
			ordersList.push({id: key, data: orders.orders[key]})
		}
		setOrders(ordersList)
	}, [])

	const handleExpand = (panel) => (event, isExpanded) => {
		setExpanded(isExpanded ? panel : false)
	}

	return(
		<div className="orders-container">
			<div className='title px-5 py-3' >Orders</div>
			<TabPanel value={0} index={0}>
				{orders && orders.map(o => (
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
							<OrderDetails orderId={o.id} products={o.data.products} address={o.data.address}/>
						</AccordionDetails>
					</Accordion>
				))}
			</TabPanel>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		userId: state.signIn.userId
	}
}


export default connect(mapStateToProps)(Orders)