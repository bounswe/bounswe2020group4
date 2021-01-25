import axios from 'axios'
import { io } from 'socket.io-client'

const baseUrl = 'http://3.138.113.101:8080/messages'
const socketUrl = 'http://3.138.113.101:5003'

const getMessages = async (id, userType, withId, withType) => {
	const response = await axios.get(`${baseUrl}?id=${id}&userType=${userType}&withId=${withId}&withType=${withType}`)
	return response.data.data.messages
}

const getLastMessages = async (id, userType) => {
	const response = await axios.get(`${baseUrl}/last?id=${id}&userType=${userType}`)
	return response.data.data.lastMessages
}

const sendSingleMessage = (userId, userType, withId, withType, message) => {
	const socket = io.connect(socketUrl)
	socket.emit('discover', {id: userId, userType: userType}, (response) => {
		socket.emit('message', {id: userId, userType: userType, withId: withId,
			withType: withType, message: message}, (res) => {
			socket.disconnect()
		})
	})

}

export default { getMessages, getLastMessages, sendSingleMessage }