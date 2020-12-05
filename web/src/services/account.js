import axios from 'axios'

const baseUrl = 'http://3.138.113.101:8080/'

const login = async (loginInput) => {

    const { email, password } = loginInput

    const response = await axios.post(`${baseUrl}login?userType=customer&email=${email}&password=${password}`)
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
        const userId = login(signUpInput)
        return userId

    }

    return null

}
  
  export default { login, signUp }