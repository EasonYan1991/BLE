@file:JvmName("ProjectConfigurations")

package depend

import org.gradle.api.JavaVersion


object ProjectConfigurations {
    // Android sdk
    // compileSdk
    const val COMPILE_SDK = 33

    // minSdk
    const val MIN_SDK = 26

    // targetSdk
    const val TARGET_SDK = 33

    val TARGET_JAVA_VERSION = JavaVersion.VERSION_17

    const val kotlinCompilerExtensionVersion = "1.4.5"

}