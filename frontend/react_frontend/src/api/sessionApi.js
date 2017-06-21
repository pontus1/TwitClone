/* global fetch, Headers */
import querystring from 'querystring'
// import {checkHttpStatus} from './httpStatus'

const storage = window.localStorage
const serverURL = 'http://localhost:8080'
const usersURL = `${serverURL}/api/users`

class SessionApi {

  /* LOGIN */
  static login (username, password) {
    let headers = new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Basic dHdpdHRlci1jbG9uZS1jbGllbnQ6MTIzNDU2'
    })

    let body = {
      client_id: 'twitter-clone-client',
      client_secret: '123456',
      grant_type: 'password',
      username: username,
      password: password
    }

    return fetch(`${serverURL}/oauth/token`, {
      method: 'POST',
      headers: headers,
      body: querystring.stringify(body)
    })
    .then(response => {
      return response.json()
    }).catch(error => {
      return error
    })
  }

  /* LOGOUT */
  static logout () {
    let headers = new Headers({
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${serverURL}/oauth/revoke-token`, {
      method: 'GET',
      headers: headers
    })
    .then(response => {
      return response
    }).catch(error => {
      return error
    })
  }

  /* GET LOGGED IN USER */
  static getLoggedInUser () {
    let headers = new Headers({
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${usersURL}/loggedInUser`, {
      method: 'GET',
      headers: headers
    })
    .then(response => {
      return response.json()
    }).catch(error => {
      return error
    })
  }
}

export default SessionApi
