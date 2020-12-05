import React, { useState } from 'react'
import AddNewAddressPopup from './AddNewAddressPopup'
import PlusIcon from '../../images/white-plus-icon.png'

import './CheckoutAddressBlock.css'



const CheckoutAddressBlock = ({ addresses, selectAddress, selectedAddress }) => {
    const [isAddressPopupOpen, setIsAddressPopupOpen] = useState(false)

    const renderAddresses = () => {
        return addresses.map((address) => {
            return (
                <div className='address-container mx-3' onClick={() => {selectAddress(address)}}>
                    <div className='address-title-container d-flex'>
                        <div className='address-title-input-container pr-2'><input type='radio' checked={address == selectedAddress} className='address-title-radio'/></div>
                        <div className='address-title'>{address.title}</div>
                    </div>
                    <div className='address-info-container p-3 my-2'>
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
        <div className='address-block p-4 border'>
            {isAddressPopupOpen ? 
                <div className='add-new-address-popup-container'>
                    <AddNewAddressPopup setIsAddressPopupOpen={setIsAddressPopupOpen}/>
                </div> : null
            }
            <h3 className='m-2 mb-4'>Shipping Address</h3>
            <div className='d-flex'>
                <div className='add-new-address p-3 m-2 mx-3 d-flex flex-column justify-content-around' onClick={() => {setIsAddressPopupOpen(true)}}>
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

export default CheckoutAddressBlock
