import axios from 'axios'
import { data } from 'jquery'

const baseUrl = 'http://3.138.113.101:8080/'

const getProfileInfo = async (userType, id) => {
	let response
	try{
		response = await axios.post(`${baseUrl}account?id=${id}userType=${userType}`)
	} catch(err){
		console.log(err)
		return null
	}

	if (userType == 'customer'){
		if (response.data.status.code == 200){
			return response.data.data.result
		}
		//TODO: error handling
	}
}

const login = async (loginInput) => {

	const { email, password } = loginInput
	let response
	try{
		response = await axios.post(`${baseUrl}login?userType=customer&email=${email}&password=${password}`)
	} catch (err) {
		return null
	}
	if (response.data.status.code == 200) {
		return response.data.data.userId
	}

	return null
}

const signUp = async (signUpInput) => {

	const { email, password } = signUpInput

	const response = await axios.post(`${baseUrl}signup?userType=customer&email=${email}&password=${password}`)
	if (response.data.status.code === 200) {
		if(typeof response.data.data.userId != 'undefined'){
			return response.data.data.userId
		} else {
			return -1
		}

	}

	return null

}

export default { login, signUp, getProfileInfo }