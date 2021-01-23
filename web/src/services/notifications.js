import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/notifications'

const getNotifications = async (userType, userId) => {
	try {
		const response = await axios.get(`${baseUrl}?userType=${userType}&userId=${userId}`)
		return response?.data?.data?.items
	} catch (err) {
		return null
	}
	
}

export default { getNotifications }
