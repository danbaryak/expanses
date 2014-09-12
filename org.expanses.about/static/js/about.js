app.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise('/home');

	$stateProvider

	.state('home', {
		url : '/home',
		templateUrl : 'about/partials/home.html'
	})

	.state('cards', {
		url : '/cards',
		templateUrl : 'about/partials/cards.html'
	})

	.state('another', {
		url : '/another',
		templateUrl : 'about/partials/another.html'
	});

});

app.run(function(MainMenu) {
	MainMenu.appName = 'My Account';
	MainMenu.appNameRef = 'home';

	MainMenu.add(0, 'Credit Cards', 'cards');
	MainMenu.add(1, 'Fixed Expanses', 'another');
});
