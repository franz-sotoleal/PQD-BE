dependencies {
    implementation(project(":application"))

    api("org.springframework.boot:spring-boot-starter-web") {
        exclude(module = "spring-boot-starter-tomcat")
    }
}
