import * as types from '../actions/actionTypes'
import initialState from './initialState'
// import { browserHistory } from 'react-router';

const sessionReducer = (state = initialState.session, action) => {
  switch (action.type) {
    /* LOGIN */
    case types.REQUEST_LOGIN:
      return {
        ...state,
        isFetching: true,
        error: null
      }
    case types.LOG_IN_SUCCESS:
      return {
        ...state,
        isFetching: false,
        isAuthenticated: true,
        error: null
        // TOKEN
      }
    case types.LOG_IN_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error
      }
    /* LOGOUT */
    case types.REQUEST_LOGOUT:
      return {
        ...state,
        isFetching: true,
        error: null
      }
    case types.LOG_OUT_SUCCESS:
      return {
        ...state,
        isFetching: false,
        isAuthenticated: false,
        error: null,
        user: null
      }
    case types.LOG_OUT_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error
      }
    /* GET LOGGED IN USER */
    case types.REQUEST_LOGGED_IN_USER:
      return {
        ...state,
        isFetching: true,
        error: null
      }
    case types.GET_LOGGED_IN_USER_SUCCESS:
      return {
        ...state,
        isFetching: false,
        error: null,
        user: action.user
      }
    case types.GET_LOGGED_IN_USER_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error,
        user: null
      }
    default:
      return state
  }
}

export default sessionReducer

/*
export default function sessionReducer(state = initialState.session, action) {
    switch (action.type) {
        case types.LOG_IN_SUCCESS:
            browserHistory.push('/tweets')
            return !!sessionStorage.token
        case types.LOG_OUT:
            browserHistory.push('/')
            return !!sessionStorage.token
        default:
            return state;
    }
}
*/
