(function() {
  'use strict';

  angular
    .module('${artifactId}')
    .constant('$appName', 'RH DEMO')
    .constant('$appVersion', '0.0.1')
    .constant('$contextUrl', '');

  angular
    .module('jcompany-view')
    .constant('$menuPath', 'app/components/json/menu.json')
    .constant('$backendUrl', '{{ENVIRONMENT.dev}}');

})();
