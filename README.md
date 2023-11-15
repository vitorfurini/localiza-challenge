# teste-localiza

Este projeto tem como finalidade demonstrar conhecimentos técnicos.

Tecnologias utilizadas:
- Java 17
- Spring boot
- jUnit 5
- Maven
- Docker
- MongoDB
- Swagger

Para rodar o projeto é necessário o docker instalado.
 - Para instalar o docker baixe diretamente do site https://www.docker.com/products/docker-desktop/

Após instalar e fazer todas as configurações, é necessário abrir um CMD do windows ou terminal do linux e rodar o comando
 - docker pull mongo:latest
    
O comando acima irá baixar uma imagem do mongoDB na ultima versão, após terminar o download da imagem, rode o comando abaixo para iniciar o mongo com as configurações do projeto.
 - docker run -d -p 27017:27017 --name mongodb-localiza-database mongo:latest
 
Após finalizar a etapa acima, rode o comando abaixo NA SUA IDE para buildar a aplicação em um container dentro do docker
 - docker build -t localiza-api:1.0 .
 
 Após o comando acima, rode o comando abaixo no cmd para rodar a aplicação linkando a mesma com a instancia do mongoDB já iniciada no DOCKER
  - docker run -p 8080:8080 --name localiza-api --link mongodb-localiza-database:mongo -d localiza-api:1.0

Após todos os procedimentos acima, a documentação(Swagger), pode ser acessada na URL: http://localhost:8080/swagger-ui.html#/


Os endpoints disponibilizados são:
POI CONTROLLER:
- POST /poi/create - Recurso disponível para cadastrar um POI na base de dados.
- GET /poi/findByName/{name} - Recurso disponível para buscar um POI pelo nome na base de dados.
- GET /poi/list - Recurso disponível para listar todos os POIS cadastrados na base de dados.
- POST /poi/upload - Recurso disponível para carga do documento Poi na base de dados a partir de um arquivo CSV

POI Tracker Controller
- POST /poiTracker/track - Recurso disponível para fazer a consulta se um determinado veículo está/esteve no POI

Position Controller
- POST /position - Recurso disponível para cadastrar uma nova posição na base de dados
- GET  /position/findByLicensePlate/{licensePlate} - Recurso disponível para buscar a posição de um veiculo pela placa
- GET /position/list - Recurso disponível para listar todos os POIS cadastrados na base de dados.
- POST /position/upload - Recurso disponível para carga do documento Position na base de dados a partir de um arquivo CSV
