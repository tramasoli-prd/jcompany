Siga os seguintes passos parar realizar sua contribuição para o jCompany.

1 - Crie um Fork.

 O Fork é um repositório git especifico do usuário onde ele vai efetuar os seus próprios commits.
 a base do Fork é o branch master no momento da solicitação.
 

2 - Realize commit das contribuições para o projeto.

 O usuário que solicitou o Fork efetua os commits de alterações no seu próprio Fork.

 
3 - Submeta um Merge Request.

 O usuário cria uma solicitação de Merge Request, para realizar a integração das alterações realizadas no branch master do projeto.

 
4 - Aprovação do Merge Request.

 O administrador master do repositorio verifica a solicitação de Merge Request do usuário contribuidor.
 O usuário master analisa as alterações e decide executar o merge ou não.
 Caso o Merge Request seja aceito, o usuário master irá efetuar o merge das alterações para a branch master.
 

 5 - Atualize seu Fork.

  Após a execução do Merge Request o usuário contribuidor deve realizar a atualização do Fork para continuar a contribuir. 
  Essa atualização visa ajuda em merges futuros do Fork. 
  A atualização traz as atualizações do branch master para o Fork.

  Passos para atualização do Fork.

  5.1 - $ git fetch http://gitpub.cast.com.br/castgroup/jcompany.git master
  	(baixa conteúdo a branch master do repositório principal, ficando disponível no FETCH_HEAD);

  5.2 - $ git checkout -b User/jcompany-master FETCH_HEAD
  	(cria uma branch local "User/jcompany-master" com o conteúdo disponível no FETCH_HEAD);

  5.3 - $ git checkout master
  	(trocar a branch selecionada para a master);
  	
  5.4 - $ git merge --no-ff User/jcompany-master
  	(realiza o merge da branch master com a branch "User/jcompany-master" );

  5.5 - Caso haja conflitos esses devem ser resolvidos manualmente. Em arquivos cujo o merge está resolvido o usuário contribuidor deve executar o comando:

  $ git add "filename.extension"

  5.6 - Quando todos os conflitos forem resolvidos, execute os comandos:
  $ git commit
  $ git push origin master