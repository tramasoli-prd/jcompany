- criar fork
 O fork é um repositório git especifico do usuário onde ele vai efetuar os seus próprios commits.
 a base do fork é o branch master no momento da solicitação.
 
- commitar contribuições para o projeto
 O usuário que solicitou o fork efetua os commits de alterações no seu próprio fork.
 
- merge request
 O usuário cria uma solicitação de merge request, para realizar a integração das alterações realizadas na branch master do projeto.
 
- execução do merge request(CastGroup)
 O administrador master do repositorio verifica a solicitação de merge request do usuário contribuidor.
 O usuário master analisa as alterações e decide executar o merge ou não.
 Caso o merge request seja aceito, o usuário master irá efetuar o merge das alterações para a branch master.
 
 - atualização do fork
  Após a execução do merge request o usuário contribuidor deve realizar a atualização do fork para continuar a contribuir. 
  Essa atualização visa ajuda em merges futuros do fork. 
  A atualização traz as atualizações do branch master para o fork.

  - passos para atualização do fork

  1 $ git fetch http://gitpub.cast.com.br/powerlogic/jcompany.git master
  	(baixa conteúdo a branch master do repositório principal, ficando disponível no FETCH_HEAD)

  2 $ git checkout -b User/jcompany-master FETCH_HEAD
  	(cria uma branch local "User/jcompany-master" com o conteúdo disponível no FETCH_HEAD).

  3 $ git checkout master
  	(trocar a branch selecionada para a master).

  4	$ git merge --no-ff User/jcompany-master
  	(realiza o merge da branch master com a branch "User/jcompany-master" ).

  5 caso haja conflitos esses devem ser resolvidos manualmente. Em arquivos cujo o merge está resolvido o usuário contribuidor deve executar o comando $ git add "filename.extension"

  6 quando todos os conflitos forem resolvidos
  $ git commit
  $ git push origin master
