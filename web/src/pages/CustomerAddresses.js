import React from 'react'
import AddressBlock from '../components/checkout/AddressBlock'

const CustomerAddresses = () => {
	return (
		<div>
			<div className='container-fluid page-header px-5 py-2' style={{
				backgroundColor: 'var(--beige)',
				fontSize: '24px',
				color: 'var(--red-secondary)',
				fontWeight: 'bold',
			}}>My Addresses</div>
			<AddressBlock/>
		</div>
	)
}

export default CustomerAddresses