# Product Quality Dashboard API

To clone project 

```
git clone git@github.com:Kert944/PQD-BE.git 
```


# Project structure
```
adapters/          adapters for implementing outside communication
application/       core business logic
configuration/     spring boot module and all configurations, builds docker image
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

