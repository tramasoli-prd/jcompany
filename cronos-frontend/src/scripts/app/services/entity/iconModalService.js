angular.module('cronos')
    .service('iconModalService', ['$modal', function iconModalServiceFactory($modal) {
        'use strict';

        var modalDefaults = {
            backdrop: true,
            keyboard: true,
            modalFade: true,
            animation: true
        };

        var modalOptions = {
            closeButtonText: 'Cancelar',
            actionButtonText: 'OK',
            headerText: '',
            headerTextIn: ''
        };

        this.showModal = function (customModalDefaults, customModalOptions, controllerDefault) {
            if (!customModalDefaults) customModalDefaults = {};
            customModalDefaults.backdrop = 'static';
            return this.show(customModalDefaults, customModalOptions, controllerDefault);
        };

        this.show = function (customModalDefaults, customModalOptions, controllerDefault) {
            //Create temp objects to work with since we're in a singleton service
            var tempModalDefaults = {};
            var tempModalOptions = {};

            //Map angular-ui modal custom defaults to modal defaults defined in service
            angular.extend(tempModalDefaults, modalDefaults, customModalDefaults);

            //Map modal.html $scope custom properties to defaults defined in service
            angular.extend(tempModalOptions, modalOptions, customModalOptions);

            if (!tempModalDefaults.controller) {
                tempModalDefaults.controller = function ($scope, $modalInstance) {
                    $scope.modalOptions = tempModalOptions;
                    $scope.modalOptions.ok = function (result) {
                        $modalInstance.close(result);
                    };
                    $scope.modalOptions.close = function (result) {
                        $modalInstance.dismiss('cancel');
                    };
                };
            }

            return $modal.open(tempModalDefaults).result;
        };

    }]);
