import React, { useEffect, useState } from 'react'

import OrderDetails from '../components/OrderDetails'

import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import Accordion from '@material-ui/core/Accordion';
import AccordionDetails from '@material-ui/core/AccordionDetails';
import AccordionSummary from '@material-ui/core/AccordionSummary';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';


import "./Orders.css"

const TabPanel = (props) => {
  const { children, value, index, ...other } = props;

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
  );
}


const Orders = () => {
  const [value, setValue] = useState(0);
  const [expanded, setExpanded] = useState(false);


  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  const handleExpand = (panel) => (event, isExpanded) => {
    setExpanded(isExpanded ? panel : false);
  };
  
  return(
    <div className="orders-container">
      <Tabs
        value={value}
        onChange={handleChange}
        indicatorColor="primary"
        textColor="primary"
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
        Item two
      </TabPanel>
    </div>
  )
}

export default Orders;