$(document).ready(function() {
	$('#test').text("Testing testing..." + atob('dHdpdHRlci1jbG9uZS1jbGllbnQ6cGFzc3dvcmQ='));

  var access_token = null;

  // Login
	$('#login').click(function() {
		var user = $('#user').val();
		var password = $('#password').val();
		console.log(user + ' ' + password);

$.ajax({
	type: 'POST',
	url: 'http://localhost:8080/oauth/token',
  xhrFields: {
    withCredentials: false
  },
	headers: {
    'Authorization': 'Basic dHdpdHRlci1jbG9uZS1jbGllbnQ6MTIzNDU2',
    'Content-Type': 'application/x-www-form-urlencoded',
  },
  data: {
    'username': user,
  	'password': password,
  	'grant_type': 'password',
  	'client_secret': 123456,
  	'client_id': 'twitter-clone-client'
  },
  success: function(data) {
    console.log(data);
    access_token = data.access_token;
  },
  error: function (request, textStatus, errorThrown) {
        console.log('Response: ' + request.responseText);
        console.log('Status: ' + textStatus);
        console.log('Thrown error: ' + errorThrown);
    }
});
});

// Test API (Get user with id 2)
$('#query').click(function() {
  var searched_username = $('#search').val();
  $('#result').text('');

  $.ajax({
  type: 'GET',
  url: 'http://localhost:8080/api/users/name/' + searched_username,
  headers: {
    'Authorization': 'Bearer ' + access_token,
    'Content-Type': 'application/json',
  },
  success: function(data) {
    console.log(data);
    $('#result').append('Username: ' + data.username + ' Email: ' + data.email);  
  },
  error: function (request, textStatus, errorThrown) {
        $('#result').append(request.responseText);
        console.log('Response: ' + request.responseText);
        console.log('Status: ' + textStatus);
        console.log('Thrown error: ' + errorThrown);
    }
  });
});

});

/*
$.ajax({
  type: 'GET',
  url: 'http://localhost:8080/api/users/1/',
  contentType: 'text/plain',
  xhrFields: {
    withCredentials: false
  },
  headers: {
    'Authorization': 'Basic ' + btoa(user + ':' + password)
  },
  success: function(data) {
    console.log(data);
  },
  error: function (request, textStatus, errorThrown) {
        console.log('Response: ' + request.responseText);
        console.log('Status: ' + textStatus);
        console.log('Thrown error: ' + errorThrown);
    }
});
});


function make_base_auth(user, password) {
	var tok = user + ':' + password;
	var hash = btoa(tok);
	//console.log(atob(hash));
	return 'Basic ' + hash;
}
*/
 
