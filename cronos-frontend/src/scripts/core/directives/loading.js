angular.module('cronos').directive('appLoading', function() {
    return {
        restrict: 'AE',
        scope: {
        	loading: '='
        },
        // templateUrl: 'views/directives/app/loading.html',
        template: '<div class="loading-container" data-ng-if="loading" data-ng-animate-children><div class="loading fa-2x"><i class="fa fa-circle-o-notch fa-spin"></i><span>carregando...</span></div></div>',
        controller: function($scope) {
        }
    };
});
