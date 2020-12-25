import React from 'react'
import AddressBlock from '../components/checkout/AddressBlock'

const AddressDummyData = [
	{
		title: 'EV',
		receivingName: 'Meric',
		receivingSurname: 'Ungor',
		receivingPhone: '0531 932 8388',
		address: 'Etiler Mah, Ucaksavarn Sit',
		province: 'Besiktas',
		city: 'Istanbul'
	},
	{
		title: 'Ucaksavar',
		receivingName: 'Merko',
		receivingSurname: 'Ungor',
		receivingPhone: '0531 932 8388',
		address: 'Etiler Mah, Ucaksavarn Sit',
		province: 'Besiktas',
		city: 'Istanbul'
	},
	{
		title: 'Ucaksavar',
		receivingName: 'Meric',
		receivingSurname: 'Ungor',
		receivingPhone: '0531 932 8388',
		address: 'Etiler Mah, Ucaksavarn Sit',
		province: 'Besiktas',
		city: 'Istanbul'
	},
	{
		title: 'Ucaksavar',
		receivingName: 'Meric',
		receivingSurname: 'Ungor',
		receivingPhone: '0531 932 8388',
		address: 'Etiler Mah, Ucaksavarn Sit',
		province: 'Besiktas',
		city: 'Istanbul'
	},
	{
		title: 'Ucaksavar',
		receivingName: 'Meric',
		receivingSurname: 'Ungor',
		receivingPhone: '0531 932 8388',
		address: 'Etiler Mah, Ucaksavarn Sit',
		province: 'Besiktas',
		city: 'Istanbul'
	}
]


const CustomerAddresses = () => {
	return (
		<div>
			<div className='container-fluid page-header px-5 py-2' style={{
				backgroundColor: 'var(--beige)',
				fontSize: '24px',
				color: 'var(--red-secondary)',
				fontWeight: 'bold',
			}}>My Addresses</div>
            <AddressBlock addresses={AddressDummyData}/>
		</div>
	)
}

export default CustomerAddresses