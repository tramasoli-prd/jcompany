angular.module('cronos')
    .directive('pesquisaGeralAcoes', ['sentencaService', function (sentencaService) {
    return {
        restrict:'AE',
        templateUrl:'./views/directives/pesquisaGeralAcoes.html',

        link: function(scope, element, attrs){
            scope.sentencaVisible = function () {
                return sentencaService.isVisible();
            };
        }
    };
}]);
