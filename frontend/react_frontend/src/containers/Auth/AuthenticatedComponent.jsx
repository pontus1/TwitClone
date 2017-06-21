import React from 'react'
import {connect} from 'react-redux'
import {push} from 'react-router-redux'
import Login from '../../components/home/Login.jsx'

export function requireAuthentication (Component) {
  class AuthenticatedComponent extends React.Component {

    componentWillMount () {
      this.checkAuth()
    }

    componentWillReceiveProps () {
      this.checkAuth()
    }

    checkAuth () {
      if (!this.props.isAuthenticated) {
        this.props.dispatch(push('/login'))
      }
    }

    render () {
      return (
        <div>
          {this.props.isAuthenticated === true
            ? <Component {...this.props} />
            : <Login />
          }
        </div>
      )
    }
}

  const mapStateToProps = (state) => ({
    token: state.session.token,
    user: state.session.user,
    isAuthenticated: state.session.isAuthenticated
  })

  return connect(mapStateToProps)(AuthenticatedComponent)
}
