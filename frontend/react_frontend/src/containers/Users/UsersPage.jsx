import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { followUser, unfollowUser } from '../../actions/usersActions'
import UserList from '../../components/users/UserList.jsx'

class UsersPage extends Component {
  render () {
    // if (this.props.loading) {
    //   return (
    //     <div className='col-md-12'>
    //       <h2 style={{ textAlign: 'center' }}>LOADING USERS...</h2>
    //     </div>
    //   )
    // }
    return (
      <div className='col-md-12'>
        <h1>Users</h1>
        <div>
          <UserList
            users={this.props.users}
            loggedInUser={this.props.loggedInUser}
            followUser={this.props.followUser}
            unfollowUser={this.props.unfollowUser}
          />
        </div>
      </div>
    )
  }
}

UsersPage.propTypes = {
  users: PropTypes.array.isRequired,
  loggedInUser: PropTypes.object.isRequired,
  followUser: PropTypes.func.isRequired,
  unfollowUser: PropTypes.func.isRequired
}

const mapStateToProps = (state, ownProps) => {
  return {
    users: state.users.data,
    loading: state.users.isFetching,
    loggedInUser: state.session.user
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    followUser: (id) => {
      dispatch(followUser(id))
    },
    unfollowUser: (id) => {
      dispatch(unfollowUser(id))
    }
  }
}



export default connect(mapStateToProps, mapDispatchToProps)(UsersPage)
