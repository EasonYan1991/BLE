import com.github.zafarkhaja.semver.Version
import org.eclipse.jgit.api.Git
import java.io.File
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import kotlin.Comparator

fun computeVersions(buildNumEnvVar: String): Pair<Version, Version> {
    val versionCode = createVersionCode(buildNumEnvVar)
    val current = toSemver("${updateVersionProperties(versionCode)}-$versionCode")
    return current to getPrevious(current)
}

internal fun updateVersionProperties(versionCode: Int): String {
    val versionNameFile: File
    val versionCodeFile: File
    if (workingDirIsRoot()) {
        versionNameFile = File("version.name")
        versionCodeFile = File("version.code")
    } else {
        versionNameFile = File("../version.name")
        versionCodeFile = File("../version.code")
    }
    val versionName = versionNameFile.readText(Charsets.UTF_8)
    if (!versionCodeFile.canWrite()) {
        versionCodeFile.setWritable(true)
    }
    versionCodeFile.writeText(versionCode.toString())
    return versionName
}

fun createVersionCode(buildNumEnvVar: String): Int {
    val build = runCatching { System.getenv(buildNumEnvVar).toInt() }.getOrDefault(0)

    return SimpleDateFormat("yyMMdd", Locale.US)
        .format(Date.from(Instant.now()))
        .let { it.toInt() * 1000 + build % 1000 }
}

internal fun handleProcessError(process: Process) = process.errorStream
    .bufferedReader()
    .use {
        val err = it.readText()
        if (err.isNotEmpty()) {
            System.err.println("Error with Git: $err")
            System.exit(1)
        }
    }

private fun toSemver(tag: String) = Version.valueOf(tag.substringAfter('v').replace('-', '+'))

private fun getPrevious(current: Version): Version {

    val root = if (!workingDirIsRoot()) File("../") else File(".")

    Git.open(root).let { git ->
        val tagList = git.tagList().call()

        println("# of tags in Repo: ${tagList.size}")

        val tags = tagList
            .asSequence()
            .map { it.name.substringAfter("refs/tags/").trim() }
            .filter { it matches Regex("""v\d+\.\d+\.\d+-\d{9}""") }

        return tags
            .map { toSemver(it) }
            .sortedWith(Comparator { a, b -> -a.compareWithBuildsTo(b) })
            .first { it <= current }
    }
}

internal fun workingDirIsRoot() = File(".").absoluteFile.name.contains("luna")
