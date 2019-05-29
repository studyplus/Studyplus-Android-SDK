package jp.studyplus.android.sdk

import jp.studyplus.android.sdk.record.StudyRecord
import jp.studyplus.android.sdk.record.StudyRecordAmountRange
import jp.studyplus.android.sdk.record.StudyRecordAmountTotal
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class StudyRecordUnitTest {

    @Test
    fun toJsonTest_littleParams() {
        val studyRecord = StudyRecord(2 * 60)

        assertEquals(
            "{\"duration\":120,\"recorded_at\":\"${StudyRecord.formatTime(studyRecord.recordedTime)}\"}",
            studyRecord.toJson()
        )
    }

    @Test
    fun toJsonTest_allParams_amountTotal() {
        val studyRecord = StudyRecord(
            duration = 2 * 60,
            amount = StudyRecordAmountTotal(30),
            comment = "perfect!",
            recordedTime = Calendar.getInstance().apply { set(2019, 5, 1, 1, 2, 3) }
        )

        assertEquals(
            "{\"duration\":120,\"recorded_at\":\"2019-06-01 01:02:03\",\"amount\":30,\"comment\":\"perfect!\"}",
            studyRecord.toJson()
        )
    }

    @Test
    fun toJsonTest_allParams_amountRange() {
        val studyRecord = StudyRecord(
            duration = 2 * 60,
            amount = StudyRecordAmountRange(5, 12),
            comment = "perfect!",
            recordedTime = Calendar.getInstance().apply { set(2019, 5, 1, 1, 2, 3) }
        )

        assertEquals(
            "{\"duration\":120,\"start_position\":5,\"recorded_at\":\"2019-06-01 01:02:03\",\"comment\":\"perfect!\",\"end_position\":12}",
            studyRecord.toJson()
        )
    }

}
