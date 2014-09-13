app.directive('chart', function($rootScope) {
	return {
		scope : false,
		link : function(scope, element, attrs) {

			function draw() {
				
				var options = {
					"width" : "100%",
					"height" : "270px",
					"autoDataStep": true,
					"legend" : {
						"visible": false
					},
					"lines": [
	                    {color: "#f15c78", width: 2}
		            ]
				};

				function updateGraph() {
					var data = new google.visualization.DataTable();
					data.addColumn('datetime', 'time');
					data.addColumn('number', 'Balance');
					balanceGraph.sort(function(a, b) {
						a = new Date(a.time);
					    b = new Date(b.time);
					    return a>b ? -1 : a<b ? 1 : 0;
					})
					
					for (var i in balanceGraph) {
						var obj = balanceGraph[i];
						
						data.addRow([new Date(obj.time), obj.amount]);
						if (i < balanceGraph.length - 1) {
							var next = balanceGraph[eval(i) + 1]
							var nextTime = new Date(obj.time);
							nextTime.setSeconds(nextTime.getSeconds() + 1);
							data.addRow([nextTime, next.amount]);
						}
					}
					var graph = new links.Graph(document
							.getElementById('chart'));
					
					google.visualization.events.addListener(graph, 'rangechange', scope.chartRangeChanged);
					scope.chart = graph;
					graph.draw(data, options);
				}
				
				var balanceGraph = [];
				
				for (var i in scope.eventContainers) {
					var container = scope.eventContainers[i];
					
					container.addListener({
						objectAdded: function(object) {
							if (this.getObjectType() == 'balance') {
								
								balanceGraph.push(object);
								if (this.batchUpdate == false) {
									updateGraph();
								}
							}
						},
						objectUpdated: function(object) {
						},
						objectRemoved: function(object) {
							
						},
						objectSelected: function(object) {
							
						},
						batchUpdateFinished: function() {
							updateGraph();
						}
					});
					
				}
					
			}

			$rootScope.$on(attrs.relinkEvent, draw());
		}
	}
})