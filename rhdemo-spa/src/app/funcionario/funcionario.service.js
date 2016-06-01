(function() {
  'use strict';

  angular
    .module('rhdemo')
    .factory('FuncionarioService', FuncionarioService);

  /** @ngInject */
  function FuncionarioService(PlcEntityService, PlcUtils, $class) {


    var Service = $class.createClass(PlcEntityService, {

      constructor: function() {
        PlcEntityService.call(this, {
          type: 'funcionario',
          applyMetadata: true
        });
      },

      /* METODOS REST */
      _all: function(funcionario) {
        var url = PlcUtils.encodeURIParams(funcionario);
        return this._get('/all?'+url);
      },

      save: function(data) {
        return this._post('/savefunc', data);
      },


    });


    return new Service();
  }

})();
