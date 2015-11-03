angular.module('rhdemo')
    .factory('messageService', ['$q', '$http', '$backendUrl', 'DeferService', '$translate', function mensagensServiceFactory($q, $http, $backendUrl, DeferService, $translate) {
    'use strict';

    var MessageService = $class(DeferService, {

        constructor: function() {
            this.load();
        },

        _onLoad: function(response){
            this.messages = response.data.messages;
        },

        load: function(locale) {
        	var l = $translate.proposedLanguage();
        	if (locale) {
        		l = locale;
        	}	
            this.defer($http.get($backendUrl + '/rest/mensagens?locale='+l))
                .then(angular.bind(this, this._onLoad));
        },

        getMessage: function(msg, type, args) {

            var messageKey = msg.key || msg;
            var messageType = type || msg.type || 'INFO';

            var defaultMessages = this.messages.INFO;
            var specificMessages = this.messages[messageType];

            var keyComparator = function(m) {
                return m.key.name === messageKey;
            };

            var messageText = specificMessages && _.find(specificMessages, keyComparator);

            if (!messageText) {
                messageText = defaultMessages && _.find(defaultMessages, keyComparator);
                if(args && Array.isArray(args)) {
                    var customMessage = angular.copy(messageText, {});
                    if (customMessage && customMessage.message) {
                        args.forEach(function (arg, i) {
                        	if (customMessage.message.indexOf("{"+i+"}")>=0){
                        		customMessage.message = customMessage.message.replace("{"+i+"}", arg);
                        	} else {
                        		customMessage.message = customMessage.message + "</br>"+ arg;
                        	}
                        });
                        return customMessage;
                    }
                }
            }

            return messageText || messageKey;
        }
    });

    return new MessageService();
}]);
