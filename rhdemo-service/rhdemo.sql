--------------------------------------------------------
--  Arquivo criado - Sexta-feira-Novembro-06-2015   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence SE_CURRICULO
--------------------------------------------------------

   CREATE SEQUENCE  "SE_CURRICULO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_CURRICULO_CONTEUDO
--------------------------------------------------------

   CREATE SEQUENCE  "SE_CURRICULO_CONTEUDO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_DEPARTAMENTO
--------------------------------------------------------

   CREATE SEQUENCE  "SE_DEPARTAMENTO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_DEPENDENTE
--------------------------------------------------------

   CREATE SEQUENCE  "SE_DEPENDENTE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_FOTO
--------------------------------------------------------

   CREATE SEQUENCE  "SE_FOTO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_FOTO_CONTEUDO
--------------------------------------------------------

   CREATE SEQUENCE  "SE_FOTO_CONTEUDO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_FUNCIONARIO
--------------------------------------------------------

   CREATE SEQUENCE  "SE_FUNCIONARIO"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_HISTORICO_PROFISSIONAL
--------------------------------------------------------

   CREATE SEQUENCE  "SE_HISTORICO_PROFISSIONAL"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Sequence SE_UNIDADEFEDERATIVA
--------------------------------------------------------

   CREATE SEQUENCE  "SE_UNIDADEFEDERATIVA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE
/
--------------------------------------------------------
--  DDL for Table CURRICULO
--------------------------------------------------------

  CREATE TABLE "CURRICULO" ("PK_CURRICULO" NUMBER(10,0), "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "NOME" VARCHAR2(255), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "TAMANHO" NUMBER(10,0), "TIPO" VARCHAR2(255), "URL" VARCHAR2(255), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0), "CONTEUDO_PK_CURRICULO_CONTEUDO" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Table CURRICULO_CONTEUDO
--------------------------------------------------------

  CREATE TABLE "CURRICULO_CONTEUDO" ("PK_CURRICULO_CONTEUDO" NUMBER(10,0), "CONTEUDO_BINARIO" BLOB, "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Table DEPARTAMENTO
--------------------------------------------------------

  CREATE TABLE "DEPARTAMENTO" ("PK_DEPARTAMENTO" NUMBER(10,0), "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "DESCRICAO" VARCHAR2(100), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0), "ID_DEPARTAMENTO_PAI" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Table DEPENDENTE
--------------------------------------------------------

  CREATE TABLE "DEPENDENTE" ("ID" NUMBER(5,0), "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "NOME" VARCHAR2(255), "SEXO" VARCHAR2(1), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0), "FUNCIONARIO_ID" NUMBER(5,0))
/
--------------------------------------------------------
--  DDL for Table FOTO
--------------------------------------------------------

  CREATE TABLE "FOTO" ("PK_FOTO" NUMBER(10,0), "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "NOME" VARCHAR2(255), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "TAMANHO" NUMBER(10,0), "TIPO" VARCHAR2(255), "URL" VARCHAR2(255), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Table FOTO_CONTEUDO
--------------------------------------------------------

  CREATE TABLE "FOTO_CONTEUDO" ("PK_FOTO_CONTEUDO" NUMBER(10,0), "CONTEUDO_BINARIO" BLOB, "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Table FUNCIONARIO
--------------------------------------------------------

  CREATE TABLE "FUNCIONARIO" ("ID" NUMBER(5,0), "CPF" VARCHAR2(255), "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "DATANASCIMENTO" DATE, "EMAIL" VARCHAR2(255), "ESTADOCIVIL" VARCHAR2(1), "NOME" VARCHAR2(255), "OBSERVACAO" VARCHAR2(255), "SEXO" VARCHAR2(1), "SITHISTORICOPLC" VARCHAR2(255), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "TEMCURSOSUPERIOR" NUMBER(1,0) DEFAULT 0, "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0), "DEPARTAMENTO_PK_DEPARTAMENTO" NUMBER(10,0), "ID_FOTO" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Table FUNCIONARIO_CURRICULO
--------------------------------------------------------

  CREATE TABLE "FUNCIONARIO_CURRICULO" ("FUNCIONARIOENTITY_ID" NUMBER(5,0), "CURRICULO_PK_CURRICULO" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Table HISTORICO_PROFISSIONAL
--------------------------------------------------------

  CREATE TABLE "HISTORICO_PROFISSIONAL" ("ID" NUMBER(5,0), "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "DATAINICIO" DATE, "DESCRICAO" VARCHAR2(255), "SALARIO" NUMBER(11,0), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0), "FUNCIONARIO_ID" NUMBER(5,0))
/
--------------------------------------------------------
--  DDL for Table UNIDADEFEDERATIVA
--------------------------------------------------------

  CREATE TABLE "UNIDADEFEDERATIVA" ("PK_UNIDADEFEDERATIVA" NUMBER(19,0), "DT_ULT_ALTERACAO" TIMESTAMP (6), "DATA_CRIACAO" TIMESTAMP (6), "NOME" VARCHAR2(100), "SIGLA" VARCHAR2(60), "IN_SITUACAO_REGISTRO" VARCHAR2(1), "NM_ULT_ALTERACAO" VARCHAR2(150), "VERSAO_REGISTRO" NUMBER(10,0))
/
--------------------------------------------------------
--  DDL for Index SYS_C0020626
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020626" ON "DEPARTAMENTO" ("PK_DEPARTAMENTO")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020654
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020654" ON "DEPENDENTE" ("ID")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020663
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020663" ON "FUNCIONARIO" ("ID")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020666
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020666" ON "HISTORICO_PROFISSIONAL" ("ID")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020669
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020669" ON "FUNCIONARIO_CURRICULO" ("FUNCIONARIOENTITY_ID", "CURRICULO_PK_CURRICULO")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020960
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020960" ON "UNIDADEFEDERATIVA" ("PK_UNIDADEFEDERATIVA")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020963
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020963" ON "CURRICULO_CONTEUDO" ("PK_CURRICULO_CONTEUDO")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020966
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020966" ON "CURRICULO" ("PK_CURRICULO")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020969
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020969" ON "FOTO_CONTEUDO" ("PK_FOTO_CONTEUDO")
/
--------------------------------------------------------
--  DDL for Index SYS_C0020972
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0020972" ON "FOTO" ("PK_FOTO")
/
--------------------------------------------------------
--  Constraints for Table CURRICULO
--------------------------------------------------------

  ALTER TABLE "CURRICULO" MODIFY ("PK_CURRICULO" NOT NULL ENABLE)
  ALTER TABLE "CURRICULO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "CURRICULO" ADD PRIMARY KEY ("PK_CURRICULO") ENABLE
/
--------------------------------------------------------
--  Constraints for Table CURRICULO_CONTEUDO
--------------------------------------------------------

  ALTER TABLE "CURRICULO_CONTEUDO" MODIFY ("PK_CURRICULO_CONTEUDO" NOT NULL ENABLE)
  ALTER TABLE "CURRICULO_CONTEUDO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "CURRICULO_CONTEUDO" ADD PRIMARY KEY ("PK_CURRICULO_CONTEUDO") ENABLE
/
--------------------------------------------------------
--  Constraints for Table DEPARTAMENTO
--------------------------------------------------------

  ALTER TABLE "DEPARTAMENTO" MODIFY ("PK_DEPARTAMENTO" NOT NULL ENABLE)
  ALTER TABLE "DEPARTAMENTO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "DEPARTAMENTO" ADD PRIMARY KEY ("PK_DEPARTAMENTO") ENABLE
/
--------------------------------------------------------
--  Constraints for Table DEPENDENTE
--------------------------------------------------------

  ALTER TABLE "DEPENDENTE" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "DEPENDENTE" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "DEPENDENTE" ADD PRIMARY KEY ("ID") ENABLE
/
--------------------------------------------------------
--  Constraints for Table FOTO
--------------------------------------------------------

  ALTER TABLE "FOTO" MODIFY ("PK_FOTO" NOT NULL ENABLE)
  ALTER TABLE "FOTO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "FOTO" ADD PRIMARY KEY ("PK_FOTO") ENABLE
/
--------------------------------------------------------
--  Constraints for Table FOTO_CONTEUDO
--------------------------------------------------------

  ALTER TABLE "FOTO_CONTEUDO" MODIFY ("PK_FOTO_CONTEUDO" NOT NULL ENABLE)
  ALTER TABLE "FOTO_CONTEUDO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "FOTO_CONTEUDO" ADD PRIMARY KEY ("PK_FOTO_CONTEUDO") ENABLE
/
--------------------------------------------------------
--  Constraints for Table FUNCIONARIO
--------------------------------------------------------

  ALTER TABLE "FUNCIONARIO" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "FUNCIONARIO" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "FUNCIONARIO" ADD PRIMARY KEY ("ID") ENABLE
/
--------------------------------------------------------
--  Constraints for Table FUNCIONARIO_CURRICULO
--------------------------------------------------------

  ALTER TABLE "FUNCIONARIO_CURRICULO" MODIFY ("FUNCIONARIOENTITY_ID" NOT NULL ENABLE)
  ALTER TABLE "FUNCIONARIO_CURRICULO" MODIFY ("CURRICULO_PK_CURRICULO" NOT NULL ENABLE)
  ALTER TABLE "FUNCIONARIO_CURRICULO" ADD PRIMARY KEY ("FUNCIONARIOENTITY_ID", "CURRICULO_PK_CURRICULO") ENABLE
/
--------------------------------------------------------
--  Constraints for Table HISTORICO_PROFISSIONAL
--------------------------------------------------------

  ALTER TABLE "HISTORICO_PROFISSIONAL" MODIFY ("ID" NOT NULL ENABLE)
  ALTER TABLE "HISTORICO_PROFISSIONAL" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "HISTORICO_PROFISSIONAL" ADD PRIMARY KEY ("ID") ENABLE
/
--------------------------------------------------------
--  Constraints for Table UNIDADEFEDERATIVA
--------------------------------------------------------

  ALTER TABLE "UNIDADEFEDERATIVA" MODIFY ("PK_UNIDADEFEDERATIVA" NOT NULL ENABLE)
  ALTER TABLE "UNIDADEFEDERATIVA" MODIFY ("IN_SITUACAO_REGISTRO" NOT NULL ENABLE)
  ALTER TABLE "UNIDADEFEDERATIVA" ADD PRIMARY KEY ("PK_UNIDADEFEDERATIVA") ENABLE
/
--------------------------------------------------------
--  Ref Constraints for Table CURRICULO
--------------------------------------------------------

  ALTER TABLE "CURRICULO" ADD CONSTRAINT "CRRCLOCNTDOPKCURRICULOCONTEUDO" FOREIGN KEY ("CONTEUDO_PK_CURRICULO_CONTEUDO") REFERENCES "CURRICULO_CONTEUDO" ("PK_CURRICULO_CONTEUDO") ENABLE
/
--------------------------------------------------------
--  Ref Constraints for Table DEPENDENTE
--------------------------------------------------------

  ALTER TABLE "DEPENDENTE" ADD CONSTRAINT "FK_DEPENDENTE_FUNCIONARIO_ID" FOREIGN KEY ("FUNCIONARIO_ID") REFERENCES "FUNCIONARIO" ("ID") ENABLE
/
--------------------------------------------------------
--  Ref Constraints for Table FUNCIONARIO
--------------------------------------------------------

  ALTER TABLE "FUNCIONARIO" ADD CONSTRAINT "FK_FUNCIONARIO_ID_FOTO" FOREIGN KEY ("ID_FOTO") REFERENCES "FOTO" ("PK_FOTO") ENABLE
  ALTER TABLE "FUNCIONARIO" ADD CONSTRAINT "FNCNRIODPRTMENTOPKDEPARTAMENTO" FOREIGN KEY ("DEPARTAMENTO_PK_DEPARTAMENTO") REFERENCES "DEPARTAMENTO" ("PK_DEPARTAMENTO") ENABLE
/
--------------------------------------------------------
--  Ref Constraints for Table FUNCIONARIO_CURRICULO
--------------------------------------------------------

  ALTER TABLE "FUNCIONARIO_CURRICULO" ADD CONSTRAINT "FNCNRCURRICULOCRRCLPKCURRICULO" FOREIGN KEY ("CURRICULO_PK_CURRICULO") REFERENCES "CURRICULO" ("PK_CURRICULO") ENABLE
  ALTER TABLE "FUNCIONARIO_CURRICULO" ADD CONSTRAINT "FNCNROCURRICULOFNCNRIOENTITYID" FOREIGN KEY ("FUNCIONARIOENTITY_ID") REFERENCES "FUNCIONARIO" ("ID") ENABLE
/
--------------------------------------------------------
--  Ref Constraints for Table HISTORICO_PROFISSIONAL
--------------------------------------------------------

  ALTER TABLE "HISTORICO_PROFISSIONAL" ADD CONSTRAINT "HSTRICOPROFISSIONALFNCONARIOID" FOREIGN KEY ("FUNCIONARIO_ID") REFERENCES "FUNCIONARIO" ("ID") ENABLE
/
