import depend.ProjectConfigurations

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    // hilt
    alias(libs.plugins.kaptVersions)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinter)
}
var ciVersionCode: Int = 1
var ciVersionName: String = "1.0.0-debug"

try {
    val readLines = file("../version.properties").readLines()
    ciVersionCode = readLines[0].toInt()
    ciVersionName = readLines[1]
    println("versionCode: $ciVersionCode ")
    println("ciVersionName: $ciVersionName ")
} catch (e: Exception) {
    println("please check you version.properties config ${e.message}")
}

android {
    namespace = "com.eason.ble"
    compileSdk = ProjectConfigurations.COMPILE_SDK
    defaultConfig {
        applicationId = "com.eason.ble"
        minSdk = ProjectConfigurations.MIN_SDK
        targetSdk = ProjectConfigurations.TARGET_SDK
        versionCode = ciVersionCode
        versionName = ciVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
        targetCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = ProjectConfigurations.TARGET_JAVA_VERSION.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = ProjectConfigurations.kotlinCompilerExtensionVersion
    }
    packaging {
        resources {
            excludes += "$projectDir/schemas"
            for (i in PackagingResource.resourceArray) {
                excludes += i
            }
        }
    }

    configurations.testImplementation {
        exclude(module = "logback-android")
    }
    sourceSets {
        getByName("main") {
            java.setSrcDirs(listOf("src/main/java"))
            kotlin.setSrcDirs(listOf("src/main/kotlin"))
        }
    }
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}

dependencies {

    val composeBom = platform(libs.compose.compose.bom)
    api(composeBom)
    androidTestImplementation(composeBom)
    api(libs.compose.ui)
    api(libs.compose.ui.tooling)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.foundation)
    api(libs.compose.material)
    api(libs.compose.material3)
    // compose debug
    debugImplementation(libs.compose.ui.tooling)
    // compose ui testing
    androidTestImplementation(libs.compose.ui.test)
    androidTestImplementation(libs.compose.ui.test.manifest)
    // compose other depends
    api(libs.compose.ui.activity.compose)
    api(libs.compose.lifecycle.viewmodel.compose)
    api(libs.compose.ui.runtime.livedata)
    api(libs.compose.ui.runtime.rxjava3)
    // material
    api(libs.material)
    // androidx
    api(libs.androidx.lifecycle.runtime.ktx)
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.androidx.constraintlayout)
    // room
    api(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    kapt(libs.androidx.room.compiler)
    api(libs.androidx.room.rxJava3)
    api(libs.androidx.room.testing)
    // hilt
    api(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)

}


// git commit check
tasks.check {
    dependsOn("installKotlinterPrePushHook")
}
// build check
kotlinter {
    ignoreFailures = false
    reporters = arrayOf("checkstyle", "plain")
}
tasks.preBuild {
    dependsOn("formatKotlin")
}