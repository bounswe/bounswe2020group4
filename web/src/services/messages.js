import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/messages'

const getMessages = async (id, userType, withId, withType) => {
	const response = await axios.get(`${baseUrl}?id=${id}&userType=${userType}&withId=${withId}&withType=${withType}`)
	return response.data.data.messages
}

const getLastMessages = async (id, userType) => {
	const response = await axios.get(`${baseUrl}/last?id=${id}&userType=${userType}`)
	return response.data.data.lastMessages
}

export default { getMessages, getLastMessages }