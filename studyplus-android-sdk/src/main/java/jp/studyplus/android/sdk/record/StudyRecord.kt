package jp.studyplus.android.sdk.record

import androidx.annotation.IntRange
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Studyplusに投稿する学習記録
 *
 * @param duration      学習時間(s), MAX 86400 (24h)
 * @param amount        学習量
 * @param comment       学習に関するコメント
 * @param recordedTime  学習を終えた日時
 * @since 2.5.0
 * @throws IllegalArgumentException duration exceeds 24h
 */
data class StudyRecord @JvmOverloads constructor(
    @IntRange(from = 0L, to = DURATION_RANGE_MAX_24H)
    val duration: Int,
    val amount: StudyRecordAmount? = null,
    val comment: String? = null,
    val recordedTime: Calendar = Calendar.getInstance(DATE_TIME_ZONE, DATE_LOCALE)
) {

    internal fun toJson(): String = if (duration > DURATION_RANGE_MAX_24H) {
        throw IllegalArgumentException("duration must be 24 hours or less")
    } else {
        JSONObject().apply {
            put("recorded_at", formatTime(recordedTime))
            put("duration", duration)
            comment?.let { put("comment", it) }
            amount?.let { studyRecordAmount ->
                studyRecordAmount.amountTotal?.let { put("amount", it) }
                studyRecordAmount.startPosition?.let { put("start_position", it) }
                studyRecordAmount.endPosition?.let { put("end_position", it) }
            }
        }.toString()
    }

    companion object {
        private const val DURATION_RANGE_MAX_24H = 24L * 60L * 60L

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
