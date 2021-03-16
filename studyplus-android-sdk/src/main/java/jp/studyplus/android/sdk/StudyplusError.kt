package jp.studyplus.android.sdk

sealed class StudyplusError {
    class IOException(val e: java.io.IOException) : StudyplusError()
    object BadRequest : StudyplusError()
    object LoginRequired : StudyplusError()
    object ServerError : StudyplusError()
    object Unknown : StudyplusError()

    companion object {
        fun byCode(code: Int) = when (code) {
            400 -> BadRequest
            401 -> LoginRequired
            in 500..599 -> ServerError
            else -> Unknown
        }
    }
}
