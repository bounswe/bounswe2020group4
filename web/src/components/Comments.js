import React from 'react'

import './Comments.css'

const Comment = ({user, comment}) => {
  return(
    <div className='comment-container'>
      <div>{'21.11.2020 | ' + user[0] + '*****'}</div>
      <p>{comment}</p>
    </div>
  )
}

const Comments = () => {
  return(
    <div className='comments-container'>
      <h2>Comments</h2>   
      <Comment user="Burak" comment="I bought these toys for my grandson and he loved it. It is made of high-quality material." />
      <Comment user="Ayşe" comment="The vendor was really helpful. There was an issue with shipping but it was solved pretty quickly." />
    </div>
  )
}

export default Comments