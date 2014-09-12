app.factory('EventContainer', function() {

	
	var EventContainer = function(objectType) {
		this.objectType = objectType;
		this.listeners = [];
		this.objects = {};
		this.className = 'blue';
		this.batchUpdate = false;
	}

	/**
	 * Returns the type of objects contained in this container.
	 */
	EventContainer.prototype.getObjectType = function() {
		return this.objectType;
	}

	/**
	 * Returns an event by its ID.
	 */
	EventContainer.prototype.getById = function(uniqueId) {
		return this.objects[uniqueId];
	}

	/**
	 * Adds an event to this container.
	 */
	EventContainer.prototype.add = function(object) {
		this.objects[object.uniqueId] = object;
		for (var i in this.listeners) {
			var listener = this.listeners[i];
			listener.objectAdded(object);
		}
	}

	EventContainer.prototype.setBatchUpdate = function(update) {
		this.batchUpdate = update;
		if (update == false) {
			for (var i in this.listeners) {
				var listener = this.listeners[i];
				listener.batchUpdateFinished();
			}
		}
	}
	
	/**
	 * Updates an object stored in this container.
	 */
	EventContainer.prototype.update = function(object) {
		for (var i in this.listeners) {
			var listener = this.listeners[i];
			listener.objectUpdated(object);
		}
	}
	
	/**
	 * Remove an object from the container.
	 */
	EventContainer.prototype.remove = function(object) {
		this.objects[object.uniqueId] = null;
		for (var i in this.listeners) {
			var listener = this.listeners[i];
			listener.objectRemoved(object);
		}
	}
	
	EventContainer.prototype.addListener = function(listener) {
		// bind callbacks
		listener.objectAdded = listener.objectAdded.bind(this);
		listener.objectUpdated = listener.objectUpdated.bind(this);
		listener.objectRemoved = listener.objectRemoved.bind(this);
		listener.objectSelected = listener.objectSelected.bind(this);
		listener.batchUpdateFinished = listener.batchUpdateFinished.bind(this);
		this.listeners.push(listener);
	}
	
	/**
	 * Selects the specified object
	 */
	EventContainer.prototype.select = function(object) {
		for (var i in this.listeners) {
			var listener = this.listeners[i];
			listener.objectSelected(object);
		}
	}
	
	EventContainer.prototype.getAllObjects = function() {
		return this.objects;
	}
	
	EventContainer.prototype.removeListener = function(listener) {
		this.listeners.remove(listener);
	}
	
	/**
	 * Returns the text to display for a given object
	 */
	EventContainer.prototype.getContentText = function(object) {
		return object.name;
	}
	
	return EventContainer;
})