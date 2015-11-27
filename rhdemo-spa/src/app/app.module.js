(function() {
  'use strict';

  angular
    .module('rhdemo', [
                       'ngCookies', 
                       'ngTouch', 
                       'ngSanitize', 
                       'ngMessages', 
                       'ngAria', 
                       'restangular', 
                       'ui.router', 
                       'ui.bootstrap', 
                       'ui.bootstrap.accordion', 
                       'toastr', 
                       'ui-notification', 
                       'ui.grid', //grid
                       'ui.grid.pagination', // suporte a paginacao no grid
                       'pascalprecht.translate', // angular-translate
                       'tmh.dynamicLocale',// angular-dynamic-locale
                       'angularFileUpload', // upload de arquivos
                       'ngDialog', // modal
                       'jcompany-view',
                       'blockUI',
                       'ui.bootstrap.datepicker'
                       ]
    		);

})();
