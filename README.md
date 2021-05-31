# goku app
###TLDR
Microservice para gerenciar clientes e endereços para um restaurante fictício

## Design da Solução


## Detalhamento dos Endpoints 

###POST /register
####Role: ALL
####Descrição: registro de usuário/cliente no sistema; id de retorno pode ser usado para obter dados do cliente;
####PayLoad:
```
{
    "username":"User name",
    "password": "Password", 
    "name": "Customer name"
}
```
####Response PayLoad:
```
{
    "username":"User name",
    "password": "******",
    "name": "Customer name",
    "id": "User Id"
}
```
###POST /admin-register
####Descrição: registro de usuário administrador;
####Role: ADMIN
####PayLoad:
```
{
    "username":"User name",
    "password": "Password", 
}
```
####Response PayLoad:
```
{
    "username":"User name",
    "password": "******",
}
```

###POST /login
####Descrição: login / obtenção de token JWT
####Role: ALL
####PayLoad:
```
{
    "username":"User name",
    "password": "Password" 
}
```
####Response PayLoad:
```
{
    "user": "User name",
    "token": "Bearer <JWT token>"
}
```

###GET /customer/{id}
####Descrição: obtém dados do customer de id informado na URL
####Role: USER
####Response Payload:
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

###PUT /customer/{id}
####Descrição: Permite modificar dados do customer e incluir/modificar endereços;
####Role: USER, ADMIN
####Input & Response payloads:
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
###GET /address/{postal-code}
####Descrição: obtém os dados do endereço correspondente ao postal-code informado
####ROLE: ADMIN
####Input & Response payloads:
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

###POST /address/{postal-code}
####Descrição: cria um novo endereço
####ROLE: ADMIN
####Input & Response payloads:
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