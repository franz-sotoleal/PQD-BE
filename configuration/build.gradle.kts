plugins {
    id("org.springframework.boot")
    id("org.flywaydb.flyway")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":adapters:web"))

    implementation("org.flywaydb:flyway-core")

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // TODO starting from here, move the dependencies to persistance adapter
    runtimeOnly("org.postgresql:postgresql")

    implementation("org.apache.commons:commons-lang3")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen")
    implementation(group = "com.vladmihalcea", name = "hibernate-types-52", version = "2.9.10")
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.11.0")
    // until here

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.testcontainers:postgresql:1.14.2")
    testImplementation("org.testcontainers:junit-jupiter:1.14.2")
}

flyway {
    url = "jdbc:postgresql://localhost:5432/pqd"
    user = "postgres"
    password = ""
}
