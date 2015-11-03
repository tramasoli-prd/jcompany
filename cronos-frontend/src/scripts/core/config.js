angular.module('cronos').config(['$controllerProvider', '$provide', function ($controllerProvider, $provide) {

    // -------------------------------------------------
    // DECORATOR
    // -------------------------------------------------
    $provide.decorator('taOptions', ['taRegisterTool', '$delegate', 'tokenService', 'authService', 'alegacaoGrupoTrabalhoService', function(taRegisterTool, taOptions, tokenService, authService, alegacaoGrupoTrabalhoService) {
        taOptions.toolbar[4] = [];

        // Adiciona a opcao para insercao de tokens que serao substituidos por textos pre-definidos
        var dropdownTokens = new StringBuilder();
        dropdownTokens.append('<div class="wrap-dropdown-token">');
        dropdownTokens.append('<span class="btn btn-default btn-dropdown-token dropdown-toggle" id="dropdowntoken" ng-mouseover="action($event, null)" ng-disabled="isDisabled()">Token&nbsp;<i class="fa fa-caret-down"></i></span>');
        dropdownTokens.append('<ul class="dropdown-token dropdown-menu" role="menu">');
        dropdownTokens.append('<li role="presentation" data-ng-repeat="option in options"><button type="button" class="btn btn-flat checked-dropdown" role="menuitem" tabindex="-1" data-ng-click="action($event, option.label)">{{option.nome}}</button></li>');
        dropdownTokens.append('</ul>');
        dropdownTokens.append('</div>');

        taRegisterTool('selectToken', {
            display: dropdownTokens.toString(),
            action: function(event, token) {
                if (!token) {
                    this.options = tokenService.getListaTokens();
                }
                if (token && typeof token !== 'function') {
                    var editor = this.$editor();
                    if (angular.isObject(editor) && editor && editor.displayElements) {
                        editor.displayElements.text[0].focus();
                        editor.wrapSelection('insertText', token);
                    }
                }
            },
            activeState: function() {
                return false;
            },
            options: tokenService.getListaTokens()
        });
        taOptions.toolbar[4].push('selectToken');

        // Adiciona a opcao para insercao de quem alega
        var dropdownAlega = new StringBuilder();
        dropdownAlega.append('<div class="wrap-dropdown-alega">');
        dropdownAlega.append('<span class="btn btn-default btn-dropdown-alega dropdown-toggle" id="dropdownalega" ng-mouseover="action($event, null)" ng-disabled="isDisabled()">Quem Alega&nbsp;<i class="fa fa-caret-down"></i></span>');
        dropdownAlega.append('<ul class="dropdown-alega dropdown-menu" role="menu">');
        dropdownAlega.append('<li role="presentation" data-ng-repeat="option in options"><button type="button" class="btn btn-flat checked-dropdown" role="menuitem" tabindex="-1" data-ng-click="action($event, option.tipoAlegacao.label)">{{option.tipoAlegacao.quemAlega}}</button></li>');
        dropdownAlega.append('</ul>');
        dropdownAlega.append('</div>');

        taRegisterTool('selectAlega', {
            display: dropdownAlega.toString(),
            action: function(event, alega) {
                if (!alega) {
                    this.options = alegacaoGrupoTrabalhoService.getAlegacoesPreenchidas();
                }
                if (alega && typeof alega !== 'function') {
                    var editor = this.$editor();
                    if (angular.isObject(editor) && editor && editor.displayElements) {
                        editor.displayElements.text[0].focus();
                        editor.wrapSelection('insertText', alega);
                    }
                }
            },
            activeState: function() {
                return false;
            },
            options: []
        });
        taOptions.toolbar[4].push('selectAlega');

        // Adiciona a opcao de expandir a area de texto para melhor visualização em tela cheia
        var expandedArea = false;
        taRegisterTool('expandArea', {
            iconclass: "fa fa-expand",
            action: function() {
                var self = this;
                if (!expandedArea) {
                    setTimeout(function() {
                        self.$element[0].parentNode.parentNode.parentNode.classList.add("expanded-editor");
                        self.$element[0].childNodes[0].classList.remove('fa-expand');
                        self.$element[0].childNodes[0].classList.add('fa-compress');
                    }, 200);
                } else {
                    setTimeout(function() {
                        self.$element[0].parentNode.parentNode.parentNode.classList.remove("expanded-editor");
                        self.$element[0].childNodes[0].classList.remove('fa-compress');
                        self.$element[0].childNodes[0].classList.add('fa-expand');
                    }, 200);
                }
                expandedArea = !expandedArea;
                return self.$editor().displayElements.text[0].focus();
            },
            activeState: function() {
                return false;
            }
        });
        taOptions.toolbar[4].push('expandArea');

        //--
        var dropdownFontName = new StringBuilder();
        dropdownFontName.append('<div class="wrap-dropdown-fontname">');
        dropdownFontName.append('<span class="btn btn-default btn-dropdown-fontname dropdown-toggle" id="dropdownfontname"><i class="fa fa-font"></i><i class="fa fa-caret-down"></i></span>');
        dropdownFontName.append('<ul class="dropdown-fontname dropdown-menu" role="menu">');
        dropdownFontName.append('<li role="presentation" data-ng-repeat="option in options"><button type="button" class="btn btn-flat checked-dropdown" role="menuitem" style="font-family: {{option.css}};" tabindex="-1" data-ng-click="action($event, option.css)"><i ng-if="option.active" class="fa fa-check"></i>{{option.name}}</button></li>');
        dropdownFontName.append('</ul>');
        dropdownFontName.append('</div>');

        taRegisterTool('fontName', {
            display: dropdownFontName.toString(),
            action: function(event, font) {
                if (font && typeof font !== 'function') {
                    var editor = this.$editor();
                    if (angular.isObject(editor) && editor && editor.displayElements) {
                        editor.displayElements.text[0].focus();
                        editor.wrapSelection('fontname', font);
                    }
                }
            },
            activeState: function() {
                return false;
            },
            options: [{
                name: 'Arial',
                css: 'Arial, Helvetica, sans-serif'
            }, {
                name: 'Arial Black',
                css: "'arial black', sans-serif"
            }, {
                name: 'Tahoma',
                css: 'tahoma, sans-serif'
            }, {
                name: 'Times New Roman',
                css: "'times new roman', serif"
            }, {
                name: 'Verdana',
                css: 'verdana, sans-serif'
            }, {
                name: 'Ecofont',
                css: 'Ecofont Vera Sans'
            } ]
        });
        taOptions.toolbar[4].push('fontName');

        //--
        var dropdownFontSize = new StringBuilder();
        dropdownFontSize.append('<div class="wrap-dropdown-fontname">');
        dropdownFontSize.append('<span class="btn btn-default btn-dropdown-fontname dropdown-toggle" id="dropdownfontsize"><i class="fa fa-text-height"></i>&nbsp;<i class="fa fa-caret-down"></i></span>');
        dropdownFontSize.append('<ul class="dropdown-fontname dropdown-menu" role="menu">');
        dropdownFontSize.append('<li role="presentation" data-ng-repeat="option in options"><button type="button" class="btn btn-flat checked-dropdown" role="menuitem" style="font-size: {{option.css}};" tabindex="-1" data-ng-click="action($event, option.value)"><i ng-if="option.active" class="fa fa-check"></i>{{option.name}}</button></li>');
        dropdownFontSize.append('</ul>');
        dropdownFontSize.append('</div>');

        taRegisterTool('fontSize', {
            display: dropdownFontSize.toString(),
            action: function(event, font) {
                if (font && typeof font !== 'function') {
                    var editor = this.$editor();
                    if (angular.isObject(editor) && editor && editor.displayElements) {
                        editor.displayElements.text[0].focus();
                        editor.wrapSelection('fontSize', font);
                    }
                }
            },
            activeState: function() {
                return false;
            },
            options: [ {
                name: 'Muito Pequeno',
                css: 'x-small',
                value : 2
            }, {
                name: 'Pequeno',
                css: 'small',
                value : 3
            }, {
                name: 'Medio',
                css: 'medium',
                value : 4
            }, {
                name: 'Grande',
                css: 'large',
                value : 5
            }, {
                name: 'Muito Grande',
                css: 'x-large',
                value : 6
            } ]
        });
        taOptions.toolbar[4].push('fontSize');

        //--
        var dropdownFontColor = new StringBuilder();
        dropdownFontColor.append('<div class="wrap-dropdown-fontname">');
        dropdownFontColor.append('<span class="btn btn-default btn-dropdown-fontname dropdown-toggle" id="dropdownfontcolor"><span style="margin-bottom:-3px;width:8px;display:inline-block">');
        dropdownFontColor.append('<i style="float:left;color:red; font-size:8px;" class="fa fa-square"></i><i style="float:left;color:blue; font-size:8px;" class="fa fa-square"></i></span>&nbsp;<i class="fa fa-caret-down"></i></span>');
        dropdownFontColor.append('<ul class="dropdown-fontname dropdown-fontcolor dropdown-menu" role="menu">');
        dropdownFontColor.append('<li role="presentation" data-ng-repeat="option in options"><button type="button" class="btn btn-flat checked-dropdown" role="menuitem" style="color: {{option.css}};" tabindex="-1" data-ng-click="action($event, option.css)"><i ng-if="option.active" class="fa fa-check"></i><i class="fa fa-square"></i></button></li>');
        dropdownFontColor.append('</ul>');
        dropdownFontColor.append('</div>');

        taRegisterTool('fontColor', {
            display: dropdownFontColor.toString(),
            action: function(event, font) {
                if (font && typeof font !== 'function') {
                    var editor = this.$editor();
                    if (angular.isObject(editor) && editor && editor.displayElements) {
                        editor.displayElements.text[0].focus();
                        editor.wrapSelection('foreColor', font);
                    }
                }
            },
            activeState: function() {
                return false;
            },
            options: [ {
                name: 'Vermelho',
                css: '#f00'
            }, {
                name: 'Azul',
                css: '#00f'
            }, {
                name: 'Preto',
                css: '#000'
            } ]
        });
        taOptions.toolbar[4].push('fontColor');

        return taOptions;
    }]);


    // -------------------------------------------------
    // FACTORY
    // -------------------------------------------------
    $provide.factory('registerController', function registerControllerFactory() {
        // Params:
        //   name: nome do controller
        //   constructor: constructor function
        // Usage: faca a injecao do factory 'registerController'
        //   depois basta chama-lo passando os paramentros, conforme abaixo.
        //
        // registerController('testCtrl', ['$scope',
        //   function testCtrl($scope) {
        //     $scope.foo = 'bar';
        // }]);
        return function registerController(name, constructor) {
            // Registra o construtor do controller como um service.
            $provide.factory(name + 'Factory', function () {
                return constructor;
            });
            // Registra o controller propriamente dito.
            $controllerProvider.register(name, constructor);
        };
    });
}]);
