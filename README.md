[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[POSTGRES_BADGE]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[AWS_BADGE]:https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white


<h1 style="font-weight: bold;">API EVENTOS TECNOLOGIA COM AWS 💻</h1>


![spring][SPRING_BADGE]
![java][JAVA_BADGE]
![postgres][POSTGRES_BADGE]
![AWS][AWS_BADGE]

<p>
 <a href="#started">Inicializando Projeto</a> • 
  <a href="#started">Configuracões AWS</a> • 
  <a href="#routes">Endpoints</a> 
</p>

<p>
  <b>O projeto é uma aplicação backend que implementa um sistema que gerencia eventos de tecnologia. Esse gerenciamento se dá inicialmente por meio do banco de dados Postgres e as atualizações são efetivadas por meio de migrations (usando o flyway). Ele permite que novos eventos sejam criados e que também possam ser acrescentados arquivos durante a criação. Alem disso é possível filtrar os eventos da tabela, bem como aplicar discontos e etc. Tudo e com Deploy na AWS!</b>
</p>

<h2 id="started">Instruções de Instalação</h2>

<h3>Pré-requisitos:</h3>

- Uma IDE habilitada para compilar código Java.
- Banco de dados Postgres.
- Um software de capaz de fazer as requisições http.
- Uma conta na AWS.
- Aws CLI instalada.

<h3>🔨 Inicializando Projeto:</h3>

No terminal digite:

```bash
git clone https://github.com/BrendaBraganca/api-eventos-aws
```

<h3>🔨Variaveis de Ambiente</h3>
1. Os testes iniciais - que foram executados usando o banco de dados local em vez do Amazon RDS - foram executados com essa configuracao no application.properties

```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/eventostec
spring.datasource.username=your-user-name
spring.datasource.password=your-password

```
2. Cada bucket da aws deve ter um nome único no espaço global da empresa. Logo o BucketName também é uma variável de ambiente:
```yaml
spring.application.name=api
spring.datasource.url=jdbc:postgresql://url-do-seu-banco-de-dados-rds/postgres #Sera criado posteriormente
spring.datasource.username=postgres
spring.datasource.password=senha-do-seu-banco-de-dados
aws.region=us-east-1
aws.bucket.name=nome-do-seu-bucket

```
   
<h3>⚙️ Configurando AWS</h3>

1. Incialmente é importante que vocÊ tenha as suas credenciais aws armazenadas em seu computador. Para isso execute os seguintes comandos no terminal:
```yaml
cd .aws
aws configure
AWS Access Key ID: sua-chave-de-acesso
AWS Secret Key ID: sua-chave-secreta-de-acesso

```
2. Quando for gerar um bucket S3 na aws é importantante que ele tenha a seguinte Política de Permissão:
```yaml
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicReadGetObject",
            "Effect": "Allow",
            "Principal": "*",
            "Action": [
                "s3:PutObject",
                "s3:GetObject"
            ],
            "Resource": "arn:aws:s3:::eventosi--imagens/*"
        }
    ]
}

```
3. Caso também esteja usando VScode como sua IDE, instale a extensão AWS ToolKit.
4. É importante criar uma instancia EC2 na sua conta AWS para que uma máquina virtual possa rodar a aplicação.


<h2 id="routes">📍Endpoints</h2>


​
| route               | description                                          
|---------------------|-----------------------------------------------------
| <kbd>GET /api/events</kbd>     | retorna todos os eventos em nosso banco de dados (eventos que ainda não ocorreram).
| <kbd>GET /api/filter</kbd>     | retorna os eventos que obedecem aos filtros que foram adicionados em nossa url.
| <kbd>POST /api/events</kbd>     | adiciona novos eventos em nosso banco de dados.

<h4>❗️POST</h4>
É necessário fazer uma requisição com o body do tipo Multipart e adicionar os campos title, description, eventURL, image (que será um upload de um arquivo), remote, city, state and date.

<h3>Banco de Dados</h3>

O banco de dados utilizado foi o Postgres e a interface utilizada para melhor acompanhar os comando utilizados na aplicaçao foi o Postico.
-  <a href="https://www.youtube.com/watch?v=PShGF_udSpk&t=420s&pp=ygUdaG93IHRvIGluc3RhbGwgcG9zdGdyZXNxbCBtYWM%3D">How to Install PostgreSQL</a>
-  <a href="https://www.youtube.com/watch?v=7ROh8Mel6Cs&t=181s&pp=ygUaaG93IHRvIGluc3RhbGwgcG9zdGljbyBtYWM%3D">How to Install Postico</a>

<h2 id="related">📍 Licença</h2>

Este projeto está licenciado sob a licença MIT.
