app.directive('fadeInUp', function() {
	return {
		restrict: 'A',
		replace: false,
		link: function(scope, element, attrs) {
			element.velocity({opacity: 0, translateY: 20}, 0);
			
			element.velocity({opacity: 1, translateY: 0}, {
				easing: 'easeInSine',
				duration: 500,
				delay: attrs.delay
			});
			
		}
	}
});

app.directive('fadeInDown', function() {
	return {
		restrict: 'A',
		replace: false,
		link: function(scope, element, attrs) {
			element.velocity({opacity: 0, translateY: -10}, 0);
			
			element.velocity({opacity: 1, translateY: 0}, {
				duration: 250,
				delay: attrs.delay
			});
			
		}
	}
});

app.directive('fadeInLeft', function() {
	return {
		restrict: 'A',
		replace: false,
		link: function(scope, element, attrs) {
			element.velocity({opacity: 0, translateX: -10}, 0);
			
			element.velocity({opacity: 1, translateX: 0}, {
				duration: 250,
				delay: attrs.delay
			});
			
		}
	}
});

app.directive('fadeIn', function() {
	return {
		restrict: 'A',
		replace: false,
		link: function(scope, element, attrs) {
			element.velocity({opacity: 0}, 0);
			
			element.velocity({opacity: 1}, {
				easing: 'easeInSine',
				duration: 250,
				delay: attrs.delay
			});
			
		}
	}
})
