import * as types from './actionTypes'
import tweetApi from '../api/tweetApi'

/* ---------------------------- *\
    ACTION-CREATORS - TWEETS
\* ---------------------------- */

/* GET TWEETS FROM FOLLOWEES */
export const requestTweets = () => ({
  type: types.REQUEST_TWEETS
})

export const loadTweetsSuccess = (tweets) => ({
  type: types.LOAD_TWEETS_SUCCESS,
  tweets
})

export const loadTweetsError = (error) => ({
  type: types.LOAD_TWEETS_ERROR,
  error
})

/* POST TWEET */
export const requestPostTweet = (content) => ({
  type: types.REQUEST_POST_TWEET,
  content
})

export const postTweetSuccess = () => ({
  type: types.POST_TWEET_SUCCESS
})

export const postTweetError = (error) => ({
  type: types.POST_TWEET_ERROR,
  error
})

/* DELETE TWEET */
export const requestDeleteTweet = (id) => ({
  type: types.REQUEST_DELETE_TWEET,
  id
})

export const deleteTweetSuccess = () => ({
  type: types.DELETE_TWEET_SUCCESS
})

export const deleteTweetError = (error) => ({
  type: types.DELETE_TWEET_ERROR,
  error
})

// TODO: Create separate actions, state, reducers etc for all tweets and tweets-by-followees

/* ----------------------- *\
       ASYNC (THUNK)
\* ----------------------- */

export const loadTweets = () => (dispatch, getState) => {
  /* GET SESSION FROM GLOBAL STATE */
  const { session } = getState()
  dispatch(requestTweets())
  return tweetApi.getTweetsByFollowees(session.user.userId)
  .then(resp => {
    if (typeof resp === 'object') {
      dispatch(loadTweetsSuccess(resp))
    } else {
      dispatch(loadTweetsError(resp))
    }
  }).catch(error => {
    throw (error)
  })
}

export const postTweet = (content) => (dispatch) => {
  dispatch(requestPostTweet())
  return tweetApi.postTweet(content)
  .then(resp => {
    if (resp === 'success') {
      dispatch(postTweetSuccess())
      /* RELOAD TWEETS */
      dispatch(loadTweets())
    } else {
      dispatch(postTweetError(resp))
    }
  }).catch(error => {
    throw (error)
  })
}

export const deleteTweet = (id) => dispatch => {
  dispatch(requestDeleteTweet(id))
  return tweetApi.deleteTweet(id)
  .then(resp => {
    if (resp === 'success') {
      dispatch(deleteTweetSuccess())
      /* RELOAD TWEETS */
      dispatch(loadTweets())
    } else {
      dispatch(deleteTweetError(resp))
    }
  }).catch(error => {
    throw (error)
  })
}
