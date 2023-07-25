import depend.ProjectConfigurations
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
//    alias(libs.plugins.kotlinJvm) apply false
    id("org.jetbrains.kotlin.jvm") apply false
    application
    `kotlin-dsl`
}
var ciVersionCode: Int = 1
var ciVersionName: String = "1.0.0-debug"
application {
    mainClass.set("BuildTools")
}

try {
    val readLines = file("../version.properties").readLines()
    ciVersionCode = readLines[0].toInt()
    ciVersionName = readLines[1]
    println("versionCode: $ciVersionCode ")
    println("ciVersionName: $ciVersionName ")
} catch (e: Exception) {
    println("please check you version.properties config ${e.message}")
}

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
    targetCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = ProjectConfigurations.TARGET_JAVA_VERSION.toString()
    }
}

//
//var ciVersionCode: Int = 1
//var ciVersionName: String = "1.0.0-debug"
//
//try {
//    val readLines = file("../version.properties").readLines()
//    ciVersionCode = readLines[0].toInt()
//    ciVersionName = readLines[1]
//    println("versionCode: $ciVersionCode ")
//    println("ciVersionName: $ciVersionName ")
//} catch (e: Exception) {
//    println("please check you version.properties config ${e.message}")
//}

dependencies {
    compileOnly(libs.kotlin.stdlib)
    testCompileOnly(libs.junit)
}