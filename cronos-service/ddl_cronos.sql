--------------------------------------------------------
--  Arquivo criado - Sexta-feira-Outubro-09-2015   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table AJUDA_CRONOS
--------------------------------------------------------

  CREATE TABLE "CRONOS"."AJUDA_CRONOS" 
   (	"ID_AJUDA" NUMBER(10,0), 
	"MENU" VARCHAR2(300 BYTE), 
	"CONTEXTO" CLOB, 
	"DATA_CRIACAO" DATE, 
	"NM_ULT_ALTERACAO" VARCHAR2(20 BYTE), 
	"DT_ULT_ALTERACAO" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table ALEGACAO_GT
--------------------------------------------------------

  CREATE TABLE "CRONOS"."ALEGACAO_GT" 
   (	"PK_ALEGACAO_GT_01" NUMBER(10,0), 
	"ID_ALEGACAO" NUMBER(10,0), 
	"TEXTO_ALEGACAO" VARCHAR2(150 BYTE) DEFAULT NULL, 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"ID_GRUPO_TRABALHO" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table CATEGORIA_ITEM
--------------------------------------------------------

  CREATE TABLE "CRONOS"."CATEGORIA_ITEM" 
   (	"PK_CATEGORIA_ITEM_01" NUMBER(10,0), 
	"TITULO" VARCHAR2(200 BYTE), 
	"COMPARTILHAR" CHAR(1 BYTE) DEFAULT 'N', 
	"ID_TIPO_CATEGORIA" NUMBER(10,0), 
	"ORDEM" NUMBER(10,0) DEFAULT 1, 
	"ID_GRUPO_TRABALHO" NUMBER(10,0), 
	"PAI" NUMBER(10,0), 
	"ID_ICONE" NUMBER(10,0), 
	"ICONE_PADRAO" VARCHAR2(30 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"TIPO" CHAR(1 BYTE), 
	"ARVORE" CHAR(1 BYTE), 
	"ICONE_PADRAO_COR" VARCHAR2(10 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table COMARCA
--------------------------------------------------------

  CREATE TABLE "CRONOS"."COMARCA" 
   (	"TJID_COMARCA" NUMBER(12,0), 
	"NM_COMARCA" VARCHAR2(100 BYTE), 
	"CD_COMARCA_THEMIS" NUMBER(6,0), 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'ethemis1g', 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"DT_ULT_ALTERACAO" DATE DEFAULT sysdate, 
	"VERSAO_REGISTRO" NUMBER(12,0), 
	"DATA_CRIACAO" DATE DEFAULT sysdate
   ) ;
--------------------------------------------------------
--  DDL for Table CONFIGURACAO
--------------------------------------------------------

  CREATE TABLE "CRONOS"."CONFIGURACAO" 
   (	"PK_CONFIGURACAO_01" NUMBER, 
	"ID_GRUPO_TRABALHO" NUMBER, 
	"PERMITE_EDITAR_MENU" VARCHAR2(1 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE
   ) ;
--------------------------------------------------------
--  DDL for Table ELEMENTO_IGT
--------------------------------------------------------

  CREATE TABLE "CRONOS"."ELEMENTO_IGT" 
   (	"PK_ELEMENTO_IGT_01" NUMBER(10,0), 
	"TITULO" VARCHAR2(100 BYTE), 
	"TEXTO" CLOB, 
	"ORDEM" NUMBER(10,0) DEFAULT 1, 
	"ID_ICONE_EDOCS" NUMBER(10,0), 
	"ICONE_PADRAO" VARCHAR2(30 BYTE), 
	"ID_CATEGORIA_ITEM" NUMBER(10,0), 
	"ID_TIPO_ELEMENTO" NUMBER(10,0), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE
   ) ;
--------------------------------------------------------
--  DDL for Table ETIQUETA_GT
--------------------------------------------------------

  CREATE TABLE "CRONOS"."ETIQUETA_GT" 
   (	"PK_ETIQUETA_01" NUMBER(10,0), 
	"ETIQUETA" VARCHAR2(100 BYTE), 
	"ID_ICONE" NUMBER(10,0), 
	"ICONE_PADRAO" VARCHAR2(30 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"ICONE_PADRAO_COR" VARCHAR2(10 BYTE), 
	"ID_GRUPO_TRABALHO" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table GRUPO_DE_TRABALHO
--------------------------------------------------------

  CREATE TABLE "CRONOS"."GRUPO_DE_TRABALHO" 
   (	"PK_GRUPO_DE_TRABALHO_01" NUMBER(10,0), 
	"ID_USUARIO" NUMBER(10,0), 
	"ORDEM" NUMBER(10,0) DEFAULT 1, 
	"ID_ICONE_EDOCS" NUMBER(10,0), 
	"ICONE_PADRAO" VARCHAR2(30 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE
   ) ;
--------------------------------------------------------
--  DDL for Table ICONES_PERSONAL
--------------------------------------------------------

  CREATE TABLE "CRONOS"."ICONES_PERSONAL" 
   (	"PK_ICONE_01" NUMBER, 
	"IMAGEM" CLOB, 
	"TIPO" VARCHAR2(30 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE
   ) ;
--------------------------------------------------------
--  DDL for Table SENTENCA_GT
--------------------------------------------------------

  CREATE TABLE "CRONOS"."SENTENCA_GT" 
   (	"PK_SENTENCA_01" NUMBER(6,0), 
	"ID_GRUPO_TRABALHO" NUMBER(10,0), 
	"PAI" NUMBER(10,0), 
	"ICONE_PADRAO" VARCHAR2(30 BYTE), 
	"FINALIZADA" VARCHAR2(1 BYTE) DEFAULT 'N', 
	"ICONE_PADRAO_COR" VARCHAR2(10 BYTE), 
	"SENTENCA_PRONTA" CLOB, 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,0) DEFAULT 0, 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(50 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"NR_PROCESSO" VARCHAR2(20 BYTE), 
	"ID_ICONE" NUMBER(10,0), 
	"ORDEM" NUMBER(10,0) DEFAULT 1
   ) ;
--------------------------------------------------------
--  DDL for Table TAXONOMIA_CATEGORIA
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TAXONOMIA_CATEGORIA" 
   (	"PK_TAXONOMIA_CAT_01" NUMBER(10,0), 
	"ID_CATEGORIA" NUMBER(10,0), 
	"ID_MODELO_SENTENCA" NUMBER(10,0), 
	"ORDEM" NUMBER(3,0), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE
   ) ;
--------------------------------------------------------
--  DDL for Table TAXONOMIA_ETIQUETA_GT
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" 
   (	"PK_TAXONOMIA_ETIQUETA_GT_01" NUMBER(10,0), 
	"ID_CATEGORIA" NUMBER(10,0), 
	"ID_ETIQUETA" NUMBER(10,0), 
	"ORDEM" NUMBER(10,0) DEFAULT 1, 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"ICONE_PADRAO" VARCHAR2(30 BYTE) DEFAULT 'fa-tag', 
	"ICONE_PADRAO_COR" VARCHAR2(10 BYTE) DEFAULT '#333', 
	"ID_ICONE" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table TAXONOMIA_SENTENCA
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TAXONOMIA_SENTENCA" 
   (	"PK_TAXONOMIA_CAT_01" NUMBER(10,0), 
	"ID_CATEGORIA" NUMBER(10,0), 
	"ID_SENTENCA" NUMBER(10,0), 
	"ORDEM" NUMBER(3,0), 
	"DATA_CRIACAO" DATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE), 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE), 
	"DT_ULT_ALTERACAO" DATE, 
	"ID_ALEGACAO_GT" NUMBER(10,0)
   ) ;
--------------------------------------------------------
--  DDL for Table TAXONOMIA_USUARIO
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TAXONOMIA_USUARIO" 
   (	"PK_TAXONOMIA_USU_01" NUMBER(10,0), 
	"ID_GT" NUMBER(10,0), 
	"ID_USUARIO" NUMBER(10,0), 
	"ORDEM" NUMBER(3,0), 
	"DATA_CRIACAO" DATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE), 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE), 
	"DT_ULT_ALTERACAO" DATE, 
	"PERMISSOES" CHAR(1 BYTE) DEFAULT 'R', 
	"PADRAO" CHAR(1 BYTE) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Table TIPO_ALEGACAO
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TIPO_ALEGACAO" 
   (	"PK_ALEGA_01" NUMBER(10,0), 
	"QUEM_ALEGA" VARCHAR2(50 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"LABEL" VARCHAR2(40 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table TIPO_CATEGORIA
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TIPO_CATEGORIA" 
   (	"PK_TIPO_CATEGORIA_01" NUMBER(10,0), 
	"NOME" VARCHAR2(150 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE
   ) ;
--------------------------------------------------------
--  DDL for Table TIPO_ELEMENTO
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TIPO_ELEMENTO" 
   (	"PK_TIPO_ELEMENTO_01" NUMBER(10,0), 
	"NOME_ITEM" VARCHAR2(100 BYTE), 
	"ALINHAMENTO" CHAR(1 BYTE) DEFAULT 'C', 
	"RECUO" NUMBER(4,0) DEFAULT 0, 
	"ID_ICONE_EDOCS" NUMBER(10,0), 
	"ICONE_PADRAO" VARCHAR2(30 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2), 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE
   ) ;
--------------------------------------------------------
--  DDL for Table TOKEN
--------------------------------------------------------

  CREATE TABLE "CRONOS"."TOKEN" 
   (	"PK_TOKEN_01" NUMBER(10,0), 
	"LABEL" VARCHAR2(40 BYTE), 
	"TABELA" VARCHAR2(20 BYTE), 
	"CAMPO" VARCHAR2(40 BYTE), 
	"FUNCAO" VARCHAR2(50 BYTE) DEFAULT NULL, 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2) DEFAULT 0, 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"METODO" VARCHAR2(50 BYTE), 
	"NOME" VARCHAR2(30 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table USUARIO
--------------------------------------------------------

  CREATE TABLE "CRONOS"."USUARIO" 
   (	"PK_USUARIO_01" NUMBER(10,0), 
	"CPF" VARCHAR2(11 BYTE), 
	"LOGIN" VARCHAR2(60 BYTE), 
	"NOME" VARCHAR2(100 BYTE), 
	"MAGISTRADO" CHAR(1 BYTE) DEFAULT 'S', 
	"PERMISSOES" CHAR(1 BYTE) DEFAULT 'R', 
	"PAI" NUMBER(10,0), 
	"PADRAO" CHAR(1 BYTE), 
	"DATA_CRIACAO" DATE DEFAULT SYSDATE, 
	"VERSAO_REGISTRO" NUMBER(8,2) DEFAULT 0, 
	"IN_SITUACAO_REGISTRO" CHAR(1 BYTE) DEFAULT 'A', 
	"NM_ULT_ALTERACAO" VARCHAR2(150 BYTE) DEFAULT 'CRONOS', 
	"DT_ULT_ALTERACAO" DATE DEFAULT SYSDATE, 
	"ACEITA_TERMO" CHAR(1 BYTE) DEFAULT 'N'
   ) ;
--------------------------------------------------------
--  DDL for Index PK_CATEGORIA_GT
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_CATEGORIA_GT" ON "CRONOS"."CATEGORIA_ITEM" ("PK_CATEGORIA_ITEM_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_ELEMENTO_IGT
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_ELEMENTO_IGT" ON "CRONOS"."ELEMENTO_IGT" ("PK_ELEMENTO_IGT_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_ETIQUETA
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_ETIQUETA" ON "CRONOS"."ETIQUETA_GT" ("PK_ETIQUETA_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_GRUPO_DE_TRABALHO
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_GRUPO_DE_TRABALHO" ON "CRONOS"."GRUPO_DE_TRABALHO" ("PK_GRUPO_DE_TRABALHO_01") 
   ;
--------------------------------------------------------
--  DDL for Index ICONES_PERSONAL_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."ICONES_PERSONAL_PK" ON "CRONOS"."ICONES_PERSONAL" ("PK_ICONE_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_TAXONOMIA_CAT
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_TAXONOMIA_CAT" ON "CRONOS"."TAXONOMIA_CATEGORIA" ("PK_TAXONOMIA_CAT_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_TAXONOMIA_ETIQUETA_GT
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_TAXONOMIA_ETIQUETA_GT" ON "CRONOS"."TAXONOMIA_ETIQUETA_GT" ("PK_TAXONOMIA_ETIQUETA_GT_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_TIPO_CATEGORIA
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_TIPO_CATEGORIA" ON "CRONOS"."TIPO_CATEGORIA" ("PK_TIPO_CATEGORIA_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_TIPO_ELEMENTO
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_TIPO_ELEMENTO" ON "CRONOS"."TIPO_ELEMENTO" ("PK_TIPO_ELEMENTO_01") 
   ;
--------------------------------------------------------
--  DDL for Index PK_USUARIO
--------------------------------------------------------

  CREATE UNIQUE INDEX "CRONOS"."PK_USUARIO" ON "CRONOS"."USUARIO" ("PK_USUARIO_01") 
   ;
--------------------------------------------------------
--  Constraints for Table ALEGACAO_GT
--------------------------------------------------------

  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("ID_GRUPO_TRABALHO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("ID_ALEGACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ALEGACAO_GT" MODIFY ("PK_ALEGACAO_GT_01" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CATEGORIA_ITEM
--------------------------------------------------------

  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("ID_GRUPO_TRABALHO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("ORDEM" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("ID_TIPO_CATEGORIA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("COMPARTILHAR" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" MODIFY ("PK_CATEGORIA_ITEM_01" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" ADD CONSTRAINT "PK_CATEGORIA_GT" PRIMARY KEY ("PK_CATEGORIA_ITEM_01")
  USING INDEX   ENABLE;
--------------------------------------------------------
--  Constraints for Table ELEMENTO_IGT
--------------------------------------------------------

  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("ID_TIPO_ELEMENTO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("ID_CATEGORIA_ITEM" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("ORDEM" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" MODIFY ("PK_ELEMENTO_IGT_01" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" ADD CONSTRAINT "PK_ELEMENTO_IGT" PRIMARY KEY ("PK_ELEMENTO_IGT_01")
  USING INDEX   ENABLE;
--------------------------------------------------------
--  Constraints for Table ETIQUETA_GT
--------------------------------------------------------

  ALTER TABLE "CRONOS"."ETIQUETA_GT" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ETIQUETA_GT" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ETIQUETA_GT" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ETIQUETA_GT" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ETIQUETA_GT" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ETIQUETA_GT" MODIFY ("ETIQUETA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ETIQUETA_GT" MODIFY ("PK_ETIQUETA_01" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."ETIQUETA_GT" ADD CONSTRAINT "PK_ETIQUETA" PRIMARY KEY ("PK_ETIQUETA_01")
  USING INDEX   ENABLE;
--------------------------------------------------------
--  Constraints for Table GRUPO_DE_TRABALHO
--------------------------------------------------------

  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("ORDEM" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("ID_USUARIO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" MODIFY ("PK_GRUPO_DE_TRABALHO_01" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" ADD CONSTRAINT "PK_GRUPO_DE_TRABALHO" PRIMARY KEY ("PK_GRUPO_DE_TRABALHO_01")
  USING INDEX   ENABLE;
--------------------------------------------------------
--  Constraints for Table ICONES_PERSONAL
--------------------------------------------------------

  ALTER TABLE "CRONOS"."ICONES_PERSONAL" ADD CONSTRAINT "ICONES_PERSONAL_PK" PRIMARY KEY ("PK_ICONE_01")
  USING INDEX   ENABLE;
  ALTER TABLE "CRONOS"."ICONES_PERSONAL" MODIFY ("PK_ICONE_01" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TAXONOMIA_CATEGORIA
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" ADD CONSTRAINT "PK_TAXONOMIA_CAT" PRIMARY KEY ("PK_TAXONOMIA_CAT_01")
  USING INDEX   ENABLE;
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("ID_MODELO_SENTENCA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("ID_CATEGORIA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_CATEGORIA" MODIFY ("PK_TAXONOMIA_CAT_01" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TAXONOMIA_ETIQUETA_GT
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("ORDEM" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("ID_ETIQUETA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("ID_CATEGORIA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" MODIFY ("PK_TAXONOMIA_ETIQUETA_GT_01" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" ADD CONSTRAINT "PK_TAXONOMIA_ETIQUETA_GT" PRIMARY KEY ("PK_TAXONOMIA_ETIQUETA_GT_01")
  USING INDEX   ENABLE;
--------------------------------------------------------
--  Constraints for Table TAXONOMIA_SENTENCA
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("ID_SENTENCA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("ID_CATEGORIA" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_SENTENCA" MODIFY ("PK_TAXONOMIA_CAT_01" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TAXONOMIA_USUARIO
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("ID_USUARIO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("ID_GT" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TAXONOMIA_USUARIO" MODIFY ("PK_TAXONOMIA_USU_01" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TIPO_ALEGACAO
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TIPO_ALEGACAO" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ALEGACAO" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ALEGACAO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ALEGACAO" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ALEGACAO" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ALEGACAO" MODIFY ("PK_ALEGA_01" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TIPO_CATEGORIA
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" MODIFY ("NOME" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" MODIFY ("PK_TIPO_CATEGORIA_01" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_CATEGORIA" ADD CONSTRAINT "PK_TIPO_CATEGORIA" PRIMARY KEY ("PK_TIPO_CATEGORIA_01")
  USING INDEX   ENABLE;
--------------------------------------------------------
--  Constraints for Table TIPO_ELEMENTO
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("RECUO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("ALINHAMENTO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("NOME_ITEM" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("PK_TIPO_ELEMENTO_01" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" ADD CONSTRAINT "PK_TIPO_ELEMENTO" PRIMARY KEY ("PK_TIPO_ELEMENTO_01")
  USING INDEX   ENABLE;
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."TIPO_ELEMENTO" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USUARIO
--------------------------------------------------------

  ALTER TABLE "CRONOS"."USUARIO" ADD CONSTRAINT "PK_USUARIO" PRIMARY KEY ("PK_USUARIO_01")
  USING INDEX   ENABLE;
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("DT_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("NM_ULT_ALTERACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("VERSAO_REGISTRO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("DATA_CRIACAO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("PERMISSOES" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("MAGISTRADO" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("NOME" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("LOGIN" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("CPF" NOT NULL ENABLE);
  ALTER TABLE "CRONOS"."USUARIO" MODIFY ("PK_USUARIO_01" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table CATEGORIA_ITEM
--------------------------------------------------------

  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" ADD CONSTRAINT "FK_CATEGORIA_GT_GRUPO_DE_TRABA" FOREIGN KEY ("ID_GRUPO_TRABALHO")
	  REFERENCES "CRONOS"."GRUPO_DE_TRABALHO" ("PK_GRUPO_DE_TRABALHO_01") ENABLE;
  ALTER TABLE "CRONOS"."CATEGORIA_ITEM" ADD CONSTRAINT "FK_CATEGORIA_GT_TIPO_CATEGORIA" FOREIGN KEY ("ID_TIPO_CATEGORIA")
	  REFERENCES "CRONOS"."TIPO_CATEGORIA" ("PK_TIPO_CATEGORIA_01") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ELEMENTO_IGT
--------------------------------------------------------

  ALTER TABLE "CRONOS"."ELEMENTO_IGT" ADD CONSTRAINT "FK_ELEMENTO_IGT_CATEGORIA_ITEM" FOREIGN KEY ("ID_CATEGORIA_ITEM")
	  REFERENCES "CRONOS"."CATEGORIA_ITEM" ("PK_CATEGORIA_ITEM_01") ENABLE;
  ALTER TABLE "CRONOS"."ELEMENTO_IGT" ADD CONSTRAINT "FK_ELEMENTO_IGT_TIPO_ELEMENTO" FOREIGN KEY ("ID_TIPO_ELEMENTO")
	  REFERENCES "CRONOS"."TIPO_ELEMENTO" ("PK_TIPO_ELEMENTO_01") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table GRUPO_DE_TRABALHO
--------------------------------------------------------

  ALTER TABLE "CRONOS"."GRUPO_DE_TRABALHO" ADD CONSTRAINT "FK_GRUPO_DE_TRABALHO_USUARIO" FOREIGN KEY ("ID_USUARIO")
	  REFERENCES "CRONOS"."USUARIO" ("PK_USUARIO_01") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table TAXONOMIA_ETIQUETA_GT
--------------------------------------------------------

  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" ADD CONSTRAINT "FK_TAXONOMIA_ETIQUETA_GT_CATEG" FOREIGN KEY ("ID_CATEGORIA")
	  REFERENCES "CRONOS"."CATEGORIA_ITEM" ("PK_CATEGORIA_ITEM_01") ENABLE;
  ALTER TABLE "CRONOS"."TAXONOMIA_ETIQUETA_GT" ADD CONSTRAINT "FK_TAXONOMIA_ETIQUETA_GT_ETIQU" FOREIGN KEY ("ID_ETIQUETA")
	  REFERENCES "CRONOS"."ETIQUETA_GT" ("PK_ETIQUETA_01") ENABLE;


CREATE SEQUENCE SEQ_ALEGACAO_GT;
CREATE SEQUENCE SEQ_CATEGORIA_ITEM;
CREATE SEQUENCE SEQ_CONFIGURACAO;
CREATE SEQUENCE SEQ_ELEMENTO_IGT;
CREATE SEQUENCE SEQ_ETIQUETA_GT;
CREATE SEQUENCE SEQ_GRUPO_TRABALHO;
CREATE SEQUENCE SEQ_ICONES_PERSONAL;
CREATE SEQUENCE SEQ_TAXONOMIA_CATEGORIA;
CREATE SEQUENCE SEQ_TAXONOMIA_ETIQUETA_GT;
CREATE SEQUENCE SEQ_TAXONOMIA_CATEGORIA;
CREATE SEQUENCE SEQ_TAXONOMIA_USUARIO;
CREATE SEQUENCE SEQ_TIPO_ALEGACAO;
CREATE SEQUENCE SEQ_TIPO_CATEGORIA;
CREATE SEQUENCE SEQ_TIPO_ELEMENTO;
CREATE SEQUENCE SEQ_TOKEN;
CREATE SEQUENCE SEQ_SENTENCA;
CREATE SEQUENCE SE_FUNDAMENTO;
CREATE SEQUENCE SE_USUARIO;