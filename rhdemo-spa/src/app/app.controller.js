(function() {
	'use strict';


	angular
	.module('rhdemo')
	.controller('AppController', AppController);

	AppController.$inject = ['$rootScope', '$scope', '$cookies','$state', '$window', 'authService', '$stateParams'];
	
	function AppController($rootScope, $scope, $cookies, $state, $window, authService, $stateParams) {

		/**  
		 * Sidebar Toggle & Cookie Control
		 */
		var mobileView = 992;

		$scope.init = function(){

			console.log($state.current);

	      if ($state.current.name === 'departamentoman' && $stateParams.id){ 
	      	console.log($rootScope);
	      	console.log($scope);
	        $scope.edit($stateParams.id);    
	      } 
	     
	    }

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
			authService.logout()  
			.then(function() { // not logged
				$window.location.href = '/';   
			});

		};

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