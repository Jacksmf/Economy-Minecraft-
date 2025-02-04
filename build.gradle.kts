plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.untistore.economy"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        name = "spigotmc-repo"
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "enginehub"
        url = uri("https://maven.enginehub.org/repo/")
    }
}

dependencies {
    // Spigot API (replace 1.21 with the latest stable version if needed)
    implementation("org.spigotmc:spigot-api:1.21-R0.1-SNAPSHOT")
    compileOnly("com.sk89q.worldedit:worldedit-core:7.3.9")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.9")

    implementation("com.zaxxer:HikariCP:5.0.1")
    implementation("mysql:mysql-connector-java:8.0.33")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("org.xerial:sqlite-jdbc:3.42.0.1")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    test {
        useJUnitPlatform()
    }

    // Configure shadowJar task
    shadowJar {
        archiveClassifier.set("") // Avoid "-all" suffix
        archiveFileName.set("UntiEco-${version}.jar") // Output jar name
        mergeServiceFiles() // Handle service files if needed
        dependencies {
            include(dependency("com.zaxxer:HikariCP"))
            include(dependency("mysql:mysql-connector-java"))
            include(dependency("com.fasterxml.jackson.core:jackson-databind"))
            include(dependency("org.xerial:sqlite-jdbc"))
        }
    }
}
