app.directive('inputField', function() {
    return {
        // can be used as attribute or element
        restrict: 'AE',
        scope: {
            value: '=ngModel',
            lbl: '=lbl'
        },
        link: function(scope, element, attrs) {
        	scope.lbl = attrs.lbl;
        },
        // which markup this directive generates
        template: '<div class="group">' +
        		  '<input type="text" ng-model="value" required>' +
            	  '<span class="highlight"></span>' +
            	  '<span class="bar"></span>' +
            	  '<label>{{lbl}}</label>' +
            	  '</div>'
    };
});