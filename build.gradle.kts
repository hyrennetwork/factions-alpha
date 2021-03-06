plugins {
    kotlin("jvm") version "1.5.10"

    `maven-publish`
}

group = "net.hyren"
version = "0.1-ALPHA"

repositories {
    mavenCentral()

    maven("https://repository.hyren.net/") {
        credentials {
            username = System.getenv("MAVEN_USERNAME")
            password = System.getenv("MAVEN_PASSWORD")
        }
    }
}

dependencies {
    // kotlin
    compileOnly(kotlin("stdlib"))

    // paper-spigot-server
    compileOnly("org.github.paperspigot:paper-spigot-server:1.8.8-R0.1-SNAPSHOT")

    //exposed
    compileOnly("org.jetbrains.exposed:exposed-dao:0.31.1")

    // core
    compileOnly("net.hyren:core-shared:0.1-ALPHA")
    compileOnly("net.hyren:core-spigot:0.1-ALPHA")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    jar {
        archiveFileName.set("${project.name}.jar")
    }
}

val sources by tasks.registering(Jar::class) {
    archiveFileName.set(project.name)
    archiveClassifier.set("sources")
    archiveVersion.set(null as String?)

    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            repositories {
                maven("https://repository.hyren.net/") {
                    credentials {
                        username = System.getenv("MAVEN_USERNAME")
                        password = System.getenv("MAVEN_PASSWORD")
                    }
                }
            }

            from(components["kotlin"])
            artifact(sources.get())
        }
    }
}