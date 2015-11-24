(function() {
	'use strict';

	angular
	.module('rhdemo')
	.factory('DepartamentoService', DepartamentoService);

	DepartamentoService.$inject = ['PlcEntityService', 'PlcUtils'];

	/** @ngInject */
	function DepartamentoService(PlcEntityService, PlcUtils) {


		var Service = $class(PlcEntityService, {

			constructor: function() {
				PlcEntityService.call(this, {
					type: 'departamento'
				});
			},

			/* METODOS REST */
			_all: function(departamento) {
				var url = PlcUtils.encodeURIParams(departamento);
				return this._get('/all?'+url);
			},

			find: function(filter, pagination) {

				pagination = pagination || {};

				var path = this.rest.toMatrixPath(this._entityPath('/find'), filter);

				var config = {
						params: {
							page: pagination.page || 0,
							size: pagination.size || 20,
							dir: pagination.dir || 'ASC', // ASC|DESC
							order: pagination.order || ''
						}
				};

				return this.rest.get(path, config);
			},

			create: function() {
				return this._get('/create');
			},

			edit: function(id) {
				return this._get('/get/' + id);
			},

			save: function(data) {
				return this._post('/save', data);
			},

			metadata: function() {
				return this._get('/metadata');
			},

			remove: function(data) {
				return this._post('/remove', data);
			}

		});


		return new Service();
	}

})();
