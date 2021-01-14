import React from 'react'
import { Link } from 'react-router-dom'

const Header = () => {
    return (
        <div className='container-fluid d-flex justify-content-center p-3 fs-4 border border-3'>
            <Link to='reported_products'>
                <div className='py-2 px-3 border rounded mx-1'>
                    Reported Products
                </div>
            </Link>
            <Link to='reported_comments'>
                <div className='py-2 px-3 border rounded mx-1'>
                    Reported Comments
                </div>
            </Link>
        </div>
    )
}

export default Header