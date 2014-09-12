/**
 * Service defining the main application navigation menu.
 */
app.service('MainMenu', [ function() {
	this.entries = [];
	this.rightMenu = [];
	
	this.appName = 'My App';
	this.appNameRef = '';
	
	this.add = function(index, name, sref) {
		this.entries[index] = {
			name : name,
			sref : sref,
			hidden: false
		};
	};
	
	this.addRight = function(index, name, sref) {
		this.rightMenu[index] = {
			name: name,
			sref: sref,
			hidden: false
		};
	};
	this.hideMenuItem = function(index) {
		this.entries[index].hidden = true;
	}
	this.hideRight = function(index) {
		this.rightMenu[index].hidden = true;
	}
} ]);