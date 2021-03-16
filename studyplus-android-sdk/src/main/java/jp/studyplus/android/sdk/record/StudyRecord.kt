package jp.studyplus.android.sdk.record

import androidx.annotation.IntRange
import org.json.JSONObject
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
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
    val recordedTime: OffsetDateTime = OffsetDateTime.now()
) {

    constructor(
        @IntRange(from = 0L, to = DURATION_RANGE_MAX_24H)
        duration: Int,
        amount: StudyRecordAmount? = null,
        comment: String? = null,
        recordedTime: Calendar
    ) : this(
        duration = duration,
        amount = amount,
        comment = comment,
        recordedTime = recordedTime.toInstant().atOffset(ZoneOffset.UTC)
    )

    internal val json: String
        get() = if (duration > DURATION_RANGE_MAX_24H) {
            throw IllegalArgumentException("duration must be 24 hours or less")
        } else {
            JSONObject().apply {
                put("record_datetime", recordedTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
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
    }

}
