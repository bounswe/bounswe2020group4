import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'
import AddNewAddressPopup from './AddNewAddressPopup'
import AccountService from '../../services/account'
import PlusIcon from '../../images/white-plus-icon.png'
import EditIcon from '../../images/edit-icon.png'
import TrashIcon from '../../images/trash-icon.png'

import './AddressBlock.css'



const AddressBlock = ({ selectAddress, selectedAddress, onCheckout, title, userType, userId }) => {
	const [isAddressPopupOpen, setIsAddressPopupOpen] = useState(false)
	const [editingAddress, setEditingAddress] = useState({})
	const [addresses, setAddresses] = useState([])

	const fetchAddresses = async () => {
		const profileAddresses = await AccountService.getProfileAddresses(userType, userId)
		setAddresses(profileAddresses)
	}

	useEffect(() => {
		fetchAddresses()
	}, [])

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

	const onDeleteClick = async (addressTitle) => {
		const responseCode = await AccountService.deleteAddress(userId, addressTitle)
		if(responseCode != 200){
			alert('Something went wrong while deleting address')
		}
		fetchAddresses()
	}

	const renderAddresses = () => {
		return addresses?.map((address) => {
			return (
				<div className='ab-address-container mx-3 mb-3' key={address.addressTitle} onClick={ onCheckout ? () => { selectAddress(address) } : null}>
					<div className='address-title-container d-flex justify-content-between'>
						<div className='d-flex'>
							{ onCheckout ?
								<div className='address-title-input-container pr-2'>
									<input type='radio' checked={address == selectedAddress} className='address-title-radio'/>
								</div> : null
							}
							<div className='address-title'>{address.addressTitle}</div>
						</div>
						<div className='d-flex'>
							<div className='edit-icon-container mr-2 cursor-pointer' onClick={() => onEditClick(address)}>
								<img className='img-fluid' src={EditIcon} alt='Edit'/>
							</div>
							<div className='trash-icon-container cursor-pointer' onClick={() => onDeleteClick(address.addressTitle)}>
								<img className='img-fluid' src={TrashIcon} alt='Delete'/>
							</div>
						</div>
					</div>
					<div className='address-info-container p-3 my-2' style={onCheckout ? {cursor: 'pointer'} : null}>
						<div className='address-block-line'>{address.name}</div>
						<div className='address-block-line'>{address.phone}</div>
						<div className='address-block-line'>{address.address}</div>
						<div className='address-block-line'>{address.province}/{address.city}</div>
					</div>
				</div>
			)
		})
	}

	const onPopupFormSubmit = async (e, isEditing) => {
		e.preventDefault()
		const address = {
			addressTitle: e.target[0].value,
			name: e.target[1].value,
			surname: e.target[2].value,
			phone: e.target[3].value,
			city: e.target[4].value,
			province: e.target[5].value,
			street: e.target[6].value,
			address: e.target[7].value
		}

		if(isEditing) {
			const responseCode = await AccountService.updateAddress(userId, address)
			if(responseCode != 200) {
				alert('Something went wrong while editing address')
			}
		} else {
			const responseCode = await AccountService.addNewAddress(userId, address)
			if(responseCode != 200) {
				alert('Something went wrong while adding new address')
			}
		}
		setIsAddressPopupOpen(false)
		fetchAddresses()
	}

	return (
		<div className='address-block p-4 border rounded-sm'>
			{isAddressPopupOpen ?
				<div className='add-new-address-popup-container'>
					<AddNewAddressPopup setIsAddressPopupOpen={setIsAddressPopupOpen} defaultInfo={editingAddress} onPopupFormSubmit={onPopupFormSubmit}/>
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
