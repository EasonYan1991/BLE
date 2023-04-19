@file:JvmName("AndroidXDependencies")

package depend

object AndroidX {
    const val lifecycleRuntimeKtx =
        "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
    private const val CORE_KTX_VERSION = "1.9.0"
    const val coreKtx = "androidx.core:core-ktx:$CORE_KTX_VERSION"
    const val appcompat = "androidx.appcompat:appcompat:1.5.1"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
    const val junit = "androidx.test.ext:junit:1.1.5"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"

    // Room
    private const val room = "2.5.1"

    // def roomVersion = "2.5.1"
    const val roomRuntime = "androidx.room:room-runtime:$room"
    const val roomCompiler = "androidx.room:room-compiler:$room"

    // To use Kotlin annotation processing tool (kapt)
    const val roomKaptCompiler = "androidx.room:room-compiler:$room"

    // optional - RxJava3 support for Room
    const val roomRxjava3 = "androidx.room:room-rxjava3:$room"

    // optional - Paging 3 Integration
    // implementation "androidx.room:room-paging:$room_version"
    // Room optional - Test helpers
    const val roomTesting = "androidx.room:room-testing:$room"
}

object Compose {
    const val kotlinCompilerExtensionVersion = "1.4.5"

    // androidx.compose:compose-bom
    const val composeBom = "androidx.compose:compose-bom:2023.03.00"

    // Choose one of the following:
    // Material Design 3
    const val material3 = "androidx.compose.material3:material3"
    const val material =  "androidx.compose.material:material"
//    const val materialIconsCore = "androidx.compose.material:material-icons-core"
//    const val materialIconsExtended = "androidx.compose.material:material-icons-extended"
//    const val material3WindowSizeClass = "androidx.compose.material3:material3-window-size-class"

    // or skip Material Design and build directly on top of foundational components
    const val foundational = "androidx.compose.foundation:foundation"

    // or only import the main APIs for the underlying toolkit systems,
    // such as input and measurement/layout
    const val ui = "androidx.compose.ui:ui"

    // Android Studio Preview support
    const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val uiTooling = "androidx.compose.ui:ui-tooling"

    // UI Tests
    const val uiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
    const val uiTestManifest = "androidx.compose.ui:ui-test-manifest"

    // Optional - Included automatically by material, only add when you need
    // the icons but not the material library (e.g. when using Material3 or a
    // custom design system based on Foundation)
    // Optional - Integration with activities
    const val activityCompose =
        "androidx.activity:activity-compose:1.7.0"

    // Optional - Integration with ViewModels
    const val lifecycleViewmodelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1"

    // Optional - Integration with LiveData
    const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata"

    // Optional - Integration with RxJava
    const val runtimeRxjava3 = "androidx.compose.runtime:runtime-rxjava3"
}
