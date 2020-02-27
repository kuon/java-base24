import com.jfrog.bintray.gradle.BintrayExtension
import com.jfrog.bintray.gradle.BintrayPlugin

val projectGroup = "ch.kuon.commons"
// Also update version in README.md
val projectVersion = "0.1.1"
val projectName = "base24"

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.3.61"

    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    // Create maven artefacts
    `maven-publish`

    // Bintray for publication
    id("com.jfrog.bintray") version "1.8.4"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

tasks {
    withType(Test::class.java) {
        dependsOn("cleanTest")
        testLogging.showStandardStreams = true
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            groupId = projectGroup
            artifactId = projectName
            version = projectVersion
        }
    }
}


bintray {
    user = System.getenv("BINTRAY_USERNAME")
    key = System.getenv("BINTRAY_API_KEY")
    publish = true
    setPublications("maven")
    pkg(delegateClosureOf<BintrayExtension.PackageConfig> {
        repo = "java"
        name = "base24"
        userOrg = "kuon"
        websiteUrl = "https://github.com/kuon/java-base24"
        vcsUrl = "https://github.com/kuon/java-base24.git"
        githubRepo = "kuon/java-base24"
        description = "Base24 encoder and decoder for java written in Kotlin"
        setLabels("kotlin")
        setLicenses("MIT", "Apache-2.0")
        desc = description
        publicDownloadNumbers = true
        version(delegateClosureOf<BintrayExtension.VersionConfig> {
            name = projectVersion
        })
    })
}
