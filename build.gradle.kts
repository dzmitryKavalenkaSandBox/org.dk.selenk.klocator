group = "org.dk"
version = "1.0-SNAPSHOT"

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib", version = "${project.properties["kotlinVersion"]}"))
    implementation(files("/Users/dzmitry_kavalenka/selenk.common/build/libs/org.dk.selenk.common-1.0-SNAPSHOT.jar"))
    implementation("io.appium:java-client:7.5.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.1.0")
    testImplementation("io.mockk:mockk:1.11.0")
    testImplementation("org.hamcrest:hamcrest-all:1.3")
}
