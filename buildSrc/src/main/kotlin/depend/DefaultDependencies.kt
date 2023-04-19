@file:JvmName("DefaultDependencies")

package depend


object Versions {
    // Android sdk
    // compileSdk
    const val COMPILE_SDK = 33

    // minSdk
    const val MIN_SDK = 26

    // targetSdk
    const val TARGET_SDK = 33


}

//fun ConfigurationContainer.forceResolutions() {
//    all {
//        resolutionStrategy.eachDependency {
//            when (requested.group) {
////                "androidx.annotation " -> useVersion(dependecies.Versions.SUPPORT_ANNOTATIONS)
//                "org.jetbrains.kotlin" -> {
//                    if (requested.name == "kotlin-stdlib") {
//                        useVersion(dependecies.Versions.KOTLIN_VERSION)
//                    }
//                }
//            }
//        }
//    }
//}
