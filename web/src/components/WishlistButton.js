import React, { useEffect, useState } from 'react'
import { Rating } from '@material-ui/lab'
import { withStyles } from '@material-ui/core/styles'
import FavoriteIcon from '@material-ui/icons/Favorite'

import history from '../util/history';
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
  customerId = 12341 //TODO delete this line
  const [isLiked, setIsLiked] = useState(0)
  
  useEffect(() => {
    wishlistService
      .isInWishlist(customerId, productId)
      .then( result => {
        if(result) {
          setIsLiked(1)
        }
      })
  }, [customerId, productId])

  const handleAddtoWishlist = (event, value) => {
    event.preventDefault()
    if(value === 1) {
      if(isLoggedIn) {  //TODO !isLoggedIn
        history.push('/signin')
      }
      else {     
        setIsLiked(1)   
        wishlistService
          .addToWishlist(customerId, productId)
          .then(response => {
            if(response.status !== 200) {
              console.log(`error while adding ${productId} to wishlist of ${customerId}`)
            }
            else {
              console.log('added to wishlist succesfully!')
            }
          })
      }
    }
    else {
      wishlistService
        .removeFromWishlist(customerId, productId)
        .then(response => {
          console.log(response)
          if(response.status !== 200) {
            console.log(`error while removing ${productId} from wishlist of ${customerId}`)
          }
          else {
            console.log('removed from wishlist succesfully!')
          }
        })
      setIsLiked(0)
    }
  }

  return <StyledRating
    name="customized-color"
    size="large"
    value={isLiked}
    max={1}
    onChange={handleAddtoWishlist}
    icon={<FavoriteIcon fontSize="inherit" />}
  />
}

export default WishlistButton