angular.module('cronos').factory('mockService', function mockServiceFactory($httpBackend, $cookieStore, RestService, authService) {
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
});
