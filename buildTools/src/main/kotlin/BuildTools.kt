@file:JvmName("BuildTools")

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale

const val CI_BUILD_NUMBER = "CI_BUILD_NUMBER"


fun main(args: Array<String>) {

    if (args.isEmpty()) {
        return help()
    }

    val command = args[0].lowercase()
    val buildNum = args.getOrNull(1) ?: CI_BUILD_NUMBER

    when (command) {
        "prepare-deploy" -> prepareDeploy(buildNum)
        "validate-delta" -> validateDelta(buildNum)
        "set-remote-features" -> setRemoteFeatures(buildNum)
        "i18n-pull-request" -> i18nPullRequest(buildNum)
        "release_build" -> releaseBuild(buildNum)
        else -> help()
    }
}


fun prepareDeploy(buildNum: String) {
    println(" prepareDeploy $buildNum")
    updateVersionProperties(createVersionCode(buildNum))
}

fun validateDelta(buildNum: String) {
    println(" validateDelta $buildNum")
}

fun createVersionCode(buildNum: String): Int {
    println(" createVersionCode $buildNum")
    val build = runCatching { System.getenv(buildNum).toInt() }.getOrDefault(0)

    return SimpleDateFormat("yyMMdd", Locale.US)
        .format(Date.from(Instant.now()))
        .let { it.toInt() * 1000 + build % 1000 }
}

/**
 * example:
 * ./gradlew :buildTools:run --args=prepare-deploy
 *
 */
fun help() = println(
    """
        Use this as follows: `./gradlew :buildTools:run --args="<args>"
        Allowable arguments are:
        - `prepare-deploy`  [BUILD_NUM]
        - `validate-delta`
        - `set-remote-features`  [BUILD_NUM]
        - `i18n-pull-request`
        - 'release_build'

        "BUILD_NUM" defaults to "CONCOURSE_BUILD_NUM", but any env var can be used.
    """.trimIndent(),
)

fun setRemoteFeatures(buildNum: String) {
    println(" setRemoteFeatures $buildNum")
}

fun i18nPullRequest(buildNum: String) {
    println(" i18nPullRequest $buildNum")
}

fun releaseBuild(buildNum: String) {
    println(" releaseBuild $buildNum")
}