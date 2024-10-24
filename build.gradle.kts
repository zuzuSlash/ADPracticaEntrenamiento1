plugins {
    id("java")
}

group = "ies.julian"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.apache.derby:derby:10.16.1.1")
    implementation("org.jasypt:jasypt:1.9.3")
}

tasks.test {
    useJUnitPlatform()
}