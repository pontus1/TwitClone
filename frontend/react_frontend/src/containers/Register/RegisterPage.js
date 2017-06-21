import React from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { Row, Col, Button, FormGroup, ControlLabel, FormControl } from 'react-bootstrap'

class RegisterPage extends React.Component {
  constructor (props) {
    super(props)
    this.state = {
      username: '',
      email: '',
      password: '',
      repeatPassword: '',
      notMatching: false
    }
    this.updateUsername = this.updateUsername.bind(this)
  }

  updateUsername (e) {
    this.setState({ username: e.target.value })
  }

  updateEmail (e) {
    this.setState({ email: e.target.value })
  }

  updatePassword (e) {
    this.setState({ password: e.target.value })
  }

  updateRepeatPassword (e) {
    this.setState({ repeatPassword: e.target.value })
  }

  render () {
    return (
      <div>
        <Row>
          <Col xs={12}>
            <h1>Sign Up</h1>
          </Col>
        </Row>
        <Row>
          <form onSubmit={this.onSubmit}>
            <FormGroup>
              <ControlLabel>Username<span style={{ 'color': '#D64242' }}>*</span></ControlLabel>
              <FormControl
                type='text'
                name='username'
                placeholder='Enter username'
                onChange={(e) => this.updateUsername(e)}
             />
            </FormGroup>
            <FormGroup>
              <ControlLabel>Email<span style={{ 'color': '#D64242' }}>*</span></ControlLabel>
              <FormControl
                type='text'
                name='email'
                placeholder='Enter email'
                onChange={(e) => this.updateEmail(e)}
             />
            </FormGroup>
            <FormGroup>
              <ControlLabel>Password<span style={{ 'color': '#D64242' }}>*</span></ControlLabel>
              <FormControl
                type='password'
                name='password'
                placeholder='Enter password'
                onChange={(e) => this.updatePassword(e)}
             />
            </FormGroup>
            <FormGroup>
              <ControlLabel>Repeat Password<span style={{ 'color': '#D64242' }}>*</span></ControlLabel>
              <FormControl
                type='password'
                name='password'
                placeholder='Enter password'
                onChange={(e) => this.updateRepeatPassword(e)}
             />
            </FormGroup>
            <Button type='submit'>
              Create Account!
            </Button>
            <br />
          </form>
        </Row>
      </div>
    )
  }
}

const mapStateToProps = (state, ownProps) => {
  return {}
}

const mapDispatchToProps = (dispatch) => {
  return {}
}

export default connect(mapStateToProps, mapDispatchToProps)(RegisterPage)
