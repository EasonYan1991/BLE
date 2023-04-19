pluginManagement {
    plugins {
//        val MyTestConfig: String by settings
//        val MyTestConfig: String by depend.GradleDependencies.version
//        id("com.android.library") version String.format("${depend.GradleDependencies.version}") apply false
//        id("com.android.library") version("${MyTestConfig}") apply false
//        id 'org.jetbrains.kotlin.android' version '1.8.20' apply false
//
//        id "org.jetbrains.kotlin.jvm" version '1.8.20' apply false
//        // hilt
//        id 'com.google.dagger.hilt.android' version '2.45' apply false
//        // Kotlin lint
//        // https://github.com/jeremymailen/kotlinter-gradle
//        id "org.jmailen.kotlinter" version '3.14.0' apply false
//        // https://github.com/ben-manes/gradle-versions-plugin
//        // discover the version to update
//        // gradle dependencyUpdates -Drevision=release
//        id "com.github.ben-manes.versions" version '0.46.0' apply false
    }

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {

    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "BLE"
include("app", "build-tools")
//include ':app', ':build-tools'
//rootProject.name = "BLE"