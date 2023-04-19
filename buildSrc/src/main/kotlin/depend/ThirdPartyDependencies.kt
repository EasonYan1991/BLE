@file:JvmName("ThirdPartyDependencies")

package depend

object ThirdPartyDependencies {

    const val junit = "junit:junit:4.13.2"

    // RxAndroidBle
    const val rxAndroidBle = "com.polidea.rxandroidble3:rxandroidble:1.17.2"

    // RxJava3
    const val rxJava = "io.reactivex.rxjava3:rxjava:3.1.6"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:4.15.1"

    //  okhttp https://github.com/square/okhttp
    // define any required OkHttp artifacts without version
    private const val OKHTTP = "4.10.0"
    const val okhttp3 = "com.squareup.okhttp3:okhttp:$OKHTTP"
    const val okhttp3LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:$OKHTTP"
    const val okhttp3MockWebServer = "com.squareup.okhttp3:mockwebserver:$OKHTTP"

    // https://github.com/square/retrofit
    // retrofit
    private const val retrofitVersion = "2.9.0"
    const val retrofit2 = "com.squareup.retrofit2:retrofit:$retrofitVersion"
    const val retrofit2ConverterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"

    // logback logback-android
    const val slf4j = "org.slf4j:slf4j-api:2.0.7"
    const val logbackAndroid = "com.github.tony19:logback-android:3.0.0"
    const val logbackClassic = "ch.qos.logback:logback-classic:1.2.11"

    // com.github.ben-manes.versions
    const val benManesVersions = "0.46.0"
}
