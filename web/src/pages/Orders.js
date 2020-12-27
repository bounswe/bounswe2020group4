import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'


import OrderDetails from '../components/OrderDetails'

import Tabs from '@material-ui/core/Tabs'
import Tab from '@material-ui/core/Tab'
import Accordion from '@material-ui/core/Accordion'
import { makeStyles } from '@material-ui/core/styles'

import AccordionDetails from '@material-ui/core/AccordionDetails'
import AccordionSummary from '@material-ui/core/AccordionSummary'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'

import orderService from '../services/orders'

import './Orders.css'

const useStyles = makeStyles(() => ({
	indicator: {
		backgroundColor: 'red',
	},
}))

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
	const [value, setValue] = useState(0)
	const [expanded, setExpanded] = useState(false)
	const [orders, setOrders] = useState([])
	console.log(orders)
	useEffect(async () => {
		const orders = await orderService.getOrders(userId, 'customer')
		let ordersList = []
		let key
		for(key of orders) {
			ordersList.push({id: key, data: orders[key]})
		}
		setOrders(ordersList)
	}, [])

	const classes = useStyles()

	const handleChange = (event, newValue) => {
		setValue(newValue)
	}

	const handleExpand = (panel) => (event, isExpanded) => {
		setExpanded(isExpanded ? panel : false)
	}

	return(
		<div className="orders-container">
			<Tabs
				value={value}
				onChange={handleChange}
				indicatorColor="primary"
				classes={{indicator: classes.indicator}}
				centered >
				<Tab label="Paid" id="tab-1"/>
				<Tab label="Delivered" id="tab-2"/>
			</Tabs>
			<TabPanel value={value} index={0}>
				<Accordion expanded={expanded === 'panel1'} onChange={handleExpand('panel1')}>
					<AccordionSummary
						expandIcon={<ExpandMoreIcon />}
						aria-controls="panel1bh-content"
						id="panel1bh-header"
					>
						<div className="order-heading">
							<div>Order No: 12345667</div>
							<div>Date:14.12.2020</div>
							<div>Total: 210 tl</div>
						</div>
					</AccordionSummary>
					<AccordionDetails>
						<OrderDetails />
					</AccordionDetails>
				</Accordion>
				{orders.map(o => (
					<Accordion key={o.id} expanded={expanded === o.id} onChange={handleExpand(o.id)}>
						<AccordionSummary
							expandIcon={<ExpandMoreIcon />}
							aria-controls={o.id + 'bh-content'}
							id={o.id + 'bh-header'}
						>
							<div className="order-heading">
								<div>Order No: {o.id}</div>
								<div>Date: {o.data.date}</div>
								<div>Shipping: {o.data.shippingPrice + '₺'}</div>
							</div>
						</AccordionSummary>
						<AccordionDetails>
							<OrderDetails products={o.data.products}/>
						</AccordionDetails>
					</Accordion>
				))}
			</TabPanel>
			<TabPanel value={value} index={1}>
				<Accordion expanded={expanded === 'panel2'} onChange={handleExpand('panel2')}>
					<AccordionSummary
						expandIcon={<ExpandMoreIcon />}
						aria-controls="panel2bh-content"
						id="panel2bh-header"
					>
						<div className="order-heading">
							<div>Order No: 123667</div>
							<div>Date:10.12.2020</div>
							<div>Total: 110 tl</div>
						</div>
					</AccordionSummary>
					<AccordionDetails>
						<OrderDetails isDelivered="true" />
					</AccordionDetails>
				</Accordion>
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