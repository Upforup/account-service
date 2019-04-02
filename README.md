# Account Service App

This application implements a RESTful api for money transfers between accounts.

There are two methods exposed to the API:

 - get account info by account number (GET) ```api/account/:account-number```
 - make transfer between two accounts (PUT/JSON) ```api/transfer/```
   accepted JSON format:
    ```
    {
       "sourceNumber": "408717001",
       "targetNumber": "408717002",
       "amount": 1000
   }
    ```
 
 
Features for account management are not in the scope and so haven't been implemented.
In order to demonstrate implemented features, the application will create 3 accounts during startup, their numbers are:

- 408717001
- 408717002
- 408717003
 
### Transfer operation

Transfer operation consists of two steps: 

 - get account info by account number: this method will return the token required for transfer. 
    Token represents state of account at some point in time. (Optimistic locking)
 - make transfer between two accounts: Client must provide acquired token on step 1 in header "If-Match". 
    Operation will be performed only in case if token matches the value calculated on the server side.

### Architecture
This project is divided into 3 layers (modules):
- account-service-core: this module defines all the business logic and interfaces for interacting with other layers, has no dependencies on other modules and has mostly zero external dependencies
- account-service-data: this module is responsible for interacting with storage (H2 in this case, but can be replaced)
- account-service-app:  this module is responsible for application logic: creates server, defines routes, manages DI etc
App dependency diagram: account-service-app -> account-service-data -> account-service-core


### Data Model:
- Account (id, accountNumber, balance, dateOpen)
- Account Movement  (direction, amount, dateCreated, operation)
- Operation (sourceAccount, targetAccount, amount, dateCreated)

Each transfer will produce Operation instance which will contain two movements (with different directions), one for each party of operation.

### Build and run 

#### Build:

```
gradlew build
```


#### Run:

```
gradlew run
```

Or if You prefer running packaged jar: it is located in the build directory of ```account-service-app``` 
just check ```/libs``` or ```/distributions```


### Demo

You can find integration tests under the directory: ```account-service-app/integration-test```

### Used Libraries and Technologies
- Java 8
- Gradle
- Jooq for data access 
- Hikari datasource
- H2 In-memory DB
- Lombok for boilerplate
- Liquibase for migrations
- Javalin for web/server
- Guice for DI
- Guava for hashing



