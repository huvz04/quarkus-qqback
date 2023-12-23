plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.allopen") version "1.9.21"
    id("io.quarkus")
    kotlin("plugin.serialization") version "1.9.21"
}

repositories {
//    maven { url = uri("https://repository.aspose.com/repo/") }
    mavenCentral()
    mavenLocal()

}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
dependencies {
    implementation("io.quarkus:quarkus-container-image-docker")
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-resteasy-reactive-links")
    // qute模板
    implementation("io.quarkus:quarkus-resteasy-reactive-qute")

    // https://mvnrepository.com/artifact/io.quarkus/quarkus-reactive-routes
    implementation("io.quarkus:quarkus-reactive-routes")


    // vertx-web
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-web:3.7.2")
    testImplementation("io.smallrye.reactive:smallrye-mutiny-vertx-web-client:3.7.2")
    implementation("io.quarkus:quarkus-vertx")

    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    testImplementation("io.quarkus:quarkus-junit5")

    implementation("io.quarkus:quarkus-websockets")
    implementation("io.quarkus:quarkus-kotlin")
    //由于整合到docker中存在兼容问题,同时没能找到好用方便的dockerFile 此依赖废除  换用selenium
    //implementation("io.github.fanyong920:jvppeteer:1.1.5")
    implementation("org.seleniumhq.selenium:selenium-java:4.16.1")
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:32.1.3-jre")

// https://mvnrepository.com/artifact/io.quarkus/quarkus-scheduler
    implementation("io.quarkus:quarkus-scheduler")
// https://mvnrepository.com/artifact/io.quarkus/quarkus-qute
    implementation("io.quarkus:quarkus-qute")



    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    //  https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.10.1")

    implementation("io.vertx:vertx-lang-kotlin-coroutines:4.5.1")
    implementation("io.smallrye.reactive:mutiny-kotlin:2.5.3")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-resteasy-reactive-kotlin-serialization")
    implementation("org.jetbrains.kotlin:kotlin-serialization")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("io.rest-assured:rest-assured")
}

group = "io.huvz"
version = "1.2-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}
tasks.quarkusBuild {
    nativeArgs {
        "container-build" to true
        "builder-image" to "quay.io/quarkus/ubi-quarkus-mandrel-builder-image:jdk-21"
    }
}
