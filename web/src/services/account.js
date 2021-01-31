import axios from 'axios'

const baseUrl = 'http://3.141.25.245:8080/'

const googleSignIn = async (email, name, id_token) => {
	try {
		const response = await axios.post(`${baseUrl}google-signin?email=${email}&name=${name}&token=${id_token}`)
		console.log("Google response: ", response)
		return response.data.data
	} catch (err) {
		console.error(err)
		return null
	}
}

const updateAddress = async (userId, address) => {
	try{
		const response = await axios.patch(`${baseUrl}account/address?id=${userId}&address={"addressTitle":"${address.addressTitle}","name":"${address.name}","surname":"${address.surname}","phone":"${address.phone}","city": "${address.city}","province":"${address.province}","street":"${address.street}","address":"${address.address}"}`)
		return response.status
	} catch(err) {
		console.error(err)
		return null
	}
}

const addNewAddress = async (userId, address) => {
	try{
		const response = await axios.post(`${baseUrl}account/address?id=${userId}&address={"addressTitle":"${address.addressTitle}","name":"${address.name}","surname":"${address.surname}","phone":"${address.phone}","city": "${address.city}","province":"${address.province}","street":"${address.street}","address":"${address.address}"}`)
		return response.status
	} catch(err) {
		console.error(err)
		return null
	}
}

const deleteAddress = async (userId, addressTitle) => {
	try{
		const response = await axios.delete(`${baseUrl}account/address?id=${userId}&address={"addressTitle":"${addressTitle}"}`)
		return response.status
	} catch(err) {
		console.error(err)
		return null
	}
}

const getProfileAddresses = async (userType, id) => {
	let response
	try{
		response = await axios.get(`${baseUrl}account?id=${id}&userType=${userType}`)
		return response.data.data.result.address
	} catch(err){
		console.log(err)
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

	let info = {}
	if (userType == 'customer'){
		if (response.data.status.code == 200){
			const fields = response.data.data.result
			info.email = fields?.email

			if(typeof fields?.phoneNumber !== 'undefined' & fields?.phoneNumber !== 'undefined')
				info.phone = fields.phoneNumber

			if(typeof fields?.name !== 'undefined'  ){
				if(fields?.name.split(' ')[0] !== 'undefined' | fields?.name.split(' ')[0] !== 'undefined')
					info.firstName = fields.name.substring(0, fields.name.lastIndexOf(' '))
				if(fields?.name.split(' ')[1] !== 'undefined' | fields?.name.split(' ')[1] !== 'undefined')
					info.lastName = fields.name.split(' ')[fields.name.split(' ').length - 1]
			}
			
			if(typeof fields?.gender != 'undefined' | fields?.gender !== 'undefined' )
				info.gender = fields.gender

			return info
		} else {
			return null
		}

	} else {
		if (response.data.status.code == 200){
			const fields = response.data.data.result
			info.email = fields?.email

			if(typeof fields?.name !== 'undefined'){
				if(fields?.name.split(' ')[0] !== 'undefined' | fields?.name.split(' ')[0] !== 'undefined')
					info.firstName = fields.name.substring(0, fields.name.lastIndexOf(' '))
				if(fields?.name.split(' ')[1] !== 'undefined' | fields?.name.split(' ')[1] !== 'undefined')
					info.lastName = fields.name.split(' ')[fields.name.split(' ').length - 1]
			}
			
			info.companyName = fields?.company
			info.website = fields?.website
			info.email = fields?.email
			
			if (!!fields?.longitude && !!fields?.latitude) {
				info.coords = {lng: fields?.longitude, lat: fields?.latitude}
			}
			
			return info
		} else {
			return null
		}
	}
}

const updateProfileInfo = async (userType, id, profileInfo) => {
	let response
	try {
		if (userType == 'customer') {
			const {firstName, lastName, email, phone, gender} = profileInfo
			response = await axios.post(`${baseUrl}account?id=${id}&userType=${userType}&email=${email}&name=${firstName}&surname=${lastName}&phoneNumber=${phone}&gender=${gender}`)
		} else {
			const {firstName, lastName, company, website, coords} = profileInfo
			response = await axios.post(`${baseUrl}account?id=${id}&userType=${userType}&name=${firstName} ${lastName}&company=${company}&website=${website}&longitude=${coords.lng}&latitude=${coords.lat}`)
		}
	} catch (err) {
		console.log(err)
		return null
	}
	return response.data.status.code
}

const updatePassword = async (userType, id, passwordInfo) => {

	const {oldPassword, newPassword} = passwordInfo
	let response
	//TODO: Add oldPassword to backend call when ready
	try{
		response = await axios.post(`${baseUrl}account-change-password?id=${id}&userType=${userType}&password=${newPassword}`)
	} catch(err){
		console.log(err)
		return null
	}
	
	return response.data.status.code
}

const forgotPassword = async (email) => {
	let response
	try {
		response = await axios.post(`${baseUrl}account/forgotPassword?email=${email}`)
	} catch (err) {
		return false
	}
	return response.data.status.code == 200
}

const verify = async (userType, userId) => {
	let response
	try{
		response = await axios.get(`${baseUrl}account/verify?userType=${userType}&id=${userId}`)
	} catch(err){
		return false
	}
	return response.data.status.code == 200
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
		return response.data.data
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
		return response.data.data
	}

	return null
}

const vendorSignUp = async (signUpInput) => {

	const {name, email, password, lng, lat, website, company} = signUpInput
	let response
	try{
		response = await axios.post(`${baseUrl}signup?userType=vendor&email=${email}&password=${password}&longitude=${lng}&latitude=${lat}&website=${website}&company=${company}&name=${name}`)
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

export default { forgotPassword, verify, login, signUp, getProfileInfo, updateProfileInfo, updatePassword, vendorLogin, vendorSignUp, getProfileAddresses, addNewAddress, updateAddress, deleteAddress, googleSignIn }

