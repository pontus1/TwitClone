import { combineReducers } from 'redux'
import { routerReducer } from 'react-router-redux'
import tweets from './tweetReducer'
import users from './usersReducer'
import session from './sessionReducer'

const rootReducer = combineReducers({
  // short hand property names
  tweets,
  users,
  session,
  routing: routerReducer
})

export default rootReducer

// TODO: Change file name to index.js
