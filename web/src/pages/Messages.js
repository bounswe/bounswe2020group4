import React, { useState, useEffect } from 'react'
import { connect } from 'react-redux'

import { makeStyles } from '@material-ui/core/styles'
import Paper from '@material-ui/core/Paper'
import Grid from '@material-ui/core/Grid'
import Divider from '@material-ui/core/Divider'
import TextField from '@material-ui/core/TextField'
import List from '@material-ui/core/List'
import ListItem from '@material-ui/core/ListItem'
import ListItemText from '@material-ui/core/ListItemText'
import Fab from '@material-ui/core/Fab'
import SendIcon from '@material-ui/icons/Send'

import messageService from '../services/messages'

import './Messages.css'

const useStyles = makeStyles({
	table: {
		minWidth: 650,
	},
	chatSection: {
		width: '70%',
		height: '60vh'
	},
	headBG: {
		backgroundColor: '#e0e0e0'
	},
	borderRight500: {
		borderRight: '1px solid #e0e0e0'
	},
	messageArea: {
		height: '50vh',
		overflowY: 'auto'
	}
})

const Messages = ({isLoggedIn, userId}) => {
	const classes = useStyles()
	const [lastMessages, setLastMessages] = useState([])
	const [displayedMessages, setDisplayedMessages] = useState([])
	const [displayedUserId, setDisplayedUserId] = useState(null)

	useEffect(async () => {
		// TODO: make user type generic
		const messages = await messageService.getLastMessages(userId, 'customer')
		setLastMessages(messages.slice(1)) // TODO remove slice
	}, [])

	const handleListItemClick = async (e, id) => {
		setDisplayedUserId(id)
		const messages = await messageService.getMessages(userId, 'customer', id, 'customer') // TODO make user type generic
		setDisplayedMessages(messages)
	}

	return(
		<div>
			<div className='list-title px-5 py-3' >Messages</div>
			<div className='messages-container'>
				<Grid container component={Paper} className={classes.chatSection}>
					<Grid item xs={3} className={classes.borderRight500}>
						<List>
							{lastMessages.map(v =>
								<ListItem button key={v.user.id}
									selected={displayedUserId === v.user.id}
									onClick={(e) => handleListItemClick(e, v.user.id)}>
									<ListItemText primary={v.user.name}>{v.user.name}</ListItemText>
								</ListItem>)}
						</List>
					</Grid>
					<Grid item xs={9}>
						{displayedMessages.length !== 0 ?
							<List className={classes.messageArea}>
								{displayedMessages.map(m =>
									<ListItem key={m.id}>
										<Grid container>
											<Grid item xs={12}>
												<ListItemText align={m.user.id === userId ? 'right' : 'left'} primary={m.message}></ListItemText>
											</Grid>
											<Grid item xs={12}>
												<ListItemText align={m.user.id === userId ? 'right' : 'left'} secondary={m.date}></ListItemText>
											</Grid>
										</Grid>
									</ListItem>)}
							</List> :
							<List className={classes.messageArea}>
								<Grid container>
									<Grid item xs={12}>
										<ListItemText align="center" primary="Please choose from the left panel a user!"></ListItemText>
									</Grid>
								</Grid>
							</List>}
						<Divider />
						<Grid container style={{padding: '20px'}}>
							<Grid item xs={11}>
								<TextField id="outlined-basic-email" label="Type Something" fullWidth />
							</Grid>
							<Grid item xs={1} align="right">
								<Fab color="primary" aria-label="add"><SendIcon /></Fab>
							</Grid>
						</Grid>
					</Grid>
				</Grid>
			</div>
		</div>
	)
}

const mapStateToProps = (state) => {
	return {
		isLoggedIn: state.signIn.isLoggedIn,
		userId: state.signIn.userId
	}
}

export default connect(mapStateToProps)(Messages)