import depend.ProjectConfigurations

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.eason.foundation.core"
    compileSdk = ProjectConfigurations.COMPILE_SDK

    defaultConfig {
        minSdk = ProjectConfigurations.TARGET_SDK

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        externalNativeBuild {
            cmake {
                cppFlags("-std=c++17")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    compileOptions {
        sourceCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
        targetCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
    }

    kotlinOptions {
        jvmTarget = ProjectConfigurations.TARGET_JAVA_VERSION.toString()
    }
}

dependencies {
    // coroutines
    api(libs.kotlinx.coroutines.core)
    api(libs.kotlinx.coroutines.android)
    api(libs.kotlinx.coroutines.jdk8)
    api(libs.kotlinx.coroutines.rxjava3)

    api(libs.reactivex.rxjava3)
    api(libs.glide)
    api(libs.okhttp3)
    api(libs.okhttp3.logging.interceptor)
    api(libs.okhttp3.mockwebserver)
    api(libs.retrofit)
    api(libs.retrofit.converter.gson)
    api(libs.retrofit.adapter.rxjava3)
    api(libs.slf4j)
    api(libs.logback.android)
    api(libs.logback.classic)


    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)
    api(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}