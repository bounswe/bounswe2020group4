import React, { useState, useRef, useEffect } from 'react'
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

import { io } from 'socket.io-client'

import messageService from '../services/messages'
import history from '../util/history'

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

const socketUrl = 'http://3.138.113.101:5003'

const Messages = ({isLoggedIn, userId, userType}) => {
	if(!isLoggedIn) {
		history.push('/signin')
		return
	}

	const classes = useStyles()
	const [message, setMessage] = useState('')
	const [lastMessages, setLastMessages] = useState([])
	const [displayedMessages, setDisplayedMessages] = useState([])
	const [displayedUserId, setDisplayedUserId] = useState(null)
	const [displayedUserType, setDisplayedUserType] = useState(null)
	const socketRef = useRef()
	const scrollRef = useRef(null)

	// Scroll to the bottom when new message is sent or received
	useEffect(() => {
		if (scrollRef.current) {
			scrollRef.current.scrollIntoView({ behaviour: 'smooth' })
		}
	}, [displayedMessages])

	// Establish socket connection and get the last messages
	useEffect(async () => {

		socketRef.current = io.connect(socketUrl)

		socketRef.current.emit('discover', {id: userId, userType: userType}, (response) => {
			console.log(response)
		})

		socketRef.current.on('message', function (data) {
			setDisplayedMessages((displayedMessages) => [...displayedMessages, {...data, id: data.date}]) //TODO id should not be date
		})

		const messages = await messageService.getLastMessages(userId, userType)
		setLastMessages(messages.slice(0, 1)) // TODO remove slice

		return () => {
			socketRef.current.disconnect()
		}
	}, [])

	// When a user is clicked on the left panel, request its messages from backend
	const handleListItemClick = async (e, withId, withUserType) => {
		setDisplayedUserId(withId)
		setDisplayedUserType(withUserType)
		const messages = await messageService.getMessages(userId, userType, withId, withUserType)
		setDisplayedMessages(messages.slice().reverse())
	}

	const handleSendMessage = () => {
		socketRef.current.emit('message',
			{id: userId, userType: userType, withId: displayedUserId,
				withType: displayedUserType, message: message})
		setDisplayedMessages([...displayedMessages, {
			message: message,
			date: Date.now(),
			id: Date.now(),
			user: {
				id: userId,
				name: 'Koray', // TODO correct this
				userType: userType
			}
		}])
		setMessage('')
	}

	const handleMessageChange = (event) => {
		setMessage(event.target.value)
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
									onClick={(e) => handleListItemClick(e, v.user.id, v.user.userType)}>
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
												<ListItemText align={m.user.id === userId ? 'right' : 'left'} secondary={new Date(m.date).toLocaleString()}></ListItemText>
											</Grid>
										</Grid>
									</ListItem>)}
								<ListItem ref={scrollRef} ></ListItem>
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
								<TextField id="outlined-basic-email" onChange={handleMessageChange} value={message} placeholder="Type Something" fullWidth />
							</Grid>
							<Grid item xs={1} align="right">
								<Fab color="primary" aria-label="add" onClick={handleSendMessage} ><SendIcon /></Fab>
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
		userId: state.signIn.userId,
		userType: state.signIn.userType
	}
}

export default connect(mapStateToProps)(Messages)