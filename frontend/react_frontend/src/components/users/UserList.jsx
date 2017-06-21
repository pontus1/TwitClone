import React from 'react'
import PropTypes from 'prop-types'
import User from './User.jsx'

const UserList = ({ users, loggedInUser, followUser, unfollowUser }) => {
  return (
    <ul className='list-group' style={{ width: '100%' }}>
      {users.map(user =>
        <li className='list-group-item' key={user.userId}>

          <User
            user={user}
            followUser={followUser}
            loggedInUser={loggedInUser}
            unfollowUser={unfollowUser}
          />

        </li>
      )}
    </ul>
  )
}

UserList.propTypes = {
  users: PropTypes.array.isRequired,
  followUser: PropTypes.func.isRequired
}

export default UserList

/*
{user.username}
  <br/>
<smal>{user.email}</smal>
*/
