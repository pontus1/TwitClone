// Implement for error-handling (ont forget to add to initial state and check when to remove errors)
// Also add actions: ADD_ERROR, REMOVE_ERROR


//http://stackoverflow.com/questions/34403269/what-is-the-best-way-to-deal-with-a-fetch-error-in-react-redux

function errors(state = [], action) {
  switch (action.type) {

    case ADD_ERROR:
      return state.concat([action.error]);

    case REMOVE_ERROR:
      return state.filter((error, i) => i !== action.index);

    default:
      return state;
  }
}
