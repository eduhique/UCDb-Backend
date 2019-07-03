# UCDb: classificações e reviews de cursos da UFCG (API REST)

O UFCG Cursos database é uma aplicação para classificação e reviews de disciplinas de cursos da UFCG. Por enquanto, a versão 1 do sistema será alimentada apenas com disciplinas do curso de Ciência da Computação. Os usuários dessa aplicação irão construir conteúdo sobre as disciplinas de forma colaborativa através de comentários e likes nas disciplinas. O sistema deve usar essa informação construída para rankear as disciplinas do curso.

## Introdução

Este API faz parte da avaliação disciplina de Projeto de Software, que foi subdividade em frontend e backend.

- [Documentação Swagger](https://api-ucdb.herokuapp.com/api/swagger-ui.html)
- [Aplicação Implanatada](https://http://ucdb-client.herokuapp.com)
- [Video Apresentação](https://youtu.be/lOhLK8wYFhE)

## Funcionalidades:

### Cadastrar/autenticar usuários:
É possível o usuário se cadastar e se logar dentro do sistema para ter acesso às demais funcionalidades.

### Pesquisar disciplinas a partir de uma (sub)string:
O usuário pode pesquisar por uma disciplina por um pedacinho do seu nome, ou então, pelo nome completo.

### Adicionar comentários de uma disciplina: 
É possível que o usuário logado adicione comentários à página da dísciplina. Também é possível responder a estes comentários.
    
### Apagar comentários de uma disciplina:
O usuário também pode apagar comentários e respostas feitos por ele.

### Dar/retirar like em uma disciplina:
O usuário pode dar like a uma disciplina, assim como pode retirar este like da mesma.

### Ranking das disciplinas:
É possível ver o ranking de disciplinas a partir do número de likes ou também pelo número de comentários.

## Grupo

- Eduardo Henrique Pontes Silva 
- Matheus silva Araújo