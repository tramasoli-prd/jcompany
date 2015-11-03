(function() {
    "use strict";

    // App bootstrap dependencies
    var app = angular.module('cronos', [
        'ui.bootstrap',
        'ui.sortable',
        'ui.bootstrap.tooltip',
        'ui-notification',
        'ui.bootstrap.modal',
        'ui.bootstrap.tabs',
        'colorpicker.module',
        'naif.base64',
        'ngRoute',
        // 'ngTable',
        'ngAnimate',
        'ngCookies',
        'ngMask',
        'ngMockE2E', // for mock rest
        'textAngular',
        'ng-context-menu',
        'ngDraggable'
    ]);

    // App bootstrap constants definitions
    app.constant('$contextUrl', '/');
    app.constant('$backendUrl', 'http://localhost:7001/cronos-service');

    // App bootstrap configuration
    app.config(["$backendUrl", "$httpProvider", "$routeProvider", "$locationProvider", "RestServiceProvider", function($backendUrl, $httpProvider, $routeProvider, $locationProvider, RestServiceProvider) {

        // REST should send cookie credentials
        $httpProvider.defaults.useXDomain = true;
        $httpProvider.defaults.withCredentials = true;
        delete $httpProvider.defaults.headers.common['X-Requested-With'];

        // Config Access Outside ngView
        $locationProvider.html5Mode(false);

        // Config REST base URL
        RestServiceProvider.setServiceUrl($backendUrl);

        // Config view routing
        $routeProvider
            .when('/', {
                templateUrl: 'views/pages/dashboard-view.html'
            })
            .when('/usuarios', {
                templateUrl: 'views/pages/usuario/usuario-view.html'
            })
            .when('/configuracoes', {
                templateUrl: 'views/pages/configuracoes/configuracoes-view.html'
            })
            .when('/pesquisa-geral', {
                templateUrl: 'views/pages/pesquisa-geral-view.html'
            })
            .when('/sentenca/:id', {
                templateUrl: 'views/pages/grupotrabalho/grupotrabalho-sentenca.html',
                controller : 'SentencaController',
                controllerAs : 'sentencaCt',
                resolve: {
                    loadData: ["sentencaService", "$route", function(sentencaService, $route){
                        return sentencaService.carregarSentenca($route.current.params.id);
                    }]
                }
            })
            .when('/grupo-trabalho/:tipo/:id?/:hash?', {
                templateUrl: 'views/pages/grupotrabalho/grupotrabalho-view.html'
            })
            .when('/acesso-negado', {
                templateUrl: 'views/pages/acesso-negado.html'
            })
            .otherwise({
                redirectTo: '/'
            });
    }]);

    // App bootstrap initialization
    app.run(["authService", "mockService", function(authService, mockService) {
        // Enable/Disable http mock
        mockService.mock({
            auth: false,
            usuario: false,
            grupoTrabalho: false
        });
        // Load authentication info
        authService.load();
    }]);
})();

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

angular
    .module('cronos')
    .controller('AppController', ['authService', '$window', '$scope', '$location', 'usuarioService', 'notificationService', 'iconesPersonalService', 'pesquisaGeralService', 'modalService', 'registerController', 'tokenService', 'sentencaService', 'Utils', 'gtService',
        function ApplicationController(authService, $window, $scope, $location, usuarioService, notificationService, iconesPersonalService, pesquisaGeralService, modalService, registerController, tokenService, sentencaService, Utils, gtService) {
        'use strict';

        var controller = this;

        var menuOpened = false,
            sentencaOpened = false;

        controller.pesquisa = null;
        controller.tipoPesquisa = "TU";
        controller.listaPesquisaGeralTopico = [];
        controller.listaPesquisaGeralModelo = [];
        controller.listaPesquisaGeralSentenca = [];
        // controller.loading = false;

        controller.loadingPesquisaGeral = function() {
            return pesquisaGeralService.isWorking();
        };

        controller.isMobile = function() {
            return $window.innerWidth<768;
        };

        controller.sentencaIsOpen = function() {
            return sentencaService.isOpen();
        };

        controller.sentencaIsLoaded = function() {
            return sentencaService.isLoaded();
        };

        controller.loadIndex = function() {
            sentencaService.setClose();
            $window.location.href = '#/';
        };

        controller.menuIsOpen = function() {
            return menuOpened;
        };

        controller.setOpenClose = function( status ) {
            if (!Utils.isUndefinedOrNull(status))
                menuOpened = status;
            else
                menuOpened = !menuOpened;
        };

        controller.isLogged = function() {
            return authService.isLogged();
        };

        controller.getUsername = function() {
            return authService.getName();
        };

        controller.isMagistrate = function() {
            return authService.isMagistrate();
        };

        controller.isAdmin = function() {
            return authService.isAdmin();
        };

        controller.hasWriteAccess = function() {
            return authService.hasWriteAccess();
        };

        controller.getPerfil = function() {
            return authService.getPerfil();
        };

        controller.logout = function() {
            authService
                .logout()
                .then(function() { // not logged
                    $window.location.href = 'login.html';
                });
        };

        // controller.changeView = function(view) {
        //     $location.path(view);
        // };

        controller.carregarCSS = function() {
            iconesPersonalService.css()
                .then(function(response){
                    Utils.generateDynamicCSS(response.data.css);
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.pesquisaGeral = function() {
            controller.listaPesquisaGeralTopico = [];
            controller.listaPesquisaGeralModelo = [];
            controller.listaPesquisaGeralSentenca = [];
            if(controller.pesquisa){
                pesquisaGeralService.pesquisarTopico(controller.pesquisa, controller.tipoPesquisa).then(function (response) {
                    var resultado = response.data;
                    if (resultado) {
                        resultado.forEach(function (elemento) {
                            controller.listaPesquisaGeralTopico.push(elemento);
                        });
                    }
                });
                pesquisaGeralService.pesquisarModelo(controller.pesquisa, controller.tipoPesquisa).then(function (response) {
                    var resultado = response.data;
                    if (resultado) {
                        resultado.forEach(function (elemento) {
                            controller.listaPesquisaGeralModelo.push(elemento);
                        });
                    }
                });
                $location.path("/pesquisa-geral");
            }
        };

        controller.showTooltip = function(item) {
            var valor = '';
            angular.forEach(item, function(value) {
                switch (value.tipo) {
                    case 'ET':
                        valor += 'Etiqueta: ';
                        break;
                    case 'EL':
                        valor += 'Elemento: ';
                        break;
                    case 'TI':
                        valor += 'Título: ';
                        break;
                }

                valor += value.label + ' <br/> ';
            });

            return valor;
        };

        $(window).resize(function(){
            $scope.$apply(function(){
                controller.setOpenClose(!controller.isMobile());
            });
        });

        controller.setOpenClose(!controller.isMobile());
        controller.carregarCSS();
    }]);

angular
    .module('cronos')
    .controller('DragDropController', ['authService',
        function MenuController(authService) {
        'use strict';

        var controller = this;

        //TODO Método a ser implementado quando a arvore for corrigida no banco
        controller.moveItem = function(data, nodo) {
            return "";
        };

        controller.hasWriteAccess = function() {
            return authService.hasWriteAccess();
        };



}]);

angular
    .module('cronos')
    .controller('MenuController', ['gtService',
        function MenuController(gtService) {
        'use strict';

        var controller = this;

        controller.isLoading = function() {
            return gtService.isWorking();
        };

        controller.carregarMenu = function () {
            gtService.carregarMenu();
        };

        controller.getMenu = function () {
            return gtService.getMenu();
        };

        controller.carregarMenu();
}]);

angular
    .module('cronos')
    .controller('ModalMenuController', ['$location', '$routeParams', 'iconModalService', 'etiquetaGTService', 'ciService', 'notificationService', '$window', '$contextUrl', 'authService', 'configService', 'sentencaService', 'gtService',
        function ModalMenuController($location, $routeParams, iconModalService, etiquetaGTService, ciService, notificationService, $window, $contextUrl, authService, configService, sentencaService, gtService) {

        var controller = this;

        controller.customFont = true;

        controller.items = [
            'fa-tag',             'fa-tags',
            'fa-pagelines',       'fa-wheelchair',     'fa-bank',              'fa-graduation-cap',    'fa-child',
            'fa-paw',             'fa-cube',           'fa-cubes',             'fa-car',
            'fa-cab',             'fa-tree',           'fa-life-ring',         'fa-bomb',              'fa-futbol-o',
            'fa-binoculars',      'fa-plug',           'fa-wifi',              'fa-cc-visa',           'fa-at',
            'fa-eyedropper',      'fa-birthday-cake',  'fa-area-chart',        'fa-bicycle',           'fa-bus',
            'fa-cart-plus',       'fa-diamond',        'fa-ship',              'fa-user-secret',       'fa-motorcycle',
            'fa-venus-mars',      'fa-whatsapp',       'fa-bed',               'fa-subway',            'fa-expeditedssl',
            'fa-battery-full',    'fa-battery-half',   'fa-balance-scale',     'fa-hand-paper-o',
            'fa-hand-scissors-o', 'fa-hand-spock-o',   'fa-internet-explorer', 'fa-television',        'fa-black-tie',
            'fa-glass',           'fa-music',          'fa-search',            'fa-heart',             'fa-star',
            'fa-user',            'fa-film',           'fa-home',              'fa-clock-o',           'fa-road',
            'fa-flag',            'fa-volume-off',     'fa-volume-up',
            'fa-book',            'fa-bookmark',       'fa-print',             'fa-camera',            'fa-bold',
            'fa-video-camera',    'fa-photo',          'fa-pencil',            'fa-adjust',            'fa-edit',
            'fa-check-square-o',  'fa-asterisk',       'fa-gift',              'fa-eye',               'fa-calendar',
            'fa-comment',         'fa-shopping-cart',  'fa-folder',            'fa-folder-open',       'fa-bar-chart',
            'fa-twitter-square',  'fa-facebook-square','fa-key',               'fa-cogs',              'fa-comments',
            'fa-trophy',          'fa-phone',          'fa-phone-square',      'fa-unlock',            'fa-credit-card',
            'fa-rss',             'fa-bullhorn',       'fa-globe',             'fa-wrench',            'fa-filter',
            'fa-briefcase',       'fa-users',          'fa-chain',             'fa-cloud',
            'fa-flask',           'fa-cut',            'fa-file-o',             'fa-copy',              'fa-paperclip',
            'fa-save',            'fa-truck',          'fa-google-plus-square','fa-envelope',
            'fa-gavel',           'fa-dashboard',      'fa-flash',
            'fa-sitemap',         'fa-umbrella',       'fa-lightbulb-o',       'fa-exchange',          'fa-bell',
            'fa-coffee',          'fa-cutlery',        'fa-ambulance',         'fa-medkit',            'fa-fighter-jet',
            'fa-beer',            'fa-gamepad',        'fa-eraser',            'fa-fire-extinguisher', 'fa-rocket',
            'fa-anchor',          'fa-pencil-square',  'fa-dollar',            'fa-youtube',           'fa-apple',
            'fa-android',         'fa-female',         'fa-archive',           'fc-cannabis',          'fc-mask'
        ];

        controller.openModalIcones = function ( tipoNodo, nodeId, label ) {

            authService.checarSessao();

            var modalOptions = {
                closeButtonText: 'Cancelar',
                actionButtonText: 'Salvar',
                headerText: 'SELECIONAR ÍCONE',
                headerLabel: ' - ' + label,
                headerTextIn: 'Selecione um ícone padrão ou carregue um de sua preferência:'
            };
            var controllerIcones = function ($scope, $modalInstance) {
                $scope.modalOptions = modalOptions;
                $scope.modalOptions.ok = function (result) {
                    var imageUpload = document.getElementById('imageUpload').value;

                    if ((!result.imagem && !imageUpload) && !result.selected) {
                        notificationService.error('ICONES_OBRIGATORIOS_020');
                    } else {
                        if (result.imagem && imageUpload) {
                            if(result.imagem.filesize > 10000000){
                                notificationService.error('ICONES_TAMANHO_MAXIMO_021');
                            }else{
                                $modalInstance.close(result);
                            }
                        }
                        else{
                            $modalInstance.close(result);
                        }
                    }

                };
                $scope.modalOptions.close = function (result) {
                    $modalInstance.dismiss('cancel');
                };
            };

            var modalDefaults = {
                animation: false,
                templateUrl: './views/directives/grupotrabalho/app-icons.html',
                controller : controllerIcones
            };

            function imageLoaded ( base64image ) {
                var img = new Image();
                    img.src = base64image;
                var canvas = document.createElement('canvas');
                    canvas.width = 16;
                    canvas.height = 16;
                var ctx = canvas.getContext('2d');
                    ctx.drawImage(img,0,0,16,16);

                return canvas.toDataURL().split(';base64,')[1];
            }

            iconModalService.showModal(modalDefaults, modalOptions)
            .then(function (result) {
                if ((result.imagem) || (result.selected)) {

                    var imagem = null;
                    var imageUpload = document.getElementById('imageUpload').files[0];
                    if (result.imagem && imageUpload) {
                        var base64data = 'data:'+result.imagem.filetype+';base64,'+result.imagem.base64;
                        imagem = {
                            "filesize" : result.imagem.filesize,
                            "filetype" : result.imagem.filetype,
                            "filename" : result.imagem.filename,
                            "base64" : imageLoaded( base64data )
                        };
                    }

                    var icone = {
                        "id" : nodeId,
                        "icone" : result.selected,
                        "cor" : result.color,
                        "usuarioAtualizacao" : authService.getUsername(),
                        "imagem" : imagem
                    };

                    if(tipoNodo === 'E'){
                        etiquetaGTService.alterarIcone(icone)
                            .then(function(response){
                                $window.location.href = $contextUrl;
                            })
                            .finally(function(response){
                                notificationService.push(response);
                            });
                    } else{
                        ciService.alterarIcone(icone)
                            .then(function(response){
                                $window.location.href = $contextUrl;
                            })
                            .finally(function(response){
                                notificationService.push(response);
                            });
                    }
                }
            });
        };

        controller.isAllowed = function() {
            return configService.getPermitirEditarMenu() == 'S';
        };

        controller.changeAccessContextMenu = function(bool) {
            authService.checarSessao();
            configService.permitirEditarMenu(bool, authService.getIdGrupoTrabalho(), authService.getUsername());
        };

        controller.addTopico = function(node){
            var item = {
                id: node.id,
                titulo: node.label
            };
            if(node.tipoArvore == 'T') {
                gtService.addTopicos(item);
            }
        };

        controller.addSentenca = function(node){
            var sentenca = sentencaService.getSentenca();
            var item = {
                id: node.id,
                titulo: node.label
            };
            if(node.tipoArvore == 'T') {
                sentencaService.addTopicos(item);
            }
            if(node.tipoArvore == 'M') {
                sentencaService.addModelos(item);
            }
        };

        controller.sentencaVisible = function () {
            return sentencaService.isVisible();
        };

        controller.modeloOpen = function () {
            return authService.isModeloScreen();
        };
}]);

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

angular.module('cronos').directive('appLoading', function() {
    return {
        restrict: 'AE',
        scope: {
        	loading: '='
        },
        // templateUrl: 'views/directives/app/loading.html',
        template: '<div class="loading-container" data-ng-if="loading" data-ng-animate-children><div class="loading fa-2x"><i class="fa fa-circle-o-notch fa-spin"></i><span>carregando...</span></div></div>',
        controller: ["$scope", function($scope) {
        }]
    };
});

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
angular.module('cronos').directive('appViewLoading', function() {
    return {
        restrict: 'AE',
        // templateUrl: 'views/directives/app/view-loading.html',
        template: '<div class="view-loading loading-container" data-ng-if="loading" data-ng-animate-children><div class="loading fa-2x"><i class="fa fa-circle-o-notch fa-spin"></i><span>carregando...</span></div></div>',
        controller: ["$rootScope", "$scope", "$route", function($rootScope, $scope, $route) {

            $scope.loading = false;

            $rootScope.$on('$routeChangeStart', function() {
                // console.log('routeChangeStart');
                $scope.loading = true;
            });
            $rootScope.$on('$routeChangeSuccess', function() {
                // console.log('routeChangeSuccess');
                $scope.loading = false;
            });
            $rootScope.$on('$routeChangeError', function() {
                // console.log('routeChangeError');
                $scope.loading = false;
            });
        }]
    };
});

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

angular.module('cronos')
    .factory('DeferService', ['$q', function DeferServiceFactory($q) {
    'use strict';

    return $class({

        constructor: function() {
            this._deferQueue = 0;
            this._deferFinally = angular.bind(this, this._deferEnd);
        },

        _deferStart: function(def) {

            var p = def.promise || def;

            if (p.finally) {
                this._deferQueue++;
                // console.log('Will deferSTart ' + this._deferQueue, this);
                p.finally(this._deferFinally);
            }
            return def;
        },

        _deferEnd: function() {
            // console.log('Will deferEnd ' + this._deferQueue, this);
            this._deferQueue--;
        },

        defer: function(d) {
            return this._deferStart(d || $q.defer());
        },

        isWorking: function() {
            return this._deferQueue > 0;
        }
    });
}]);

angular.module('cronos').filter('reverse', function() {
	return function(items) {
		return items.slice().reverse();
	};
});
/*

var BaseService = $class();

var ChildService = $class(BaseService, {
	
	constructor: function(){
		// super constructor call
		BaseService.call(this);
	},
	
	someMethod: function(){
		return 'ChildService';
	},

	toString: function(){
		return this.someMethod();
	}
});

var GrandchildService = ChildService.extends({
	
	constructor: function(){
		// super constructor call
		ChildService.call(this);
	},

	// override
	someMethod: function(){
		// super method call
		return 'GrandchildService and ' + ChildService.prototype.someMethod.call(this);
	}
});

*/
(function(scope) {

    function inheritClass(superClass, overrides) {
        // clone superclass prototype!        
        var newClass, classBody = Object.create(superClass.prototype);
        // resolve constructor
        if (overrides.hasOwnProperty('constructor')) {
            newClass = overrides.constructor;
        } else {
            newClass = overrides.constructor = function() {
                superClass.apply(this, arguments);
            };
        }
        // override properties and methods.
        for (var name in overrides) {
            if (overrides.hasOwnProperty(name)) {
                classBody[name] = overrides[name];
            }
        }
        // Helper to extend class
        newClass.extends = function(classBody) {
            return inheritClass(newClass, classBody);
        };
        // Reference to superclass, used in superCall
        newClass.$superClass = superClass;
        // reference inherited class body!
        newClass.prototype = classBody;
        newClass.prototype.constructor = newClass;
        // return created class.
        return newClass;
    }

    function createClass(superClass, classBody) {
        if (arguments.length <= 1) {
            classBody = superClass || {};
            superClass = Object;
        }
        return inheritClass(superClass, classBody);
    }

    // export function as $class!
    scope.$class = createClass;

})(window);
// ----------------------------------------------
// Add Generic DOM Prototype Functions
// ----------------------------------------------
Element.prototype.hasClass = function(cssClass) {
    if (cssClass)
        return this.classList.contains(cssClass);
    return this;
};

// ----------------------------------------------
// Add TextArea Prototype Functions
// ----------------------------------------------
HTMLTextAreaElement.prototype.asPlainText = function () {
    return this.value.replace(/<[^>]+>/gm, '');
};

HTMLTextAreaElement.prototype.insertAtCaret = function (text) {
    text = text || '';
    if (document.selection) {
        // IE
        this.focus();
        var sel = document.selection.createRange();
        sel.text = text;
    } else if (this.selectionStart || this.selectionStart === 0) {
        // Others
        var startPos = this.selectionStart;
        var endPos = this.selectionEnd;
        this.value = this.value.substring(0, startPos) + text + this.value.substring(endPos, this.value.length);
        this.selectionStart = startPos + text.length;
        this.selectionEnd = startPos + text.length;
    } if (window.getSelection) {
        // IE9 e outros browsers
        var selection, range;
        selection = window.getSelection();
        if (selection.getRangeAt && selection.rangeCount) {
            range = selection.getRangeAt(0);
            range.deleteContents();

            var el = document.createElement("div");
            el.innerHTML = text;
            var frag = document.createDocumentFragment(), node, lastNode;
            while ( (node = el.firstChild) ) {
                lastNode = frag.appendChild(node);
            }
            var firstNode = frag.firstChild;
            range.insertNode(frag);

            // Preserve the selection
            if (lastNode) {
                range = range.cloneRange();
                range.setStartAfter(lastNode);
                range.collapse(true);
                selection.removeAllRanges();
                selection.addRange(range);
            }
        }
    } else {
        console.error('Função não suportada!');
    }

    return this;
};

// ----------------------------------------------
// Add Array Prototype Functions
// ----------------------------------------------
Array.prototype.isEmpty = function() {
    return (this.length > -1);
};
// Retorna o primeiro elemento de um array
Array.prototype.first = function () {
    return this[0];
};
// Retorna o ultimo elemento de um array
Array.prototype.last = function () {
    return this[this.length - 1];
};
// Remove o elemento de um array
Array.prototype.delete = function (element) {
    var index = this.indexOf(element);
    if (index !== -1)
        this.splice(index, 1);
    return (index !== -1);
};
// Verifica a existencia de um elemento em um array
Array.prototype.search = function (searchParam, searchAttr) {
    searchAttr = searchAttr || null;
    if (typeof searchParam !== 'object' && searchAttr ) {
        var result = false;
        this.forEach( function( el ) {
            if (el[searchAttr] == searchParam) {
                result = true; return;
            }
        });
        return result;
    }
    return (this.indexOf(searchParam) !== -1);
};
// Substitui um elemento por outro em um array,
// mantendo a posicao do elemento anterior
Array.prototype.replace = function (oldElement, newElement) {
    var index = this.indexOf(oldElement);
    if (index !== -1)
        this[index] = newElement;
    return this;
};
// Retorna apenas os elementos unicos de um array
Array.prototype.unique = function() {
    var array = this.concat();
    for (var i = 0; i < array.length; ++i ) {
        for (var j = i + 1; j < array.length; ++j ) {
            if (array[i] === array[j])
                array.splice(j--, 1);
        }
    }
    return array;
};
// Move um elemento array de uma posicao para outra
Array.prototype.swap = function (element, posicao) {
    var index = this.indexOf(element), newPos = 0;

    if(posicao == 1) newPos = index - 1;
        else newPos = index + 1;

    if (newPos < 0) newPos = 0;
    if (newPos >= this.length) newPos = this.length;

    this.splice(index, 1);
    this.splice(newPos, 0, element);
};
Array.prototype.toObject = function () {
       var newObject = {};
       this.forEach( function ( value, key ){
              newObject[key] = value;
       });
       return newObject;
};
// ----------------------------------------------
// Add Object Prototype Functions
// ----------------------------------------------
Object.defineProperty( Object.prototype, 'map', {
    value: function(func, context) {
        context = context || this;
        var self = this, result = {};
        Object.keys(self).forEach(function(k) {
            result[k] = func.call(context, k, self[k], self);
        });
        return result;
    }
});

Object.defineProperty( Object.prototype, 'toArray', {
    value: function(context) {
        context = context || this;
        var self = this, result = [];
        Object.keys(self).forEach(function(k) {
            result[k] = self[k];
        });
        return result;
    }
});
// ----------------------------------------------
// Add String Prototype Functions
// ----------------------------------------------
// Verifica ser a string está vazia
String.prototype.isEmpty = function() {
    return (this.length === 0 || !this.trim());
};
String.prototype.asPlainText = function () {
    return this.replace(/<[^>]+>/gm, '');
};
String.prototype.equals = function (str, ignoreCase) {
    if (ignoreCase)
        return (this.toLowerCase() == str.toLowerCase());
    else
        return (this == str);
};
// Retorna a string alterando a primeira letra para maiuscula
String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
};
// Retorna somente a primeira letra da string
String.prototype.first = function() {
    return this.charAt(0);
};
String.prototype.replaceAll = String.prototype.replaceAll || function(needle, replacement) {
    return this.split(needle).join(replacement);
};
// String Builder Object
function StringBuilder(string) {
    this.arrStrings = [];
    if (string || string === 0)
        this.arrStrings.push(string);
    return this;
}
StringBuilder.prototype.append = function(string) {
    if (string || string === 0)
        this.arrStrings.push(string);
    return this;
};
StringBuilder.prototype.clear = function() {
    this.arrStrings = [];
};
StringBuilder.prototype.toString = function() {
    return this.arrStrings.join("");
};

var Cronos = Cronos || {};

angular.module('cronos').factory('Utils', function UtilsServiceFactory() {
    var UtilsService = $class({

        constructor: function() {
            this.SimNao = {
                S : "S",
                N : "N"
            };
        },

        isUndefinedOrNull: function(variable) {
            return !angular.isDefined(variable) || variable===null;
        },

        sanitizeObject: function (object, propertiesArray) {
            propertiesArray.forEach( function(property) {
                delete object[property];
            });
            return object;
        },

        sanitizeObjectCollection: function (arrayOfObjects, propertiesArray) {
            var newArrayOfObjects = [];
            arrayOfObjects.forEach( function(object, key) {
                newArrayOfObjects.push(sanitizeObject(object, propertiesArray));
            });
            return newArrayOfObjects;
        },

        cloneObject: function(obj){
            var GDCC = "__getDeepCircularCopy__";

            if (obj !== Object(obj)) {
                return obj; // primitive value
            }

            var set = GDCC in obj,
                cache = obj[GDCC],
                result;

            if (set && typeof cache == "function") {
                return cache();
            }

            // else
            obj[GDCC] = function() {
                return result;
            }; // overwrite

            if (obj instanceof Array) {
                result = [];
                for (var i=0; i<obj.length; i++) {
                    result[i] = this.cloneObject(obj[i]);
                }
            } else {
                result = {};
                for (var prop in obj)
                    if (prop != GDCC)
                        result[prop] = this.cloneObject(obj[prop]);
                    else if (set)
                        result[prop] = this.cloneObject(cache);
            }

            if (set) {
                obj[GDCC] = cache; // reset
            } else {
                delete obj[GDCC]; // unset again
            }

            return result;
        },

        validaObrigatorio: function () {
            var retorno = false;
            var obrigatorio = document.querySelectorAll('.obrigatorio');
            var obrigatorioarr = Array.prototype.slice.call(obrigatorio);

            obrigatorioarr.forEach(function(elemento) {
                if (elemento.type === "text") {
                    if (!elemento.value) {
                        elemento.classList.add("haserror");
                        retorno = true;
                    } else {
                        elemento.classList.remove("haserror");
                    }
                } else if (elemento.type === "checkbox") {
                    if (!elemento.checked) {
                        elemento.classList.add("haserror");
                        retorno = true;
                    } else {
                        elemento.classList.remove("haserror");
                    }
                } else if (elemento.type === "select-one") {
                    if ( elemento.selectedIndex <= 0 ) {
                        elemento.classList.add("haserror");
                        retorno = true;
                    } else {
                        elemento.classList.remove("haserror");
                    }
                }
            });
            return retorno;
        },

        validaEtiquetas: function (valida) {
            var obrigatorio = document.querySelector('.obrigatorioEtiqueta');
            if (valida) {
                obrigatorio.classList.add("haserror");
            } else {
                obrigatorio.classList.remove("haserror");
            }
            return valida;
        },

        validaElementos: function (valida) {
            var obrigatorio = document.querySelector('.obrigatorioElemento');

            /*var obrigatorioarr = Array.prototype.slice.call(obrigatorio);
            obrigatorioarr.forEach(function(elemento) {
            });*/

            if (valida) {
                obrigatorio.classList.add("haserror");
            } else {
                obrigatorio.classList.remove("haserror");
            }
            return valida;
        },

        validaModelo: function (valida) {
            var obrigatorio = document.querySelectorAll('.obrigatorioModelo');
            var obrigatorioarr = Array.prototype.slice.call(obrigatorio);
            obrigatorioarr.forEach(function(elemento) {
                if (valida) {
                    elemento.classList.add("haserror");
                } else {
                    elemento.classList.remove("haserror");
                }
            });
            return valida;
        },

        generateDynamicCSS: function (content) {
            var style = document.createElement("style");
                style.type="text/css";
                style.innerHTML = content;
            document.head.appendChild(style);
        },

        sanitizeXSS: function (text) {
            // Remove everything in between tags
            text = text.replace(/(<head)[\s\S]+(head>)/gi, '');

            ['style', 'script','applet','embed','noframes','noscript'].forEach( function (tag, i) {
                // var tagStripper = new RegExp('<'+tag+'.*?'+tag+'(.*?)>', 'gi');
                var tagStripper = new RegExp('(<'+tag+')[\\s\\S]+('+tag+'>)', 'gi');
                text = text.replace(tagStripper, '');
            });

            // remove tags <br /> inside other tags
            text = text.replace(/((?:<pre|(?!^))(?:(?!<\/pre|\G<\s*\/?\s*br))*)<\s*\/?\s*br\s*\/?\s*>/gi, ' $1');

            // remove tags leave content if any
            text = text.replace(/<(\/)*(\\?xml:|st1:|o:)(.*?)>/gi, '');
            text = text.replace(/<(\/)*(html|body|meta|link)(.*?)>/gi, '');

            // strip Word generated HTML comments
            text = text.replace(/<!--(.*?)-->/g, '');

            // console.log(text);
            // text = text.replace(/\uFFFD/g, '');

            return text;
        },

        sanitizeHTMLText: function (text) {
            text = this.sanitizeXSS(text);

            // remove attributes ' start="..."'
            ['start'].forEach( function (attr, i) {
                var attributeStripper = new RegExp(' ' + attr + '="(.*?)"','gi');
                text = text.replace(attributeStripper, '');
            });

            // Outras tags
            text = text.replace(/(\n|\r)/g, ' ');
            text = text.replace(/<(\w[^>]*) lang=([^ |>]*)([^>]*)/gi, "<$1$3") ;
            // text = text.replace(/<\\?\?xml[^>]*>/gi, "") ;
            // text = text.replace(/<\/?\w+:[^>]*>/gi, "") ;
            // text = text.replace( /<([^\s>]+)[^>]*>\s*<\/\1>/g, '' ) ;

            // Tamanho e posicionamento
            text = text.replace( /\s*MARGIN: 0cm 0cm 0pt\s*;/gi, "" ) ;
            text = text.replace( /\s*MARGIN: 0cm 0cm 0pt\s*"/gi, "\"" ) ;
            text = text.replace( /\s*PAGE-BREAK-BEFORE: [^\s;]+;?"/gi, "\"" ) ;

            // Formatação
            text = text.replace( /\s*tab-stops:[^;"]*;?/gi, "" ) ;
            text = text.replace( /\s*tab-stops:[^"]*/gi, "" ) ;
            text = text.replace(/<(\w[^>]*) class=([^ |>]*)([^>]*)/gi, "<$1$3") ;

            // Divisor
            text = text.replace( /<\/H\d>/gi, '<br>' ) ; //remove this to take out breaks where Heading tags were

            return text;
        },

        sanitizeStyleText: function (text) {
            // Outras tags
            text = text.replace(/(.*(?:endif-->))|([ ]?<[^>]*>[ ]?)|(&nbsp;)|([^}]*})/g,'');

            // Fontes
            text = text.replace( /\s*FONT-VARIANT: [^\s;]+;?"/gi, "\"" ) ;
            text = text.replace( /\s*face="[^"]*"/gi, "" ) ;
            text = text.replace( /\s*face=[^ >]*/gi, "" ) ;
            text = text.replace( /\s*color="[^"]*"/gi, "" ) ;
            text = text.replace( /\s*FONT-FAMILY:[^;"]*;?/gi, "" ) ;
            text = text.replace( /(<font|<FONT)([^*>]*>.*?)(<\/FONT>|<\/font>)/gi, "$2") ;
            text = text.replace( /<FONT\s*>(.*?)<\/FONT>/gi, '$1' ) ;

            // Tamanho e posicionamento
            text = text.replace( /\s*TEXT-INDENT: 0cm\s*;/gi, "" ) ;
            text = text.replace( /\s*TEXT-INDENT: 0cm\s*"/gi, "\"" ) ;
            text = text.replace( /\s*TEXT-ALIGN: [^\s;]+;?"/gi, "\"" ) ;
            text = text.replace( /size|SIZE = ([\d]{1})/g, '' ) ;

            // Formatação
            text = text.replace( /<(\w[^>]*) style="([^\"]*)"([^>]*)/gi, "<$1$3" ) ;
            text = text.replace( /\s*style="\s*"/gi, '' ) ;

            // Cabeçalhos
            text = text.replace( /<H\d>\s*<\/H\d>/gi, '' ) ;
            text = text.replace( /<H1([^>]*)>/gi, '' ) ;
            text = text.replace( /<H2([^>]*)>/gi, '' ) ;
            text = text.replace( /<H3([^>]*)>/gi, '' ) ;
            text = text.replace( /<H4([^>]*)>/gi, '' ) ;
            text = text.replace( /<H5([^>]*)>/gi, '' ) ;
            text = text.replace( /<H6([^>]*)>/gi, '' ) ;

            // Divisor
            text = text.replace( /<\/H\d>/gi, '<br>' ) ; //remove this to take out breaks where Heading tags were

            // Italico, sublinhado e tachado
            text = text.replace( /<(U|I|STRIKE)>&nbsp;<\/\1>/g, '&nbsp;' ) ;

            // Negrito
            text = text.replace( /<(B|b)>&nbsp;<\/\b|B>/g, '' ) ;

            // Destaques
            text = text.replace( /<SPAN\s*[^>]*>\s*&nbsp;\s*<\/SPAN>/gi, '&nbsp;' ) ;
            text = text.replace( /<SPAN\s*[^>]*><\/SPAN>/gi, '' ) ;
            text = text.replace( /<SPAN\s*>(.*?)<\/SPAN>/gi, '$1' ) ;

            // Paragrafos
            text = text.replace( /(<P)([^>]*>.*?)(<\/P>)/gi, "<p$2</p>" ) ;

            return text;
        },

        cleanMSOfficeStyle: function (text) {
            // remove line breaks / Mso classes / mso attributes
            text = text.replace(/(\n|\r| class=(")?Mso[a-zA-Z]+(")?)/g, ' ');
            text = text.replace(/mso-[^:]+:[^;("|')]+;?/g, ' ');
            text = text.replace(/<(\/)*(\\?xml:|st1:|o:)(.*?)>/gi, '');

            return text;
        },

        getDocumentSelection: function( htmlFormat ) {
            if (htmlFormat) {
                var range;
                if (document.selection && document.selection.createRange) {
                    range = document.selection.createRange();
                    return range.htmlText;
                } else if (window.getSelection) {
                    var selection = window.getSelection();
                    if (selection.rangeCount > 0) {
                        range = selection.getRangeAt(0);
                        var clonedSelection = range.cloneContents();
                        var div = document.createElement('div');
                        div.appendChild(clonedSelection);
                        return div.innerHTML;
                    } else {
                        return '';
                    }
                } else {
                    return '';
                }
            } else {
                return window.getSelection().toString();
            }
        },

        copyDataToClipboard: function(data) {
            var documentBody, selectRange, htmlData;

            htmlData = document.createElement('div');
            htmlData.innerHTML = data;

            documentBody = document.getElementsByTagName('body')[0];
            documentBody.appendChild(htmlData);

            if (window.getSelection) {
                window.getSelection().removeAllRanges();
            } else if (document.selection) {
                document.selection.empty();
            }

            selectRange = document.createRange();
            selectRange.selectNode(htmlData);

            window.getSelection(htmlData).addRange(selectRange);

            document.execCommand('copy');

            documentBody.removeChild(htmlData);
        },

        removeElementById: function(obj, element) {
            // remove um elemento comparando o ID
            var indice = -1;
            for (var i = 0; i < obj.length; i++) {
                if (obj[i].id === element.id) {
                    indice = i;
                    break;
                }
            }
            obj.splice(indice, 1);
        }
    });

    return new UtilsService();
});
angular
    .module('cronos')
    .controller('AlegacaoController', ['$window', '$contextUrl', 'notificationService', 'tipoAlegacaoService', 'alegacaoGrupoTrabalhoService', 'authService', 'Utils',
        function AlegacaoController($window, $contextUrl, notificationService, tipoAlegacaoService, alegacaoGrupoTrabalhoService, authService, Utils) {
        'use strict';

        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        controller.loading = false;
        controller.alegacoes = [];

        /* ------------------
         * Metodos
         * -----------------*/
        controller.isLoading = function() {
            return alegacaoGrupoTrabalhoService.isWorking();
        };

        controller.save = function() {
            controller.alegacoes.forEach( function (alegacao) {
                alegacao.usuarioAtualizacao = authService.getUsername();

                Utils.sanitizeObject(alegacao,['dataAtualizacao','dataCriacao']);

                alegacaoGrupoTrabalhoService
                    .save( alegacao )
                    .then( function (response) {
                        alegacao.versao = response.data.versao;
                        alegacaoGrupoTrabalhoService.carregarAlegacoes(authService.getIdGrupoTrabalho());
                    });
            });
            notificationService.success("DADOS_SALVOS_SUCESSO_000");

        };

        controller.clean = function(alegacao) {
            alegacao.textoAlegacao = "";
            alegacao.usuarioAtualizacao = authService.getUsername();
        };

        controller.loadAlegacoes = function() {
            alegacaoGrupoTrabalhoService
                ._findAllByGrupoTrabalho( authService.getIdGrupoTrabalho() )
                .then( function (response) {
                    controller.alegacoes = response.data;
                });
        };

        /* ------------------
         * Carrega Dados
         * -----------------*/
        controller.loadAlegacoes();

        if (!authService.isAdmin())
            $window.location.href = $contextUrl + '#/acesso-negado';
    }]);

angular
    .module('cronos')
    .controller('EtiquetaGTFormController', ['$routeParams', 'etiquetaGTService', function EtiquetaGTFormController($routeParams, etiquetaGTService) {
        'use strict';
        var controller = this;
    }]);

angular
    .module('cronos')
    .controller('GtFormController', ['$scope', '$routeParams', '$window', 'notificationService', 'gtService', 'ciService', 'authService', '$contextUrl', 'modalService', 'sentencaService', 'Utils',
        function GtFormController($scope, $routeParams, $window, notificationService, gtService, ciService, authService, $contextUrl, modalService, sentencaService, Utils) {
        'use strict';

        var TEMPO = 5;
        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        controller.excluir = false;
        controller.item = null;
        controller.compartilhar = false;
        controller.readonly = false;
        controller.stop = false;
        controller.timer = TEMPO;

        /* ------------------
         * Metodos
         * -----------------*/
        if($routeParams.tipo === 'M'){
            gtService.setModeloScreen();
        }

        controller.isLoading = function() {
            return ciService.isWorking();
        };

        controller.montarTela = function(retorno) {
            controller.categoriaItem = retorno;
            controller.item = controller.categoriaItem.categoria;
            controller.categorias = controller.categoriaItem.categoriasModelo;
            controller.item.usuarioAtualizacao = authService.getUsername();

            if (controller.item.taxonomiasEtiqueta) {
                for(var iNT = 0; iNT < controller.item.taxonomiasEtiqueta.length; iNT++) {
                    var etiquetaIn = controller.item.taxonomiasEtiqueta[iNT].etiquetaGT;
                    var etiquetaNova = {
                        'id' : etiquetaIn.id,
                        'etiqueta' : etiquetaIn.etiqueta,
                        'idIcone'  : etiquetaIn.idIcone,
                        'icone'   : etiquetaIn.icone,
                        'cor'   : etiquetaIn.cor
                    };
                    controller.etiquetas.push(etiquetaNova);
                }
                for(var indTaxCat = 0; indTaxCat < controller.categoriaItem.taxonomiasCategoriaNovas.length; indTaxCat++) {
                    var modelo = controller.categoriaItem.taxonomiasCategoriaNovas[indTaxCat].categoriaItem;
                    modelo = Utils.sanitizeObject(modelo, ["$$hashKey"]);
                    gtService.addTopicos(modelo);
                }
                var elementosAntigo = [];
                for (var indEleAnt = 0; indEleAnt < controller.item.elementos.length; indEleAnt++) {
                    var elementoAntigo = controller.item.elementos[indEleAnt];
                    elementoAntigo = Utils.sanitizeObject(elementoAntigo, ["$$hashKey"]);
                    controller.categoriaItem.elementos.push(elementoAntigo);
                }

                var pai = controller.categoriaItem.pai;
                if(pai){
                    controller.categoriasTopicos.push(pai);
                }

                controller.categoriaItem.taxonomiasEtiqueta = controller.item.taxonomiasEtiqueta;
                controller.categoriaItem.taxonomiasCategoria = controller.categoriaItem.taxonomiasCategoriaNovas;
            } else {
                controller.categoriaItem.taxonomiasEtiqueta = [];
                controller.categoriaItem.taxonomiasCategoria = [];
                controller.categoriaItem.taxonomiasCategoriaNovas = [];
                controller.categoriaItem.elementos = [];
                controller.item.taxonomiasEtiqueta = [];
            }
            if (controller.item.compartilhar == "S") {
                controller.compartilhar = true;
            }
            if ("T" === controller.item.arvore) {
                controller.item.descricaoArvore = "Tópico";
            } else {
                controller.item.descricaoArvore = "Modelo";
            }
        };

        controller.carregaElementoPadrao = function(tipo, id, readonly) {
            controller.categoriaItem = false;
            controller.etiquetas = [];
            gtService.clear();
            controller.categorias = [];
            controller.categoriasTopicos = [];

            var carregarCategoria = {
                    "idCategoria" : id,
                    "tipoArvore" : tipo,
                    "idGrupoTrabalho" : authService.getIdGrupoTrabalho()
            };

            if (readonly)
                controller.readonly = readonly;

            ciService
                .carregarCategoria(carregarCategoria)
                .then(function(response){
                    controller.montarTela(response.data);
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.salvar = function() {
            var temErro = Utils.validaObrigatorio();
            var temErroEtiqueta = Utils.validaEtiquetas(controller.etiquetas.length === 0);
            var temElementoPreenchido = false;
            var temErroElemento = false;
            if (controller.item.arvore == "M") {
                if (controller.item.elementos.length === 0 && gtService.getTopicos().length === 0) {
                    temElementoPreenchido = true;
                }
                temErroElemento = Utils.validaModelo(temElementoPreenchido);
            } else {
                for (var iEL = 0; iEL < controller.item.elementos.length; iEL++) {
                    var elemento = controller.item.elementos[iEL];
                    if (elemento.texto) {
                        temElementoPreenchido = true;
                    }
                }
                temErroElemento = Utils.validaElementos(!temElementoPreenchido);
            }

            if (temErro || temErroEtiqueta || temErroElemento) {
                if (temErroElemento) {
                    if (controller.item.arvore == "T" || controller.item.arvore == "S") {
                        notificationService.error("ELEMENTOS_OBRIGATORIOS_018");
                    } else {
                        notificationService.error("ELEMENTOS_OBRIGATORIOS_019");
                    }
                }
                notificationService.error("CAMPOS_OBRIGATORIOS_TOPICO_017");
                return;
            }

            if (controller.item.titulo.length < 3 ) {
                 notificationService.error('CAMPO_TAMANHO_MINIMO_025', ['Título', '3']);
                return;
            }

            if (controller.excluir) {
                ciService
                    .remove(controller.item)
                    .then(function(response){
                        $window.location.href = $contextUrl;
                    })
                    .finally(function(response){
                        notificationService.push(response);
                    });
            } else {
                var taxonomiasEtiquetaInseridas = [];
                for(var indEti = 0; indEti < controller.etiquetas.length; indEti++) {
                    var taxonomiaEtiqueta = null;
                    for(var indTaxEnt = 0; indTaxEnt < controller.item.taxonomiasEtiqueta.length; indTaxEnt++) {
                        if (controller.item.taxonomiasEtiqueta[indTaxEnt].etiquetaGT && controller.etiquetas[indEti].id == controller.item.taxonomiasEtiqueta[indTaxEnt].etiquetaGT.id) {
                            taxonomiaEtiqueta = controller.item.taxonomiasEtiqueta[indTaxEnt];
                            break;
                        }
                    }
                    if (!taxonomiaEtiqueta) {
                        taxonomiaEtiqueta = {
                            "idEtiquetaGT" : controller.etiquetas[indEti].id,
                            "idCategoria" : controller.item.id,
                            "ordem" : indEti
                        };
                    }
                    taxonomiasEtiquetaInseridas.push(taxonomiaEtiqueta);
                }
                controller.categoriaItem.categoria.taxonomiasEtiqueta = taxonomiasEtiquetaInseridas;

                var taxonomiasCategoriaInseridas = [];
                for(var indTop = 0; indTop < gtService.getTopicos().length; indTop++) {
                    var topicoIn = gtService.topicos[indTop];
                    var taxonomiaCategoria = null;
                    for(var indTaxoCat = 0; indTaxoCat < controller.categoriaItem.taxonomiasCategoriaNovas.length; indTaxoCat++) {
                        var taxCatIn = controller.categoriaItem.taxonomiasCategoriaNovas[indTaxoCat];
                        if (taxCatIn.categoriaItem && topicoIn.id == taxCatIn.categoriaItem.id) {
                            taxonomiaCategoria = taxCatIn;
                            break;
                        }
                    }
                    if (!taxonomiaCategoria) {
                        taxonomiaCategoria = {
                            "idModeloSentenca" : controller.item.id,
                            "idCategoria" : topicoIn.id,
                            "ordem" : indTop
                        };
                    }
                    taxonomiasCategoriaInseridas.push(taxonomiaCategoria);
                }
                controller.categoriaItem.taxonomiasCategoriaNovas = taxonomiasCategoriaInseridas;

                if (controller.compartilhar) {
                    controller.item.compartilhar = "S";
                } else {
                    controller.item.compartilhar = "N";
                }

                if (controller.item.arvore == "T") {
                    if (controller.categoriasTopicos.length > 0) {
                        controller.item.idPai = controller.categoriasTopicos[0].id;
                    } else {
                        controller.item.idPai = controller.categoriaItem.paiGeral.id;
                    }
                }

                controller.categoriaItem.categoria = controller.item;

                for(var indEleNov = 0; indEleNov < controller.categoriaItem.categoria.elementos.length; indEleNov++) {
                    var elementoNovo = controller.categoriaItem.categoria.elementos[indEleNov];
                    elementoNovo.ordem = indEleNov + 1;
                }
                ciService
                    .salvarTopico(controller.categoriaItem)
                    .then(function(response){
                        var retorno = response.data;
                        notificationService.success("DADOS_SALVOS_SUCESSO_000");
                        gtService.carregarMenu();
                        controller.atualizaRegistro(retorno);
                    });
            }
        };

        controller.atualizaRegistro = function(categoriaNova) {
            var data = new Date();
            $window.location.href = "#/grupo-trabalho/" + categoriaNova.arvore + "/" + categoriaNova.id + "/" + data.getMilliseconds();
        };

        controller.cloneItem = function(item){
            var elemento = {
                "usuarioAtualizacao": authService.getUsername(),
                "id": item.id,
                "idGrupoTrabalho" : authService.getIdGrupoTrabalho()
            };

            ciService
                .cloneItem(elemento)
                .then(function(response){
                    $window.location.href = $contextUrl;
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.excluirItem = function(item) {
            var elemento = {
                "usuarioAtualizacao": authService.getUsername(),
                "id": item.id,
                "versao" : item.versao
            };

            ciService
                .remove(elemento)
                .then(function(response){
                    $window.location.href = $contextUrl;
                })
                .finally(function(response){
                    notificationService.push(response);
                });
        };

        controller.preview = function(id) {
            modalService.open( 'previewGt', {
                confirmButton: "Fechar",
                previewGtId: id
            });
        };

        controller.addSentenca = function(item){
            var sentenca = sentencaService.getSentenca();

            if(item.arvore == 'T') {
                item = _.pick(item, 'id', 'titulo');
                sentencaService.addTopicos(item);
            }
            if(item.arvore == 'M') {
                item = _.pick(item, 'id', 'titulo');
                sentencaService.addModelos(item);
            }
        };

        controller.getTopicos = function(){
            return gtService.getTopicos();
        };

    /* TODO Comentado para posterior ajuste.

		controller.startTimer = function() {
            if (angular.isDefined(controller.stop)) return;
            controller.stop = $interval(function() {
                if (controller.timer > 0) {
                    controller.timer--;
                } else {
                    controller.timer = TEMPO;
                    controller.stopTimer();
                }
            }, 1000);
        };

        controller.stopTimer = function() {
            if (angular.isDefined(controller.stop)) {
                $interval.cancel(controller.stop);
                controller.stop = undefined;
                //controller.salvar();
                controller.autoSalvar();
            }
        };

        controller.autoSalvar = function(){
            if (!controller.item.titulo) {
                controller.item.titulo = "RASCUNHO MARCELO";
            }
            var taxonomiasEtiquetaInseridas = [];
            var etiquetaGT = {
                "id" : 1
            };
            controller.etiquetas.push(etiquetaGT);

            for(var indEti = 0; indEti < controller.etiquetas.length; indEti++) {
                var taxonomiaEtiqueta = null;
                for(var indTaxEnt = 0; indTaxEnt < controller.item.taxonomiasEtiqueta.length; indTaxEnt++) {
                    var etiquetaAutosalvar = controller.item.taxonomiasEtiqueta[indTaxEnt];
                    if (etiquetaAutosalvar.etiquetaGT && controller.etiquetas[indEti].id == etiquetaAutosalvar.etiquetaGT.id) {
                        taxonomiaEtiqueta = etiquetaAutosalvar;
                        break;
                    }
                }
                if (!taxonomiaEtiqueta) {
                    taxonomiaEtiqueta = {
                        "idEtiquetaGT" : controller.etiquetas[indEti].id,
                        "idCategoria" : controller.item.id,
                        "ordem" : indEti
                    };
                }
                taxonomiasEtiquetaInseridas.push(taxonomiaEtiqueta);
            }
            controller.categoriaItem.categoria.taxonomiasEtiqueta = taxonomiasEtiquetaInseridas;

            var taxonomiasCategoriaInseridas = [];
            for(var indTop = 0; indTop < controller.topicos.length; indTop++) {
                var topicoIn = controller.topicos[indTop];
                var taxonomiaCategoria = null;
                for(var indTaxoCat = 0; indTaxoCat < controller.categoriaItem.taxonomiasCategoriaNovas.length; indTaxoCat++) {
                    var taxCatIn = controller.categoriaItem.taxonomiasCategoriaNovas[indTaxoCat];
                    if (taxCatIn.categoriaItem && topicoIn.id == taxCatIn.categoriaItem.id) {
                        taxonomiaCategoria = taxCatIn;
                        break;
                    }
                }
                if (!taxonomiaCategoria) {
                    taxonomiaCategoria = {
                        "idModelo" : controller.item.id,
                        "idCategoria" : topicoIn.id,
                        "ordem" : indTop
                    };
                }
                taxonomiasCategoriaInseridas.push(taxonomiaCategoria);
            }
            controller.categoriaItem.taxonomiasCategoriaNovas = taxonomiasCategoriaInseridas;

            if (controller.compartilhar) {
                controller.item.compartilhar = "S";
            } else {
                controller.item.compartilhar = "N";
            }
            controller.categoriaItem.categoria = controller.item;

            for(var indEleNov = 0; indEleNov < controller.categoriaItem.categoria.elementos.length; indEleNov++) {
                var elementoNovo = controller.categoriaItem.categoria.elementos[indEleNov];
                elementoNovo.ordem = indEleNov + 1;
            }
            ciService
                .salvarTopico(controller.categoriaItem)
                .then(function(response){
                    //controller.item = response.data;
                    var item = response.data;
                    controller.item = null;
                    controller.categoriaItem = false;
                    controller.etiquetas = [];
                    controller.topicos = [];
                    controller.categorias = [];
                    controller.carregaElementoPadrao(item.arvore, item.id);
                });
        }*/

        /* ------------------
         * Carrega Dados
         * -----------------*/
         if ($routeParams.tipo) {
             controller.carregaElementoPadrao($routeParams.tipo, $routeParams.id);
         }
    }]);

angular
    .module('cronos')
    .controller('GtPreviewController', ['ciService', 'authService', 'Utils', function GtPreviewController(ciService, authService, Utils) {
        'use strict';

        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        controller.item = null;
        controller.categoriaItem = false;
        controller.etiquetas = [];
        controller.topicos = [];
        controller.categorias = [];
        controller.compartilhar = false;
        controller.readonly = true;

        /* ------------------
         * Metodos
         * -----------------*/

        controller.isLoading = function() {
            return ciService.isWorking();
        };

        controller.carregaElementoPadrao = function(id) {
            var carregarCategoria = {
                    "idCategoria" : id,
                    "idGrupoTrabalho" : authService.getIdGrupoTrabalho()
            };

            ciService
                .carregarCategoria(carregarCategoria)
                .then(function(response){
                    controller.categoriaItem = response.data;
                    controller.item = controller.categoriaItem.categoria;
                    controller.categorias = controller.categoriaItem.categoriasModelo;
                    controller.item.usuarioAtualizacao = authService.getUsername();

                    if (controller.item.taxonomiasEtiqueta) {
                        for(var iNT = 0; iNT < controller.item.taxonomiasEtiqueta.length; iNT++) {
                            var etiquetaIn = controller.item.taxonomiasEtiqueta[iNT].etiquetaGT;
                            var etiquetaNova = {
                                'id' : etiquetaIn.id,
                                'etiqueta' : etiquetaIn.etiqueta,
                                'idIcone'  : etiquetaIn.idIcone,
                                'icone'   : etiquetaIn.icone,
                                'cor'   : etiquetaIn.cor
                            };
                            controller.etiquetas.push(etiquetaNova);
                        }
                        for(var indTaxCat = 0; indTaxCat < controller.categoriaItem.taxonomiasCategoriaNovas.length; indTaxCat++) {
                            var modelo = controller.categoriaItem.taxonomiasCategoriaNovas[indTaxCat].categoriaItem;
                            modelo = Utils.sanitizeObject(modelo, ["$$hashKey"]);
                            controller.topicos.push(modelo);
                        }
                        var elementosAntigo = [];
                        for (var indEleAnt = 0; indEleAnt < controller.item.elementos.length; indEleAnt++) {
                            var elementoAntigo = controller.item.elementos[indEleAnt];
                            elementoAntigo = Utils.sanitizeObject(elementoAntigo, ["$$hashKey"]);
                            controller.categoriaItem.elementos.push(elementoAntigo);
                        }
                        controller.categoriaItem.taxonomiasEtiqueta = controller.item.taxonomiasEtiqueta;
                        controller.categoriaItem.taxonomiasCategoria = controller.categoriaItem.taxonomiasCategoriaNovas;
                    } else {
                        controller.categoriaItem.taxonomiasEtiqueta = [];
                        controller.categoriaItem.taxonomiasCategoria = [];
                        controller.categoriaItem.taxonomiasCategoriaNovas = [];
                        controller.categoriaItem.elementos = [];
                        controller.item.taxonomiasEtiqueta = [];
                    }
                    if (controller.item.compartilhar == "S") {
                        controller.compartilhar = true;
                    }
                    if ("T" === controller.item.arvore) {
                        controller.item.descricaoArvore = "Tópico";
                    } else {
                        controller.item.descricaoArvore = "Modelo";
                    }
                });
        };

    }]);

angular
    .module('cronos')
    .controller('GrupoTrabalhoLoginController', ['$scope', '$location', '$routeParams', '$window', 'gtService', '$contextUrl', 'authService', 'Utils',
        function GrupoTrabalhoLoginController($scope, $location, $routeParams, $window, gtService, $contextUrl, authService, Utils) {
        'use strict';

        var controller = this;

        controller.itens = [];

        controller.loadingGTLogin = function(){
            return gtService.isWorking();
        };

        controller.carregar = function() {
            authService.check().then(function(userInfo){
                gtService._findByUsuario(userInfo.usuario.id).then(function(response){
                    if(response.data.length > 1){
                        controller.itens = response.data;
                    } else {
                        $window.location.href = $contextUrl;
                    }
                });
            });
        };

        controller.selecionaGrupo = function () {
            $window.location.href = $contextUrl;
        };

        controller.setGrupoPadrao = function (elemento) {
            gtService._alteraPadrao(elemento)
            .then(function(response){

            },function(error){
                console.log(error);
            });
        };

        controller.carregar();
}]);

angular
    .module('cronos')
    .controller('LoginController', ['$contextUrl', '$scope', '$window', 'authService', 'notificationService', function LoginController($contextUrl, $scope, $window, authService, notificationService) {
        'use strict';

        $scope.username = authService.user.username;
        $scope.password = '';

        $scope.isLoading = function(){
            return authService.isWorking();
        };

        $scope.login = function($event) {
            $event.preventDefault();

            if(!$scope.username || !$scope.password){
                notificationService.error("FALHA_LOGIN_001");
                return;
            }

            if (authService.isLogged()) {
                $window.location.href = 'grupo-trabalho-login.html';
            } else {
                authService
                    .login($scope.username, $scope.password)
                    .then(function(userInfo) {
                        $window.location.href = 'grupo-trabalho-login.html';
                    }, function(result) {
                        notificationService.push(result);
                    });
            }
        };
    }]);

angular
    .module('cronos')
    .controller('SentencaController', ['$window','$scope', '$contextUrl', 'notificationService', 'cpuService', 'modalService', 'sentencaService', 'gtService', 'exportarService', 'Utils',
        function SentencaController($window, $scope, $contextUrl, notificationService, cpuService, modalService, sentencaService, gtService, exportarService, Utils) {
        'use strict';

        var controller = this;
        var isVisible = false;
        var isChanged = false;
        var sentencaModalTemplate = './views/pages/sentenca/sidebar-sentenca-modal.html';

        // 9000002-78.2013.8.21.0039
        // 9000352-70.2014.8.21.3001
        // 9000001-93.2013.8.21.0039
        // 00008610520038210039
        
        var getBaseUrl = function () {
            return $contextUrl + '#/sentenca/';
        };

        var buscaDadosProcesso = function (numeroProcesso) {
            var dto = {
                numeroProcesso: numeroProcesso.replace(/\D/g,'')
            };

            cpuService
                .carregarProcesso(dto)
                .then(function(response) {
                    var data = response && response.data;
                    controller.setDadosProcesso(data);
                    sentencaService.getSentenca().numeroProcesso = data.numeroCNJ;
                },function(error){
                    sentencaService.getSentenca().numeroProcesso = sentencaService.getDadosProcesso().numeroCNJ;
                });
        };

        controller.isVisible = function() {
            return sentencaService.isVisible();
        };

        controller.isChanged = function() {
            return isChanged;
        };

        controller.setIsChanged = function (status) {
            isChanged = status;
        };

        controller.setIsChangedOnce = function(status) {
            if (!isChanged) isChanged = status;
        };

        controller.isOpen = function() {
            return sentencaService.isOpen();
        };

        controller.setOpenClose = function( status ) {
            if (!Utils.isUndefinedOrNull(status))
                sentencaService.setOpen(status);
            else
                sentencaService.setOpen(!sentencaService.isOpen());
        };

        var _isAbaSentenca = function(){
            return sentencaService.isAbaSentenca();
        };

        controller.setAbaSentenca = function(){
            sentencaService.setAbaDados(false);
            sentencaService.setAbaSentenca(true);
            controller.isAbaSentencaActive = _isAbaSentenca();
        };

        var _isAbaDados = function(){
            return sentencaService.isAbaDados();
        };
        controller.setAbaDados = function(){
            sentencaService.setAbaDados(true);
            sentencaService.setAbaSentenca(false);
            controller.isAbaDadosActive = _isAbaDados();
        };

        controller.isLoadingCPU = function() {
            return cpuService.isWorking();
        };

        controller.isLoading = function() {
            return sentencaService.isWorking();
        };

        controller.getSentenca = function() {
            return sentencaService.getSentenca();
        };

        controller.getDadosProcesso = function() {
            return sentencaService.getDadosProcesso();
        };

        controller.setDadosProcesso = function(dto) {
            return sentencaService.setDadosProcesso(dto);
        };

        controller.getSentenca = function() {
            return sentencaService.getSentenca();
        };

        controller.isSentencaGerada = function() {
            var sentencaGerada = sentencaService.getSentenca().sentencaGerada;
            return !Utils.isUndefinedOrNull(sentencaGerada) ? (sentencaGerada.length > 0) : false;
        };

        controller.isSentencaFinalizada = function() {
            var statusFinalizada = sentencaService.getSentenca().finalizada;
            return statusFinalizada.equals(Utils.SimNao.S) || false;
        };

        controller.pesquisaProcesso = function (numeroProcesso) {
            if ( !numeroProcesso || numeroProcesso === "" ) {
                notificationService.error('CAMPO_OBRIGATORIO_026', ['Número Processo']);
                return;
            }

            controller.setAbaDados();
            if ( controller.getDadosProcesso().numeroCNJ && (numeroProcesso != controller.getDadosProcesso().numeroCNJ) ) {
                modalService.open('confirm', {
                    message: 'Ao alterar o número do processo, todas as informações serão substituídas.',
                    confirmButton: 'Confirmar',
                    cancelButton: 'Cancelar'
                })
                .then( function(){
                    buscaDadosProcesso(numeroProcesso);
                },function(){
                    sentencaService.getSentenca().numeroProcesso = sentencaService.getDadosProcesso().numeroCNJ;
                });
            } else {
                buscaDadosProcesso(numeroProcesso);
            }
        };

        controller.getTopicos = function() {
            return sentencaService.getTopicos();
        };

        controller.getModelos = function() {
            return sentencaService.getModelos();
        };

        controller.getAutores = function() {
            var autores = [];
            angular.forEach(controller.getDadosProcesso().autor, function(autor, key) {
                this.push(autor.nome);
            }, autores);
            return autores.join('\n');
        };

        controller.getReus = function() {
            var reus = [];
            angular.forEach(controller.getDadosProcesso().reu, function(reu, key) {
                this.push(reu.nome);
            }, reus);
            return reus.join('\n');
        };

        controller.visualizarAutores = function() {
            modalService.open('preview', {
                headerText: 'Listagem de Autores do Processo: ' + controller.getDadosProcesso().numeroCNJ,
                confirmButton: 'Fechar',
                contentPreviewPath: sentencaModalTemplate,
                contentPreviewModel: controller.getDadosProcesso().autor
            });
        };

        controller.visualizarReus = function() {
            modalService.open('preview', {
                headerText: 'Listagem de R&eacute;us do Processo: ' + controller.getDadosProcesso().numeroCNJ,
                confirmButton: 'Fechar',
                contentPreviewPath: sentencaModalTemplate,
                contentPreviewModel: controller.getDadosProcesso().reu
            });
        };

        controller.gerarSentenca = function() {
            var numeroProcesso = sentencaService.getSentenca().numeroProcesso;
            var numeroCNJ = sentencaService.getDadosProcesso().numeroCNJ;
            var modelo = sentencaService.getModelos();

            if(!numeroProcesso || !numeroCNJ || modelo.length === 0){
                if (modelo.length === 0) {
                    notificationService.error('CAMPO_OBRIGATORIO_026', ['Modelos']);
                }
                if (!numeroCNJ) {
                    notificationService.error('CAMPO_OBRIGATORIO_026', ['Dados do Processo']);
                }
                if (!numeroProcesso) {
                    notificationService.error('CAMPO_OBRIGATORIO_026', ['Número Processo']);
                }
            } else {
                sentencaService.gerarSentenca().then(function(response){
                    controller.setIsChanged(false);
                    $window.location.href = getBaseUrl() + response.data.id;
                });
            }

        };

        controller.visualizarSentenca = function () {
            var url = getBaseUrl() + controller.getSentenca().id;
            console.log(url);
            $window.location.href = url;
        };

        controller.exportarParaArquivo = function (extensao) {
            return exportarService.exportarSentenca(controller.getDadosProcesso().numeroCNJ, extensao);
        };

        controller.salvarSentencaFinalizada = function () {
            sentencaService.finalizarSentenca().then( function (response){
                sentencaService.carregarSentenca(response.data.id, true);
                gtService.carregarMenu();
                notificationService.success("DADOS_SALVOS_SUCESSO_000");
            });
        };

        controller.finalizarSentenca = function () {
            sentencaService.verificaSeExisteFinalizada().then( function ( sentencaAntigaFinalizada ) {
                // if (sentencaAntigaFinalizada)
                console.log(sentencaAntigaFinalizada);
                modalService.open('confirm', {
                    message: 'Já existe uma sentença finalizada para este processo, que será substituída.',
                    confirmButton: 'Confirmar',
                    cancelButton: 'Cancelar'
                })
                .then( function(){
                    sentencaService.removerSentenca( sentencaAntigaFinalizada.data ).then( function (response){
                        sentencaService.finalizarSentenca().then( function (response){
                            sentencaService.carregarSentenca(response.data.id, false);
                            gtService.carregarMenu();
                        });
                    });
                });
            }, function () {
                sentencaService.finalizarSentenca().then( function (response){
                    sentencaService.carregarSentenca(response.data.id, false);
                    gtService.carregarMenu();
                });
            });
        };

        controller.copiarParaAreaDeTransferencia = function () {
            Utils.copyDataToClipboard(sentencaService.getSentenca().sentencaGerada);
            notificationService.info('Texto copiado para mem&oacute;ria.<br/>Pressione CTRL+V para colar.');
        };

        controller.verificaDadosSentenca = function () {
            $scope.$watch(function() {
                var modelos = $scope.sentencaCt.getModelos().length;
                var topicos = $scope.sentencaCt.getTopicos().length;
                return (modelos && topicos);
            }, function(newLength, oldLength) {
                controller.setIsChangedOnce((oldLength != newLength) && controller.isSentencaGerada());
            });
        };
        controller.verificaDadosSentenca();

}]);

angular
    .module('cronos')
    .controller('UserFormController', ['$scope', '$routeParams', '$window', '$contextUrl', 'notificationService', 'usuarioService', 'usuarioCTJService', 'authService', 'modalService', 'Utils',
        function UserFormController($scope, $routeParams, $window, $contextUrl, notificationService, usuarioService, usuarioCTJService, authService, modalService, Utils) {
        'use strict';

        var controller = this;

        /* ------------------
         * Atributos Gerais
         * -----------------*/
        // Pesquisa Usuarios CTJ
        controller.username = null;
        controller.usuariosCTJ = [];
        controller.usuariosSelecionados = [];
        controller.usuariosSubordinados = [];

        /* ------------------
         * Metodos Privados
         * -----------------*/
        var _carregarUsuariosSubordinados = function() {
            usuarioService
                ._findByGrupoTrabalho(authService.getIdGrupoTrabalho())
                .then(function(response){
                    controller.usuariosSubordinados = response.data;
                });
        };

        /* ------------------
         * Metodos Publicos
         * -----------------*/
        controller.loadingUsuarios = function(){
            return usuarioCTJService.isWorking();
        };

        controller.loadingSubordinados = function(){
            return usuarioService.isWorking();
        };

        controller.pesquisaUsuarioCTJ = function() {
            controller.selectAll = false;

            if (!controller.username) {
                notificationService.error("PESQUISA_VAZIA_015");
                return;
            }
            if (controller.username.length < 3) {
                notificationService.warning("PESQUISA_MINIMA_014");
                return;
            }

            usuarioCTJService
                .listaPessoas(controller.username)
                .then(function(response) {
                    controller.usuariosSubordinados.forEach(function(usuario){
                        var result = _.find(response.data, {cpf: usuario.cpf});
                        if (result) {
                            var idx = response.data.indexOf(result);
                            response.data[idx].disabled = true;
                        }
                    });
                    controller.usuariosCTJ = response.data;
                }, function() {
                    controller.usuariosCTJ = [];
                });
        };

        controller.selecionarTodos = function() {
            controller.usuariosCTJ.forEach(function(usuario) {
                usuario.selecionado = controller.selectAll;
                controller.adicionarRemover(usuario);
            });
        };

        controller.adicionarRemover = function(usuario) {
            if (usuario.selecionado) {
                if (!controller.usuariosSelecionados.search(usuario))
                    controller.usuariosSelecionados.push(usuario);
            } else {
                if (controller.usuariosSelecionados.search(usuario))
                    controller.usuariosSelecionados.delete(usuario);
            }
        };

        controller.adicionarSelecionados = function() {
            // TODO: Refatorar para salvar multiplos.
            controller.usuariosSelecionados.forEach(function(usuario) {

                Utils.sanitizeObject(usuario, ["$$hashKey","selecionado","magistrado"]);

                usuario.usuarioAtualizacao = authService.getUsername();
                usuario.idGrupoTrabalho = authService.getIdGrupoTrabalho();
                usuario.login = usuario.cpf;

                usuarioService._salvarUsuario(usuario)
                    .then(function(response){
                        controller.usuariosSubordinados.push(response.data);
                        controller.usuariosSubordinados.sort(function(a, b){
                            if(a.nome < b.nome) return -1;
                            if(a.nome > b.nome) return 1;
                            return 0;
                        });
                        controller.usuariosSelecionados.delete(usuario);
                        controller.usuariosCTJ.delete(usuario);
                    });
            });
        };

        controller.mudaPermissoes = function( usuario ) {
            usuario.usuarioAtualizacao = authService.getUsername();
            usuarioService
                ._mudaPermissoes(usuario)
                .then(function(response){
                    controller.usuariosSubordinados.replace(usuario,response.data);
                });
        };

        controller.excluirSubordinado = function( usuario ) {
            modalService.open('confirm', {
                message: 'Tem certeza de que deseja remover esse usuário?',
                confirmButton: 'Confirmar',
                cancelButton: 'Cancelar'
            })
            .then(function(){
                usuario.usuarioAtualizacao = authService.getUsername();
                usuario.idGrupoTrabalho = authService.getIdGrupoTrabalho();
                usuarioService
                    ._removerUsuario(usuario)
                    .then(function(response){
                        if (response.data) {
                            controller.usuariosSubordinados.delete(usuario);
                        }
                    });
            });
        };

        /* ------------------
         * Carrega Dados
         * -----------------*/
        _carregarUsuariosSubordinados();

        if (!authService.isAdmin())
            $window.location.href = $contextUrl + '#/acesso-negado';
    }]);

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

angular
    .module('cronos')
    .directive('appFab', ['$window', 'authService', 'sentencaService', function ($window, authService, sentencaService) {
        return {
            restrict: 'AE',
            scope: true,
            templateUrl: './views/directives/actionbutton/app-fab.html',
            link: function(scope, elem, attrs) {
                
                scope.openSentenca = function (attrs) {
                    authService.checarSessao();
                    sentencaService.clear();
                    sentencaService.setOpen(true);
                };

                scope.hasWriteAccess = function() {
                    return authService.hasWriteAccess();
                };
            }
        };
    }]);

angular.module('cronos')
    .directive('autoCompleteCategoriaTopico',['$http', 'ciService', 'authService', function ($http, ciService, authService){
    return {
        restrict:'AE',
        scope:{
            categoriaTopicoModel:'=model',
            readonly: '='
        },
        templateUrl:'./views/directives/autocomplete/autocomplete-categoria-topico.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = !scope.readonly && authService.hasWriteAccess();

            scope.categorias = [];
            scope.selectedCats = [];
            scope.categoriaTopicoModel.forEach(function(elemento){
                scope.selectedCats.push(elemento);
            });

            scope.selectedIndex=-1;

            scope.textoCriarNovo = ' (Criar novo)';

            scope.removeCategoriaTopico=function(element){
                scope.selectedCats = [];
                scope.categoriaTopicoModel = [];
            };

            scope.search=function(){
                if (scope.categoriaTopico.length >= 3 && scope.selectedCats.length === 0) {

                    var autoCompleteDTO = {
                        "part" : scope.categoriaTopico,
                        "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                        "tipoArvore" : "T"
                    };
                    ciService.listNodeByPartDescription(autoCompleteDTO)
                        .then(function(response) {

                            var data = response.data;
                            var arrayCategoria = [];
                            var arrayCategoriaT = [];
                            var arrayLinks = [];

                            var topicosarr = Array.prototype.slice.call(data);
                            topicosarr.forEach(function(elementoM) {
                                arrayCategoria.push(_.pick(elementoM, 'id', 'titulo'));
                            });

                            var linksarr = Array.prototype.slice.call(scope.selectedCats);
                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'titulo'));
                            });

                            if(scope.selectedCats.indexOf(scope.categoriaTopico)===-1){
                                if(scope.categoriaTopicoModel.indexOf(scope.categoriaTopico)===-1){
                                    arrayCategoriaT.push({id: -1, titulo: scope.categoriaTopico + scope.textoCriarNovo});
                                }
                            }

                            arrayCategoria.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length <= 0){
                                    arrayCategoriaT.push(tes);
                                }
                            });

                            scope.categorias = arrayCategoriaT;

                            scope.selectedIndex=-1;
                        });

                } else {
                    scope.categorias = [];
                }
            };

            scope.sanitizeSelectedSuggestionTag=function(titulo){
                return titulo.replace(scope.textoCriarNovo, "").trim();
            };

            scope.addToSelectedCats=function(index){
                var categoriaTag = scope.categorias[index];

                if(scope.selectedCats.indexOf(categoriaTag)===-1 && categoriaTag !== undefined){
                    if(categoriaTag.id < 0){
                        var sanitizedTitulo = scope.sanitizeSelectedSuggestionTag(categoriaTag.titulo);
                        categoriaTag = {
                            titulo: sanitizedTitulo,
                            idGrupoTrabalho: authService.getIdGrupoTrabalho(),
                            usuarioAtualizacao: authService.getUsername(),
                            tipoArvore: 'T'
                        };

                        ciService.saveNode(categoriaTag)
                            .then(function(response){
                                var data = response.data;

                                scope.selectedCats.push({id:data.id, titulo:data.titulo});
                                scope.categoriaTopicoModel.push({id:data.id, titulo:sanitizedTitulo});
                        });
                    } else {
                        scope.selectedCats.push({id:categoriaTag.id, titulo:categoriaTag.titulo});
                        scope.categoriaTopicoModel.push({id:categoriaTag.id, titulo:categoriaTag.titulo});
                    }

                    scope.categoriaTopico='';
                    scope.categorias=[];
                }

                scope.selectedIndex=-1;
            };

            scope.checkKeyDown=function(event){
                if(event.keyCode===40){
                    event.preventDefault();
                    if(scope.selectedIndex+1 !== scope.categorias.length){
                        scope.selectedIndex++;
                    }
                }
                else if(event.keyCode===38){
                    event.preventDefault();
                    if(scope.selectedIndex-1 !== -1){
                        scope.selectedIndex--;
                    }
                }
                else if(event.keyCode===13){
                    event.preventDefault();
                    if (scope.selectedIndex === -1) {
                        scope.selectedIndex = 0;
                    }
                    scope.addToSelectedCats(scope.selectedIndex);
                }
            };
        },
        controller: ["$scope", function($scope) {
            //scope.hasWriteAccess = !scope.readonly && authService.hasWriteAccess();
        }]
    };
}]);

angular.module('cronos')
    .directive('autoCompleteModelo',['$http', 'ciService', 'authService', 'modalService', 'sentencaService', 'Utils', '$timeout', function ($http, ciService, authService, modalService, sentencaService, Utils, $timeout){
    return {
        restrict:'AE',
        scope:{
            vinculoTMmodel:'=modelo',
            classe: '@classe',
            readonly: '='
        },
        templateUrl:'./views/directives/autocomplete/autocomplete-modelo.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = function() {
                return (!scope.readonly && authService.hasWriteAccess());
            };

            scope.isPreviewMode = function() {
                return scope.readonly;
            };

            scope.addModeloSentenca = function(data) {
                if (data.tipoArvore === 'M') {
                    var modelo = {
                        id : data.id,
                        titulo : data.label
                    };
                    sentencaService.addModelos(modelo);
                }
            };

            scope.suggestions = [];
            scope.selectedLinks = [];

            // sentencaService.modelos.forEach(function(elemento){
            //     scope.selectedLinks.push(elemento);
            // });

            scope.selectedIndex=-1;

            scope.removeVinculoTM=function(element){
                var removerModelos = false;
                modalService.open('confirm', {
                    message: 'Deseja excluir os t&oacute;picos relacionados ao modelo?',
                    confirmButton: 'Sim',
                    cancelButton: 'N&atilde;o',
                    abortButton: 'Cancelar',
                    abortButtonActive: true
                })
                .then( function( confirmActionResponse ){
                    var idModelo = sentencaService.modelos[0].id;
                    sentencaService.getTopicosByModelo(idModelo).then(function (response) {
                        angular.forEach( response.data, function (topico) {
                            Utils.removeElementById(sentencaService.topicos, topico);
                        });
                    });
                    removerModelos = true;
                }, function( cancelActionResponse ){
                    removerModelos = true;
                }, function( abortActionResponse ){
                    removerModelos = false;
                }).finally(function () {
                    if (removerModelos) {
                        Utils.removeElementById(scope.selectedLinks, element);
                        Utils.removeElementById(sentencaService.modelos, element);
                    }
                });
            };

            scope.search=function(){
                if (scope.vinculoTM.length >= 3 && scope.selectedLinks.length === 0) {
                    var autoCompleteDTO = {
                        "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                        "part" : scope.vinculoTM,
                        "tipoArvore" : 'M'
                    };

                    ciService.listByPartDescription(autoCompleteDTO)
                        .then(function(response) {
                            var data = response.data;
                            var arrayModelos = [];
                            var arrayModelosT = [];
                            var arrayLinks = [];

                            var modelosarr = Array.prototype.slice.call(data);
                            modelosarr.forEach(function(elementoM) {
                                arrayModelos.push(_.pick(elementoM, 'id', 'titulo'));
                            });

                            var linksarr = Array.prototype.slice.call(sentencaService.modelos);
                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'titulo'));
                            });

                            arrayModelos.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length <= 0){
                                    arrayModelosT.push(tes);
                                }
                            });

                            scope.suggestions = arrayModelosT;

                            scope.selectedIndex=-1;
                        });

                } else {
                    scope.suggestions = [];
                }
            };

            scope.clearSuggestions = function() {
                $timeout(function () {
                    scope.suggestions = [];
                    scope.searchText = '';
                    scope.selectedIndex= 0;
                }, 200);
            };

            scope.addToSelectedLinks=function(index){
                var suggestionTag = _.pick(scope.suggestions[index], 'id', 'titulo');

                if(scope.selectedLinks.indexOf(suggestionTag)===-1 && suggestionTag !== undefined){
                    scope.selectedLinks.push(suggestionTag);
                    sentencaService.addModelos(suggestionTag);

                    scope.vinculoTM='';
                    scope.suggestions=[];
                }
                scope.selectedIndex=-1;
            };

            scope.checkKeyDown=function(event){
                if(event.keyCode===40){
                    event.preventDefault();
                    if(scope.selectedIndex+1 !== scope.suggestions.length){
                        scope.selectedIndex++;
                    }
                }
                else if(event.keyCode===38){
                    event.preventDefault();
                    if(scope.selectedIndex-1 !== -1){
                        scope.selectedIndex--;
                    }
                }
                else if(event.keyCode===13){
                    event.preventDefault();
                    if (scope.selectedIndex === -1) {
                        scope.selectedIndex = 0;
                    }
                    scope.addToSelectedLinks(scope.selectedIndex);
                }
                else if(event.keyCode===27 || event.keyCode===9){
                    scope.clearSuggestions();
                }
            };
        },
        controller: ["$scope", function($scope) {

            $scope.hasWriteAccess = function() {
                return (!$scope.readonly && authService.hasWriteAccess());
            };

            $scope.isPreviewMode = function() {
                return $scope.readonly;
            };

            $scope.preview = function(selectedLink) {
                modalService.open( 'previewGt', {
                    confirmButton: "Fechar",
                    previewGtId: selectedLink.id
                });
            };
        }]
    };
}]);

angular.module('cronos')
    .directive('autoCompleteTopico',['$http', 'ciService', 'authService', 'modalService', 'sentencaService', 'Utils', '$timeout', 'gtService', function ($http, ciService, authService, modalService, sentencaService, Utils, $timeout, gtService){
    return {
        restrict:'AE',
        scope:{
            vinculoTMmodel:'=model',
            classe: '@classe',
            readonly: '=',
            chamador: '@chamador'
        },
        templateUrl:'./views/directives/autocomplete/autocomplete-topico.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = function() {
                return (!scope.readonly && authService.hasWriteAccess());
            };

            scope.addTopico = function (data, chamador) {
                if (data.tipoArvore === 'T') {
                    var topico = {
                        id : data.id,
                        titulo : data.label
                    };
                    if (chamador === "sentenca") {
                        sentencaService.addTopicos(topico);
                    } else {
                        gtService.addTopicos(topico);
                    }
                }
            };

            scope.getTopicos = function() {
                return gtService.getTopicos();
            };

            scope.isPreviewMode = function() {
                return scope.readonly;
            };

            scope.suggestions = [];
            scope.selectedLinks = [];

            if (scope.chamador === "sentenca") {
                sentencaService.topicos.forEach(function(elemento){
                    scope.selectedLinks.push(elemento);
                });
            } else {
                gtService.topicos.forEach(function(elemento){
                    scope.selectedLinks.push(elemento);
                });
            }

            scope.selectedIndex=-1;

            scope.removeVinculoTM=function(element){
                Utils.removeElementById(scope.selectedLinks, element);

                if (scope.chamador === "sentenca") {
                    Utils.removeElementById(sentencaService.topicos, element);
                } else {
                    Utils.removeElementById(gtService.topicos, element);
                }
            };
            scope.trocaSelecionado=function(indice){
                scope.selectedIndex=indice;
            };
            scope.search=function(){
                if (scope.vinculoTM.length >= 3) {
                    var autoCompleteDTO = {
                        "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                        "part" : scope.vinculoTM
                    };
                    ciService.listByPartDescription(autoCompleteDTO)
                        .then(function(response) {
                            var data = response.data;
                            var arrayModelos = [];
                            var arrayModelosT = [];
                            var arrayLinks = [];

                            var modelosarr = Array.prototype.slice.call(data);
                            modelosarr.forEach(function(elementoM) {
                                arrayModelos.push(_.pick(elementoM, 'id', 'titulo'));
                            });

                            var linksarr = false;
                            if (scope.chamador === "sentenca") {
                                linksarr = Array.prototype.slice.call(sentencaService.topicos);
                            } else {
                                linksarr = Array.prototype.slice.call(gtService.topicos);
                            }

                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'titulo'));
                            });

                            arrayModelos.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length === 0){
                                    arrayModelosT.push(tes);
                                }
                            });

                            scope.suggestions = arrayModelosT;

                            scope.selectedIndex=-1;
                        });

                } else {
                    scope.suggestions = [];
                }
            };

            scope.clearSuggestions = function() {
                $timeout(function () {
                    scope.suggestions = [];
                    scope.vinculoTM = '';
                    scope.selectedIndex=-1;
                }, 200);
            };

            scope.addToSelectedLinks=function(index){
                var suggestionTag = scope.suggestions[index];

                if(scope.selectedLinks.indexOf(suggestionTag)===-1 && suggestionTag !== undefined){
                    scope.selectedLinks.push(suggestionTag);
                    if (scope.chamador === "sentenca") {
                        sentencaService.addTopicos(suggestionTag);
                    } else {
                        gtService.addTopicos(suggestionTag);
                    }

                    scope.vinculoTM='';
                    scope.suggestions=[];
                }
                scope.selectedIndex=-1;
            };

            scope.swap = function(index, pos){
                return scope.vinculoTMmodel.swap(index, pos);
            };

            scope.checkKeyDown=function(event){
                if(event.keyCode===40){
                    event.preventDefault();
                    if(scope.selectedIndex+1 !== scope.suggestions.length){
                        scope.selectedIndex++;
                    }
                }
                else if(event.keyCode===38){
                    event.preventDefault();
                    if(scope.selectedIndex-1 !== -1){
                        scope.selectedIndex--;
                    }
                }
                else if(event.keyCode===13){
                    event.preventDefault();
                    if (scope.selectedIndex === -1) {
                        scope.selectedIndex = 0;
                    }
                    scope.addToSelectedLinks(scope.selectedIndex);
                }
                else if(event.keyCode===27 || event.keyCode===9){
                    scope.clearSuggestions();
                }
            };
        },
        controller: ["$scope", function($scope) {

            $scope.hasWriteAccess = function() {
                return (!$scope.readonly && authService.hasWriteAccess());
            };

            $scope.isPreviewMode = function() {
                return $scope.readonly;
            };

            $scope.preview = function(selectedLink) {
                modalService.open( 'previewGt', {
                    confirmButton: "Fechar",
                    previewGtId: selectedLink.id
                });
            };
        }]
    };
}]);

angular.module('cronos')
    .directive('autoComplete',['$http', 'etiquetaGTService', 'authService', '$timeout', 'Utils', function($http, etiquetaGTService, authService, $timeout, Utils){
    return {
        restrict:'AE',
        scope:{
            listaEtiqueta:'=model',
            readonly: '='
        },
        templateUrl:'./views/directives/autocomplete/autocomplete.html',
        link:function(scope,elem,attrs){

            scope.hasWriteAccess = !scope.readonly && authService.hasWriteAccess();

            scope.suggestions = [];
            scope.selectedTags = [];

            scope.listaEtiqueta.forEach(function(elemento){
                scope.selectedTags.push(elemento);
            });
            scope.selectedIndex= 0;

            scope.textoCriarNovo = ' (Criar novo)';

            scope.removeTag=function(elemento) {
                Utils.removeElementById(scope.selectedTags, elemento);
                Utils.removeElementById(scope.listaEtiqueta, elemento);
            };
            scope.trocaSelecionado=function(indice){
                scope.selectedIndex=indice;
            };

            scope.isActive=function(indice){
                return scope.selectedIndex === indice;
            };

            scope.search=function(){
                scope.selectedTags = [];
                scope.listaEtiqueta.forEach(function(elemento){
                    scope.selectedTags.push(elemento);
                });

                if (scope.searchText.length >= 3) {
                    var listPartEtiqueta = {
                        idGrupoTrabalho : authService.getIdGrupoTrabalho(),
                        part : scope.searchText
                    };
                    etiquetaGTService.listByPartDescription(listPartEtiqueta)
                        .then(function(response){
                            var data = response.data;
                            var arrayEtiqueta = [];
                            var arrayEtiquetaT = [];
                            var arrayLinks = [];

                            var etiquetaarr = Array.prototype.slice.call(data);
                            etiquetaarr.forEach(function(elementoE) {
                                arrayEtiqueta.push(_.pick(elementoE, 'id', 'etiqueta', 'idIcone', 'icone', 'cor'));
                            });
                            //--
                            var linksarr = Array.prototype.slice.call(scope.selectedTags);
                            linksarr.forEach(function(elementoL){
                                arrayLinks.push(_.pick(elementoL, 'id', 'etiqueta', 'id', 'etiqueta', 'idIcone', 'icone', 'cor'));
                            });
                            //--
                            arrayEtiqueta.forEach(function(tes){
                                if(_.where(arrayLinks, tes).length <= 0){
                                    arrayEtiquetaT.push(tes);
                                }
                            });
                            /* Verifica se existe uma etiqueta com a string digitada */
                            var achou = false;
                            linksarr.forEach(function(eti) {
                                if (eti.etiqueta === scope.searchText) {
                                    achou = true;
                                }
                            });
                            /* Verifica se existe uma etiqueta com a string digitada */
                            arrayEtiquetaT.forEach(function(eti) {
                                if (eti.etiqueta === scope.searchText) {
                                    achou = true;
                                }
                            });
                            if (!achou) {
                                arrayEtiquetaT.push({id: 0, etiqueta: scope.searchText + scope.textoCriarNovo});
                            }
                            scope.suggestions = arrayEtiquetaT;
                            scope.selectedIndex=0;

                        });
                } else {
                    scope.suggestions = [];
                }
            };

            scope.clearSuggestions = function() {
                $timeout(function () {
                    scope.suggestions = [];
                    scope.searchText = '';
                    scope.selectedIndex= 0;
                }, 200);
            };

            scope.addToSelectedTags=function(index){
                var suggestionTag = _.pick(scope.suggestions[index], 'id', 'etiqueta', 'id', 'etiqueta', 'idIcone', 'icone', 'cor');

                var sanitizedTag = suggestionTag.etiqueta.replace(scope.textoCriarNovo, "").trim();

                if(scope.selectedTags.indexOf(suggestionTag)===-1 && suggestionTag !== undefined){
                    if (suggestionTag.etiqueta.indexOf(scope.textoCriarNovo) > -1) {
                        scope.salvarEtiqueta(sanitizedTag).success(function(response){
                            scope.selectedTags.push({id:response.id, etiqueta:sanitizedTag});
                            scope.listaEtiqueta.push({id:response.id, etiqueta:sanitizedTag});
                        });
                    } else {
                        scope.selectedTags.push(suggestionTag);
                        scope.listaEtiqueta.push(suggestionTag);
                    }

                    scope.searchText='';
                    scope.suggestions=[];
                }
                scope.selectedIndex = 0;
            };

            scope.checkKeyDown=function(event){
                if(event.keyCode===40){
                    event.preventDefault();
                    if(scope.selectedIndex !== (scope.suggestions.length -1)) {
                        scope.selectedIndex++;
                    }
                }
                else if(event.keyCode===38){
                    event.preventDefault();
                    if(scope.selectedIndex !== 0){
                        scope.selectedIndex--;
                    }
                }
                else if(event.keyCode===13){
                    event.preventDefault();
                    scope.addToSelectedTags(scope.selectedIndex);
                }
                else if(event.keyCode===27 || event.keyCode===9){
                    scope.clearSuggestions();
                }
            };

            scope.salvarEtiqueta = function( nomeEtiqueta ){
                var etiqueta = {
                    usuarioAtualizacao: authService.getUsername(),
                    idGrupoTrabalho : authService.getIdGrupoTrabalho(),
                    etiqueta: nomeEtiqueta
                };
                return etiquetaGTService.save(etiqueta);
            };
        }
    };
}]);

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
            controller: ["$scope", function($scope){
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
            }]

        };
    }]);

angular
    .module('cronos')
    .directive('appGtItem', ['authService', function (authService) {
        return {
            restrict: 'AE',
            scope: {
                node: '=item'
            },
            templateUrl: './views/directives/grupotrabalho/app-gt-item.html',
            controller: ["$scope", function($scope){
                $scope.hasWriteAccess = authService.hasWriteAccess();
                $scope.isAdmin = authService.isAdmin();
            }]
        };
    }]);

angular
    .module('cronos')
    .directive('appGtModelos', function() {
        return {
            restrict: 'AE',
            scope: {
                item: '=item',
                topicos: '=topicos',
                preview: '=',
                readonly: '='
            },
            templateUrl: './views/directives/grupotrabalho/app-gt-modelos.html',
            link: function(scope, element, attrs){

            },
            controller: ["$scope", function($scope){

            }]
        };
    });

angular
    .module('cronos')
    .directive('appGtTopicos', function() {
        return {
            restrict: 'AE',
            scope: {
                item: '=',
                preview: '=',
                readonly: '='
            },
            templateUrl: './views/directives/grupotrabalho/app-gt-topicos.html',
            link: function(scope, element, attrs){

            },
            controller: ["$scope", function($scope){

            }]
        };
    });

angular.module('cronos').factory('alegacaoGrupoTrabalhoService', ['EntityService', function alegacaoGrupoTrabalhoServiceFactory(EntityService) {
    'use strict';

    var AlegacaoGrupoTrabalhoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'alegacaoGT'
            });
            this.alegacoes = [];
            this.alegacoesPreenchidas = [];
        },
        /* METODOS REST */
        _all: function() {
            return this._get('/all');
        },
        _findAllByGrupoTrabalho : function( idGrupoTrabalho ) {
            return this._get('/findAllByGrupoTrabalho/' + idGrupoTrabalho);
        },
        /* METODOS DE INTERFACE */
        carregarAlegacoes : function( idGrupoTrabalho ) {
            var service = this;
            service._findAllByGrupoTrabalho(idGrupoTrabalho).then(function(response) {
                service.setAlegacoes(response && response.data);
            });
        },
        /*METODO DE ATRIBUTOS */
        getAlegacoes : function () {
            return this.alegacoes;
        },
        getAlegacoesPreenchidas : function () {
            return this.alegacoesPreenchidas;
        },
        setAlegacoes : function (lista) {
            this.alegacoes = lista;
            this.setAlegacoesPreenchidas(this.alegacoes);
        },
        setAlegacoesPreenchidas : function (lista) {
            var listaNova = [];
            lista.forEach(function(elemento){
                if (elemento.textoAlegacao) {
                    listaNova.push(elemento);
                }
            });
            listaNova.sort(function(a, b){
                if(a.tipoAlegacao.label < b.tipoAlegacao.label) return -1;
                if(a.tipoAlegacao.label > b.tipoAlegacao.label) return 1;
                return 0;
            });
            this.alegacoesPreenchidas = listaNova;
        }
    });
    return new AlegacaoGrupoTrabalhoService();
}]);

angular.module('cronos')
    .factory('autoCompleteService', ['$q', 'RestService', function autoCompleteFactory($q, RestService) {
    'use strict';

    var AutoCompleteService = $class({

        constructor: function() {
            this.rest = new RestService({
                path: '/rest/autocomplete'
            });
        }
    });

    return new AutoCompleteService();
}]);

angular.module('cronos')
    .factory('ciService', ['EntityService', function CategoriaItemServiceFactory(EntityService) {
    'use strict';

    var CategoriaItemService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'categoriaItem'
            });
        },

        /**
         * @param
         * {
         * 		tipoArvore = ('T'/'M'/'S'),
         * 		idCategoria,
         * 		idGrupoTrabalho
         * }
         *
         * @returns
         * {
         *  	categoria = <CategoriaItemEntity> {
         * 			id;
         * 			titulo;
         * 			icone,
         * 			cor,
         * 			idIcone,
         * 			ordem,
         * 			idPai,
         * 			idGrupoTrabalho,
         * 			descricaoArvore,
         * 			compartilhar = ('S'/'N'),
         * 			tipo, ('N'/'E'/'I')
         * 			arvore, ('T'/'M'/'S')
         * 			iconePersonal = <IconesPersonalEntity> {
         * 				id,
         * 				imagem,
         * 				tipo
         * 			},
         * 			tipoCategoria = <TipoCategoriaEntity> {
         * 				id,
         * 				nome
         * 			},
         * 			itens = [<CategoriaItemEntity>],
         * 			taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         * 			elementos = [<ElementoIGTEntity>]
         * 		},
         *		categoriasModelo = [<CategoriaItemEntity>]
         * }
         * */
        carregarCategoria: function(carregarCategoria) {
            return this._post('/carregarCategoria', carregarCategoria);
        },

        /**
         * @param
         * {
         * 		part,
         * 		idGrupoTrabalho
         * }
         *
         * @returns
         * {
         * 		[<CategoriaItemEntity>]
         * }
         * */
        listByPartDescription : function(autoCompleteDTO) {
            return this._post('/listByPartDescription', autoCompleteDTO);
        },

        /**
         * @param
         * {
         * 		part,
         * 		idGrupoTrabalho
         * }
         *
         * @returns
         * {
         * 		[<CategoriaItemEntity>]
         * }
         * */
        listNodeByPartDescription : function(autoCompleteDTO) {
            return this._post('/listNodeByPartDescription', autoCompleteDTO);
        },

        /**
         * @param
         * {
         * 		titulo,
         * 		idGrupoTrabalho,
         *      usuarioAtualizacao,
         *      tipoArvore, ('T'/'M'/'S')
         * }
         *
         * @returns
         * {
         * 		<CategoriaItemEntity>
         * }
         * */
        saveNode : function(node) {
            return this._post('/saveNode', node);
        },

        /**
         * @param
         * {
         * 		part,
         * 		tipoArvore = ('T'/'M'/'S'),
         * 		idGrupoTrabalho,
         * }
         *
         * @returns
         * [{
         * 		id,
         * 		titulo,
         *  	arvore = ('T'/'M'/'S'),
         *  	label = [{String}],
         *		excluir = (true/false),
         *		editar = (true/false),
         *		revisar = (true/false),
         *		adicionar = (true/false),
         *		visualizar = (true/false),
         * }]
         * */
        buscarPesquisaGeral : function(pesquisaGeralDTO) {
            return this._post('/buscarPesquisaGeral', pesquisaGeralDTO);
        },

        /**
         * @param
         * {
         *  	categoria = <CategoriaItemEntity>,
         *  	taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         *  	taxonomiasCategoria = [<TaxonomiaCategoriaEntity>],
         *  	taxonomiasCategoriaNovas = [<TaxonomiaCategoriaEntity>,
         *  	elementos = [<ElementoIGTEntity>]
         * }
         *
         * @returns
         * <CategoriaItemEntity> {
         * 		id;
         * 		titulo;
         * 		icone,
         * 		cor,
         * 		idIcone,
         * 		ordem,
         * 		idPai,
         * 		idGrupoTrabalho,
         * 		descricaoArvore,
         * 		compartilhar = ('S'/'N'),
         * 		TipoNodo tipo, ('N'/'E'/'I')
         * 		TipoArvore arvore, ('T'/'M'/'S')
         * 		iconePersonal = <IconesPersonalEntity> {
         * 			id,
         * 			imagem,
         * 			tipo
         * 		},
         * 		tipoCategoria = <TipoCategoriaEntity> {
         * 			id,
         * 			nome
         * 		},
         * 		itens = [<CategoriaItemEntity>],
         * 		taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         * 		elementos = [<ElementoIGTEntity>]
         * }
         * */
        salvarTopico: function(topico) {
            return this._post('/salvarTopico', topico);
        },

        /**
         * @param
         * {
         * 		id,
         * 		icone,
         * 		cor,
         * 		imagem = {
         * 			filesize,
         * 			filetype,
         * 			filename,
         * 			base64
         * 		}
         * }
         *
         * @returns
         * <CategoriaItemEntity> {
         * 		id;
         * 		titulo;
         * 		icone,
         * 		cor,
         * 		idIcone,
         * 		ordem,
         * 		idPai,
         * 		idGrupoTrabalho,
         * 		descricaoArvore,
         * 		compartilhar = ('S'/'N'),
         * 		TipoNodo tipo, ('N'/'E'/'I')
         * 		TipoArvore arvore, ('T'/'M'/'S')
         * 		iconePersonal = <IconesPersonalEntity>{
         * 			id,
         * 			imagem,
         * 			tipo
         * 		},
         * 		tipoCategoria = <TipoCategoriaEntity>{
         * 			id,
         * 			nome
         * 		},
         * 		itens = [<CategoriaItemEntity>],
         * 		taxonomiasEtiqueta = [<TaxonomiaEtiquetaGTEntity>],
         * 		elementos = [<ElementoIGTEntity>]
         * }
         * */
        alterarIcone: function(icone) {
            return this._post('/alterarIcone', icone);
        },

        cloneItem: function(item) {
            return this._post('/cloneItem', item);
        },

        listTopicosByIdModelo: function(id) {
            return this._get('/listTopicosByIdModelo/' + id);
        }
    });

    return new CategoriaItemService();
}]);

angular.module('cronos')
    .factory('configService', ['EntityService', function confServiceFactory(EntityService) {
    'use strict';

    var ConfiguracaoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'configuracao'
            });
            this.configuracao = false;
        },
        /* METODOS REST */
        _findConfiguracaoByGrupoTrabalho: function(idGrupoTrabalho) {
            return this._get('/findConfiguracaoByGrupoTrabalho/' + idGrupoTrabalho);
        },
        _permitirEditarMenu: function(obj) {
            return this._post('/permitirEditarMenu', obj);
        },
        /* METODOS DE INTERFACE */
        carregarConfiguracao: function(idGrupoTrabalho) {
            var service = this;
            service._findConfiguracaoByGrupoTrabalho(idGrupoTrabalho)
                .then(function(response) {
                    service.setConfiguracao(response && response.data);
                });
        },
        permitirEditarMenu: function(permitir, idGrupoTrabalho, usuarioAtualizacao) {
            var service = this;

            var obj = {
                permiteEditarMenu: permitir,
                idGrupoTrabalho: idGrupoTrabalho,
                usuarioAtualizacao: usuarioAtualizacao
            };

            service._permitirEditarMenu(obj)
                .then(function(response) {
                    service.setPermitirEditarMenu(permitir);
                });
        },
        /*METODO DE ATRIBUTOS */
        getConfiguracao : function () {
            return this.configuracao;
        },
        setConfiguracao : function (resposta) {
            this.configuracao = resposta;
        },
        getPermitirEditarMenu : function () {
            return this.configuracao ? this.configuracao.permiteEditarMenu : null;
        },
        setPermitirEditarMenu : function (permite) {
            if (this.configuracao) {
                this.configuracao.permiteEditarMenu = permite;
            }
        }
    });

    return new ConfiguracaoService();
}]);

angular.module('cronos')
    .factory('elementoItemGrupoTrabalhoService', ['EntityService', function ElementoItemGrupoTrabalhoServiceFactory(EntityService) {
    'use strict';

    var ElementoItemGrupoTrabalhoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'elementoIGT'
            });
        },

        listByPartDescription : function(part) {
            return this._get('/listByPartDescription/' + part);
        },

        /**
         * @param
         * {
         * 		part,
         * 		tipoArvore = ('T'/'M'/'S'),
         * 		idGrupoTrabalho,
         * }
         *
         * @returns
         * [{
         * 		id,
         * 		titulo,
         *  	arvore = ('T'/'M'/'S'),
         *  	label = [{String}],
         *		excluir = (true/false),
         *		editar = (true/false),
         *		revisar = (true/false),
         *		adicionar = (true/false),
         *		visualizar = (true/false),
         * }]
         * */
        buscarPesquisaGeral : function(pesquisaGeralDTO) {
            return this._post('/buscarPesquisaGeral', pesquisaGeralDTO);
        },

    });

    return new ElementoItemGrupoTrabalhoService();
}]);

angular.module('cronos')
    .factory('etiquetaGTService', ['EntityService', function etiquetaGTServiceFactory(EntityService) {
    'use strict';

    var EtiquetaGTService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'etiquetaGT'
            });
        },

        listByPartDescription: function(listPartEtiqueta) {
            return this._post('/listByPartDescription/', listPartEtiqueta);
        },

        /**
         * @param
         * {
         * 		part,
         * 		tipoArvore = ('T'/'M'/'S'),
         * 		idGrupoTrabalho,
         * }
         *
         * @returns
         * [{
         * 		id,
         * 		titulo,
         *  	arvore = ('T'/'M'/'S'),
         *  	label = [{String}],
         *		excluir = (true/false),
         *		editar = (true/false),
         *		revisar = (true/false),
         *		adicionar = (true/false),
         *		visualizar = (true/false),
         * }]
         * */
        buscarPesquisaGeral : function(pesquisaGeralDTO) {
            return this._post('/buscarPesquisaGeral', pesquisaGeralDTO);
        },

        getAll: function() {
            return this._get('/all');
        },

        /**
         * @param
         * {
         * 		id,
         * 		icone,
         * 		cor,
         * 		imagem = {
         * 			filesize,
         * 			filetype,
         * 			filename,
         * 			base64
         * 		}
         * }
         *
         * @returns
         * <EtiquetaGTEntity> {
         * 		id,
         * 		etiqueta,
         * 		icone,
         * 		idIcone,
         * 		cor,
         * 		iconePersonal = <IconesPersonalEntity> {
         * 			id,
         * 			imagem,
         * 			tipo
         * 		}
         * }
         * */
        alterarIcone: function(icone) {
            return this._post('/alterarIcone', icone);
        }
    });

    return new EtiquetaGTService();
}]);

angular.module('cronos')
    .factory('gtService', ['EntityService', 'authService', function gtServiceFactory(EntityService, authService) {
    'use strict';

    var GrupoTrabalhoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'grupoTrabalho'
            });
            this.result = [];
            this.topicos = [];
        },

        /* METODOS REST */
        _findTreeView: function(idGrupoTrabalho) {
            return this._get('/findTreeView/' + idGrupoTrabalho);
        },
        _findByUsuario: function(idUsuario) {
            return this._get('/findByUsuario/' + idUsuario);
        },
        _alteraPadrao: function(grupo) {
            return this._post('/alteraPadrao',  grupo);
        },

        /* METODOS DE INTERFACE */
        carregarMenu : function() {
            var service = this;
            service
                ._findTreeView(authService.getIdGrupoTrabalho(), {})
                .then(function(response) {
                        service.result = response.data;
                    });
        },
        clear: function() {
            this.topicos = [];
        },
        getMenu : function() {
            return this.result.itens;
        },
        setModeloScreen : function() {
            authService.setModeloScreen(true);
        },
        getTopicos: function() {
            return this.topicos;
        },
        addTopicos: function(topico) {
            var top = _.pick(topico, 'id', 'titulo');
            if(_.where(this.topicos, top).length === 0){
                this.topicos.push(top);
            }
        }

        /* METODO DE ATRIBUTOS */
    });

    return new GrupoTrabalhoService();
}]);

angular.module('cronos')
    .factory('iconesPersonalService', ['EntityService', function iconesPersonalServiceFactory(EntityService) {
    'use strict';

    var IconesPersonalService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'iconesPersonal'
            });
        },

        css: function() {
            return this._get('/css');
        }
    });

    return new IconesPersonalService();
}]);

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

angular
    .module('cronos')
    .factory('sentencaService', ['EntityService', 'authService', 'ciService', 'Utils', function sentencaServiceFactory(EntityService, authService, ciService, Utils) {
    'use strict';

    var SentencaService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'sentenca'
            });
            this.openStatus = false;
            this.loadedStatus = false;
            this.visibleStatus = false;
            this.abaSentenca = true;
            this.abaDados = false;
            //Objeto principal do servico
            this.sentenca = {
                numeroProcesso : null,
                finalizada : 'N',
                dadosProcesso : {},
                modelo : {},
                topicos : []
            };
            //--
            this.dadosProcesso = {
                assuntoCNJ: null,
                classeCNJ: null,
                codigoComarca: null,
                juizado: null,
                numeroCNJ: null,
                numeroFormatado : null,
                reu: [],
                autor: [],
                valorAcao: null,
                vara: null
            };

            this.modelos = [];
            this.topicos = [];
        },

        /* METODOS REST */
        testarGerarDocumentoSentenca: function(id) {
            return this._get('/testarGerarDocumentoSentenca/' + id);
        },
        _carregarSentencaId : function(sentencaDTO){
            return this._post('/carregarSentencaId', sentencaDTO);
        },
        _salvarSentenca : function(sentencaDTO){
            return this._post('/salvarSentenca', sentencaDTO);
        },
        _finalizarSentenca : function(sentencaDTO){
            return this._post('/finalizarSentenca', sentencaDTO);
        },
        _removerSentenca : function(sentencaDTO) {
            return this._post('/removerSentenca', sentencaDTO);
        },
        _findByStatusFinalizada : function(sentencaDTO) {
            return this._post('/findByStatusFinalizada/', sentencaDTO);
        },

        /* METODOS DE INTERFACE */
        gerarSentenca : function(){
            var service = this;
            if (service.getModelos()[0]) {
                service.sentenca.modelo = service.getModelos()[0];
            } else {
                service.sentenca.modelo = {};
            }
            service.sentenca.topicos = service.getTopicos();
            service.sentenca.idGrupoTrabalho = (service.sentenca.idGrupoTrabalho ? service.sentenca.idGrupoTrabalho : authService.getIdGrupoTrabalho());
            service.sentenca.usuarioAtualizacao = authService.getUsername();
            service.sentenca.numeroProcesso = (service.sentenca.numeroProcesso ? service.sentenca.numeroProcesso.replace(/\D/g,'') : "");
            return service._salvarSentenca(service.getSentenca());
        },
        carregarSentenca : function(id, open){
            var service = this;
            var dto = { id: id };
            if (id) {
                service._carregarSentencaId(dto).then(function (response){
                    service.setSentenca(response && response.data);
                    if (!Utils.isUndefinedOrNull(open))
                        service.setOpen(open);
                    else
                        service.setOpen(true);

                });
            }
        },
        clear: function() {
            //Objeto principal do servico
            this.sentenca = {
                numeroProcesso : null,
                finalizada : 'N',
                dadosProcesso : {},
                modelo : {},
                topicos : []
            };
            //--
            this.dadosProcesso = {
                assuntoCNJ: null,
                classeCNJ: null,
                codigoComarca: null,
                juizado: null,
                numeroCNJ: null,
                numeroFormatado : null,
                reu: [],
                autor: [],
                valorAcao: null,
                vara: null
            };
            this.modelos = [];
            this.topicos = [];
        },
        isOpen : function() {
            return this.openStatus;
        },
        isLoaded : function() {
            return this.loadedStatus;
        },
        isVisible : function() {
            return this.visibleStatus;
        },
        setOpen : function( status ) {
            this.visibleStatus = true;
            this.loadedStatus = true;
            this.openStatus = status;
        },
        setClose : function() {
            this.visibleStatus = false;
            this.openStatus = false;
        },
        isAbaSentenca : function() {
            return this.abaSentenca;
        },
        isAbaDados : function() {
            return this.abaDados;
        },
        setAbaSentenca : function(valor) {
            this.abaSentenca = valor;
        },
        setAbaDados : function(valor) {
            this.abaDados = valor;
        },

        getTopicosByModelo : function (idModelo) {
            return ciService.listTopicosByIdModelo(idModelo);
        },
        /* METODO DE ATRIBUTOS */
        getSentenca: function() {
            return this.sentenca;
        },
        setSentenca: function(dto) {
            var service = this;
            service.sentenca = dto;
            if (service.sentenca.modelo) {
                service.addModelos(service.sentenca.modelo);
            }
            service.sentenca.topicos.forEach(function(elemento){
                service.addTopicos(elemento);
            });
            service.setDadosProcesso(service.sentenca.dadosProcesso);
        },
        getDadosProcesso: function() {
            return this.dadosProcesso;
        },
        setDadosProcesso: function(dto) {
            this.dadosProcesso = dto;
        },
        getTopicos: function() {
            return this.topicos;
        },
        setTopicos: function(lista) {
            this.topicos = lista;
        },
        addTopicos: function(topico) {
            var top = _.pick(topico, 'id', 'titulo');
            if(_.where(this.topicos, top).length === 0){
                this.topicos.push(top);
            }
        },
        getModelos: function() {
            return this.modelos;
        },
        setModelos: function(lista) {
            this.modelos = lista;
        },
        addModelos: function(modelo) {
            var sentenca = this;
            if(sentenca.modelos.length === 0){
                sentenca.modelos.push(_.pick(modelo, 'id', 'titulo'));

                ciService.listTopicosByIdModelo(modelo.id)
                .then(function(response) {
                    var topicos = response.data;

                    topicos.forEach(function(elemento){
                        elemento = _.pick(elemento, 'id', 'titulo');
                        if(_.where(sentenca.topicos, elemento).length === 0){
                            sentenca.topicos.push(elemento);
                        }
                    });
                });
            }
        },

        /* REGRAS E AÇOES */
        verificaSeExisteFinalizada: function() {
            var service = this;
            return service._findByStatusFinalizada( service.getSentenca() );
        },
        removerSentenca: function ( sentencaDTO ) {
            var service = this;
            console.log(sentencaDTO);
            return service._removerSentenca( sentencaDTO );
        },
        finalizarSentenca: function() {
            var service = this;
            var sentenca = service.getSentenca();
            return service._finalizarSentenca( sentenca );
        }

    });
    return new SentencaService();
}]);

angular.module('cronos')
    .factory('tipoAlegacaoService', ['EntityService', function tipoAlegacaoServiceFactory(EntityService) {
    'use strict';

    var TipoAlegacaoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'tipoAlegacao'
            });
        },

        all: function() {
            return this._get('/all');
        }
    });

    return new TipoAlegacaoService();
}]);

angular.module('cronos')
    .factory('tipoElementoService', ['EntityService', function tipoElementoServiceFactory(EntityService) {
    'use strict';

    var TipoElementoService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'tipoElemento'
            });
        },

        all: function() {
            return this._get('/all');
        }
    });

    return new TipoElementoService();
}]);

angular.module('cronos')
    .factory('tokenService', ['EntityService', function tokenServiceFactory(EntityService) {
    'use strict';

    var TokenService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                 type: 'token'
            });
            this.listaTokens = false;
            this.token = false;
        },
        /* METODOS REST */
        _all: function() {
            return this._get('/all');
        },
        /* METODOS DE INTERFACE */
        carregarTokens : function() {
            var service = this;
            service._all().then(function(response){
                service.setListaTokens(response && response.data);
            });
        },
        /*METODO DE ATRIBUTOS */
        getListaTokens : function() {
            return this.listaTokens;
        },
        setListaTokens : function(lista) {
            this.listaTokens = lista;
        }
    });

    return new TokenService();
}]);

angular.module('cronos')
    .factory('usuarioService', ['EntityService', function usuarioServiceFactory(EntityService) {
    'use strict';

    var UsuarioService = $class(EntityService, {

        constructor: function() {
            EntityService.call(this, {
                type: 'usuario'
            });
        },

        getAll: function() {
            return this._get('/all');
        },
        _findByGrupoTrabalho: function(id) {
            return this._get('/findByGrupoTrabalho/' + id);
        },
        _salvarUsuario: function(dto) {
            return this._post('/salvarUsuario', dto);
        },
        _removerUsuario: function(usuario) {
            return this._post('/removerUsuario', usuario);
        },
        getByCpf: function(cpf) {
            return this._get('/findByCpf?cpf=' + cpf);
        },
        /*
         * {
         * id,
         * usuarioAtualizacao
         * }
         **/
        aceiteTermo: function(aceiteTermoDTO) {
            return this._post('/aceiteTermo', aceiteTermoDTO);
        },
        _mudaPermissoes :function(usuarioDTO) {
            return this._post('/mudaPermissoes', usuarioDTO);
        }
    });

    return new UsuarioService();
}]);

angular.module('cronos').factory('mockService', ["$httpBackend", "$cookieStore", "RestService", "authService", function mockServiceFactory($httpBackend, $cookieStore, RestService, authService) {
    'use strict';

    // http response
    var response = {
        ok: function(content) {
            return this.other(200, content);
        },
        error: function(error) {
            return this.other(500, error);
        },
        unauthorized: function() {
            return this.other(401);
        },
        other: function(status, content) {
            return [status, content];
        }
    };

    var appMocks = {
        // authentication mock
        auth: function(baseUrl, config) {
            // Emulate authentication using cookie

            $httpBackend.whenGET(baseUrl + '/rest/auth/check').respond(function(method, url, data) {

                var user = $cookieStore.get('auth-user');

                if (user) {
                    return response.ok(user);
                }
                return response.unauthorized();
            });

            $httpBackend.whenPOST(baseUrl + '/rest/auth/login').respond(function(method, url, data) {

                var dataUser = angular.fromJson(data);

                var user = {
                    mock: true,
                    username: 'Mock ' + (dataUser.username || 'User'),
                    roles: ['APP_CRONOS']
                };

                $cookieStore.put('auth-user', user);

                return response.ok(user);
            });

            $httpBackend.whenPOST(baseUrl + '/rest/auth/logout').respond(function(method, url, data) {

                $cookieStore.remove('auth-user');

                return response.ok(true);
            });
        },

        grupoTrabalho: function(baseUrl, config) {

            $httpBackend.whenGET(baseUrl + '/rest/entity/grupoTrabalho/findTreeView/').respond(function(method, url, data) {
                return response.ok({
                    page: 1,
                    total: 10,
                    list: [{
                        nome: 'TÓPICOS',
                        itens: [{
                            nome: 'Furto',
                            items: [{
                                nome: 'Drogas 1'
                            }, {
                                nome: 'Drogas 9',
                                items: [{
                                    nome: 'SubSubItem'
                                }]
                            }]
                        }, {
                            nome: 'Furto 2',
                            itens: [{
                                nome: 'Drogas 1'
                            }, {
                                nome: 'Drogas 5'
                            }]
                        }, {
                            nome: 'Latrocinio',
                            items: [{
                                nome: 'Drogas 1'
                            }]
                        }]
                    }, {
                        nome: 'MODELOS',
                        items: [{
                            nome: 'Doc 1'
                        }, {
                            nome: 'Doc 4'
                        }]
                    }, {
                        nome: 'SENTENÇAS'
                    }]
                });
            });
        }
    };

    return {
        mock: function(mocksConfig) {

            var baseUrl = RestService.getServiceUrl();

            // iterate over all mocks
            angular.forEach(appMocks, function(value, key) {
                var config = mocksConfig[key];
                if (config !== false) {
                    value(baseUrl, config);
                    console.log('#mockService - mock ' + key + ' enabled!');
                } else {
                    // console.log('#mockService - mock ' + key + ' disabled!');
                }
            });

            // All other URL's must pass directily
            $httpBackend.whenGET(/.*/).passThrough();
            $httpBackend.whenPOST(/.*/).passThrough();
        }
    };
}]);

angular.module('cronos')
    .factory('cpuService', ['$q', 'RestSupport', function cpuServiceFactory($q, RestSupport) {
    'use strict';

    var CpuService = $class(RestSupport, {

        constructor: function() {
            RestSupport.call(this, {
                path: '/rest/cpu'
            });
        },

        /**
        * {
        * numeroProcesso,
        * comarca
        * }
        */
        carregarProcesso: function(carregarProcessoDTO) {
            return this.rest.post('/carregarProcesso', carregarProcessoDTO);
        }
    });

    return new CpuService();
}]);

angular.module('cronos')
    .factory('pesquisaGeralService', ['DeferService', 'ciService', 'authService', function pesquisaGeralServiceFactory(DeferService, ciService, authService) {
    'use strict';

    var PesquisaGeralService = $class(DeferService, {
        isWorking: function() {
            return ciService.isWorking();
        },
        pesquisarTopico: function(pesquisa, tipoPesquisa){
            return this._pesquisar('T', pesquisa, tipoPesquisa);
        },
        pesquisarModelo: function(pesquisa, tipoPesquisa){
            return this._pesquisar('M', pesquisa, tipoPesquisa);
        },
        _pesquisar: function(tipo, pesquisa, tipoPesquisa) {

            var pesquisaGeralTopicoDTO = {
                "part" : pesquisa,
                "tipoArvore" : tipo,
                "idGrupoTrabalho" : authService.getIdGrupoTrabalho(),
                "tipo" : tipoPesquisa
            };

            return ciService.buscarPesquisaGeral(pesquisaGeralTopicoDTO);
        }
    });
    return new PesquisaGeralService();
}]);

angular.module('cronos')
    .factory('usuarioCTJService', ['$q', 'RestSupport', function usuarioCTJServiceFactory($q, RestSupport) {
    'use strict';

    var UsuarioCTJService = $class(RestSupport, {

        constructor: function() {
            RestSupport.call(this, {
                path: '/rest/usuarioCTJ'
            });
        },

        listaPessoas: function(username) {
            return this.rest.get('/listaPessoas/' + username);
        }
    });

    return new UsuarioCTJService();
}]);

angular.module('cronos')
    .factory('exportarService', ['DeferService', '$window', '$backendUrl', function exportarServiceFactory(DeferService, $window, $backendUrl) {
    'use strict';

    var ExportarService =  $class(DeferService, {

        constructor: function() {
            DeferService.call(this);
            this.baseUrl = $backendUrl;
            this.exportarSentencaUrl = '/exportarSentenca';
        },

        /**
        * @param numeroProcesso
        * @param extensao
        */
        exportarSentenca: function(numeroProcesso, extensao) {
            $window.location = (this.baseUrl + this.exportarSentencaUrl + '?numeroProcesso=' + numeroProcesso +'&extensao=' + extensao);
        }
    });

    return new ExportarService();
}]);
angular
    .module('cronos')
    .factory('authService', ['$rootScope', '$window', '$location', 'DeferService', 'RestService', 'modalService', 'usuarioService', '$contextUrl', 'configService', 'alegacaoGrupoTrabalhoService', 'tokenService',
    function authServiceFactory($rootScope, $window, $location, DeferService, RestService, modalService, usuarioService, $contextUrl, configService, alegacaoGrupoTrabalhoService, tokenService) {
    'use strict';

    var AuthService = $class(DeferService, {

        constructor: function() {

            DeferService.call(this);

            this.user = false;
            this.idGrupoTrabalho = false;
            this.loaded = false;
            this.contextMenuAllowed = false;
            this.modeloScreen = false;

            this.rest = new RestService({
                path: '/rest/auth'
            });

            this.sessionTimer = 0;
            this.sessionTimeout = 3600;
        },

        _httpBind: function(http, ok, error) {
            // http already have deferWork from DeferService
            return http.then(angular.bind(this, ok), angular.bind(this, error));
        },

        check: function() {

            var deferred = this.defer();

            this._httpBind(

                this.rest.get('/user'),

                function(response) {
                    var user = response && response.data;
                    if (user) {
                        // copy internal
                        this.user = angular.copy(user, {});
                        // ok
                        deferred.resolve(user);
                    } else {
                        deferred.reject(this.user = false);
                    }
                },

                function(error) {
                    deferred.reject(this.user = false);
                }
            );
            return deferred.promise;
        },

        _checkSession: function() {

            var deferred = this.defer();

            this._httpBind(

                this.rest.get('/checkSession'),

                function(response) {
                    var user = response && response.data;
                    if (user) {
                        // copy internal
                        this.user = angular.copy(user, {});
                        // ok
                        deferred.resolve(user);
                    } else {
                        deferred.reject(this.user = false);
                    }
                },
                function() {
                    deferred.reject(this.user = false);
                }
            );
            return deferred.promise;
        },

        checarSessao: function() {
            var auth = this;
            var loginPage = 'login.html';
            var currentPage = $window.location.toString();

            auth._checkSession()
                .then(function(userInfo) {
                    // login ok
                    if (!userInfo) {
                        $window.location.href = loginPage;
                    }
                }, function() {
                    // not logged
                    if (currentPage.indexOf(loginPage) === -1) {
                        $window.location.href = loginPage;
                    }
                }).finally(function() {
                    auth.loaded = true;
                });
        },

        login: function(username, password) {

            var deferred = this.defer();

            this._httpBind(

                this.rest.post('/login', {
                    username: username,
                    password: password
                }),

                function(response) {
                    var user = response && response.data;
                    if (user) {
                        // copy internal
                        this.user = angular.copy(user, {});
                        // ok
                        deferred.resolve(user);
                    } else {
                        deferred.reject(this.user = false);
                    }
                },

                function() {
                    deferred.reject(this.user = false);
                }
            );

            return deferred.promise;
        },

        logout: function() {

            var deferred = this.defer();

            this._httpBind(

                this.rest.post('/logout'),

                function(ok) {
                    if (ok) {
                        this.user = false;
                        deferred.resolve(ok);
                    } else {
                        deferred.reject();
                    }
                },

                function() {
                    deferred.reject();
                }
            );

            return deferred.promise;
        },

        load: function() {
            if (this.loaded) {
                return;
            }

            var auth = this;
            var loginPage = 'login.html';
            var currentPage = $window.location.toString();

            if (currentPage.indexOf('/index.html') === -1) {
                // execute auth check and fire events LOGIN
                auth.check()
                    .then(function(userInfo) {
                        // login ok
                        if (auth.getIdGrupoTrabalho()) {
                            configService.carregarConfiguracao(auth.getIdGrupoTrabalho());
                            auth.verificaTermoAceite();
                            alegacaoGrupoTrabalhoService.carregarAlegacoes(auth.getIdGrupoTrabalho());
                            tokenService.carregarTokens();
                        }
                    }, function() {
                        // not logged
                        if (currentPage.indexOf(loginPage) === -1) {
                            $window.location.href = loginPage;
                        }
                    }).finally(function() {
                        auth.loaded = true;
                    });
            }
            // Cada troca de view faz checagem de permissao.
            $rootScope.$on('$routeChangeStart', function(event, nextRoute, currRoute) {

                auth.checarSessao();
                auth.modeloScreen = false;

                var access = (nextRoute && nextRoute.access) || 'private';

                if (!auth.hasRole(access)) {
                    if (!auth.isLogged()) {
                        // $window.location.href = 'login.html';
                        // console.log('user not authenticated');
                    } else {
                        // $location.path('/403');
                        // console.log('user has no access role');
                    }
                }
            });
        },

        isModeloScreen: function() {
            return this.modeloScreen;
        },
        setModeloScreen: function(status) {
            this.modeloScreen = status;
        },

        isLogged: function() {
            // console.log(this.grupoTrabalho);
            // if(this.grupoTrabalho !== false){
                return (this.user !== false);
            // }
            // return null;
        },

        getUserId: function() {
            if (this.user) {
                return this.user.usuario.id;
            }
            return null;
        },

        getUserParentId: function() {
            if (this.user) {
                if (this.user.usuario.idPai) {
                    return this.user.usuario.idPai;
                } else {
                    return this.user.usuario.id;
                }
            }
            return null;
        },

        getPerfil : function() {
            var perfil = null;
            if (this.user) {
                var permissoes = this.user.usuario.permissoes;
                if (this.isMagistrate()) {
                    perfil = "Magistrado";
                } else if (permissoes == "A") {
                    perfil = "Perfil Administrador";
                } else if (permissoes == "W") {
                    perfil = "Perfil Leitura e Escrita";
                } else {
                    perfil = "Perfil Somente Leitura";
                }
            }
            return perfil;
        },

        getUsername: function() {
            return this.user ? this.user.username : null;
        },

        getName: function() {
            return this.user ? this.user.name : null;
        },

        getUser: function() {
            if (this.user) {
                return this.user;
            }
            return null;
        },

        getIdGrupoTrabalho: function() {
            if (!this.idGrupoTrabalho) {
                this.idGrupoTrabalho = this.user.idGrupoTrabalho;
            }
            return this.idGrupoTrabalho;
        },

        setIdGrupoTrabalho: function(idGrupoTrabalho) {
            this.idGrupoTrabalho = idGrupoTrabalho;
        },

        isMagistrate : function() {
            // return this.user ? this.user.magistrado : "N";
            return this.user ? this.user.magistrado : false;
        },

        isAdmin : function() {
            return this.user ? (this.user.magistrado || this.user.usuario.permissoes == "A") : false;
        },

        hasWriteAccess : function() {
            return this.user ? (this.user.magistrado || this.user.usuario.permissoes == "A" || this.user.usuario.permissoes == "W") : false;
        },

        getAceiteTermo : function() {
            return this.user && this.user.usuario.aceiteTermo ? this.user.usuario.aceiteTermo : "N";
        },

        hasRole : function(role) {
            if (role === 'public' || (role === 'private' && this.user)) {
                return true;
            } else if (this.user && this.user.roles && this.user.roles.length > 0) {
                return this.user.roles.indexOf(role) != -1;
            }
            return false;
        },

        verificaTermoAceite : function() {
            var auth = this;
            if(auth.getAceiteTermo() != "S") {
                modalService.open( 'termoAceite', {
                    confirmButton: "Prosseguir",
                    cancelButton: "N&atilde;o Aceito",
                    contentPreviewPath: "./views/templates/termo.html"
                })
                .then( function handleResolve( response ) {
                    var aceiteTermoDTO = {
                        id: auth.getUserId(),
                        usuarioAtualizacao: auth.getUsername()
                    };

                    usuarioService.aceiteTermo(aceiteTermoDTO)
                        .then(function(response) {
                            $window.location.href = $contextUrl;
                    });
                }, function handleReject( error ) {
                    auth.logout();
                    $window.location.href = 'login.html';
                });
            }

        }
    });

    return new AuthService();
}]);

angular.module('cronos')
    .factory('EntityService', ['RestService', function EntityServiceFactory(RestService) {
    'use strict';

    var EntityService = $class({

        constructor: function(config) {

            this.type = config.type || '';

            this.rest = new RestService({
                path: '/rest/entity'
            });
        },

        // from DeferService
        isWorking: function() {
            return this.rest.isWorking();
        },

        _entityPath: function(actionPath) {
            return '/' + this.type + (actionPath || '');
        },

        _get: function(uri, config) {
            return this.rest.get(this._entityPath(uri), config);
        },

        _post: function(uri, data, config) {
            return this.rest.post(this._entityPath(uri), data, config);
        },

        find: function(filter, pagination) {

            pagination = pagination || {};

            var path = this.rest.toMatrixPath(this._entityPath('/find'), filter);

            var config = {
                params: {
                    page: pagination.page || 0,
                    size: pagination.size || 20,
                    dir: pagination.dir || 'ASC', // ASC|DESC
                    order: pagination.order || ''
                }
            };

            return this.rest.get(path, config);
        },

        create: function() {
            return this._get('/create');
        },

        get: function(id, config) {
            return this._get('/get/' + id);
        },

        save: function(data) {
            return this._post('/save', data);
        },

        remove: function(data) {
            return this._post('/remove', data);
        }
    });

    return EntityService;
}]);

angular.module('cronos')
    .factory('mensagensService', ['$q', '$http', '$backendUrl', 'DeferService', function mensagensServiceFactory($q, $http, $backendUrl, DeferService) {
    'use strict';

    var MensagensService = $class(DeferService, {

        constructor: function() {
            this.load();
        },

        _onLoad: function(response){
            this.messages = response.data.messages;
        },

        load: function() {
            this.defer($http.get($backendUrl + '/rest/mensagens'))
                .then(angular.bind(this, this._onLoad));
        },

        getMessage: function(msg, type, args) {

            var messageKey = msg.key || msg;
            var messageType = type || msg.type || 'INFO';

            var defaultMessages = this.messages.INFO;
            var specificMessages = this.messages[messageType];

            var keyComparator = function(m) {
                return m.key.name === messageKey;
            };

            var messageText = specificMessages && _.find(specificMessages, keyComparator);

            if (!messageText) {
                messageText = defaultMessages && _.find(defaultMessages, keyComparator);
                if(args && Array.isArray(args)) {
                    var customMessage = angular.copy(messageText, {});
                    if (customMessage && customMessage.message) {
                        args.forEach(function (arg, i) {
                            customMessage.message = customMessage.message.replaceAll("{"+i+"}", arg);
                        });
                        return customMessage;
                    }
                }
            }

            return messageText || messageKey;
        }
    });

    return new MensagensService();
}]);

angular.module('cronos')
    .factory('notificationService', ['Notification', 'mensagensService', 'DeferService', function notificationServiceFactory(Notification, mensagensService, DeferService) {
    'use strict';

    var types = {
        SUCCESS: "SUCCESS",
        WARNING: "WARNING",
        ERROR: "ERROR",
        INFO: "INFO"
    };

    var NotificacaoService = $class(DeferService, {

        push: function(content) {
            if (content) {
                if (content.SUCCESS) {
                    this.pushMessages(content.SUCCESS);
                }
                if (content.INFO) {
                    this.pushMessages(content.INFO);
                }
                if (content.WARNING) {
                    this.pushMessages(content.WARNING);
                }
                if (content.ERROR) {
                    this.pushMessages(content.ERROR);
                }
            }
        },

        pushMessages: function(messages) {
            for (var i = 0; i < messages.length; i++) {
                this.pushMessage(messages[i]);
            }
        },

        pushMessage: function(message, type) {

            type = (type || message.type || types.INFO).toUpperCase();

            if (type === types.ERROR) {
                this.error(message);
            } else if (type === types.WARNING) {
                this.warning(message);
            } else if (type === types.SUCCESS) {
                this.success(message);
            } else {
                this.info(message);
            }
        },

        translateMessage: function(msg, type, args) {
            return mensagensService.getMessage(msg, type, args);
        },
        error: function(msg, args) {
            Notification.error(this.translateMessage(msg, types.ERROR, args));
        },
        warning: function(msg, args) {
            Notification.warning(this.translateMessage(msg, types.WARNING, args));
        },
        info: function(msg, args) {
            Notification.info(this.translateMessage(msg, types.INFO, args));
        },
        success: function(msg, args) {
            Notification.success(this.translateMessage(msg, types.SUCCESS, args));
        }
    });

    return new NotificacaoService();

}]).config(["NotificationProvider", function(NotificationProvider) {
    NotificationProvider.setOptions({
        delay: 5 * 1000,
        startTop: 60,
        startRight: 10,
        verticalSpacing: 20,
        horizontalSpacing: 20,
        positionX: 'right',
        positionY: 'top'
    });
}]);

angular.module('cronos').provider('RestService', function RestServiceProvider() {
    'use strict';

    var providerConfig = {
        url: '.',
        options: {}
    };
    function setServiceUrl(url) {
        providerConfig.url = url;
    }
    function getServiceUrl() {
        return providerConfig.url;
    }

    // Provider configuration
    this.setServiceUrl = setServiceUrl;
    this.getServiceUrl = getServiceUrl;

    // Service initialization
    this.$get = ["$http", "DeferService", "notificationService", function($http, DeferService, notificationService) {

        var RestService = $class(DeferService, {

            constructor: function(config) {

                DeferService.call(this);

                this.baseUrl = (config.url || providerConfig.url) + (config.path || '');
            },

            toUrl: function(path) {
                return this.baseUrl + (path || '');
            },

            toMatrixPath: function(path, matrix) {
                var params = [];
                if (matrix) {
                    for (var key in matrix) {
                        if (matrix.hasOwnProperty(key)) {
                            var value = matrix[key];
                            if (value !== null && value !== '' && typeof(value) !== 'undefined') {
                                params.push(key + '=' + value);
                            }
                        }
                    }
                }
                return (path || '') + (params.length > 0 ? (';' + params.join(';')) : '');
            },

            _notifySuccess: function(response) {
                // dont use this.. callback only
                if (response && response.messages) {
                    notificationService.push(response.messages);
                }
                // return response, to other callbacks registered
                return response;
            },
            _notifyError: function(response) {
                var notified = false;
                // dont use this.. callback only
                if (response && response.messages) {
                    notified = true;
                    notificationService.push(response.messages);
                }
                if (!notified) {
                    notificationService.push('Não consegui executar a operação. Verifique os dados e tente novamente.');
                }
                // return response, to other callbacks registered
                return response;
            },

            get: function(path, config) {
                return this.defer($http.get(this.toUrl(path), config))
                    .success(this._notifySuccess)
                    .error(this._notifyError);
            },

            post: function(path, data, config) {
                return this.defer($http.post(this.toUrl(path), data, config))
                    .success(this._notifySuccess)
                    .error(this._notifyError);
            }
        });

        RestService.getServiceUrl = getServiceUrl;

        return RestService;
    }];
});

angular.module('cronos')
    .factory('RestSupport', ['RestService', function restServiceFactory(RestService) {
    'use strict';

    var RestSupport = $class({

        constructor: function(restConfig) {
            this.rest = new RestService(restConfig);
        },

        bindThen: function(http, ok, error) {
            // http already have deferWork from DeferService
            return http.then(angular.bind(this, ok), angular.bind(this, error));
        },

        isWorking: function() {
            return this.rest.isWorking();
        }
    });

    return RestSupport;
}]);