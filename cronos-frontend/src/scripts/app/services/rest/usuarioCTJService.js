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
