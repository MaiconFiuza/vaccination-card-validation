# vaccination-card-validation
Projeto de Hack final da pós graduação em Arquitetura e Desenvolvimento Java na FIAP


## FIAP - Tech Challenge - Fase 5 Hackathon


### Microserviço de envio e gerenciamento do cartão de Vacina
Nesta quinta e última etapa da pós tech realizei a criação de um microserviço responsável pelo envio do cartão de vacina do usuário do sus, ainda facilitando ao usuário que hoje em dia precisa procurar uma unidade básica de saúde que seja informatizada para pode atualizar suas vacinas na plataforma a partir do seu cartão de vacina.

### Serviço

Serviço responsável pelo envio do cartão de vacina e a adição das vacinas no cadastro do usuário 

Disponível na porta http://localhost:8080/

Link para a documentação das API's do projeto (OpenAPI):
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Para executar a aplicação:

### 1. Fazer o build dos containeres da aplicação:
Executar o seguinte comando:
    
    docker-compose up --build

O comando acima gerará os conteineres de aplicação e banco de dados.

### 2. Executar a aplicação através dos containeres criados:
Executar o seguinte comando para inicializar os containeres da aplicação, na raíz do projeto (onde se encontra o arquivo docker-compose.yml):

    docker-compose up

### 3. Acessar a aplicação
A aplicação estará disponível nas seguintes URL:

    http://localhost:8080/
   

As collections do postman se encontram no seguinte link: [Collection Postman](https://github.com/MaiconFiuza/vaccination-card-validation/blob/main/Projeto%20M%C3%B3dulo%205.postman_collection.json)


### Cobertura de testes
Para cobertura de testes foi utilizado a ferramenta [Jacoco](https://www.eclemma.org/jacoco/)
Para rodar os testes unitários, na raiz do projeto, execute o seguinte comando: 

> mvn test

O relatório de cobertura pode ser encontrado dentro da pasta `./target`. Para acessar o relatório web acesse:

> taget/site/jacoco/index.html

### Vídeo de apresentação do projeto

O Vídeo de apresentação se encontra no seguinte (link)[]
