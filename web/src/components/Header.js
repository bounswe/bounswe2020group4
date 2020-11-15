import React from 'react'

import Icon from '../images/buyo-icon.png'
import ProfileIcon from '../images/profile-icon.png'
import CartIcon from '../images/cart-icon.png'
import WishlistIcon from '../images/wishlist-icon.png'
import SearchIcon from '../images/search-icon.png'

//Styling
import './Header.css'

const Header = () => {
    return (
        <div className='header-container'>
            <div className='header-icon-container'>
                <img className='header-icon vertical-align-middle' src={Icon} alt='site icon'/>
            </div>
            <div className='searchbar-container'>
                <input className='searchbar vertical-align-middle' type='text' placeholder='Search'/>
                <img className='searchbar-icon vertical-align-middle' src={SearchIcon} alt='search'/>
            </div>
            <div className='header-right-container'>
                <div className='header-right-icon-container'>
                    <img className='header-right-icon vertical-align-middle' src={ProfileIcon} alt='profile'/>
                </div>
                <div className='header-right-icon-container'>
                    <img className='header-right-icon vertical-align-middle' src={WishlistIcon} alt='wishlist icon'/>
                </div>
                <div className='header-right-icon-container'>
                    <img className='header-right-icon vertical-align-middle' src={CartIcon} alt='cart icon'/>
                </div>
            </div>
        </div>
    );
}

export default Header;