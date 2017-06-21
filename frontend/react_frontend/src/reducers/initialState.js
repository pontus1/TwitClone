/* ------------------ *\
    INITIAL STATE
\* ------------------ */

const storage = window.localStorage
// const token = storage.getItem('token')

export default {
  tweets: {
    data: [],
    isFetching: false
  },
  users: {
    data: [],
    isFetching: false
  },
  session: {
    token: !!storage.getItem('token'),
    isAuthenticated: false,
    isFetching: false,
    error: null,
    user: null
  }
}
