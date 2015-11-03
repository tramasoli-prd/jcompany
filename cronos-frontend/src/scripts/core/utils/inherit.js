/*

var BaseService = $class();

var ChildService = $class(BaseService, {
	
	constructor: function(){
		// super constructor call
		BaseService.call(this);
	},
	
	someMethod: function(){
		return 'ChildService';
	},

	toString: function(){
		return this.someMethod();
	}
});

var GrandchildService = ChildService.extends({
	
	constructor: function(){
		// super constructor call
		ChildService.call(this);
	},

	// override
	someMethod: function(){
		// super method call
		return 'GrandchildService and ' + ChildService.prototype.someMethod.call(this);
	}
});

*/
(function(scope) {

    function inheritClass(superClass, overrides) {
        // clone superclass prototype!        
        var newClass, classBody = Object.create(superClass.prototype);
        // resolve constructor
        if (overrides.hasOwnProperty('constructor')) {
            newClass = overrides.constructor;
        } else {
            newClass = overrides.constructor = function() {
                superClass.apply(this, arguments);
            };
        }
        // override properties and methods.
        for (var name in overrides) {
            if (overrides.hasOwnProperty(name)) {
                classBody[name] = overrides[name];
            }
        }
        // Helper to extend class
        newClass.extends = function(classBody) {
            return inheritClass(newClass, classBody);
        };
        // Reference to superclass, used in superCall
        newClass.$superClass = superClass;
        // reference inherited class body!
        newClass.prototype = classBody;
        newClass.prototype.constructor = newClass;
        // return created class.
        return newClass;
    }

    function createClass(superClass, classBody) {
        if (arguments.length <= 1) {
            classBody = superClass || {};
            superClass = Object;
        }
        return inheritClass(superClass, classBody);
    }

    // export function as $class!
    scope.$class = createClass;

})(window);