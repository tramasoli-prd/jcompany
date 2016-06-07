(function() {
  'use strict';

  angular
    .module('rhdemo')
    .constant('$appName', 'RH DEMO')
    .constant('$appVersion', '0.0.1')
    .constant('$contextUrl', '');

  angular
    .module('jcompany-view')
    .constant('$menuPath', 'app/components/json/menu.json')
    .constant('$backendUrl', 'http://localhost:9080/rhdemo');

})();
