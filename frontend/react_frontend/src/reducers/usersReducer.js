import * as types from '../actions/actionTypes'
import initialState from './initialState'

/* ----------------------- *\
     REDUCERS - USERS
\* ----------------------- */

const usersReducer = (state = initialState.users, action) => {
  switch (action.type) {
    /* GET ALL USERS */
    case types.REQUEST_USERS:
      return {
        ...state,
        isFetching: true
      }
    case types.LOAD_USERS_SUCCESS:
      return {
        ...state,
        isFetching: false,
        data: action.users
      }
    case types.LOAD_USERS_ERROR:
      return {
        ...state,
        isFetching: false
      }
    /* FOLLOW USER */
    case types.REQUEST_FOLLOW_USER:
      return {
        ...state,
        isFetching: true
      }
    case types.FOLLOW_USER_SUCCESS:
      return {
        ...state,
        isFetching: false
      }
    case types.FOLLOW_USER_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error
      }
    /* UNFOLLOW USER */
    case types.REQUEST_UNFOLLOW_USER:
      return {
        ...state,
        isFetching: true
      }
    case types.UNFOLLOW_USER_SUCCESS:
      return {
        ...state,
        isFetching: false
      }
    case types.UNFOLLOW_USER_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error
      }
    default:
      return state
  }
}

export default usersReducer
