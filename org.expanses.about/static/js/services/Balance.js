app.service('Balance',  function($http) {
	this.list = function(cb) {
		$http.get("balance").success(cb);
	}
	this.update = function(balance, cb) {
		$http.post('balance/' + balance.uniqueId, balance).success(cb);
	}
	this.create = function(balance, cb) {
		$http.post('balance', balance).success(cb);
	}
	this.remove = function(id, cb) {
		$http.delete('balance/' + id).success(cb);
	}
	
});
