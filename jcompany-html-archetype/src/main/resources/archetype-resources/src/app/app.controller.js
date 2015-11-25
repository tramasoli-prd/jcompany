#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
	(function() {
		'use strict';


		angular
		.module('${artifactId}')
		.controller('AppController', AppController);

		AppController.$inject = ['$scope', '$cookies','$state', '$window', 'PlcAuthService', '$stateParams', 'PlcMenuLoader', 'PlcMenu'];

		function AppController($scope, $cookies, $state, $window, PlcAuthService, $stateParams, PlcMenuLoader, PlcMenu) {

			/**  
			 * Sidebar Toggle & Cookie Control
			 */
			var mobileView = 992;

			$scope.getWidth = function() {
				return window.innerWidth;
			};

			$scope.$watch($scope.getWidth, function(newValue) {
				if (newValue >= mobileView) {
					if (angular.isDefined($cookies.get('toggle'))) {
						$scope.toggle = ! $cookies.get('toggle') ? false : true;
					} else {
						$scope.toggle = true;
					}
				} else {
					$scope.toggle = false;
				}

			}); 

			$scope.toggleSidebar = function() {
				$scope.toggle = !$scope.toggle;
				$cookies.put('toggle', $scope.toggle);
			};

			window.onresize = function() {
				$scope.$apply();
			};

			$scope.logout = function() {  
				PlcAuthService.logout()  
				.then(function() { // not logged
					$window.location.href = '/';   
				});

			};

			activate();

			////////////////

			function activate() {
				// Load menu from json file
				// ----------------------------------- 
				PlcMenu.addMenuPath(PlcMenuLoader.getMenuPaths());
				$scope.menuItems = PlcMenu.getMenuArray();
			}



			$scope.profileA = function() {  
				document.getElementById('profile').className= "item dropdown open";
			};

			$scope.profileI = function() {  
				document.getElementById('profile').className= "item dropdown";
			};

			$scope.notificationA = function() {  
				document.getElementById('notification').className= "item dropdown open";
			};

			$scope.notificationI = function() {  
				document.getElementById('notification').className= "item dropdown";
			};

		}

	})();