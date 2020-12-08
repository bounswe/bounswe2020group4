import React from 'react'
import CrossIcon from '../../images/cross-icon.png'

import './AddNewAddressPopup.css'

const AddNewAddressPopup = ({ setIsAddressPopupOpen }) => {
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
                    <div className='d-flex'>
                        <div className='form-group px-2'>
                            <label for='name'>Name</label>
                            <input type='text' className='form-control' id='name'/>
                        </div>
                        <div className='form-group px-2'>
                            <label for='surname'>Surname</label>
                            <input type='text' className='form-control' id='surname'/>
                        </div>
                    </div>
                    <div className='d-flex'>
                        <div className='form-group px-2'>
                            <label for='phone'>Phone</label>
                            <input type='text' className='form-control' id='phone'/>
                        </div>
                        <div className='form-group px-2'>
                            <label for='city'>City</label>
                            <input type='text' className='form-control' id='city'/>
                        </div>
                    </div>
                    <div className='d-flex'>
                        <div className='form-group px-2'>
                            <label for='name'>Province</label>
                            <input type='text' className='form-control' id='province'/>
                        </div>
                        <div className='form-group px-2'>
                            <label for='title'>Address Title</label>
                            <input type='text' className='form-control' id='title'/>
                        </div>
                    </div>
                    <div className='form-group'>
                        <label for='address'>Address</label>
                        <textarea className='form-control' id='address' />
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