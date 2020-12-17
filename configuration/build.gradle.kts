plugins {
    id("org.springframework.boot")
    id("org.flywaydb.flyway")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":adapters:web"))
    implementation(project(":adapters:persistence"))

    implementation("org.flywaydb:flyway-core")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.testcontainers:postgresql:1.14.2")
    testImplementation("org.testcontainers:junit-jupiter:1.14.2")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/pqd"
    user = "postgres"
    password = ""
}
