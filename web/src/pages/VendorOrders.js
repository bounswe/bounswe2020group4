import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'

import './VendorOrders.css'

import OrderDetails from '../components/OrderDetails'
import orderService from '../services/orders'

import Tabs from '@material-ui/core/Tabs'
import Tab from '@material-ui/core/Tab'
import Accordion from '@material-ui/core/Accordion'
import { makeStyles } from '@material-ui/core/styles'
import AccordionDetails from '@material-ui/core/AccordionDetails'
import AccordionSummary from '@material-ui/core/AccordionSummary'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'

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

const VendorOrders = ({showHeader, hideHeader, showVendorHeader, isLoggedIn, userId}) => {
	const [value, setValue] = useState(0)
	const [expanded, setExpanded] = useState(false)
	const [orders, setOrders] = useState([])
	const [totalEarnings, setTotalEarnings] = useState(0)

	useEffect(async () => {
		const orders = await orderService.getOrders(userId, 'vendor')
		let ordersList = []
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

		hideHeader()
		showVendorHeader()
		return () => showHeader()
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
				<Tab label="Orders" id="tab-1"/>
			</Tabs>
			<TabPanel value={value} index={0}>
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
							{/* <OrderDetails products={o.data.products} address={o.data.address}/> */}
							<OrderDetails orderId={o.id} products={o.data.products} address="Etiler Mahallesi Muharipler sokak Sakarya Apartman no:4 Beşiktaş/Istanbul"/>
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
		userId: state.signIn.userId
	}
}

export default connect(mapStateToProps, {showHeader, hideHeader, showVendorHeader, hideVendorHeader})(VendorOrders)
