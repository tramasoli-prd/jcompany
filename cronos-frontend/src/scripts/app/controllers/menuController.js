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
