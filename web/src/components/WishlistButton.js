import React from 'react'
import { Rating } from '@material-ui/lab'
import { withStyles } from '@material-ui/core/styles'
import FavoriteIcon from '@material-ui/icons/Favorite'

const StyledRating = withStyles({
  iconFilled: {
    color: '#ff6d75',
  },
  iconHover: {
    color: '#ff3d47',
  },
})(Rating);

const WishlistButton = () => {
  return <StyledRating
    name="customized-color"
    size="large"
    defaultValue={0}
    max={1}
    icon={<FavoriteIcon fontSize="inherit" />}
  />
}

export default WishlistButton