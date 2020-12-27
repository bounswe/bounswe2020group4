import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/' 

const updateAddress = async (userId, address) => {
	const params = {
		id: userId, 
		address: address
	}
	try{
		const response = await axios.patch(`${baseUrl}account/address`, params)
		return response.status.code
	} catch(err) {
		console.error(err)
		return null
	}
}

const addNewAddress = async (userId, address) => {
	const params = {
		id: userId, 
		address: address
	}
	try{
		const response = await axios.post(`${baseUrl}account/address`, params)
		return response.status.code
	} catch(err) {
		console.error(err)
		return null
	}
}

const getProfileInfo = async (userType, id) => {
	let response
	try{
		response = await axios.get(`${baseUrl}account?id=${id}&userType=${userType}`)
	} catch(err){
		console.log(err)
		return null
	}

	let info = {
		'email': '',
		'phone': '',
		'firstName': '',
		'lastName': '',
		'gender': 'choose'
	}

	if (userType == 'customer'){
		if (response.data.status.code == 200){
			//TODO: add more field when ready
			info.email = response.data.data.result.email
			return info
		} else {
			return null
		}
		//TODO: error handling
	} else {
		if (response.data.status.code == 200){
			return response.data.data.result
		} else {
			return null
		}
	}
}

const updateProfileInfo = async (userType, id, profileInfo) => {

	const {firstName, lastName, email, phone, gender} = profileInfo
	let response
	//TODO: Add backend call when it's ready
	/* 
	try{
		response = await axios.post(`${baseUrl}account?id=${id}userType=${userType}`)
	} catch(err){
		console.log(err)
		return null
	}
	*/
	if (userType == 'customer'){
		return null
	} else {
		return null
	}
}

const updatePassword = async (userType, id, passwordInfo) => {

	const {oldPassword, newPassword} = passwordInfo
	let response
	//TODO: Add backend call when it's ready
	/* 
	try{
		response = await axios.post(`${baseUrl}account?id=${id}userType=${userType}`)
	} catch(err){
		console.log(err)
		return null
	}
	*/
	if (userType == 'customer'){
		return null
	} else {
		return null
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

const vendorLogin = async (loginInput) => {

	const { email, password } = loginInput
	let response
	try{
		response = await axios.post(`${baseUrl}login?userType=vendor&email=${email}&password=${password}`)
	} catch (err) {
		return null
	}
	if (response.data.status.code == 200) {
		return response.data.data.userId
	}

	return null
}

const vendorSignUp = async (signUpInput) => {

	const {name, email, password, lng, lat, website, company} = signUpInput
	let response
	try{
		//TODO: add name when endpoint is changed
		response = await axios.post(`${baseUrl}signup?userType=vendor&email=${email}&password=${password}&longitude=${lng}&latitude=${lat}&website=${website}&company=${company}`)
	} catch {
		return null
	}
	if (response.data.status.code == 200){
		if(typeof response.data.data.userId != 'undefined'){
			return response.data.data.userId
		} else {
			return -1
		}
	} else {
		return null
	}

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

export default { login, signUp, getProfileInfo, updateProfileInfo, updatePassword, vendorLogin, vendorSignUp, addNewAddress, updateAddress }