import depend.ProjectConfigurations
import org.jetbrains.kotlin.ir.backend.js.compile

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "com.eason.blecore"
    compileSdk = ProjectConfigurations.COMPILE_SDK
    compileOptions {
        sourceCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
        targetCompatibility = ProjectConfigurations.TARGET_JAVA_VERSION
    }
    kotlinOptions {
        jvmTarget = ProjectConfigurations.TARGET_JAVA_VERSION.toString()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    api(libs.rxandroidble3.rxandroidble)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}