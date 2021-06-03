# goku app

## TLDR
Microservice para gerenciar clientes e endereços para um restaurante fictício; 
* Credenciais do usuário admin pré-cadastrado: admin/admin;
* Rodar mysql e hazelcast: docker-compose up na pasta goku-backend;
* Valores de configuração podem ser injetados via variáveis de ambiente; ver arquivo config.properties; 

## Design da Solução
A solução para o teste envolveu a criação de um microserviço usando Java e SpringBoot e banco MySQL;
Os pacotes foram organizados de acordo com a arquitetura de "ports & adapters" ou arquitetura hexagonal, 
onde a camada mais interna representa o domínio do problema e as interfaces correspondentes às "ports", 
a camada de infrastructure provê os adaptadores para componentes externos à aplicação, no caso o banco de dados MySQL e  
a camada de application fornece a interface p/ interação com a aplicação a partir do mundo exterior (controladores REST).

#### Database:
Para fins de simplificação, foi usado o mecanismo de schema/data do SpringBoot para criação do schema e pré-população de dados; 
Para um app real, a recomendação seria usar algo mais robusto, como Liquibase ou Flyway

#### Cache:
A implementação de cache via hazelcast está incompleta; É possível ter uma idéia do design da solução olhando 
a classe Cache CacheCityRepository e na classe de config. CacheConfig. Infelizmente a versão usada não suporta Optional.
Tentei registrar um serializador customizado mas acabou não funcionando :(    

#### Busca por CEP
A solução provê um endpoint para cadastro e busca de endereços; Obviamente, para uma aplicação real, precisaríamos 
estudar a viabilidade de acessar uma API externa de busca no correios ou serviço correspondente.
No entato, a soluçao foi pensada para que não fosse dependente desse serviço externo, permitido cadastro manual 
em caso de indisponibilidade; 

## Detalhamento dos Endpoints 

### POST /register
* Role: ALL
* Descrição: registro de usuário/cliente no sistema; id de retorno pode ser usado para obter dados do cliente;
* PayLoad:
```
{
    "username":"User name",
    "password": "Password", 
    "name": "Customer name"
}
```
* Response PayLoad:
```
{
    "username":"User name",
    "password": "******",
    "name": "Customer name",
    "id": "User Id"
}
```
### POST /admin-register
* Descrição: registro de usuário administrador;
* Role: ADMIN
* PayLoad:
```
{
    "username":"User name",
    "password": "Password", 
}
```
* Response PayLoad:
```
{
    "username":"User name",
    "password": "******",
}
```

### POST /login
* Descrição: login / obtenção de token JWT
* Role: ALL
* PayLoad:
```
{
    "username":"User name",
    "password": "Password" 
}
```
* Response PayLoad:
```
{
    "user": "User name",
    "token": "Bearer <JWT token>"
}
```

### GET /customer/{id}
* Descrição: obtém dados do customer de id informado na URL
* Role: USER
* Response Payload:
```{
    "id": "Customer Id",
    "name": "Customer name",
    "addresses": [
        {
            "name": "Street name",
            "postalCode": "Postal Code",
            "city": {
                "id": "City Id",
                "name": "City Name",
                "stateName": "State Name",
                "stateAbbreviation": "State Abbreviation"
            },
            "number": <Address Number>,
            "description": "Address description"
        }
    ]
}
```

### PUT /customer/{id}
* Descrição: Permite modificar dados do customer e incluir/modificar endereços;
* Role: USER, ADMIN
* Input & Response payloads:
```
{
    "id": "Customer Id",
    "name": "Customer name",
    "addresses": [
        {
            "name": "Street name",
            "postalCode": "Postal Code",
            "city": {
                "id": "City Id",
                "name": "City Name",
                "stateName": "State Name",
                "stateAbbreviation": "State Abbreviation"
            },
            "number": <Address Number>,
            "description": "Address description"
        }
    ]
}
```
### GET /address/{postal-code}
* Descrição: obtém os dados do endereço correspondente ao postal-code informado
* ROLE: ADMIN
* Input & Response payloads:
```
{
    "name": "<postal-code>",
    "postalCode": "Rua XXXX",
    "city": {
        "id": "1361b630-edd6-4f09-a680-ca0d16454213",
        "name": "Taubaté",
        "stateName": "São Paulo",
        "stateAbbreviation": "SP"
    }
}
```

### POST /address/{postal-code}
* Descrição: cria um novo endereço
* ROLE: ADMIN
* Input & Response payloads:
```
{
    "name": "<postal-code>",
    "postalCode": "Rua XXXX",
    "city": {
        "id": "1361b630-edd6-4f09-a680-ca0d16454213",
        "name": "Taubaté",
        "stateName": "São Paulo",
        "stateAbbreviation": "SP"
    }
}
```
