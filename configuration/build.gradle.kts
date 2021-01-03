plugins {
    id("org.springframework.boot")
    id("org.flywaydb.flyway")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":adapters:web"))
    implementation(project(":adapters:persistence"))
    implementation(project(":adapters:sonarqube"))

    implementation("org.flywaydb:flyway-core")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.cloud:spring-cloud-starter:2.2.2.RELEASE")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:postgresql:1.15.1")
    testImplementation("org.testcontainers:junit-jupiter:1.15.1")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/pqd"
    user = "postgres"
    password = ""
}
