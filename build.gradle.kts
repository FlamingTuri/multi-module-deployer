plugins {
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.9")

    val junitVersion = "5.3.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}
