@file:JvmName("BuildTools")

import com.lordcodes.turtle.ShellLocation
import com.lordcodes.turtle.shellRun
import net.swiftzer.semver.SemVer
import okhttp3.Call
import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider
import java.io.File
import kotlin.text.Charsets.UTF_8

const val CIRCLE_BUILD_NUM = "CIRCLE_BUILD_NUM"

fun main(vararg args: String) {
    if (args.isEmpty()) return help()

    val command = args[0].lowercase()
    val buildNum = args.getOrNull(1) ?: CIRCLE_BUILD_NUM

    when (command) {
        "prepare-deploy" -> prepareDeploy(buildNum)
        "validate-delta" -> validateDelta()
        "set-app-features" -> setAppFeatures(buildNum)
        "i18n-pull-request" -> i18nPullRequest()
        "rc_branch" -> createRcBranch()
        else -> help()
    }
}

val USER_NAME = System.getenv("RC_NAME")
val PASSWORD = System.getenv("RC_PASSWORD")

val CREDS = UsernamePasswordCredentialsProvider(USER_NAME, PASSWORD)

val REMOTE = "you remote url "
val DEVELOP = "develop"
val MASTER = "master"
val RC_TITLE = "Release %s"
val BUMP_TITLE = "Bump to %s"
val HTTP_CLIENT = OkHttpClient.Builder().build()
val RELEASE_BRANCH = "release_%s"
val BUMP_BRANCH = "bump_to_%s"
const val VERSION_NAME_FILE = "version.name"

fun createRcBranch() {
    val luna = ShellLocation.CURRENT_WORKING.resolve("../")
    println(
        shellRun(
            "git",
            listOf("config", "--global", "user.email", "mobileops@august.com"),
            luna
        )
    )
    println(shellRun("git", listOf("config", "--global", "user.name", "MobileOps"), luna))
    println(shellRun("git", listOf("checkout", DEVELOP), luna))
    println(shellRun("git", listOf("remote", "set-url", "origin", REMOTE), luna))
    val versionNameFile = File(luna, VERSION_NAME_FILE)
    val RCVersion = SemVer.parse(versionNameFile.readText(UTF_8))
    val bumpVersion = SemVer(RCVersion.major, RCVersion.minor + 1, 0)
    val releaseBranchName = RELEASE_BRANCH.format(RCVersion)
    val bumpBranchName = BUMP_BRANCH.format(bumpVersion)
    println(shellRun("git", listOf("branch", releaseBranchName), luna))
    println(
        shellRun(
            "git",
            listOf(
                "push",
                "--set-upstream",
                "origin",
                "refs/heads/$releaseBranchName:refs/heads/$releaseBranchName"
            ),
            luna
        )
    )
    // bump
    println(shellRun("git", listOf("branch", bumpBranchName), luna))
    println(
        shellRun(
            "git",
            listOf(
                "push",
                "--set-upstream",
                "origin",
                "refs/heads/$bumpBranchName:refs/heads/$bumpBranchName"
            ),
            luna
        )
    )
    // rc
    println(shellRun("git", listOf("checkout", releaseBranchName), luna))
    println(shellRun("git", listOf("commit", "--allow-empty", "-m", "trigger build"), luna))
    println(
        shellRun(
            "git",
            listOf(
                "push",
                "origin",
                "refs/heads/$bumpBranchName:refs/heads/$bumpBranchName"
            ),
            luna
        )
    )
    // bump
    println(shellRun("git", listOf("checkout", bumpBranchName), luna))
    versionNameFile.writeText(bumpVersion.toString())
    println(shellRun("git", listOf("add", "-f", "--", VERSION_NAME_FILE), luna))
    println(shellRun("git", listOf("commit", "-m", "bump to $bumpVersion"), luna))
    println(
        shellRun(
            "git",
            listOf(
                "push",
                "origin",
                "refs/heads/$bumpBranchName:refs/heads/$bumpBranchName"
            ),
            luna
        )
    )
    createPR(RC_TITLE.format(RCVersion), "RC", releaseBranchName, MASTER)
    createPR(BUMP_TITLE.format(bumpVersion), "bump", bumpBranchName, DEVELOP)
}

private fun createPR(title: String, description: String, source: String, target: String) {
    val payload = """
        {
            "title": "$title",
            "source": {
                "branch": {
                    "name": "$source"
                }
            },
            "destination": {
                "branch": {
                    "name": "$target"
                    }
            },
            "description": "$description",
            "close_source_branch": true
        }
    """.trimIndent()

    // TODO
    Request.Builder()
        .post(payload.toRequestBody("application/json; charset=utf8".toMediaType()))
        .url("repostiory/pullrequests")
        .addHeader(
            "Authorization",
            Credentials.basic(USER_NAME, PASSWORD)
        )
        .build()
        .let { networkRequest { HTTP_CLIENT.newCall(it) } }

    println("$title PR was created!")
}

private inline fun networkRequest(crossinline request: () -> Call) {
    return runCatching { request().execute() }
        .fold({ response ->
            if (!response.isSuccessful) throw HttpException(response)
        }, { exception ->
            System.err.println("error: ${exception.message}")
        })
}

fun prepareDeploy(buildNumEnvVar: String) {
    updateVersionProperties(createVersionCode(buildNumEnvVar))
}

/**checks if a version tag exists at the current commit and cancels the build if so */
fun validateDelta() {
    Runtime.getRuntime().exec("git tag --points-at HEAD")
        .also(::handleProcessError)
        .inputStream
        .bufferedReader()
        .useLines { sequence ->
            val tags = sequence.joinToString("\n")
            if (tags.isEmpty()) {
                println("No tags for this commit.")
            } else {
                System.err.println("This commit is tagged as: $tags!")
                System.exit(1)
                return
            }
        }
}

fun setAppFeatures(buildNumEnvVar: String) {
//    syncAppFeatures(OkHttpClient.Builder().build(), computeVersions(buildNumEnvVar))
}

fun help() = System.out.println(
    """
        Use this as follows: `./gradlew :build-tools:run --args="<args>"
        Allowable arguments are:
        - `prepare-deploy`  [BUILD_NUM]
        - `validate-delta`
        - `set-app-features`  [BUILD_NUM]
        - `i18n-pull-request`
        - 'rc_branch'

        "BUILD_NUM" defaults to "CIRCLE_BUILD_NUM", but any env var can be used.
    """.trimIndent()
)
