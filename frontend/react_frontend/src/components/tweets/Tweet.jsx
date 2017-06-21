import React from 'react'
import { Row, Col } from 'react-bootstrap'
import FontAwesome from 'react-fontawesome'
// TODO: Move moment stuff to utility
import moment from 'moment'

moment.locale('sv')

const Tweet = ({ tweet, user, deleteTweet }) => {
  /* REMOVE TWEET */
  const removeTweet = user.userId === tweet.authorId ? <FontAwesome
    name='times'
    style={{cursor: 'pointer', color: 'rgb(135, 32, 45)'}}
    size='2x'
    onClick={() => deleteTweet(tweet.messageId)}
  /> : ''

  return (
    <div style={{border: '1px solid black', marginBottom: '20px'}}>
      {/* TWEET HEAD */}
      <Row>
        <Col xs={3}>
          <div>
            <p style={{textAlign: 'center', fontWeight: 'bold', paddingTop: '10px'}}>{ tweet.author.username }
              <br />
              <span>{ tweet.author.email }</span>
            </p>
          </div>
        </Col>
        <Col xs={6} />
        <Col xs={3}>
          <Row style={{paddingTop: '10px'}}>
            <Col xs={6}>
              <smal>{ moment(tweet.pubDate).format('DD MMM YYYY HH:mm') }</smal>
            </Col>
            <Col xs={6}>
              {removeTweet}
            </Col>
          </Row>
        </Col>
      </Row>
      {/* TWEET CONTENT */}
      <Row>
        <Col xs={2} />
        <Col xs={8}>
          <h4 style={{textAlign: 'center'}}>{ tweet.content }</h4>
        </Col>
        <Col xs={2} />
      </Row>
    </div>
  )
}

export default Tweet
