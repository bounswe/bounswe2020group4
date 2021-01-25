import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import AddNewAddressPopup from './AddNewAddressPopup'
import Account from '../../services/account'
import PlusIcon from '../../images/white-plus-icon.png'
import EditIcon from '../../images/edit-icon.png'

import './AddressBlock.css'



const AddressBlock = ({ selectAddress, selectedAddress, onCheckout, title, userType, userId }) => {
	const [isAddressPopupOpen, setIsAddressPopupOpen] = useState(false)
	const [editingAddress, setEditingAddress] = useState({})
	const [addresses, setAddresses] = useState([
		{
			title: 'Home',
			receivingName: 'Battal',
			receivingSurname: 'Lattab',
			receivingPhone: '0531 932 8388',
			address: 'Etiler Mah, Ucaksavarn Sit',
			province: 'Besiktas',
			city: 'Istanbul',
			street: 'Ihsan Hilmi Alantar sokak'
		},
		{
			title: 'Work',
			receivingName: 'Hakim',
			receivingSurname: 'Gazi',
			receivingPhone: '0532 323 4589',
			address: 'Konaklar Mahallesi Ihsan Hilmi Alantar Sokak',
			province: 'Besiktas',
			city: 'Istanbul',
			street: 'Ihsan Hilmi Alantar sokak'
		}
	])

	useEffect(() => {
		//Clear the editing address when the popup closes
		//So that we won't receive the same address when we try to add a new one
		if(!isAddressPopupOpen){
			setEditingAddress({})
		}
	}, [isAddressPopupOpen])

	const onEditClick = async (address) => {
		await setEditingAddress(address)
		setIsAddressPopupOpen(true)
	}

	const renderAddresses = () => {
		return addresses.map((address) => {
			return (
				<div className='ab-address-container mx-3 mb-3' key={address.title} onClick={ onCheckout ? () => { selectAddress(address) } : null}>
					<div className='address-title-container d-flex justify-content-between'>
						<div className='d-flex'>
							{ onCheckout ? 
								<div className='address-title-input-container pr-2'>
									<input type='radio' checked={address == selectedAddress} className='address-title-radio'/>
								</div> : null
							}
							<div className='address-title'>{address.title}</div>
						</div>
						<div className='edit-icon-container' onClick={() => onEditClick(address)}>
							<img className='float-end img-fluid' src={EditIcon} alt='Edit'/>
						</div>
					</div>
					<div className='address-info-container p-3 my-2' style={onCheckout ? {cursor: 'pointer'} : null}>
						<div>{address.receivingName}</div>
						<div>{address.receivingPhone}</div>
						<div>{address.address}</div>
						<div>{address.province}/{address.city}</div>
					</div>
				</div>
			)
		})
	}

	return (
		<div className='address-block p-4 border rounded-sm'>
			{isAddressPopupOpen ?
				<div className='add-new-address-popup-container'>
					<AddNewAddressPopup setIsAddressPopupOpen={setIsAddressPopupOpen} defaultInfo={editingAddress}/>
				</div> : null
			}
			<h3 className='m-2 mb-4'>{title}</h3>
			<div className={onCheckout ? 'd-flex overflow-auto' : 'd-flex flex-wrap'}>
				<div className='add-new-address p-2 my-4 mx-2 d-flex flex-column justify-content-around' onClick={() => { setIsAddressPopupOpen(true) }}>
					<div className='add-new-address-img-container text-center'>
						<img className='add-new-address-img d-inline-block' src={PlusIcon} alt='add new address' />
					</div>
					<div className='text-center'>Add New Address</div>
				</div>
				{renderAddresses()}
			</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return { userType: state.signIn.userType, userId: state.signIn.userId }
}

export default connect(mapStateToProps)(AddressBlock)
