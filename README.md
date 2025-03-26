# Dokka 2 Markdown Plugin 

The aim of this project is to provide a replacement to the gfm and jekyll dokka plugins that were removed in dokka 2



### Applying the plugin

In order to apply this plugin it needs to be published to a repository.
For development, we can use mavenLocal.
This repository contains a basic setup for publishing artefacts to that repository.
In order to do it use `./gradlew publishToMavenLocal` task.

In order to apply the plugin you need to add it to project dependencies:
```kotlin
dependencies {
    dokkaPlugin("net.chrisfey:dokka-markdown-plugin:1.0.0-SNAPSHOT")
}
```

Please keep in mind, that you need to have a `mavenLocal()` repository in your project.