plugins {
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.allopen") version "1.8.22"
    id("io.quarkus")
    `maven-publish`
}

group = "io.hasura"
version = "2.0.1"

repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url = uri("https://raw.githubusercontent.com/hasura/jvm-ndc-ir/m2repo")
    }
    maven {
        url = uri("https://raw.githubusercontent.com/hasura/jvm-ndc-sqlgen/m2repo")
    }
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    api("io.hasura:ndc-sqlgen:2.0.1")

    // NOTE: Changed "enforcedPlatform" to "platform" to allow overriding of versions in the Quarkus BOM
    // for vulnerabilities reported by Aquasec Trivy. This may have unintended consequences.
    api(platform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
    api("io.quarkus:quarkus-agroal")
    api("io.quarkus:quarkus-arc")
    api("io.quarkus:quarkus-cache")
    api("io.quarkus:quarkus-kotlin")
    api("io.quarkus:quarkus-micrometer-registry-prometheus")
    api("io.quarkus:quarkus-resteasy-reactive")
    api("io.quarkus:quarkus-resteasy-reactive-jackson")
    api("io.quarkus:quarkus-smallrye-fault-tolerance")
    api("io.quarkus:quarkus-smallrye-openapi")
    api("io.quarkus:quarkus-vertx")
    api("io.quarkus:quarkus-reactive-routes")
    api("io.quarkus:quarkus-logging-json")

    api("io.quarkus:quarkus-opentelemetry")
    api("io.opentelemetry:opentelemetry-extension-kotlin")
    api("io.opentelemetry:opentelemetry-extension-trace-propagators")
    api("io.opentelemetry.instrumentation:opentelemetry-jdbc")

    // [Last scanned: 04/19/2023]
    // Aquasec Trivy vulnerabilities reported, manual overrides
    api("io.vertx:vertx-web:4.3.8!!")
    api("org.yaml:snakeyaml:2.0!!")

    // Spring JDBC ScriptUtils used for loading .sql files
    api("org.springframework:spring-jdbc:6.0.8")

    // JWT
    api("org.bitbucket.b_c:jose4j:0.9.2")


    // //////////////////////
    // Test Dependencies
    // //////////////////////
    testImplementation("io.quarkus:quarkus-junit5")

    // RestAssured
    testImplementation("io.rest-assured:rest-assured")
    testImplementation("io.rest-assured:kotlin-extensions")

    // Testcontainers
    testImplementation(platform("org.testcontainers:testcontainers-bom:1.17.6"))
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:testcontainers")
}

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.quarkusDev.configure {
    jvmArgs = listOf(
        "-Djava.net.preferIPv4Stack=true",
        "-Djava.net.preferIPv4Addresses=true",
    )
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    kotlinOptions.javaParameters = true
}

java {
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/hasura/jvm-ndc-app")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("GH_USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("GH_TOKEN")
            }
        }
    }
}
