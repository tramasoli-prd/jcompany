#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
(function() {
  'use strict';

  angular
    .module('${artifactId}', [
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
                       'blockUI',,
                       'ui.bootstrap.datepicker',
                       'ui.utils.masks'
                       ]
    		);

})();
