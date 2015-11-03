angular.module('cronos')
    .service("modalService", ['$modal','$rootScope', '$q', function( $modal, $rootScope, $q ) {
        'use strict';

        var modal = {
            deferred: null,
            params: {
                headerText: defaults().headerText,
                headerTextIn: defaults().headerTextIn,
                abortButton: defaults().abortButton,
                cancelButton: defaults().cancelButton,
                confirmButton: defaults().confirmButton,
                abortButtonActive: defaults().abortButtonActive,
                contentPreviewPath: defaults().contentPreviewPath,
                contentPreviewModel: defaults().contentPreviewModel,
                contentPreviewController: defaults().contentPreviewController
            }
        };

        return({
            open: open,
            params: params,
            defaults: defaults,
            proceedTo: proceedTo,
            abort: abort,
            reject: reject,
            resolve: resolve
        });

        function defaults() {
            return {
                backdrop: true,
                keyboard: true,
                headerText: null,
                headerTextIn: null,
                abortButton: 'Abortar',
                cancelButton: 'Cancelar',
                confirmButton: 'Ok',
                abortButtonActive: false,
                contentPreviewPath: null,
                contentPreviewModel: null,
                contentPreviewController: null
            };
        }

        function open( type, params, pipeResponse ) {
            var previousDeferred = modal.deferred;

            modal.deferred = $q.defer();
            modal.params = params;

            if ( previousDeferred && pipeResponse ) {
                modal.deferred.promise
                    .then( previousDeferred.resolve,
                           previousDeferred.reject );
            } else if ( previousDeferred ) {
                previousDeferred.reject();
            }

            $rootScope.$emit( "modalService.open", type );

            return( modal.deferred.promise );
        }

        function params() {
            return( modal.params || {} );
        }

        function proceedTo( type, params ) {
            return( open( type, params, true ) );
        }

        function abort() {
            if ( ! modal.deferred ) {
                return;
            }
            modal.deferred.notify( 'abort' );
            modal.deferred = modal.params = null;

            $rootScope.$emit( "modalService.close" );
        }

        function reject( reason ) {
            if ( ! modal.deferred ) {
                return;
            }
            modal.deferred.reject( reason );
            modal.deferred = modal.params = null;

            $rootScope.$emit( "modalService.close" );
        }

        function resolve( response ) {
            if ( ! modal.deferred ) {
                return;
            }
            modal.deferred.resolve( response );
            modal.deferred = modal.params = null;

            $rootScope.$emit( "modalService.close" );
        }


    }]);
