// directives.js

app.directive('timeline', function($rootScope) {

	return {
		scope : false,
		link : function(scope, element, attrs) {

			var idToIndex = {};
			
			console.log("timeline binded");
			console.log(scope);
			console.log(google);

			// Set callback to run when API is loaded
			// google.setOnLoadCallback(drawVisualization);

			// Called when the Visualization API is loaded.
			function drawVisualization() {
				// Create and populate a data table.
				var data = new google.visualization.DataTable();
				data.addColumn('datetime', 'start');
				data.addColumn('datetime', 'end');
				data.addColumn('string', 'content');
				
			
				var options = {
					width : "100%",
					height : "500px",
					layout : "box",
					editable : true,
					selectable : true,
					axisOnTop : true,
					style : "box",
					showCustomTime: true,
					animate: true,
					animateZoom: true,
					cluster: true
				};

				// Instantiate our timeline object.
				var timeline = new links.Timeline(document
						.getElementById('timeline'), options);
				scope.timeline = timeline;
				
				// Draw our timeline with the created data and options
				timeline.draw(data);
				for (var i in scope.eventContainers) {
					var container = scope.eventContainers[i];
					idToIndex[container.getObjectType()] = {};
					
				
					container.addListener({
						
						objectAdded: function(object) {
							timeline.addItem({
								start : new Date(object.time),
								content : this.getContentText(object),
								className: this.className,
								uniqueId : object.uniqueId,
								objectType: this.getObjectType(),
								
							});
							var index = data.getNumberOfRows() - 1;
							var indexMap = idToIndex[this.getObjectType()];
							indexMap[object.uniqueId] = index;
						},
						
						objectUpdated: function(object) {
							var indexMap = idToIndex[this.getObjectType()];
							var index = indexMap[object.uniqueId];
							var item = timeline.getItem(index);
							if (item.content != this.getContentText(object)) {
								timeline.changeItem(index, {
									content: this.getContentText(object)
								})
							}
						},
						
						objectRemoved: function(object) {
							var indexMap = idToIndex[this.getObjectType()];
							var index = indexMap[object.uniqueId];
							timeline.deleteItem(index);
							indexRemoved(index);
						},
						
						objectSelected: function(object) {
							var indexMap = idToIndex[this.getObjectType()];
							var index = indexMap[object.uniqueId];
							timeline.selectItem(index);
							onSelect();
						},
						batchUpdateFinished: function() {
							
						}
					});
				}
				
				/**
				 * Decrements all indexes larger than the removed index.
				 */
				function indexRemoved(removedIndex) {
					for (var objType in idToIndex) {
						var indexMap = idToIndex[objType];
						for (var i in indexMap) {
							var index = indexMap[i];
							if (index > removedIndex) {
								indexMap[i] = index - 1;
							}
						}
					}
				}
				
				
//				for ( var i in scope.timelineAdapters) {
//					var adapter = scope.timelineAdapters[i];
//					scope.$watch(function() {
//						return adapter.list
//					}, function(newItems, oldItems) {
//						for (var key in newItems) {
//							if (oldItems == null
//									|| ! (key in oldItems)) {
//								timeline.addItem(adapter.get(newItems[key]));
//								var count = data.getNumberOfRows();
//								timeline.setSelection([ {
//									'row' : count - 1
//								} ]);
//							}
//						}
//					}, true);
//				}

				var onSelect = function() {
					var sel = timeline.getSelection();
					var item = null;
					if (sel.length) {
						if (sel[0].row != undefined) {
							var row = sel[0].row;
							item = timeline.getItem(row);
						} 
					}
					if (scope.onTimelineSelect) {
						scope.onTimelineSelect(item);
					}
				}
				function ondelete() {
					timeline.cancelDelete();
				}
				function onadd() {
					timeline.cancelAdd();
				}
				google.visualization.events.addListener(timeline, 'timechange', scope.customTimeChange);
				google.visualization.events.addListener(timeline, 'rangechange', scope.timelineRangeChanged);
				google.visualization.events.addListener(timeline, 'delete', ondelete);
				google.visualization.events.addListener(timeline, 'add', onadd);
				google.visualization.events.addListener(timeline, 'select',
						onSelect);

				google.visualization.events.addListener(timeline, 'change',
						function() {
					var sel = timeline.getSelection();
					if (sel.length) {
						if (sel[0].row != undefined) {
							var row = sel[0].row;
							var item = timeline.getItem(row);
							if (scope.onTimelineEventMoved) {
								scope.onTimelineEventMoved(item);
							}
						}
					}
				});

				if (scope.timelineReady) {
					scope.timelineReady();
				}

			}

			$rootScope.$on(attrs.relinkEvent, drawVisualization());
			
		}
	}

})