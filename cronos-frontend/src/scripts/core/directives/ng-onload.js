angular.module('cronos')
    .directive('ngOnload', [ function(){
        return {
            scope: {
                callBack: '&ngOnload'
            },
            link: function(scope, element, attrs){
                element.on('load', function(){
                    return scope.callBack();
                });
            }
        };
}]);