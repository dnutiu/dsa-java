plugins {
    id("java")
}

group = "com.nuculabs.dev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.testng:testng:7.1.0")
    testImplementation("junit:junit:4.13.1")
}

tasks.test {
    useJUnitPlatform()
}