import React from 'react'
import PropTypes from 'prop-types'
import Tweet from './Tweet.jsx'

const TweetList = ({ tweets, user, deleteTweet }) => {
  return (
    <ul className='list-group'>
      {tweets.map(tweet =>
        <li className='list-group-item' key={tweet.messageId}>
          <Tweet
            tweet={tweet}
            user={user}
            deleteTweet={deleteTweet}
          />
        </li>
      ).reverse()}
    </ul>
  )
}

TweetList.propTypes = {
  tweets: PropTypes.array.isRequired,
  user: PropTypes.object,
  deleteTweet: PropTypes.func
}

export default TweetList

/*
{tweet.content}
  <br/>
<smal>{tweet.author.username}</smal>
*/
