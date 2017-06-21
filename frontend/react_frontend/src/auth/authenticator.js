class Auth {
  static loggedIn() {
    return !!sessionStorage.token
  }

  static logOut() {
    sessionStorage.removeItem('token')
  }
}

export default Auth
