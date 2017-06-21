import React from 'react'
import { render } from 'react-dom'
import configureStore from './store/configureStore'
import { Provider } from 'react-redux'
import { Router, browserHistory } from 'react-router'
import { syncHistoryWithStore } from 'react-router-redux'
import createRoutes from './routes'

// import { loadTweets } from './actions/tweetActions'
// import { loadUsers } from './actions/usersActions'

const store = configureStore()
const routes = createRoutes(store)

// TODO:
//* Reload initial data on page refresh */
// let token = window.localStorage.getItem('token')
//
// if (token) {
//   store.dispatch(loadTweets())
//   store.dispatch(loadUsers())
// }

/* Create an enhanced history that syncs navigation events with the store */
const history = syncHistoryWithStore(browserHistory, store)

render (
  <Provider store={store}>
    <Router history={history} routes={routes} />
  </Provider>,
  document.getElementById('root')
)
