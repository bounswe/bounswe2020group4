import React from 'react'
import { Rating } from '@material-ui/lab'
import { withStyles } from '@material-ui/core/styles'
import FavoriteIcon from '@material-ui/icons/Favorite'

import wishlistService from '../services/wishlist'

const StyledRating = withStyles({
  iconFilled: {
    color: '#ff6d75',
  },
  iconHover: {
    color: '#ff3d47',
  },
})(Rating);

const WishlistButton = ({ customerId, productId, isLoggedIn }) => {

  const handleAddtoWishlist = (event, value) => {
    event.preventDefault()
    if(value === 1) {
      if(!isLoggedIn) {
        //TODO: redirect to signin page!
      }
      else {        
        wishlistService
          .addToWishlist(customerId, productId)
          .then(response => {
            if(response.status.code !== 200) {
              console.log(`error while adding ${productId} to wishlist of ${customerId}`)
            }
            else {
              console.log('added to wishlist succesfully!')
            }
          })
      }
    }
  }

  return <StyledRating
    name="customized-color"
    size="large"
    defaultValue={0}
    max={1}
    onChange={handleAddtoWishlist}
    icon={<FavoriteIcon fontSize="inherit" />}
  />
}

export default WishlistButton