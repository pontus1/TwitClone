import React, { Component } from 'react'
import { Row, Col, FormGroup, ControlLabel, FormControl, Button } from 'react-bootstrap'

class AddTweet extends Component {
  constructor (props) {
    super(props)

    this.state = {
      tweetContent: ''
    }

    this.handleUpdate = this.handleUpdate.bind(this)
    this.addTweet = this.addTweet.bind(this)
  }

  handleUpdate (event) {
    this.setState({ tweetContent: event.target.value })
  }

  addTweet () {
    this.props.addTweet(this.state.tweetContent)
    this.setState({ tweetContent: '' })
  }

  render () {
    return (
      <div style={{ border: '1px solid blue', height: '150px', marginBottom: '10px' }}>
        <Row>
          <Col xs={2}></Col>
          {/* TWEET FORM */}
          <Col xs={8}>
            <form>
              <FormGroup>
                <ControlLabel style={{width: '100%', textAlign: 'center'}}>Posta en Tweet!</ControlLabel>
                  <FormControl
                    type='text'
                    placeholder='Vad händer...'
                    onChange={this.handleUpdate}
                    value={this.state.tweetContent}
                  />
              </FormGroup>
              <Button className='pull-right' onClick={this.addTweet}>Tweet!</Button>
            </form>
          </Col>
          <Col xs={2}></Col>
        </Row>
      </div>
    )
  }
}

export default AddTweet
