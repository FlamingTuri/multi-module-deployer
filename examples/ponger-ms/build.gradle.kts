plugins {
    `java-library`
    application
}

repositories {
    jcenter() 
}


version = "0.1.0"

application {
    mainClassName = "com.example.Launcher"
}

repositories {
    mavenCentral()
}

var vertxVersion = "3.8.0"

dependencies {
    api("org.apache.commons:commons-math3:3.6.1") 

    implementation("com.google.guava:guava:27.0.1-jre") 

    testImplementation("junit:junit:4.12")
    
    implementation("io.vertx:vertx-core:$vertxVersion")
    implementation("io.vertx:vertx-web:$vertxVersion")
}
