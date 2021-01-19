import React from 'react'

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

const Messages = () => {
	const classes = useStyles()
	const vendors = ['Koton', 'Ayşe Teyze', 'Apple']

	return(
		<div>
			<div className='list-title px-5 py-3' >Messages</div>
			<div className='messages-container'>
				<Grid container component={Paper} className={classes.chatSection}>
					<Grid item xs={3} className={classes.borderRight500}>
						<List>
							{vendors.map(v =>
								<ListItem button key={v}>
									<ListItemText primary={v}>{v}</ListItemText>
								</ListItem>)}
						</List>
					</Grid>
					<Grid item xs={9}>
						<List className={classes.messageArea}>
							<ListItem key="1">
								<Grid container>
									<Grid item xs={12}>
										<ListItemText align="right" primary="Merhaba ürünümü nasıl iade edebilirim?"></ListItemText>
									</Grid>
									<Grid item xs={12}>
										<ListItemText align="right" secondary="09:30"></ListItemText>
									</Grid>
								</Grid>
							</ListItem>
							<ListItem key="2">
								<Grid container>
									<Grid item xs={12}>
										<ListItemText align="left" primary="Edemezsiniz."></ListItemText>
									</Grid>
									<Grid item xs={12}>
										<ListItemText align="left" secondary="09:31"></ListItemText>
									</Grid>
								</Grid>
							</ListItem>
							<ListItem key="3">
								<Grid container>
									<Grid item xs={12}>
										<ListItemText align="right" primary="Peki :("></ListItemText>
									</Grid>
									<Grid item xs={12}>
										<ListItemText align="right" secondary="10:30"></ListItemText>
									</Grid>
								</Grid>
							</ListItem>
						</List>
						<Divider />
						<Grid container style={{padding: '20px'}}>
							<Grid item xs={11}>
								<TextField id="outlined-basic-email" label="Type Something" fullWidth />
							</Grid>
							<Grid xs={1} align="right">
								<Fab color="primary" aria-label="add"><SendIcon /></Fab>
							</Grid>
						</Grid>
					</Grid>
				</Grid>
			</div>
		</div>
	)
}


export default Messages