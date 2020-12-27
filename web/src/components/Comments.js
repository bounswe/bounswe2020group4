import React from 'react'

import './Comments.css'

const Comment = ({user, comment}) => {
	return(
		<div className='comment-container'>
			<div>{'User: ' + user[0] + '*****'}</div>
			<p>{comment}</p>
		</div>
	)
}

const Comments = ({ comments }) => {
	return(
		<div className='comments-container'>
			<h2>Comments</h2>
			{comments.length !== 0 ?
				comments.map(c => <Comment key={c.id} comment={c.text} user={c.owner.username}/>) :
				<p>No comments have been made for this product!</p>}
		</div>
	)
}

export default Comments