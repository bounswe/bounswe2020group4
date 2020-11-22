import React, {useState} from 'react'

import Icon from '../images/buyo-icon.png'
import ProfileIcon from '../images/profile-icon.png'
import CartIcon from '../images/cart-icon.png'
import WishlistIcon from '../images/wishlist-icon.png'
import SearchIcon from '../images/search-icon.png'

//Styling
import './Header.css'

const sampleCategories = [
    {
        "name": "Bebek",
        "subcategories": [
            {
                "name": "Erkek Bebek",
                "subcategories": [
                    {
                        "name": "Tulum",
                        "subcategories": [],
                        "path": "Bebek,Erkek Bebek,Tulum"
                    }
                ],
                "path": "Bebek,Erkek Bebek"
            }
        ],
        "path": "Bebek"
    },
    {
        "name": "Erkek",
        "subcategories": [
            {
                "name": "Pijama",
                "subcategories": [],
                "path": "Erkek,Pijama"
            }
        ],
        "path": "Erkek"
    },
    {
        "name": "Kadin",
        "subcategories": [
            {
                "name": "Corap",
                "subcategories": [],
                "path": "Kadin,Corap"
            }
        ],
        "path": "Kadin"
    }
]

const Header = () => {
    const [selectedPath, setSelectedPath] = useState("");

    const renderCategories = (categories) => {
        return categories.map((category) => {
            const thisPathSelected = selectedPath == category.path
            const handleClick = () => {
                if(selectedPath == category.path)
                    setSelectedPath("")
                else 
                    setSelectedPath(category.path)
            }

            return (
                <div className='col'>
                    <div className='category text-center' onClick={handleClick}>{category.name}</div>
                    <div className='position-absolute container-fluid list-group mt-2' style={thisPathSelected ? {} : {maxHeight: '0', overflow: 'hidden'}}>{renderSubcategories(category.subcategories)}</div>
                </div>
            )
        })
    }

    const renderSubcategories = (subcategories) => {
        return subcategories.map((subc) => {
            return <div className='list-group-item text-center'>{subc.name}</div>
        })
    }

    return (
        <div>
            <div className='header-top-container'>
                <div className='header-icon-container'>
                    <img className='header-icon vertical-align-middle' src={Icon} alt='site icon'/>
                </div>
                <div className='searchbar-container'>
                    <input className='searchbar vertical-align-middle' type='text' placeholder='Search'/>
                    <img className='vertical-align-middle searchbar-icon' src={SearchIcon} alt='search'/>
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
            <div className='container-fluid py-4 px-5'>
                <div className='row'>
                    {renderCategories(sampleCategories)}
                </div>
            </div>
        </div>
    );
}

export default Header;