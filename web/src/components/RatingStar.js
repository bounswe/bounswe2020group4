import React from 'react'
import { Rating } from '@material-ui/lab'
import { withStyles } from '@material-ui/core/styles'

import './RatingStar.css'

const StyledRating = withStyles({
	iconFilled: {
		color: '#ff6d75',
	},
	iconHover: {
		color: '#ff3d47',
	},
})(Rating)

const RatingStar = ({ rating, readOnly=true, precision=0.1 }) => {
	return(
		<div className='rating-container'>
			<StyledRating className='rating-star'
				defaultValue={Number(rating)}
				max={5}
				precision={precision}
				readOnly={readOnly} />
			<div className='rating-label'>
				{rating}
			</div>
		</div>
	)
}

export default RatingStar