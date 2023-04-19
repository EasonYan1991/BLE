@file:JvmName("GoogleDependencies")

package depend

object GoogleDependencies {
    const val material = "com.google.android.material:material:1.6.1"
    const val gson = "com.google.code.gson:gson:2.10.1"
    const val hiltVersion = "2.45"

    // Hilt
    // https://developer.android.com/training/dependency-injection/hilt-android
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:$hiltVersion"

    // kapt
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$hiltVersion"
    const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"

    // kapt
    const val hiltCompiler = "com.google.dagger:hilt-compiler:$hiltVersion"
}
