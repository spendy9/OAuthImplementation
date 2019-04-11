<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<!-- The top of file index.html -->
<html itemscope itemtype="http://schema.org/Article">
<head>
	<!-- BEGIN Pre-requisites -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js">
  	</script>
	<script src="https://apis.google.com/js/client:platform.js?onload=start" async defer>
  </script>
	<!-- END Pre-requisites -->
	<meta charset="ISO-8859-1">
	<title>SSO</title>
	<script>
    function start() {
      gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
          client_id: '787591210057-680u7b0shhup4m70h6c8ee2vpj0pa1ob.apps.googleusercontent.com',
          // Scopes to request in addition to 'profile' and 'email'
          scope: 'openid',
          redirect_uri:'http://localhost:8090/SpringMVC/newEmployee',
          response_type:'code'
        });
      });
    }
  </script>
</head>
<body>

<button id="signinButton">Sign in with Google</button>
<script>
  $('#signinButton').click(function() {
    // signInCallback defined in step 6.
    auth2.grantOfflineAccess().then(signInCallback);
  });
  
  function signInCallback(authResult) {
	  if (authResult['code']) {
		
			
		// Hide the sign-in button now that the user is authorized, for example:
	    $('#signinButton').attr('style', 'display: none');

	    // Send the code to the server
	    $.ajax({
	      type: 'POST',
	      url: 'http://localhost:8090/SpringMVC/processauthcode?authCode='+authResult['code'],
	      // Always include an `X-Requested-With` header in every AJAX request,
	      // to protect against CSRF attacks.
	      headers: {
	        'X-Requested-With': 'XMLHttpRequest'
	      },
	      contentType: 'application/octet-stream; charset=utf-8',
	      success: function(result) {
	        // Handle or verify the server response.
	      },
	      //processData: false,
	      
	    });
	  } else {
	    alert("Error Occurred");
	  }
	}
</script>

</body>
</html>