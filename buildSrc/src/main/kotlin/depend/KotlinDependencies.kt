@file:JvmName("KotlinDependencies")

package depend

object KotlinDependencies {
    // Kotlin
    const val kotlinVersion = "1.8.20"
    private const val Coroutine = "1.7.0-Beta"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
    const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$Coroutine"
    const val kotlinxCoroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:$Coroutine"
    const val kotlinxCoroutinesJdk8 = "org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$Coroutine"

    // coroutines + Rxjava
    const val kotlinxCoroutinesRx3 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx3:$Coroutine"
}
