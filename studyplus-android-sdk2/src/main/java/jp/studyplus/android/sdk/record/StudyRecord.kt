package jp.studyplus.android.sdk.record

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Studyplusに投稿する学習記録
 *
 * @param duration      学習時間(s)
 * @param recordedTime  学習を終えた日時
 * @param comment       学習に関するコメント
 * @param amount        学習量
 * @since 2.5.0
 */
data class StudyRecord @JvmOverloads constructor(
    var duration: Int,
    var recordedTime: Calendar = Calendar.getInstance(DATE_TIME_ZONE, DATE_LOCALE),
    var comment: String? = null,
    var amount: StudyRecordAmount? = null
) {

    internal fun toJson(): String = JSONObject().apply {
        put("recorded_at", formatTime(recordedTime))
        put("duration", duration)
        comment?.let { put("comment", it) }
        amount?.let { studyRecordAmount ->
            studyRecordAmount.amountTotal?.let { put("amount", it) }
            studyRecordAmount.startPosition?.let { put("start_position", it) }
            studyRecordAmount.endPosition?.let { put("end_position", it) }
        }
    }.toString()

    companion object {
        private const val DATE_FORMAT = "yyyy'-'MM'-'dd' 'HH':'mm':'ss"
        private val DATE_LOCALE: Locale = Locale.US
        private val DATE_TIME_ZONE: TimeZone = TimeZone.getTimeZone("UTC")

        internal fun formatTime(calendar: Calendar): String {
            val format = SimpleDateFormat(DATE_FORMAT, DATE_LOCALE)
            format.timeZone = calendar.timeZone
            return format.format(calendar.time)
        }
    }

}
