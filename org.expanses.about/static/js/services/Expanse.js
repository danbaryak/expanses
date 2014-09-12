/**
 * 
 */
app.service('Expanse',  function($http) {
	this.list = function(cb) {
		$http.get("expanses").success(cb);
	}
	this.update = function(expanse, cb) {
		$http.post('expanses/' + expanse.uniqueId, expanse).success(cb);
	}
});
