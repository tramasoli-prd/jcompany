/**
 * Loading Directive
 * @see http://tobiasahlin.com/spinkit/
 */

angular
    .module('rhdemo')
    .directive('ngTranslateLanguageSelect', ngTranslateLanguageSelect);

function ngTranslateLanguageSelect() {
    return {
        restrict: 'A',
        replace: true,
        template: ''+
        '<div class="language-select" ng-if="visible">'+
                '<select ng-model="currentLocaleDisplayName"'+
                    'ng-options="localesDisplayName for localesDisplayName in localesDisplayNames"'+
                    'ng-change="changeLanguage(currentLocaleDisplayName)">'+
                '</select>'+
        '</div>'+
        '',
        controller: function ($scope, LocaleService, messageService) {
            $scope.currentLocaleDisplayName = LocaleService.getLocaleDisplayName();
            $scope.localesDisplayNames = LocaleService.getLocalesDisplayNames();
            $scope.visible = $scope.localesDisplayNames &&
            $scope.localesDisplayNames.length > 1;

            $scope.changeLanguage = function (locale) {
                LocaleService.setLocaleByDisplayName(locale);
                messageService.load(LocaleService.getLocaleByName(locale));
            };
        }
    };
};