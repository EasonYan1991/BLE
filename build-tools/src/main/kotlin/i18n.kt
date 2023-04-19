import okhttp3.Call
import okhttp3.Credentials
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.eclipse.jgit.api.Git
import java.io.File
import java.time.LocalDate

const val TITLE = "Automated CrowdIn Nightly Sync: %s"
const val BRANCH = "i18n-auto-%s"
//val bitbucketUuid = hashMapOf(
//)

fun i18nPullRequest() {
    val branch = commitAndPushBranch() ?: return
    val httpClient = OkHttpClient.Builder().build()

    val payload = """
        {
            "title": "${"%1\$s"}",
            "source": {
                "branch": {
                    "name": "${"%2\$s"}"
                }
            },
            "description": "Beep boop, I'm a robot, helpfully making a pull request with updated translation files.",
            "close_source_branch": true
        }
    """.trimIndent()
        .format(TITLE.format(LocalDate.now()), branch)

    Request.Builder()
        .post(payload.toRequestBody("application/json; charset=utf8".toMediaType()))
        .url("yourepository/pullrequests")
        .addHeader("Authorization", Credentials.basic("mobileops", System.getenv("BITBUCKET_PULL_REQUEST_TOKEN")))
        .build()
        .let { networkRequest { httpClient.newCall(it) } }

    println("New Pull Request created!")
}

private fun commitAndPushBranch(): String? {
    val branchName = BRANCH.format(LocalDate.now())
    Git.open(
        if (!workingDirIsRoot()) File("../")
        else File(".")
    )
        .let { git ->
            println(git.repository.config.getString("remote", "origin", "url"))
            // git status

            git.status().call().run {
                val changes = hasUncommittedChanges()
                val modified = this.modified.isNotEmpty()
                val clean = this.isClean
                println(
                    """uncommited changes: $changes
                        | modified: $modified
                        | clean: $clean""".trimMargin()
                )
                if ((!changes && !modified) || clean) return null
            }
            println("repo has edits!")

            // git checkout -b branchname
            git.checkout()
                .setCreateBranch(true)
                .setName(branchName)
                .call()
            println("checked out branch: $branchName")
            git.submoduleInit().call()
            println("init submodules")

            // git add .
            git.add()
                .addFilepattern("strings.xml")
                .call()

            println("Added these files to staging index: \n${git.status().call().changed}")

            // git commit -am "msg"
            val commit = git.commit()
                .setAuthor("MobileOps", "mobileops@august.com")
                .setMessage(TITLE.format(LocalDate.now()))
                .setAll(true)
                .call()
            println("Committed as ${commit.toObjectId()}")

            // git push origin branchname --force
            Runtime.getRuntime().exec("git push --set-upstream origin $branchName").waitFor()

            println("pushed branch to remote")
        }
    return branchName
}

private inline fun networkRequest(crossinline request: () -> Call) {
    return runCatching { request().execute() }
        .fold({ response ->
            if (!response.isSuccessful) throw HttpException(response)
        }, { exception ->
            System.err.println("error: $exception")
        })
}
