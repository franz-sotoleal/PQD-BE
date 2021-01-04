# Product Quality Dashboard API

To clone the project: 

```
git clone git@github.com:Kert944/PQD-BE.git 
```
API Swagger (web adapter)
```
http://localhost:8080/swagger-ui/index.html#/
```
Requests through the web adapter are protected by jwt, except login and register.

# Project structure
```
adapters/          adapters for implementing outside communication (with unit tests)
application/       core business logic (with unit tests)
configuration/     spring boot module and all configurations, builds docker image (with integration tests)
dev-scripts/       common scripts to set up db 
```

### Development environment setup
* JDK11 and JAVA_HOME set
* Docker (enable **Expose daemon on tcp...** from settings)
* IDEA plugins: Lombok
* Enable -> File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors

### Third party services
* PostgreSQL - backend database. (**required**)

docker-compose file is provided to launch postgreSQL: 
* Can be either ran by right clicking on the manifest by 'Run ...'
* Using command line:
```
docker-compose -f dev-scripts/docker/postgresql.yml up -d
```
To kill containers
```
docker-compose -f dev-scripts/docker/postgresql.yml down
```

### Database changelog
Flyway is used for database changelog. Migration files are located at:
`configuration/src/main/resources/db/migration`

New change sets must be in order and prefixed with ```V<timestamp>__changename.sql```
Change sets that should run on every migration should be prefixed with ```R__changename.sql```

# Using the API
Triggering release info collection: 
```
http://localhost:8080/api/messaging/trigger?productId=<product_id>
```
* Release info collection can be started with post request (url above) to messaging controller
* The endpoint requires basic authentication with token: 
    * In Postman, simply put the token to the username field and postman does the rest
    * If sending manually, then
        * Add colon ':' to the end of the token
        * Encrypt the token to Base64 
          * Javascript encrypting example: btoa("<your_product_token>:"))
        * Add the encrypted token to authorization header
          * add word "Basic" before the token
            * example: "Basic ODI1N2N..."
* The endpoint triggers asynchronous data collection
