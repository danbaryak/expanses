app.controller('HomeCtrl', function($scope, EventContainer, Transaction,
		Expanse, Balance, $timeout) {
	$scope.selectedObjectType = '';
	$scope.selected = null;
	$scope.selectedTime = new Date();
	
	$scope.safeApply = function(fn) {
		  var phase = this.$root.$$phase;
		  if(phase == '$apply' || phase == '$digest') {
		    if(fn && (typeof(fn) === 'function')) {
		      fn();
		    }
		  } else {
		    this.$apply(fn);
		  }
		};
		
	var transactionContainer = new EventContainer('transaction');
	var expanseContainer = new EventContainer('expanse');
	expanseContainer.className = 'blue';
	transactionContainer.className = 'red';
	
	var balanceContainer = new EventContainer('balance');
	balanceContainer.className = 'orange';
	balanceContainer.getContentText = function(object) {
		return '<i class="fa fa-money"></i>';
	}
	$scope.eventContainers = {
		'transaction' : transactionContainer,
		'expanse' : expanseContainer,
		'balance' : balanceContainer
	}
	
	$scope.customTimeChange = function(time) {
		$scope.selected = null;
		$scope.selectedTime = time.time;
		$scope.safeApply();
	}
	
	/**
	 * Called when timeline events are selected
	 */
	$scope.onTimelineSelect = function(item) {
		
		if (item == null) {
			$scope.selected = null;
			$scope.$apply();
			return;
		}

		var event = $scope.eventContainers[item.objectType]
				.getById(item.uniqueId);
		$scope.selected = event;
		$scope.selectedObjectType = item.objectType;
		$scope.safeApply();
	}

	/**
	 * Creates a new balance entry for the currently selected date.
	 */
	$scope.updateBalance = function() {
		var balance = {
			time: $scope.selectedTime,
			amount: 0
		};
		Balance.create(balance, function(result) {
			console.log('after creating balance, result is ' + JSON.stringify(result));
			balanceContainer.add(result);
			balanceContainer.select(result);
		});
	}
	
	$scope.deleteSelected = function() {
		if ($scope.selectedObjectType == 'balance') {
			Balance.remove($scope.selected.uniqueId, function() {
				var obj = $scope.selected;
				$scope.selected = null;
				balanceContainer.remove(obj);
			});
		}
	}
	
	/**
	 * Called by the timeline when an event is moved.
	 */
	$scope.onTimelineEventMoved = function(item) {

		var obj = $scope.eventContainers[item.objectType]
				.getById(item.uniqueId);
		obj.time = item.start;
		if (item.objectType == 'expanse') {
			Expanse.update(obj, function(updated) {
				
			});
		} else if (item.objectType == 'transaction') {
			Transaction.update(obj, function(updated) {
				
			});
		} else if (item.objectType == 'balance') {
			Balance.update(obj, function(updated) {
				
			});
		}

	}

	/**
	 * Called when the timeline is ready.
	 */
	$scope.timelineReady = function() {
		
		Expanse.list(function(expanses) {
			for (i in expanses) {
				var t = expanses[i];
				expanseContainer.add(t);
			}
		});

		Transaction.list(function(transactions) {
			for (i in transactions) {
				var t = transactions[i];
				transactionContainer.add(t);
			}
		});

		balanceContainer.setBatchUpdate(true);
		Balance.list(function(balances) {
			for (var i in balances) {
				var b = balances[i];
				balanceContainer.add(b);
			}
			balanceContainer.setBatchUpdate(false);
		});
		
		$scope.timelineRangeChanged();
	}

	var saveUpdates = function() {
		if ($scope.selected) {
			$scope.eventContainers[$scope.selectedObjectType]
					.update($scope.selected);
			if ($scope.selectedObjectType == 'expanse') {
				Expanse.update($scope.selected, function(updated) {
					console.log('updated expanse');
				});
			} else if ($scope.selectedObjectType == 'transaction') {
				Transaction.update($scope.selected, function(updated) {
					console.log('updated transaction');
				});
			} else if ($scope.selectedObjectType == 'balance') {
				Balance.update($scope.selected, function(updated) {
					console.log('updated balance');
				});
			}
		}
	}

	/**
	 * Called when the timeline's visible range changes. Sets the chart's
	 * visilbe range to the same value.
	 */
	$scope.timelineRangeChanged = function() {
		var range = $scope.timeline.getVisibleChartRange();
		if ($scope.chart) {
			$scope.chart.setVisibleChartRange(range.start, range.end);
		}
	}
	

	/**
	 * Called when the chart's visible range changes. Sets the timeline position
	 * to the same value
	 */
	$scope.chartRangeChanged = function() {
		var range = $scope.chart.getVisibleChartRange();
		$scope.timeline.setVisibleChartRange(range.start, range.end);
	}

	var timeout = null;
	var debounceSaveUpdates = function(newVal, oldVal) {
		if (oldVal != null) {
			if (timeout) {
				$timeout.cancel(timeout)
			}
			timeout = $timeout(saveUpdates, 400); // 1000
			// = 1
			// second
		}
	};

	$scope.$watch('chart', function() {
		$scope.timelineRangeChanged();
	})
	
	$scope.$watch('selected', debounceSaveUpdates, [ Equality = true ]);

});
