(function() {
  'use strict';

  angular
    .module('rhdemo', [
        'ngAnimate',
        'ngCookies',
        'ngTouch',
        'ngSanitize',
        'ngMessages',
        'ngAria',
        'ui.router',
        'ui.bootstrap',
        'ui-notification',
        'ui.grid', //grid
        'ui.grid.pagination', // suporte a paginacao no grid
        'pascalprecht.translate', // angular-translate
        'tmh.dynamicLocale',// angular-dynamic-locale
        'angularFileUpload', // upload de arquivos
        'ngDialog', // modal
        'jcompany-view',
        'blockUI',
        'ui.utils.masks'

      ]
    );

})();
