// -------------------------------------------------- //
// Alert modal window.
// -------------------------------------------------- //
angular.module('cronos')
    .controller("AlertModalController", ['$scope', 'modalService', function( $scope, modalService ) {
        'use strict';

        $scope.message = modalService.params().message;
        $scope.confirmButton = modalService.params().confirmButton;
        $scope.cancelButton = modalService.params().cancelButton;
        $scope.cancel = modalService.resolve;

        // EXEMPLO COM MODAIS ENCADEADAS
        // $scope.jumpToConfirm = function() {
        //     modalService.proceedTo(
        //         "confirm",
        //         {
        //             message: "I just came from Alert - doesn't that blow your mind?",
        //             confirmButton: "Eh, maybe a little",
        //             cancelButton: "Oh please"
        //         }
        //     )
        //     .then(
        //         function handleResolve() {
        //             console.log( "Piped confirm resolved." );
        //         },
        //         function handleReject() {
        //             console.warn( "Piped confirm rejected." );
        //         }
        //     );
        // };
    }]);

// -------------------------------------------------- //
// Confirm modal window.
// -------------------------------------------------- //
angular.module('cronos')
    .controller( "ConfirmModalController", [ '$scope', 'modalService', function( $scope, modalService ) {
        'use strict';

        $scope.message = modalService.params().message;
        $scope.confirmButton = modalService.params().confirmButton;
        $scope.cancelButton = modalService.params().cancelButton;
        $scope.abortButton = modalService.params().abortButton;
        $scope.abortButtonActive = modalService.params().abortButtonActive;
        $scope.confirm = modalService.resolve;
        $scope.cancel = modalService.reject;
        $scope.abort = modalService.abort;
    }]);

// -------------------------------------------------- //
// Preview modal window.
// -------------------------------------------------- //
angular.module('cronos')
    .controller("PreviewModalController", ['$scope', '$element', 'modalService', function( $scope, $element, modalService ) {
        'use strict';
        $scope.message = modalService.params().message;
        $scope.headerText = modalService.params().headerText;
        $scope.headerTextIn = modalService.params().headerTextIn;

        $scope.confirmButton = modalService.params().confirmButton;
        $scope.cancelButton = modalService.params().cancelButton;
        $scope.confirm = modalService.resolve;
        $scope.cancel = modalService.reject;

        $scope.contentPreviewPath = modalService.params().contentPreviewPath;
        $scope.contentPreviewModel = modalService.params().contentPreviewModel;
        $scope.contentPreviewController = modalService.params().contentPreviewController;
    }]);

// -------------------------------------------------- //
// Preview GrupoTrabalho modal window.
// -------------------------------------------------- //
angular.module('cronos')
    .controller("PreviewGtModalController", ['$scope', '$element', 'modalService', function( $scope, $element, modalService ) {
        'use strict';
        $scope.previewGtId = modalService.params().previewGtId;
        $scope.confirmButton = modalService.params().confirmButton;
        $scope.cancelButton = modalService.params().cancelButton;
        $scope.cancel = modalService.resolve;
    }]);
