import * as types from '../actions/actionTypes'
import initialState from './initialState'

/* ----------------------- *\
     REDUCERS - TWEETS
\* ----------------------- */

const tweetReducer = (state = initialState.tweets, action) => {
  switch (action.type) {
    /* REQUEST TWEETS */
    case types.REQUEST_TWEETS:
      return {
        ...state,
        isFetching: true
      }
    case types.LOAD_TWEETS_SUCCESS:
      return {
        ...state,
        isFetching: false,
        data: action.tweets
      }
    case types.LOAD_TWEETS_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error
      }
    /* POST TWEET */
    case types.REQUEST_POST_TWEET:
      return {
        ...state,
        isFetching: true
      }
    case types.POST_TWEET_SUCCESS:
      return {
        ...state,
        isFetching: false
      }
    case types.POST_TWEET_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error
      }
    /* DELETE TWEET */
    case types.REQUEST_DELETE_TWEET:
      return {
        ...state,
        isFetching: true
      }
    case types.DELETE_TWEET_SUCCESS:
      return {
        ...state,
        isFetching: false
      }
    case types.DELETE_TWEET_ERROR:
      return {
        ...state,
        isFetching: false,
        error: action.error
      }
    default:
      return state
  }
}

export default tweetReducer
