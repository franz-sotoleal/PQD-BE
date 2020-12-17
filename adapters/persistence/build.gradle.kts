dependencies {
    implementation(project(":application"))

    runtimeOnly("org.postgresql:postgresql")

    implementation("org.apache.commons:commons-lang3")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen")
    implementation(group = "com.vladmihalcea", name = "hibernate-types-52", version = "2.9.10")
    implementation(group = "com.fasterxml.jackson.core", name = "jackson-databind", version = "2.11.0")

}
