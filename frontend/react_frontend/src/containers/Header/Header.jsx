import React from 'react'
import PropTypes from 'prop-types'
import { Link, IndexLink } from 'react-router'
import { connect } from 'react-redux'
import { logoutUser } from '../../actions/sessionActions'

class Header extends React.Component {
  render () {
    let navBar
    /* LOAD DIFFERENT NAVBARS DEPENDING ON AUTHENTICATED STATE */
    if (this.props.isAuthenticated) {
      navBar = <nav>
        <Link to='/users' activeClassName='active'>USERS</Link> |
        <Link to='/' activeClassName='active'> TWEETS</Link> |
        <Link to='' activeClassName='active' onClick={this.props.logout}> LOG OUT</Link>
      </nav>
    } else {
      navBar = <nav>
        <IndexLink to='/' activeClassName='active'>LOG IN</IndexLink> |
        <Link to='/register' activeClassName='active'> REGISTER</Link>
      </nav>
    }

    return (
      <div>{navBar}</div>
    )
  }
}

Header.propTypes = {
  isAuthenticated: PropTypes.bool.isRequired,
  logout: PropTypes.func.isRequired
}

const mapStateToProps = (state) => {
  return {
    isAuthenticated: state.session.isAuthenticated
    // loading: state.session.isFetching
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    logout: () => dispatch(logoutUser())
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Header)
