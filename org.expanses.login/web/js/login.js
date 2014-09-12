app
		.directive(
				'googlePlusSignin',
				function() {
					var ending = /\.apps\.googleusercontent\.com$/;

					return {
						restrict : 'E',
						template : '<span class="g-signin"></span>',
						replace : true,
						link : function(scope, element, attrs) {
							attrs.clientid += (ending.test(attrs.clientid) ? ''
									: '.apps.googleusercontent.com');

							attrs.$set('data-clientid', attrs.clientid);

							// Some default values, based on prior versions of
							// this directive
							var defaults = {
								callback : 'signinCallback',
								cookiepolicy : 'single_host_origin',
								requestvisibleactions : 'http://schemas.google.com/AddActivity',
								scope : 'https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/userinfo.email',
								width : 'wide'
							};

							// Provide default values if not explicitly set
							angular.forEach(Object
									.getOwnPropertyNames(defaults), function(
									propName) {
								if (!attrs.hasOwnProperty(propName)) {
									attrs.$set('data-' + propName,
											defaults[propName]);
								}
							});

							// Default language
							// Supported languages:
							// https://developers.google.com/+/web/api/supported-languages
							attrs.$observe('language', function(value) {
								window.___gcfg = {
									lang : value ? value : 'en'
								};
							});

							// Asynchronously load the G+ SDK.
							(function() {
								var po = document.createElement('script');
								po.type = 'text/javascript';
								po.async = true;
								po.src = 'https://apis.google.com/js/client:plusone.js';
								var s = document.getElementsByTagName('script')[0];
								s.parentNode.insertBefore(po, s);
							})();
						}
					};
				}).run(
				[
						'$window',
						'$rootScope',
						function($window, $rootScope) {
							$window.signinCallback = function(authResult) {
								if (authResult && authResult.access_token) {
									$rootScope.$broadcast(
											'event:google-plus-signin-success',
											authResult);
								} else {
									$rootScope.$broadcast(
											'event:google-plus-signin-failure',
											authResult);
								}
							};
						} ]);

app.run(function(MainMenu, $cookies, $state) {
	var amdatu_token = $cookies.amdatu_token;
	console.log('Amdatu token is ' + amdatu_token);

	if (amdatu_token == null) {
		MainMenu.addRight(0, 'Sign In', 'login');
		MainMenu.addRight(1, 'Join', 'join');
		$state.transitionTo('join');
	}
	
});

app.service('UserSession', [ function() {

} ]);

app.controller('LoginCtrl', [ '$scope', '$state', 'MainMenu', 'UserService', 
                              function($scope, $state, MainMenu, UserService) {
	console.log('In LoginController');
	$scope.message = 'hiush';
	$scope.$on('event:google-plus-signin-success', function(event, authResult) {
		
		MainMenu.hideRight(0);
		MainMenu.hideRight(1);
		
		console.log('menu entries are: ' + JSON.stringify(MainMenu.entries));
		gapi.client.load('plus', 'v1', function() {
			var request = gapi.client.plus.people.get({
				'userId' : 'me'
			});

			request.execute(function(resp) {
				resp.access_token = authResult['access_token'];
				console.log('response is ' + JSON.stringify(resp));

				var googleuser = {
					id : resp.id,
					displayName : resp.displayName,
					// firstName : resp.name.givenName,
					// lastName : resp.name.familyName,
					accessToken : resp.access_token
				}
				jQuery.ajax({
					url : '/googlelogin',
					type : "POST",
					data : JSON.stringify(googleuser),
					contentType : "application/json",
					success : function(response) {
						UserService.loginSuccess(resp);
						
						$state.transitionTo('home');
						
						
					}
				});
			});
		});
		
	});
	$scope.$on('event:google-plus-signin-failure', function(event, authResult) {
		console.log("Google login failed");
	});
	
} ]);

app.config(function($stateProvider, $urlRouterProvider) {
	 
  
	$stateProvider

	.state('login', {
		url : '/login',
		templateUrl : 'login/partials/login.html',
		controller : 'LoginCtrl'
	})

	.state('join', {
		url : '/join',
		templateUrl : 'login/partials/join.html',
		controller : 'LoginCtrl'
	})

});

(function() {
	var po = document.createElement('script');
	po.type = 'text/javascript';
	po.async = true;
	po.src = 'https://apis.google.com/js/client:plusone.js';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(po, s);

})();

function signinCallback(authResult) {
	console.log('in signin callback with authResult: '
			+ JSON.stringify(authResult));
	gapi.client.load('plus', 'v1', function() {
		var request = gapi.client.plus.people.get({
			'userId' : 'me'
		});

		request.execute(function(resp) {
			resp.access_token = authResult['access_token'];
			console.log('response is ' + JSON.stringify(resp));

			var googleuser = {
				id : resp.id,
				displayName : resp.displayName,
				// firstName : resp.name.givenName,
				// lastName : resp.name.familyName,
				accessToken : resp.access_token
			}
			jQuery.ajax({
				url : '/googlelogin',
				type : "POST",
				data : JSON.stringify(googleuser),
				contentType : "application/json",
				success : function(response) {
					
					// console.log('request has succeeded with response ' +
					// JSON.stringify(response));
					// window.location.href = '/index.html';
				}
			});
		});
	});
}
