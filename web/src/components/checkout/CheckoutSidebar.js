import React from 'react'

import './CheckoutSidebar.css'

const CheckoutSidebar = () => {
    return (
        <div>
            <div className='contract-container position-relative d-flex border p-2 rounded-sm'>
                <input type='checkbox' className='contract-checkbox'/>
                <div className='contract-text'>
                    Ön Bilgilendirme Koşulları'nı ve Mesafeli Satış Sözleşmesi'ni okudum, onaylıyorum.
                </div>
            </div>
        </div>
    )
}

export default CheckoutSidebar