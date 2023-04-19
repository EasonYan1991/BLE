import okhttp3.Response

/** Exception for an unexpected, non-2xx HTTP response.  */
internal class HttpException(@field:Transient private val response: Response) : RuntimeException(getMessage(response)) {

    /** HTTP status code.  */
    fun code(): Int = response.code

    /** HTTP status message.  */
    fun message(): String = response.message

    /** The full HTTP response. This may be null if the exception was serialized. */
    fun response(): Response = response

    companion object {
        @JvmStatic
        private fun getMessage(response: Response): String = "HTTP ${response.code} ${response.body?.string()}"
    }
}
