package com.powerlogic.jcompany.rhdemo.app.model.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.metamodel.ManagedType;

import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.model.qbe.OrderBy;
import com.powerlogic.jcompany.core.model.qbe.OrderByDirection;
import com.powerlogic.jcompany.core.model.qbe.PropertySelector;
import com.powerlogic.jcompany.core.model.qbe.SearchParameters;
import com.powerlogic.jcompany.core.model.repository.PlcAbstractRepository;
import com.powerlogic.jcompany.rhdemo.app.model.entity.DepartamentoEntity;

@ApplicationScoped
public class DepartamentoRepository extends PlcAbstractRepository<Long, DepartamentoEntity> {


	public DepartamentoEntity save(DepartamentoEntity entity) throws PlcException {
		return super.save(entity);
	}

	@Override
	public Class<DepartamentoEntity> getEntityType() {
		return DepartamentoEntity.class;
	}

	@Override
	public List<DepartamentoEntity> findAll(DepartamentoEntity departamento) {

		try {
			SearchParameters sp = new SearchParameters();

			if (departamento.isPesquisaPaiIsNull()) {

				// Testando com departamento pai is null -------
				ManagedType<DepartamentoEntity> mt = getEntityManager().getMetamodel().entity(DepartamentoEntity.class);
				PropertySelector<DepartamentoEntity, Object> ps = PropertySelector.newPropertySelector(mt.getAttribute("departamentoPai"));
				ps.selected((Object)null);
				sp.addProperty(ps);
				// ------

			} 
			
			if (departamento.isPesquisaPaiIsNotNull()){

				// Testando com departamento pai is not null -------
				ManagedType<DepartamentoEntity> mt = getEntityManager().getMetamodel().entity(DepartamentoEntity.class);
				PropertySelector<DepartamentoEntity, Object> ps = PropertySelector.newPropertySelector(mt.getAttribute("departamentoPai"));
				ps.withoutNull();
				sp.addProperty(ps);
				// ------

			}

			if (departamento.isPesquisaDescricaoIsNull()){

				// Testando com descricao is null -------
				ManagedType<DepartamentoEntity> mt = getEntityManager().getMetamodel().entity(DepartamentoEntity.class);
				PropertySelector<DepartamentoEntity, String> ps = PropertySelector.newPropertySelector(mt.getAttribute("descricao"));
				ps.selected((String)null);
				sp.addProperty(ps);
				// ------

			} 

			if (departamento.isPesquisaDescricaoIsNotNull()){

				// Testando com descricao is not null -------
				ManagedType<DepartamentoEntity> mt = getEntityManager().getMetamodel().entity(DepartamentoEntity.class);
				PropertySelector<DepartamentoEntity, String> ps = PropertySelector.newPropertySelector(mt.getAttribute("descricao"));
				ps.withoutNull();
				sp.addProperty(ps);
				// ------

			}
			
			ManagedType<DepartamentoEntity> mt = getEntityManager().getMetamodel().entity(DepartamentoEntity.class);
			sp.addOrderBy(new OrderBy(OrderByDirection.ASC, mt.getAttribute("descricao")));
			
			sp.getMultiSelect().add("id");
			sp.getMultiSelect().add("descricao");
			sp.getMultiSelect().add("departamentoPai.descricao");

			return find(departamento, sp);

		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}

	}


	public DepartamentoEntity findByRoot(Long id) {
		//Buscar pelo Pai
		return null;
	}

	@Override
	public List<DepartamentoEntity> findAll() throws PlcException {
		//throw AppErrorMessage.TESTE_MESSAGE_ERROR_APP_01.create();
		return super.findAll();
	}

}