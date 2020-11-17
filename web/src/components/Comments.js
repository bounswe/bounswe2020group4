import React from 'react'

import './Comments.css'

const Comment = ({user, comment}) => {
  return(
    <div className='comment-container'>
      <div>{user[0] + '*****'}</div>
      <p>{comment}</p>
    </div>
  )
}

const Comments = () => {
  return(
    <div className='comments-container'>
      <h2>Comments</h2>   
      <Comment user="Burak" comment="comment is displayed here..." />
    </div>
  )
}

export default Comments