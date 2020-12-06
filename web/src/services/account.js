import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/'

const login = async (loginInput) => {

    const { email, password } = loginInput
    var response;
    try{
        response = await axios.post(`${baseUrl}login?userType=customer&email=${email}&password=${password}`)
    } catch (err) {
        return null
    }
    console.log(response)
    if (response.data.status.code == 200) {
        return response.data.data.userId
    }

    return null
  }

const signUp = async (signUpInput) => {

    const { email, password } = signUpInput

    const response = await axios.post(`${baseUrl}signup?userType=customer&email=${email}&password=${password}`)
    console.log(response)
    if (response.data.status.code === 200) {
        console.log(response.data.data.userId)
        console.log(typeof response.data.data.userId != "undefined")
        if(typeof response.data.data.userId != "undefined"){
            return response.data.data.userId
        } else {
            return -1
        }

    }

    return null

}
  
  export default { login, signUp }