app.service('UserService', [ 'mainMenu', function(mainMenu) {
	
	this.loggedIn = false;
	this.user = null;
	
	this.loginSuccess = function(result) {
		console.log('result is ' + JSON.stringify(result));
		this.user = {
			displayName: result.displayName
		}
		console.log('in loginSuccess(). user is ' + JSON.stringify(this.user));
		this.loggedIn = true;
		mainMenu.addRight(2, this.user.displayName, 'about');
	
	
	
		 
	}
	
}]);