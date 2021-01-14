# Product Quality Dashboard API

Here you can read about the business use case of the API, how it was built and what to keep in mind while
implementing additional functionality.

## Purpose of the API
The PQD stands for Product Quality Dashboard, which is a system that I implemented during my master thesis at the
University of Tartu (Software Engineering curriculum).
In short, the idea is to read data from different static
analysis tools and visualize them all together in a dashboard to provide easier overview of the software product quality
metrics, extracted from various sources.
In addition, the system provides a quality level between 0 and 1 (or 0 - 100%) to
make it easier to track the changes of quality through time. The purpose from the beginning was to lay a foundation
and implement a minimum viable product (MVP) that can be later improved (after my thesis).
The MVP supports one tool, Sonarqube, and reads info about three quality characteristics from there.

## Technical description
The project is written using hexagonal architecture (aka ports and adapters pattern). The components are separated
by gradle modules.

At the center lies the core business logic, that doesn't know anything about the other modules.
It doesn't care where the data is coming or where it is going. The core business logic is implemented by use
cases instead of service classes. Basically, the methods that would typically be in the service classes are now each
a separate class with one concrete purpose. The use cases are executed with a request wrapper, and they respond with a
response wrapper. The gateway of an outgoing exposed port is omitted for executing the use cases,
because it does not improve the layer division. The use cases can be imported and executed in the adapters while keeping the
desired separation between layers. However, if the use cases need something from anywhere external of the core, they
ask it from a gateway of input interface that is implemented by some adapter.

The adapters implement the core layer, but do not see each other. This means that while both adapters A and B can
access resources from the application core, they cannot access each other. Each layer must map the data into the
layer objects, even if it means code duplication. For example, if core defines ReleaseInfo, then web adapter
maps it into ReleaseInfoResultJson, and persistence adapter maps it into ReleaseInfoEntity.

Keep in mind not to change the layer dependencies between each other, while developing new functionality. Adapters
cannot access or implement each other. They can only access the core layer. Keep writing unit tests to each adapter
and to the core module. Integration tests are written in the configuration module using MockMvc and Testcontainers.

### Directory structure:
```
adapters/          adapters for implementing outside communication (with unit tests)
    messaging/     rest api (one endpoint) for triggering release info collection, responsible for security
    persistence/   adapter for database connection (stores data)
    sonarqube/     adapter for sonarqube api connection (asks raw data)
    web/           rest api for web communication, responsible for security 
application/       core business logic (with unit tests)
configuration/     spring boot module and all configurations, builds docker image (with integration tests)
dev-scripts/       script to set up db 
```

### Database changelog
Flyway is used for database changelog. Migration files are located at:
`configuration/src/main/resources/db/migration`

_Not so relevant, if not deployed to production_: New change sets must be in order and prefixed with
```V<timestamp>__changename.sql```
Change sets that should run on every migration should be prefixed with ```R__changename.sql```


# Running Locally

To clone the project:

```
git clone git@github.com:Kert944/PQD-BE.git 
```

### Development environment setup
* JDK11 and set JAVA_HOME
* Docker (enable **Expose daemon on tcp...** from settings)
* IDEA plugins: Lombok
* Enable -> File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors

### Starting the API
* Run **PostgreSQL** in a Docker container
    * docker-compose file is provided to launch postgreSQL:
        * Can be either ran by right clicking on the manifest by 'Run ...'
        * Using command line (at the project root directory):
    
            * To run container:

                ```
                docker-compose -f dev-scripts/docker/postgresql.yml up -d
                ```

            * To kill container:
                ```
                docker-compose -f dev-scripts/docker/postgresql.yml down
                ```
* Run the **API** 
    * from configuration/src/main/java/com/pqd/BackendApplication.java
* _Optional_: Run **Sonarqube** locally to test release info collection:
    * https://docs.sonarqube.org/latest/setup/get-started-2-minutes/ (I used the zip file)
        * Follow the instructions on the Sonarqube webpage to install and analyze a (java) project
            * Use component name (project key) _ESI-builtit_ so that you can trigger release info collection with the 
              test user 
              that is already inserted into the DB
              

# Using the API
API Swagger (majority of requests need authorization with Bearer token, jwt)
```
http://localhost:8080/swagger-ui/index.html#/
```
Triggering release info collection (needs PQD API token): 
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
* The endpoint triggers asynchronous data collection, meaning that you get status 200 if the request passes the 
  controller - it doesn't indicate that the collection was successful (this runs on a different thread)
