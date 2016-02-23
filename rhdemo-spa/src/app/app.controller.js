(function() {
	'use strict';


	angular
	.module('rhdemo')
	.controller('AppController', AppController);
	
	/** @ngInject */
	function AppController($rootScope, $scope, $cookies, $state, $window, PlcAuthService, $stateParams, PlcMenuLoader, PlcMenu) {
		var vm = this;
		vm.toggle =  true;
		vm.isLogged = PlcAuthService.isLogged();
		vm.menuItems = [];
		
		/**  
		 * Sidebar Toggle & Cookie Control
		 */
		var mobileView = 992;

		vm.getWidth = function() {
			return window.innerWidth;
		};
		
		

		$scope.$watch(vm.getWidth, function(newValue) {
			if (newValue >= mobileView) {
				if (angular.isDefined($cookies.get('toggle'))) {
					vm.toggle = ! $cookies.get('toggle') ? false : true;
				} else {
					vm.toggle = true;
				}
			} else {
				vm.toggle = false;
			}

		}); 

		vm.toggleSidebar = function() {
			vm.toggle = !vm.toggle;
			$cookies.put('toggle', vm.toggle);
		}

		window.onresize = function() {
			$scope.$apply();
		}

		vm.logout = function() {  
			PlcAuthService.logout()  
			.then(function() { // not logged
				$state.go('login');   
			});

		}

		var activate = function () {
			// Load menu from json file
			// ----------------------------------- 
			PlcMenu.clear();
			var promisseMenu = PlcMenu.addMenuPath(PlcMenuLoader.getMenuPaths());
			promisseMenu.then(function(menus) {
				vm.menuItems = menus;
		    });
		}
		

		vm.profileShow = function() {  
			document.getElementById('profile').className= "item dropdown open";
		};

		vm.profileHide = function() {  
			document.getElementById('profile').className= "item dropdown";
		};

		vm.notificationShow = function() {  
			document.getElementById('notification').className= "item dropdown open";
		};

		vm.notificationHide = function() {  
			document.getElementById('notification').className= "item dropdown";
		};
		
		activate();

	}

})();