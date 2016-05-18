/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.model.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OptimisticLockException;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.powerlogic.jcompany.core.commons.search.PlcPagedResult;
import com.powerlogic.jcompany.core.commons.search.PlcPagination;
import com.powerlogic.jcompany.core.exception.PlcException;
import com.powerlogic.jcompany.core.messages.PlcBeanMessages;
import com.powerlogic.jcompany.core.model.domain.PlcSituacao;
import com.powerlogic.jcompany.core.model.entity.IPlcEntityModel;
import com.powerlogic.jcompany.core.model.entity.IPlcLogicalExclusion;
import com.powerlogic.jcompany.core.model.qbe.PlcQBERepository;
import com.powerlogic.jcompany.core.util.ConstantUtil;

/**
 * 
 * Base Abstrata para serviços ou objetos de negócio que fazem o acesso aos repositorios de dados.<p>
 * 
 * Encapsulam os acessos a base de dados, que nesse caso utiliza a EntityManager que é injetada pelo Servidor de Aplicação.
 * 
 * São invocados através dos Serviços/Services que implementa (PlcAbstractServiceEntity) que por sua vez faz o controle transacional (commit/rolback).
 * 
 * Classe ancestral para serviços de acesso a dados<p>
 * 
 * Ver conceito de Repository na teoria de Domain Driven-Design.
 * 
 * @category Repository
 * @since 1.0.0
 * @author Powerlogic
 * 
 */
public abstract class PlcBaseAbstractRepository<PK extends Serializable, E extends IPlcEntityModel<PK>> extends PlcQBERepository<PK, E>  {

	private static PropertyUtilsBean propertyUtilsBean = BeanUtilsBean.getInstance().getPropertyUtils();

	/** 
	 * Abstract Method forçando a implementação na classe concreta.
	 * Deve ser realizada uma injeção da EntityManager default.
	 *  
	 * @see com.powerlogic.jcompany.core.model.qbe.PlcQBERepository#getEntityManager()
	 */
	protected abstract EntityManager getEntityManager();

	public abstract Class<E> getEntityType();

	@Override
	public E get(PK id) {

		try {
			return getEntityManager().find(getEntityType(), id);
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}


	@Override
	public E getDetached(PK id) {

		try {
			E ent = getEntityManager().find(getEntityType(), id);
			getEntityManager().detach(ent);
			return ent;
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	@Override
	public E save(E entity) {
		// Implementar Listener JPA
		try {
			checkConstraintsBeforeRemoveOrSave(entity, "Save");
			if (entity.getId() == null) {
				getEntityManager().persist(entity);
			} else {
				entity = getEntityManager().merge(entity);
			}
		} catch (OptimisticLockException e) {
			throw PlcBeanMessages.REGISTROS_CONCORRENTES_011.create();
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}

		return entity;
	}

	@Override
	public void remove(E entity) {
		try {
			checkConstraintsBeforeRemoveOrSave(entity, "Remove");
			if (entity instanceof IPlcLogicalExclusion) {
				logicalRemove(entity);
			} else {
				physicalRemove(entity);
			}
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	protected void logicalRemove(E entity) {

		try {
	    	if (IPlcLogicalExclusion.class.isAssignableFrom(entity.getClass())) {
	    		((IPlcLogicalExclusion)entity).setSituacao(PlcSituacao.E);
	    	}
			getEntityManager().merge(entity);
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	protected void physicalRemove(E entity) {
		try {
			getEntityManager().remove(entity);
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	@Override
	public void inative(E entity) {

		try {

	    	if (IPlcLogicalExclusion.class.isAssignableFrom(entity.getClass())) {
	    		((IPlcLogicalExclusion)entity).setSituacao(PlcSituacao.I);
	    	}
			getEntityManager().merge(entity);

		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	@Override
	public List<E> findAll() {
		try {
			CriteriaBuilder builder = criteriaBuilder();

			CriteriaQuery<E> query = builder.createQuery(getEntityType());

			Root<E> from = query.from(getEntityType());

			query.select(from).where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), PlcSituacao.A));

			return createQuery(query).getResultList();
		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	@Override
	public PlcPagedResult<E> find(PlcPagination<E> config) {

		try {
			CriteriaQuery<E> query = criteriaBuilder().createQuery(getEntityType());

			query.select(query.from(getEntityType()));

			return getPagedResult(config, createQuery(query));

		} catch (PlcException e) {
			throw e;
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	@Override
	public List<E> findAll(E entity) throws PlcException {

		try {

			CriteriaBuilder builder = criteriaBuilder();

			CriteriaQuery<E> query = builder.createQuery(getEntityType());

			Root<E> from = query.from(getEntityType());
			
			if (entity instanceof IPlcLogicalExclusion){
				query.select(from).where(builder.equal(from.get(ConstantUtil.QUERY_PARAM_SITUACAO), PlcSituacao.A));
			}else{
				query.select(from);
			}

			return createQuery(query).getResultList();

		} catch( PlcException e){
			throw e;
		} catch( Exception e){ 
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	protected <T> PlcPagedResult<T> getPagedResult(PlcPagination<T> config, TypedQuery<T> query) {

		try {

			query.setFirstResult(config.getOffset());
			query.setMaxResults(config.getLimit());

			return new PlcPagedResult<>(config, config.getLimit(), query.getResultList());

		} catch( PlcException e){
			throw e;
		} catch( Exception e){ 
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}

	protected CriteriaBuilder criteriaBuilder() {

		try {
			return getEntityManager().getCriteriaBuilder();
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}

	}

	protected <T> TypedQuery<T> createQuery(CriteriaQuery<T> query) {

		try {
			return getEntityManager().createQuery(query);
		} catch (Exception e) {
			throw PlcBeanMessages.FALHA_PERSISTENCIA_20.create(e.getMessage());
		}
	}


	protected void checkConstraintsBeforeRemoveOrSave( E entity, String removeOrSave)  {


		List<NamedQuery> checkConstraintsBeforeRemoveOrSabe = getNamedQueriesCheckConstraintsBeforeRemoveOrSave(entity.getClass(), removeOrSave);

		if (checkConstraintsBeforeRemoveOrSabe == null || checkConstraintsBeforeRemoveOrSabe.size()==0) {
			return;
		}

		Iterator j = checkConstraintsBeforeRemoveOrSabe.iterator();

		String query = "";
		Long totalResultados = 0L;

		while (j.hasNext()) {

			query = "";
			totalResultados = 0L;

			NamedQuery nqcCheckConstraintsBeforeRemove = (NamedQuery) j.next();
			query = nqcCheckConstraintsBeforeRemove.query();

			if (entity instanceof IPlcLogicalExclusion) {
				//query = query + " and o.situacao = :situacao";
			}

			Query q = getEntityManager().createQuery(query);

			Set<Parameter<?>> parameters = q.getParameters();

			try{
				for (Parameter<?> parameter : parameters) {
					if(propertyUtilsBean.isReadable(entity, parameter.getName())) {
						if (parameter.getName().equals("situacao")) {
						//	q.setParameter(parameter.getName(), PlcSituacao.A);
						} else if (parameter.getName().equals("id") && entity.getId()==null) {
							if (parameter.getParameterType().isAssignableFrom(Integer.class)) {
								q.setParameter(parameter.getName(), 0);
							} else if (parameter.getParameterType().isAssignableFrom(Long.class)) {
								q.setParameter(parameter.getName(), 0L);	
							}							
						} else {
							q.setParameter(parameter.getName(), propertyUtilsBean.getProperty(entity, parameter.getName()));
						}
					}
				}
			} catch (Exception e ){
				throw PlcBeanMessages.FALHA_OPERACAO_003.create(e.getMessage());
			}

			totalResultados = (Long) q.getSingleResult();

			if (totalResultados>0) {
				throw PlcBeanMessages.FALHA_CHECK_CONSTRAINT_BEFORE_REMOVE_025.create("{app.falha."+nqcCheckConstraintsBeforeRemove.name()+"}");
			}

		}

	}

	public List<NamedQuery> getNamedQueriesCheckConstraintsBeforeRemoveOrSave(Class<? extends Object> classe, String removeOrSave)  {

		List<NamedQuery> anotacoesCheckConstraintsBeforeRemoveOrSave = new ArrayList<NamedQuery>();

		String nomeClasseSemPackage = classe.getName().substring(classe.getName().lastIndexOf(".")+1);
		NamedQueries nqs = (NamedQueries) classe.getAnnotation(NamedQueries.class);

		if (nqs == null) {
			NamedQuery nq = (NamedQuery) classe.getAnnotation(NamedQuery.class);
			if (nq != null && nq.name().indexOf(nomeClasseSemPackage+"."+"checkConstraintsBefore"+removeOrSave)>-1){
				anotacoesCheckConstraintsBeforeRemoveOrSave.add(nq);
			}
		} else {

			for (int i = 0; i < nqs.value().length; i++) {
				NamedQuery nq = nqs.value()[i];
				if (nq.name().indexOf(nomeClasseSemPackage+"."+"checkConstraintsBefore"+removeOrSave)>-1) {
					anotacoesCheckConstraintsBeforeRemoveOrSave.add(nq);
				}
			}

		}

		return anotacoesCheckConstraintsBeforeRemoveOrSave;

	}

}