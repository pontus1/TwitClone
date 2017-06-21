/* global fetch, Headers */

const storage = window.localStorage
const serverURL = 'http://localhost:8080'
const usersURL = `${serverURL}/api/users`

class UsersApi {
  /* GET ALL USERS */
  static getAllUsers () {
    let headers = new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${usersURL}/`, {
      method: 'GET',
      headers: headers
    })
    .then(response => {
      if (response.status !== 200) {
        return 'error: ' + response.status
      }
      return response.json()
    }).catch(error => {
      return error
    })
  }

  /* FOLLOW USER */
  static followUser (followerId, followeeId) {
    let headers = new Headers({
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${usersURL}/${followerId}/follow/${followeeId}`, {
      method: 'PUT',
      headers: headers
    })
    .then(response => {
      if (response.status !== 204) {
        return 'error: ' + response.status
      }
      return 'success'
    }).catch(error => {
      return error
    })
  }

  /* UNFOLLOW USER */
  static unfollowUser (followerId, followeeId) {
    let headers = new Headers({
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${usersURL}/${followerId}/unfollow/${followeeId}`, {
      method: 'PUT',
      headers: headers
    })
    .then(response => {
      if (response.status !== 204) {
        return 'error: ' + response.status
      }
      return 'success'
    }).catch(error => {
      return error
    })
  }

}

export default UsersApi
