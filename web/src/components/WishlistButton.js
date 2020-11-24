import React, { useEffect, useState } from 'react'
import { connect } from 'react-redux'
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

const WishlistButton = (props) => {
  const [isLiked, setIsLiked] = useState(0)
  
  useEffect(() => {
    wishlistService
      .isInWishlist(props.customerId, props.productId)
      .then( result => {
        if(result) {
          setIsLiked(1)
        }
      })
  }, [props.customerId, props.productId])

  const handleAddtoWishlist = (event, value) => {
    event.preventDefault()
    if(value === 1) {
      if(!props.isLoggedIn) {
        history.push('/signin')
      }
      else {     
        wishlistService
          .addToWishlist(props.customerId, props.productId)
          .then(response => {
            if(response.status !== 200) {
              console.log(`error while adding ${props.productId} to wishlist of ${props.customerId}`)
            }
            else {
              console.log('added to wishlist succesfully!')
            }
          })
          setIsLiked(1)   
      }
    }
    else {
      wishlistService
        .removeFromWishlist(props.customerId, props.productId)
        .then(response => {
          console.log(response)
          if(response.status !== 200) {
            console.log(`error while removing ${props.productId} from wishlist of ${props.customerId}`)
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

const mapStateToProps = (state) => {
  return {
    isLoggedIn: state.signIn.isLoggedIn,
    customerId: state.signIn.userId
  }
}

export default connect(mapStateToProps)(WishlistButton)