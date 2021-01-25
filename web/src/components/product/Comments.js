import React, { useState } from 'react'

import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogContentText from '@material-ui/core/DialogContentText'
import DialogTitle from '@material-ui/core/DialogTitle'
import TextField from '@material-ui/core/TextField'
import Button from '@material-ui/core/Button'

import reportService from '../../services/report'
import './Comments.css'
import RatingStar from '../RatingStar'

const Comment = ({key, user, comment, rating}) => {
	const [open, setOpen] = useState(false)
	const [message, setMessage] = useState('')

	const handleClickOpen = () => {
		setOpen(true)
	}
	const handleClose = () => {
		setOpen(false)
		setMessage('')
	}
	const handleMessageChange = (e) => {
		setMessage(e.target.value)
	}
	const handleReport = async () => {
		await reportService.reportComment(key, message)
		setMessage('')
		setOpen(false)
	}

	return(
		<div className='comment-container'>
			<div className='comment-header'>
				<div>{'User: ' + user[0] + '*****'}</div>
				<RatingStar rating={rating} showLabel={false}/>
			</div>
			<div className='comment-text-container'>
				<div className='comment-text'>{comment}</div>
				<button className='report-comment-button' onClick={handleClickOpen}>Report Comment</button>
			</div>
			<Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
				<DialogTitle id="form-dialog-title">Report Comment</DialogTitle>
				<DialogContent>
					<DialogContentText> State the problem with this comment:
					</DialogContentText>
					<TextField
						onChange={handleMessageChange}
						margin="dense"
						id="name"
						label="Comment"
						type="text"
						fullWidth
						multiline
					/>
				</DialogContent>
				<DialogActions>
					<Button onClick={handleClose} color="primary"> Cancel
					</Button>
					<Button onClick={handleReport} color="primary"> Report comment
					</Button>
				</DialogActions>
			</Dialog>
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