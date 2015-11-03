angular.module('cronos')
    .directive("appModal", ['$rootScope', 'modalService', function( $rootScope, modalService ) {
        'use strict';

        return {
            scope: true,
            restrict: 'AE',
            templateUrl: './views/templates/modal.html',

            link: function( scope, element, attributes ) {

                scope.subview = null;

                element.on( "click", function handleClickEvent( event ) {
                    if ( element[ 0 ] !== event.target )
                        return;

                    scope.$apply( modalService.reject );
                });

                $rootScope.$on( "modalService.open", function handleModalOpenEvent( event, modalType ) {
                    scope.modalOpen = true;
                    scope.subview = modalType;
                });

                $rootScope.$on( "modalService.close", function handleModalCloseEvent( event ) {
                    scope.modalOpen = false;
                    scope.subview = null;
                });
            }
        };
}]);
