plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jetbrains.dokka") version "2.0.0" // Used to create a javadoc jar
    `maven-publish`
    signing
}

group = "net.chrisfey"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val dokkaVersion: String by project
dependencies {
    compileOnly("org.jetbrains.dokka:dokka-core:$dokkaVersion")
    implementation("org.jetbrains.dokka:dokka-base:$dokkaVersion")
    implementation("org.jetbrains.dokka:gfm-plugin:$dokkaVersion")

    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.dokka:dokka-test-api:$dokkaVersion")
    testImplementation("org.jetbrains.dokka:dokka-base-test-utils:$dokkaVersion")
}

kotlin {
    jvmToolchain(8)
}

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
}

val javadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}

java {
    withSourcesJar()
}

publishing {
    publications {
        val dokkaMarkdownPlugin by creating(MavenPublication::class) {
            artifactId = "dokka-markdown-plugin"
            from(components["java"])
            artifact(javadocJar)

            pom {
                name.set("Dokka Markdown plugin")
                description.set("This is a plugin for Dokka 2 that supports Markdown")
                url.set("https://github.com/chrisfey/dokka-markdown-plugin/")

                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }

                developers {
                    developer {
                        name.set("Chris Fey")
                        organizationUrl.set("https://chrisfey.net")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/chrisfey/dokka-markdown-plugin.git")
                    url.set("https://github.com/chrisfey/dokka-markdown-plugin/tree/master")
                }
            }
        }
        signPublicationsIfKeyPresent(dokkaMarkdownPlugin)
    }

    repositories {
        maven("https://oss.sonatype.org/service/local/staging/deploy/maven2/") {
            credentials {
                username = System.getenv("SONATYPE_USER")
                password = System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

fun Project.signPublicationsIfKeyPresent(publication: MavenPublication) {
    val signingKeyId: String? = System.getenv("SIGN_KEY_ID")
    val signingKey: String? = System.getenv("SIGN_KEY")
    val signingKeyPassphrase: String? = System.getenv("SIGN_KEY_PASSPHRASE")

    if (!signingKey.isNullOrBlank()) {
        extensions.configure<SigningExtension>("signing") {
            if (signingKeyId?.isNotBlank() == true) {
                useInMemoryPgpKeys(signingKeyId, signingKey, signingKeyPassphrase)
            } else {
                useInMemoryPgpKeys(signingKey, signingKeyPassphrase)
            }
            sign(publication)
        }
    }
}
