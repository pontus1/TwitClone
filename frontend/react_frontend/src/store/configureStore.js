import {createStore, applyMiddleware} from 'redux'
import { createLogger } from 'redux-logger'
import thunk from 'redux-thunk'
import { browserHistory } from 'react-router'
import { routerMiddleware } from 'react-router-redux'
import rootReducer from '../reducers/rootReducer'

// Sync dispatched route actions to the history
const reduxRouterMiddleware = routerMiddleware(browserHistory)

/* APPLY MIDDLEWERE */
const middleware = [ thunk, reduxRouterMiddleware ]

/* ADD LOGGER FOR DEV ENVIRONMENT */
if (process.env.NODE_ENV !== 'production') {
  middleware.push(createLogger())
}

/* CREATE STORE WITH MIDDLEWERE */
export default function configureStore () {
  return createStore(
    rootReducer,
    applyMiddleware(...middleware)
  )
}
