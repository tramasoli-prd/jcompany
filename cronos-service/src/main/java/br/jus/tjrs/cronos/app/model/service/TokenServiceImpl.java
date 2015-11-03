package br.jus.tjrs.cronos.app.model.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.jus.tjrs.cronos.app.model.entity.TokenEntity;
import br.jus.tjrs.cronos.app.model.repository.TokenRepository;
import br.jus.tjrs.cronos.core.model.service.AbstractServiceEntity;

@Stateless
public class TokenServiceImpl extends AbstractServiceEntity<Long, TokenEntity> implements
         TokenService
{

   @Inject
   private TokenRepository tokenRepository;

   @Override
   protected TokenRepository getEntityRepository()
   {
      return tokenRepository;
   }

}
