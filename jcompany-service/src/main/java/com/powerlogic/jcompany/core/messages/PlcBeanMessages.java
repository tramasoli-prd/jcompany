/*  																													
	    			       Jaguar-jCompany Developer Suite.																		
			    		        Powerlogic 2015-2020.
			    		    
		Please read licensing information in your installation directory.
		Contact Powerlogic for more information or contribute with this project. 
			site...: www.powerlogic.org																								
			e-mail.: suporte@powerlogic.com.br
*/

package com.powerlogic.jcompany.core.messages;

import com.powerlogic.jcompany.core.exception.PlcException;

/**
 * 
 * Enum para centralização das Mensagens Controladas pelo Framework.
 * 
 * @category Enum
 * @author Powerlogic
 * @since Jaguar 1.0.0
 */
public enum PlcBeanMessages implements IPlcMessageKey {
	
	DADOS_SALVOS_SUCESSO_000,
	FALHA_LOGIN_001,
	FALHA_SOLICITACAO_002,
	FALHA_OPERACAO_003,
	REGISTROS_NAO_ENCONTADOS_004,
	PESQUISA_MINIMA_005,
	PESQUISA_VAZIA_006,
	CAMPOS_OBRIGATORIOS_TOPICO_007,
	ICONES_OBRIGATORIOS_008,
	ICONES_TAMANHO_MAXIMO_009,
	REGISTROS_DUPLICADOS_010,
	REGISTROS_CONCORRENTES_011,
	FALHA_SOLICITACAO_012,
	CAMPO_TAMANHO_MINIMO_013,
	CAMPO_OBRIGATORIO_014,
	ELEMENTOS_OBRIGATORIOS_015,
	CAMPO_TAMANHO_MINIMO_016,
	CAMPO_TAMANHO_MAXIMO_017,
	CAMPO_TAMANHO_EXATO_018,
	UNSUPPORTED_OPERATION_019,
	FALHA_PERSISTENCIA_20,
	REGISTRO_EXCLUIDO_SUCESSO_021,
	NENHUM_REGISTRO_ENCONTRADO_022,
	FALHA_VALIDACAO_023,
	CAMPOS_OBRIGATORIOS_TOPICO_024,
	FALHA_CHECK_CONSTRAINT_BEFORE_REMOVE_025,
	FALHA_ADICIONAR_ARQUIVO_UPLOAD_026,
	CAMPOS_INVALIDOS_TOPICO_027,
	CAMPOS_NAO_PREENCHIDOS_FORMULARIO_028
	;

   public String getName()
   {
      return name();
   }

   public PlcException create()
   {
      return new PlcException(this);
   }

   public PlcException create(String... args)
   {
      return new PlcException(this, args);
   }
}
