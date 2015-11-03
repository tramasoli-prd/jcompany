(function() {
  'use strict';

  angular
    .module('rhdemo')
    .config(config);

  /** @ngInject */
  function config($logProvider, $backendUrl, $httpProvider, $locationProvider, toastrConfig, RestServiceProvider, $translateProvider, tmhDynamicLocaleProvider) {
    // Enable log
    $logProvider.debugEnabled(true);

    // Set options third-party lib
    toastrConfig.allowHtml = true;
    toastrConfig.timeOut = 3000;
    toastrConfig.positionClass = 'toast-top-right';
    toastrConfig.preventDuplicates = true;
    toastrConfig.progressBar = true;
    
    RestServiceProvider.setServiceUrl($backendUrl);
    
    // REST should send cookie credentials
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.withCredentials = true;
    delete $httpProvider.defaults.headers.common['X-Requested-With'];

    // Config Access Outside ngView
    $locationProvider.html5Mode(false);
    
    $translateProvider.useMissingTranslationHandlerLog();
    
    $translateProvider.useUrlLoader($backendUrl + '/rest/mensagens/i18n', {
    	  queryParameter : 'locale'
    });
    $translateProvider.preferredLanguage('pt_BR');// is applied on first load
    $translateProvider.useLocalStorage();// saves selected language to localStorage
    
    $translateProvider.useSanitizeValueStrategy('escape');
    
    tmhDynamicLocaleProvider.localeLocationPattern('bower_components/angular-i18n/angular-locale_{{locale}}.js');
    
  }

})();
