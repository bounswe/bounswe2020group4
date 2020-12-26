import React from 'react'
import CrossIcon from '../../images/cross-icon.png'

import './AddNewAddressPopup.css'

const AddNewAddressPopup = ({ setIsAddressPopupOpen, defaultInfo }) => {
	return (
		<div className='add-new-address-popup'>
			<div className='add-new-address-popup-inner-container p-4 rounded'>
				<div className='d-flex justify-content-between'>
					<div>Add New Address</div>
					<div className='close-icon-container' onClick={() => setIsAddressPopupOpen(false)}>
						<img src={CrossIcon} alt='close' className='img-fluid'/>
					</div>
				</div>
				<hr/>
				<form>
					<div className='form-group px-2'>
						<label htmlFor='title'>Address Title</label>
						<input typ='text' className='form-control' id='title' defaultValue={defaultInfo?.title}/>
					</div>
					<div className='d-flex'>
						<div className='form-group px-2'>
							<label htmlFor='name'>Name</label>
							<input type='text' className='form-control' id='name' defaultValue={defaultInfo?.receivingName}/>
						</div>
						<div className='form-group px-2'>
							<label htmlFor='surname'>Surname</label>
							<input type='text' className='form-control' id='surname' defaultValue={defaultInfo?.receivingSurname}/>
						</div>
					</div>
					<div className='d-flex'>
						<div className='form-group px-2'>
							<label htmlFor='phone'>Phone</label>
							<input type='text' className='form-control' id='phone' defaultValue={defaultInfo?.receivingPhone}/>
						</div>
						<div className='form-group px-2'>
							<label htmlFor='city'>City</label>
							<input type='text' className='form-control' id='city' defaultValue={defaultInfo?.city}/>
						</div>
					</div>
					<div className='d-flex'>
						<div className='form-group px-2'>
							<label htmlFor='name'>Province</label>
							<input type='text' className='form-control' id='province' defaultValue={defaultInfo?.province}/>
						</div>
						<div className='form-group px-2'>
							<label htmlFor='street'>Street</label>
							<input type='text' className='form-control' id='street' defaultValue={defaultInfo?.street}/>
						</div>
					</div>
					<div className='form-group px-2'>
						<label htmlFor='address'>Address</label>
						<textarea className='form-control' id='address' defaultValue={defaultInfo?.address}/>
					</div>
					<hr/>
					<div className='text-center'>
						<button type='submit' className='btn btn-danger'>Submit</button>
					</div>
				</form>
			</div>
		</div>
	)
}

export default AddNewAddressPopup