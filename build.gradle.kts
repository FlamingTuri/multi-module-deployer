plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

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
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web-client:$vertxVersion")
    implementation("io.vertx:vertx-mqtt:$vertxVersion")

    val junitVersion = "5.3.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

tasks.shadowJar.configure {
    // removes "-all" from the jar name
    archiveClassifier.set("")
}
