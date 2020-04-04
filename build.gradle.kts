import java.nio.file.Files


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

fun doIfFileIsMissing(fileName: String, saveDir: String, lambda: (File) -> Unit) {
    val folder = File(saveDir)
    if (!folder.exists()) {
        folder.mkdirs()
    }
    val file = File("$saveDir/$fileName")
    if (!file.exists()) {
        lambda(file)
    }
}

fun downloadResourceFromUrl(fileName: String, fileUrl: String, saveDir: String) {
    doIfFileIsMissing(fileName, saveDir) {
        ant.invokeMethod("get", mapOf("src" to fileUrl, "dest" to it))
    }
}

fun downloadExecInNewTerminal(version: String) {
    val resourcesDir = "src/main/resources"
    val fileName = "exec-in-new-terminal-$version"
    doIfFileIsMissing(fileName, resourcesDir) {
        val tarFileName = "$fileName.tar.gz"
        val url = "https://github.com/FlamingTuri/exec-in-new-terminal/releases/download/v$version/$tarFileName"
        downloadResourceFromUrl(tarFileName, url, resourcesDir)
        // untar
        val tarFile = File("$resourcesDir/$tarFileName")
        copy {
            from(tarTree(resources.gzip(tarFile)))
            into(resourcesDir)
        }
        tarFile.delete()
    }
    // remove exec-in-new-terminal directories which version differs from the specified one
    Files.walk(File(resourcesDir).toPath())
        .map { it.toFile() }
        .filter {
            it.isDirectory && !it.name.endsWith(version) &&
                it.name.startsWith("exec-in-new-terminal-")
        }
        .forEach { delete(it.absolutePath) }
}

dependencies {
    implementation("org.reflections:reflections:0.9.12")
    implementation("org.apache.commons:commons-lang3:3.9")
    val vertxVersion = "3.8.0"
    api("io.vertx:vertx-core:$vertxVersion")
    api("io.vertx:vertx-web-client:$vertxVersion")
    api("io.vertx:vertx-mqtt:$vertxVersion")
    downloadExecInNewTerminal("1.0.1")

    val junitVersion = "5.3.1"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
}

// shadowJar outputs inside build/lib
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
