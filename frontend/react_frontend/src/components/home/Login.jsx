// THIS IS A CONTAINER!!!!
import React, { Component } from 'react'
import { connect } from 'react-redux'
import { FormGroup, ControlLabel, FormControl, Button } from 'react-bootstrap'
import { loginUser } from '../../actions/sessionActions'

class Login extends Component {
  constructor (props) {
    super(props)
    this.state = {
      username: '',
      password: ''
    }
    this.onSubmit = this.onSubmit.bind(this)
  }

  // DISPATCH ON SUBMIT
  onSubmit (e) {
    e.preventDefault()
    let { username, password } = this.state
    this.props.login(username, password)
    this.setState({
      username: '',
      password: ''
    })
  }

  render () {
    let { isLoading, isLoginSuccess, loginError } = this.props

    return (
      <div className='col-md-12'>
        <h1>Login</h1>
        <div>
          <form onSubmit={this.onSubmit}>
            <FormGroup>
              <ControlLabel>Username</ControlLabel>
              <FormControl
                type='text'
                name='username'
                placeholder='Enter username'
                onChange={e => this.setState({ username: e.target.value })} />
            </FormGroup>
            <FormGroup>
              <ControlLabel>Password</ControlLabel>
              <FormControl
                type='password'
                name='password'
                placeholder='Enter password'
                onChange={e => this.setState({ password: e.target.value })} />
            </FormGroup>
            <Button type='submit'>
              Submit
            </Button>
            <br />
            { isLoading && <div>Please wait...</div> }
            { isLoginSuccess && <div>Success.</div> }
            { loginError && <div>{ loginError }</div> }
          </form>
        </div>
      </div>
    )
  }
}

const mapStateToProps = (state) => {
  return {
    isLoading: state.session.isFetching,
    isLoginSuccess: state.session.isAuthenticated,
    loginError: state.session.error
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    login: (username, password) => dispatch(loginUser(username, password))
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Login)
