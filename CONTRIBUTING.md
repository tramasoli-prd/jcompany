# Contribuindo para o projeto

## 1 - Criar Fork </br>

 O Fork é um repositório git especifico do usuário onde ele vai efetuar os seus próprios commits.
 a base do Fork é o branch master no momento da solicitação.

## 2 - Baixar o Fork </br>

## 3 - Realize commit/push das contribuições para o projeto </br>

 O usuário que solicitou o Fork efetua os commits de alterações no seu próprio Fork.

 
## 4 - Submeta um Merge Request

 O usuário cria uma solicitação de Merge Request, para realizar a integração das alterações realizadas no branch master do projeto.

 
## 5 - Aprovação do Merge Request

 O administrador master do repositorio verifica a solicitação de Merge Request do usuário contribuidor.
 O usuário master analisa as alterações e decide executar o merge ou não.
 Caso o Merge Request seja aceito, o usuário master irá efetuar o merge das alterações para a branch master.
 

## 6 - Atualize seu Fork

  Após a execução do Merge Request o usuário contribuidor deve realizar a atualização do Fork para continuar a contribuir. 
  Essa atualização visa ajuda em merges futuros do Fork. 
  A atualização traz as atualizações do branch master para o Fork.

  Passos para atualização do Fork

####  6.1 - Baixe o conteúdo da branch master no repositório principal para  FETCH_HEAD
    $ git fetch http://gitpub.cast.com.br/powerlogic/jcompany.git master
 

####  6.2 - Crie uma branch local "User/jcompany-master" com o conteúdo disponível em FETCH_HEAD
    $ git checkout -b User/jcompany-master FETCH_HEAD

####  6.3 - Troque a branch selecionada para a master
    $ git checkout master

####  6.4 - Realize o merge da branch master com a branch "User/jcompany-master"
    $ git merge --no-ff User/jcompany-master

####  6.5 - Conflitos devem ser resolvidos manualmente
    $ git add "filename.extension"
    
####  6.6 - Quando todos os conflitos forem resolvidos, execute os comandos:
    $ git commit
</br>

    $ git push origin master