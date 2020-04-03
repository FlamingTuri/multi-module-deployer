plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "5.2.0"
    `maven-publish`
    signing
}

group = "multi.module.deployer"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.9")
    val vertxVersion = "3.8.0"
    api("io.vertx:vertx-core:$vertxVersion")
    api("io.vertx:vertx-web-client:$vertxVersion")
    api("io.vertx:vertx-mqtt:$vertxVersion")

    val junitVersion = "5.3.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.shadowJar.configure {
    // removes "-all" from the jar name
    archiveClassifier.set("")
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/FlamingTuri/multi-module-deployer")
            credentials {
                username = "*"
                password = "*"
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = "multi-module-deployer"
            groupId = project.group as String?
            version = project.version as String?
            from(components["java"])
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
