dependencies {
    implementation(project(":application"))

    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation(group = "io.jsonwebtoken", name = "jjwt", version = "0.9.1")

}
