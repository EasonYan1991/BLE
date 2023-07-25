pluginManagement {
    apply(from = file("BuildLibs.gradle.kts"))
    val gradleVersion: String by extra
    val mDependenciesKotlinTest: String by extra
    println("============pluginManagement=============$gradleVersion=====")
    println("============pluginManagement=============$mDependenciesKotlinTest=====")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
//    resolutionStrategy.eachPlugin {
//        when (requested.id.namespace) {
//            "com.android.application" -> {
//                useVersion(comAndroidApplication)
//                apply(false)
//            }
//    }

}
println("============settings.gradle.kts==================")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "BLE"
include(":app",":buildTools")
include(":bleCore")
include(":foundation")
include(":foundationCore")
