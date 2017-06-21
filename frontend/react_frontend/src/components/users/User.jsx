import React from 'react'
import { Row, Col, Button } from 'react-bootstrap'

const User = ({ user, loggedInUser, followUser, unfollowUser }) => {
  /* CHECK IF USER IS BEEING FOLLOWED */
  let followed = false

  for (let follower of user.followers) {
    if (follower.followerId === loggedInUser.userId) {
      followed = true
      break
    }
  }

  const toggleFollowButton = followed ? <Button
    onClick={() => unfollowUser(user.userId)}>
    UNFOLLOW
  </Button> : <Button
    onClick={() => followUser(user.userId)} >
    FOLLOW
  </Button>

  return (
    <div style={{border: '1px solid black', marginBottom: '20px'}}>
      {/* TWEET HEAD */}
      <Row>
        <Col xs={3}>
          <div>
            <p style={{textAlign: 'center', fontWeight: 'bold', paddingTop: '10px'}}>{ user.username }</p>
          </div>
        </Col>
        <Col xs={6} />
        <Col xs={3}>
          <span> { user.email } </span>
        </Col>
      </Row>
      {/* TWEET CONTENT */}
      <Row>
        <Col xs={2} />
        <Col xs={8} style={{ textAlign: 'center' }}>
          {toggleFollowButton}
        </Col>
        <Col xs={2} />
      </Row>
    </div>
  )
}

export default User
