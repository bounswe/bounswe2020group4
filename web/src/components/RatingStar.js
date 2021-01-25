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

const RatingStar = ({ rating, readOnly=true, precision=0.1, onChange=null, showLabel=true }) => {
	return(
		<div className='rating-container'>
			<StyledRating className='rating-star'
				value={parseFloat(Number(rating).toFixed(1))}
				max={5}
				precision={precision}
				readOnly={readOnly}
				onChange={onChange}
				name="rating"/>
			<div className='rating-label'>
				{showLabel && parseFloat(Number(rating).toFixed(1))}
			</div>
		</div>
	)
}

export default RatingStar