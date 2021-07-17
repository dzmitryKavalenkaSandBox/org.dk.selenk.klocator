group = "org.dk.selenk"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm")
    `maven-publish`
}

repositories {
    mavenLocal()
    mavenCentral()
}

val sourcesJar by tasks.creating(Jar::class) {
    classifier = "sources"
    from(sourceSets["main"].allSource)
}

publishing {
    publications {
        register("mavenKotlin", MavenPublication::class) {
            groupId = group.toString()
            artifactId = rootProject.name
            from(components["kotlin"])
            artifact(sourcesJar)
        }
    }
}

dependencies {
    implementation(kotlin("stdlib", version = "${project.properties["kotlinVersion"]}"))
    implementation("io.appium:java-client:7.5.1")
    implementation("org.dk.selenk:common:1.0-SNAPSHOT")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.1.0")
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
}

tasks {
    test {
        useJUnitPlatform {  }
    }
}
