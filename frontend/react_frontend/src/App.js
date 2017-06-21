import React, { Component } from 'react'
import PropTypes from 'prop-types'
import Header from './containers/Header/Header.jsx'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/css/bootstrap-theme.css'
import 'font-awesome/css/font-awesome.css'

class App extends Component {
  render () {
    return (
      <div className='container-fluid' style={{ padding: '30px 30px' }}>
        <Header />
        { this.props.children }
      </div>
    )
  }
}

App.propTypes = {
  children: PropTypes.object.isRequired
}

export default App
