/**
 *
 */
app.service('Transaction',  function($http) {
	this.list = function(cb) {
		$http.get("transactions").success(cb);
	}
	this.update = function(transaction, cb) {
		$http.post('transactions/' + transaction.uniqueId, transaction).success(cb);
	}
});
