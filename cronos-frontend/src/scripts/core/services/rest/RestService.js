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
    this.$get = function($http, DeferService, notificationService) {

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
    };
});
