import React from 'react'
import { Route, IndexRoute } from 'react-router'
import {requireAuthentication} from './containers/Auth/AuthenticatedComponent.jsx'
import App from './App'
import TweetPage from './containers/Tweets/TweetPage'
import UsersPage from './containers/Users/UsersPage'
import Login from './components/home/Login'
import RegisterPage from './containers/Register/RegisterPage'
import NotFound from './containers/NotFound/NotFound.jsx'

const createRoutes = (store) => (
  <Route path='/' component={App}>
    <IndexRoute component={requireAuthentication(TweetPage)} />
    <Route path='/login' component={Login} />
    <Route path='/register' component={RegisterPage} />
    <Route path='/users' component={requireAuthentication(UsersPage)} />
    <Route path='*' component={NotFound} />
  </Route>
)

export default createRoutes
