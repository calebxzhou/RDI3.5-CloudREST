import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.4"
    id("io.spring.dependency-management") version "1.0.14.RELEASE"
    id ("org.jetbrains.kotlin.plugin.noarg" )version "1.7.20"
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.spring") version "1.7.20"
}

group = "calebzhou.rdi.microservice"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}
apply(plugin = "kotlin-noarg")
noArg {
    annotation("calebzhou.rdi.microservice.annotation.NoArg")
    invokeInitializers = true
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web"){
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    //用undertow比tomcat要快
    implementation("org.springframework.boot:spring-boot-starter-undertow")
// https://mvnrepository.com/artifact/mysql/mysql-connector-java
    implementation("mysql:mysql-connector-java:8.0.30")
// https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
// https://mvnrepository.com/artifact/com.ip2location/ip2location-java
    implementation("com.ip2location:ip2location-java:8.9.1")
// https://mvnrepository.com/artifact/org.springframework/spring-context-indexer
    // https://mvnrepository.com/artifact/it.unimi.dsi/fastutil
    implementation("it.unimi.dsi:fastutil:8.5.9")
// https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.2.2")
// https://mvnrepository.com/artifact/org.lionsoul/ip2region
    implementation("org.lionsoul:ip2region:2.6.5")
    implementation( "io.github.microutils:kotlin-logging-jvm:3.0.0")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {

        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.withType<Jar>() {

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

   /* manifest {
        attributes["Main-Class"] = "MainKt"
    }*/

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}