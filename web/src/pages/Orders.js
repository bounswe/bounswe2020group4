import React, { useState } from 'react'

import OrderDetails from '../components/OrderDetails'

import Tabs from '@material-ui/core/Tabs'
import Tab from '@material-ui/core/Tab'
import Accordion from '@material-ui/core/Accordion'
import { makeStyles } from '@material-ui/core/styles'

import AccordionDetails from '@material-ui/core/AccordionDetails'
import AccordionSummary from '@material-ui/core/AccordionSummary'
import ExpandMoreIcon from '@material-ui/icons/ExpandMore'

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


const Orders = () => {
	const [value, setValue] = useState(0)
	const [expanded, setExpanded] = useState(false)

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

export default Orders