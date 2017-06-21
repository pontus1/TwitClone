/* global fetch, Headers */

const storage = window.localStorage
const serverURL = 'http://localhost:8080'
const tweetsURL = `${serverURL}/api/tweets`

class TweetApi {
  /* GET ALL TWEETS FROM EVERYONE */
  static getAllTweets () {
    let headers = new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${tweetsURL}/all_tweets`, {
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

  /* GET ALL TWEETS POSTED BY FOLLOWED USERS */
  static getTweetsByFollowees (loggedInUserID) {
    let headers = new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${tweetsURL}/follower/${loggedInUserID}`, {
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

  /* POST NEW TWEET */
  static postTweet (content) {
    let headers = new Headers({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    let body = {
      content: content
    }
    return fetch(`${tweetsURL}/`, {
      method: 'POST',
      headers: headers,
      body: JSON.stringify(body)
    })
    .then(response => {
      if (response.status !== 201) {
        return 'error: ' + response.status
      }
      return 'success'
    }).catch(error => {
      return error
    })
  }

  /* DELETE TWEET BY ID */
  static deleteTweet (id) {
    let headers = new Headers({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization': 'Bearer ' + storage.getItem('token')
    })
    return fetch(`${tweetsURL}/${id}`, {
      method: 'DELETE',
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

export default TweetApi

/*

export const fetchRegisterData = body => dispatch => {

  console.log("I'm wondering why no data is sent...")

  dispatch(requestRegister(body))

  let headers = new Headers();
  headers.append('Content-Type', 'application/x-www-form-urlencoded')
  headers.append('Authorization', 'Basic dHdpdHRlci1jbG9uZS1jbGllbnQ6MTIzNDU2')

  return fetch("http://localhost:8080/oauth/token", {
          method: "POST",
          headers: headers,
          body: querystring.stringify(body)
      })
      .then(response => response.json())
      .then(json => dispatch(reciveRegisterSuccess(json))) // TODO: add error handling
}
*/
