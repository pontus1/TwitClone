import React, { Component } from 'react'
import PropTypes from 'prop-types'
import { connect } from 'react-redux'
import { postTweet, deleteTweet } from '../../actions/tweetActions'
import AddTweet from '../../components/tweets/AddTweet.jsx'
import TweetList from '../../components/tweets/TweetList.jsx'

class TweetPage extends Component {

  render () {
    // if (this.props.loading) {
    //   return (
    //     <div className='col-md-12'>
    //       <h2 style={{ textAlign: 'center' }}>LOADING TWEETS...</h2>
    //     </div>
    //   )
    // }
    return (
      <div className='col-md-12'>
        <h1>Tweets</h1>
        <div>
          <AddTweet
            addTweet={this.props.addTweet}
          />
          <TweetList
            tweets={this.props.tweets}
            user={this.props.user}
            deleteTweet={this.props.deleteTweet}
          />
        </div>
      </div>
    )
  }
}

TweetPage.propTypes = {
  tweets: PropTypes.array.isRequired,
  addTweet: PropTypes.func.isRequired,
  deleteTweet: PropTypes.func.isRequired,
  user: PropTypes.object
}

const mapStateToProps = (state, ownProps) => {
  return {
    tweets: state.tweets.data,
    user: state.session.user,
    loading: state.tweets.isFetching
  }
}

const mapDispatchToProps = (dispatch) => {
  return {
    addTweet: (content) => {
      dispatch(postTweet(content))
    },
    deleteTweet: (id) => {
      dispatch(deleteTweet(id))
    }
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(TweetPage)
