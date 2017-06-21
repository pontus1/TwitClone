import * as types from './actionTypes'
import usersApi from '../api/usersApi'
import { loadTweets } from './tweetActions'

/* --------------------------- *\
    ACTION-CREATORS - USERS
\* --------------------------- */

/* GET ALL USERS */
export const requestUsers = () => ({
  type: types.REQUEST_USERS
})

export const loadUsersSuccess = (users) => ({
  type: types.LOAD_USERS_SUCCESS,
  users
})

export const loadUsersError = (error) => ({
  type: types.LOAD_USERS_ERROR,
  error
})

/* FOLLOW USER */
export const requestFollowUser = (id) => ({
  type: types.REQUEST_FOLLOW_USER,
  id
})

export const followUserSuccess = () => ({
  type: types.FOLLOW_USER_SUCCESS
})

export const followUserError = (error) => ({
  type: types.FOLLOW_USER_ERROR,
  error
})

/* UNFOLLOW USER */
export const requestUnfollowUser = (id) => ({
  type: types.REQUEST_UNFOLLOW_USER,
  id
})

export const unfollowUserSuccess = () => ({
  type: types.UNFOLLOW_USER_SUCCESS
})

export const unfollowUserError = (error) => ({
  type: types.UNFOLLOW_USER_ERROR,
  error
})

/* ----------------------- *\
       ASYNC (THUNK)
\* ----------------------- */

export const loadUsers = () => dispatch => {
  dispatch(requestUsers())
  return usersApi.getAllUsers()
  .then(resp => {
    if (typeof resp === 'object') {
      dispatch(loadUsersSuccess(resp))
    } else {
      dispatch(loadUsersError(resp))
    }
  }).catch(error => {
    throw (error)
  })
}

export const followUser = (followeeId) => (dispatch, getState) => {
  /* GET SESSION FROM GLOBAL STATE */
  const { session } = getState()
  dispatch(requestFollowUser(followeeId))
  return usersApi.followUser(session.user.userId, followeeId)
  .then(resp => {
    if (resp === 'success') {
      dispatch(followUserSuccess())
      /* RELOAD USERS */
      dispatch(loadUsers())
      /* RELOAD TWEETS */
      dispatch(loadTweets())
    } else {
      dispatch(followUserError(resp))
    }
  }).catch(error => {
    throw (error)
  })
}

export const unfollowUser = (followeeId) => (dispatch, getState) => {
  /* GET SESSION FROM GLOBAL STATE */
  const { session } = getState()
  dispatch(requestUnfollowUser(followeeId))
  return usersApi.unfollowUser(session.user.userId, followeeId)
  .then(resp => {
    if (resp === 'success') {
      dispatch(unfollowUserSuccess())
      /* RELOAD USERS */
      dispatch(loadUsers())
      /* RELOAD TWEETS */
      dispatch(loadTweets())
    } else {
      dispatch(unfollowUserError(resp))
    }
  }).catch(error => {
    throw (error)
  })
}
