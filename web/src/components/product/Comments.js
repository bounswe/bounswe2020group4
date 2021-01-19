import React from 'react'

import './Comments.css'
import RatingStar from '../RatingStar'

const Comment = ({user, comment, rating}) => {
	return(
		<div className='comment-container'>
			<div className='comment-header'>
				<div>{'User: ' + user[0] + '*****'}</div>
				<RatingStar rating={rating} showLabel={false}/>
			</div>
			<div className='comment-text-container'>
				<div className='comment-text'>{comment}</div>
				<button className='report-comment-button'>Report Comment</button>
			</div>
		</div>
	)
}

const Comments = ({ comments }) => {
	return(
		<div className='comments-container'>
			<h2>Comments</h2>
			{comments.length !== 0 ?
				comments.map(c => <Comment key={c.id} comment={c.text} user={c.owner.email} rating={c.rating}/>) :
				<p>No comments have been made for this product!</p>}
		</div>
	)
}

export default Comments