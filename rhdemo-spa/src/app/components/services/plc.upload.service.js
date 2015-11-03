/**=========================================================
 * Module: upload.js
 =========================================================*/

(function() {
    'use strict';

    angular
        .module('rhdemo')
        .factory('fileUploadService', FileUploadService);

    FileUploadService.$inject = ['FileUploader'];
    
    function FileUploadService(FileUploader) {

        var FileUploadService = $class({

            constructor: function(config) {

              this.uploader = new FileUploader({
                url: 'http://localhost:7001/rhdemo-service/uploadFiles'
              });

              // FILTERS

              uploader.filters.push({
                  name: 'customFilter',
                  fn: function(/*item, options*/) {
                      return this.queue.length < 10;
                  }
              });


               // CALLBACKS

                uploader.onWhenAddingFileFailed = function(item /*{File|FileLikeObject}*/, filter, options) {
                    console.info('onWhenAddingFileFailed', item, filter, options);
                };
                uploader.onAfterAddingFile = function(fileItem) {
                    console.info('onAfterAddingFile', fileItem);
                    uploader.uploadAll();
                };
                uploader.onAfterAddingAll = function(addedFileItems) {
                    console.info('onAfterAddingAll', addedFileItems);
                };
                uploader.onBeforeUploadItem = function(item) {
                    console.info('onBeforeUploadItem', item);
                };
                uploader.onProgressItem = function(fileItem, progress) {
                    console.info('onProgressItem', fileItem, progress);
                };
                uploader.onProgressAll = function(progress) {
                    console.info('onProgressAll', progress);
                };
                uploader.onSuccessItem = function(fileItem, response, status, headers) {
                    console.info('onSuccessItem', fileItem, response, status, headers);
                };
                uploader.onErrorItem = function(fileItem, response, status, headers) {
                    console.info('onErrorItem', fileItem, response, status, headers);
                };
                uploader.onCancelItem = function(fileItem, response, status, headers) {
                    console.info('onCancelItem', fileItem, response, status, headers);
                };
                uploader.onCompleteItem = function(fileItem, response, status, headers) {              
                    $scope.fileItem = fileItem._file.name;
                };
                uploader.onCompleteAll = function() {
                    console.info('onCompleteAll');
                };
            }
        });

        return FileUploadService;


       
    }
})();
