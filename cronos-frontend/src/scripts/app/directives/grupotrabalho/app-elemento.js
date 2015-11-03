angular
    .module('cronos')
    .directive('appElemento',['authService', 'modalService', 'Utils', function (authService, modalService, Utils) {
        return {
            restrict: 'AE',
            scope: {
                node: '=item',
                preview: '=',
                readonly: "=",
                elementos: "=elementos",
                tipoArvore: "=tipoarvore",
                iterationIndex: '@iterationindex'
            },
            templateUrl: './views/directives/grupotrabalho/app-elemento.html',
            link: function(scope, element, attrs){

                scope.hasWriteAccess = !scope.readonly && authService.hasWriteAccess();

                scope.sanitizeCopiedData = function (event) {
                    var selectedText = Utils.getDocumentSelection(true),
                        clipboardData = (event.originalEvent || event).clipboardData,
                        sanitizedText = Utils.sanitizeHTMLText(selectedText);

                    if (sanitizedText) {
                        clipboardData.setData('text/plain', sanitizedText);
                        event.preventDefault();
                    }
                };

                scope.sanitizePastedData = function ($html) {
                    $html = Utils.sanitizeXSS($html);
                    return $html;
                };

            },
            controller: function($scope){
                $scope.hasWriteAccess = !$scope.readonly && authService.hasWriteAccess();

                $scope.delete = function(id) {
                    event.preventDefault();
                    event.stopPropagation();
                    modalService.open('confirm', {
                        message: 'Tem certeza de que deseja excluir esse item?',
                        confirmButton: 'Confirmar',
                        cancelButton: 'Cancelar'
                    })
                    .then( function(){
                        $scope.elementos.delete($scope.node);
                    });
                };

                $scope.swap = function(index, pos){
                    event.preventDefault();
                    event.stopPropagation();

                    return $scope.elementos.swap(index, pos);
                };
            }

        };
    }]);
