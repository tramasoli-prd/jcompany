angular
    .module('cronos')
    .directive('appFabModelo', ['authService', 'tipoElementoService', 'notificationService', 'Utils',
    function (authService, tipoElementoService, notificationService, Utils) {
        return {
            restrict: 'AE',
            scope: {
                model: '=',
                idCategoria: "=idcategoria",
                readonly: '='
            },
            templateUrl: './views/directives/actionbutton/app-fab-modelo.html',
            link: function (scope, element, attrs) {

                // --------------------------
                // Directive Configuration
                // --------------------------
                var ShortcutButton = function(transition, unique, disabled, icon) {
                    return {
                        transition: parseFloat(transition),
                        disabled: disabled,
                        bgcolor: '#2196F3',
                        unique: unique,
                        icon: icon
                    };
                };

                var config = {
                    getKeyByID: false,
                    buttons: {
                        E: ShortcutButton('0.55', true, false, 'fa-hand-o-up'),
                        R: ShortcutButton('0.45', true, false, 'fa-file-text-o'),
                        F: ShortcutButton('0.35', true, false, 'fa-th-list'),
                        D: ShortcutButton('0.25', true, false, 'fa-bell'),
                        V: ShortcutButton('0.15', false, false, 'fa-users'),
                        T: ShortcutButton('0.10', false, false, 'fa-pencil')
                    },
                    primary: {
                        bgcolor: '#4DADD6',
                        sizeclass: 'fa-lg' // 'fa-lg' ou 'fa-2x'
                    },
                    stack: {
                        sizeclass: 'fa-sm' // 'fa-sm' ou 'fa-stack-1x'
                    },
                    tooltip: { // 'bottom', 'top', 'left' or 'right'
                        position: 'top'
                    },
                    list: { // 'inline' or 'stack'
                        position: 'inline'
                    }
                };

                var keyComparator = function(b) {
                    if (!Utils.isUndefinedOrNull(b[key]))
                        return b[key];
                };

                var getButtonReference = function (key) {
                    return (config.buttons[key] || _.find(config.buttons, keyComparator));
                };

                var loadData = function () {
                    tipoElementoService.all().success( function ( response ) {
                        var tipoElementoOptions = [];

                        if (config.getKeyByID) {
                            response.forEach( function ( el ) {
                                tipoElementoOptions[el.id] = { id: el.id, nomeItem: el.nomeItem };
                            });
                        } else {
                            response.forEach( function ( el ) {
                                tipoElementoOptions[el.nomeItem.capitalize().first()] = { id: el.id, nomeItem: el.nomeItem };
                            });
                        }

                        config.buttons.map( function ( key, obj ) {
                            obj.tipoElemento = tipoElementoOptions[key];
                        });
                    });
                };

                var startupVariables= function() {
                    scope.configuration = config;
                };

                // --------------------------
                // Scope Functions
                // --------------------------
                scope.hasWriteAccess = function() {
                    return (!scope.readonly && authService.hasWriteAccess());
                };

                scope.action = function ( key ) {
                    var button = getButtonReference(key);

                    if (button.disabled) return;

                        button.disabled = button.unique;

                    var novoElemento = {
                        "usuarioAtualizacao": authService.getUsername(),
                        "titulo": null,
                        "ordem": null,
                        "icone": null,
                        "texto": null,
                        "idCategoria": scope.idCategoria,
                        "tipoElemento": button.tipoElemento,
                        "idTipoElemento": button.tipoElemento.id
                    };
                    scope.model.push(novoElemento);
                    scope.active = !scope.active;
                };

                // --------------------------
                // Directive Startup
                // --------------------------
                loadData();
                startupVariables();

                element.click( function ( event ) {
                    event.preventDefault();
                    if (event.target.parentNode.hasClass('disabled'))
                        event.stopPropagation();
                });

                scope.$watchCollection('model', function (newValue, oldValue) {
                    config.buttons.map(function ( key, value ) {
                        value.disabled = false;
                    });
                    scope.model.forEach( function ( el ) {
                        var key = config.getKeyByID ? el.tipoElemento.id : el.tipoElemento.nomeItem.capitalize().first();
                        var button = getButtonReference(key);
                            button.disabled = button.unique;
                    });
                });
            }
        };
    }]);
