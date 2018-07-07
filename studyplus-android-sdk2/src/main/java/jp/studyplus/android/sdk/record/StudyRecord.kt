package jp.studyplus.android.sdk.record

import com.google.gson.annotations.SerializedName
import jp.studyplus.android.sdk.record.StudyRecord.Companion.getTime
import java.security.InvalidParameterException
import java.text.SimpleDateFormat
import java.util.*

class StudyRecord {
    companion object {

        private const val DATE_FORMAT = "yyyy'-'MM'-'dd' 'HH':'mm':'ss"
        val DATE_LOCALE: Locale = Locale.US
        val DATE_TIME_ZONE: TimeZone = TimeZone.getTimeZone("UTC")

        fun getTime(calendar: Calendar): String {
            val format = SimpleDateFormat(DATE_FORMAT, DATE_LOCALE)
            format.timeZone = calendar.timeZone
            return format.format(calendar.time)
        }
    }

    @SerializedName("recorded_at")
    var recordedTime: String = getTime(GregorianCalendar(DATE_TIME_ZONE, DATE_LOCALE))
    @SerializedName("duration")
    var duration: Int = 0
    @SerializedName("comment")
    var comment: String = ""
    @SerializedName("amount")
    var amountTotal: Int? = null
    @SerializedName("start_position")
    var startPosition: Int? = null
    @SerializedName("end_position")
    var endPosition: Int? = null
}

class StudyRecordBuilder {

    private val studyRecord = StudyRecord()

    fun build(): StudyRecord {
        return studyRecord
    }

    fun setRecordedTime(calendar: Calendar): StudyRecordBuilder {
        studyRecord.recordedTime = getTime(calendar)
        return this
    }

    fun setDurationSeconds(duration: Int): StudyRecordBuilder {
        studyRecord.duration = duration
        return this
    }

    fun setComment(comment: String): StudyRecordBuilder {
        studyRecord.comment = comment
        return this
    }

    fun setAmountTotal(amount: Int): StudyRecordBuilder {
        if (studyRecord.startPosition != null
                || studyRecord.endPosition != null) {
            throw InvalidParameterException()
        }
        studyRecord.amountTotal = amount
        return this
    }

    fun setAmountRange(start: Int, end: Int): StudyRecordBuilder {
        if (studyRecord.amountTotal != null) {
            throw InvalidParameterException()
        }
        studyRecord.startPosition = start
        studyRecord.endPosition = end
        return this
    }
}
