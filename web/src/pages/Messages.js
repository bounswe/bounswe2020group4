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

import { hideHeader, showHeader, showVendorHeader, hideVendorHeader } from '../redux/actions'
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

const Messages = ({isLoggedIn, userId, userType, hideHeader, showHeader, hideVendorHeader, showVendorHeader}) => {
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
		if (userType === 'vendor') {
			hideHeader()
			showVendorHeader()
		} else {
			showHeader()
			hideVendorHeader()
		}

		socketRef.current = io.connect(socketUrl)

		socketRef.current.emit('discover', {id: userId, userType: userType})

		socketRef.current.on('message', function (data) {
			setDisplayedMessages((displayedMessages) => [...displayedMessages, {...data}])
		})

		const messages = await messageService.getLastMessages(userId, userType)
		setLastMessages(messages)

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
		if(displayedUserId === null) {
			return
		}

		socketRef.current.emit('message',
			{id: userId, userType: userType, withId: displayedUserId,
				withType: displayedUserType, message: message}, (response) => {
				setDisplayedMessages([...displayedMessages, {
					...response.payload,
					user: {
						id: userId,
						userType: userType
					}
				}])
			})
		setMessage('')
	}

	const handleMessageChange = (event) => {
		setMessage(event.target.value)
	}

	const handlePressEnter = (event) => {
		if(event.key === 'Enter') {
			handleSendMessage()
		}
	}

	return(
		<div>
			<div className='list-title px-5 py-3' >Messages</div>
			<div className='messages-container'>
				<Grid container component={Paper} className={classes.chatSection}>
					<Grid item xs={3} className={classes.borderRight500}>
						<List>
							{lastMessages.length === 0 ?
								'You have not received or sent any messages yet!' :
								lastMessages.map(v =>
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
								<TextField id="outlined-basic-email" onChange={handleMessageChange} onKeyPress={handlePressEnter} value={message} placeholder="Type Something" fullWidth />
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

export default connect(mapStateToProps, { showHeader, hideHeader, showVendorHeader, hideVendorHeader })(Messages)