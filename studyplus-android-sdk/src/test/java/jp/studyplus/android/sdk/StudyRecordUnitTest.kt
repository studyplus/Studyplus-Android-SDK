package jp.studyplus.android.sdk

import android.os.Build
import jp.studyplus.android.sdk.record.StudyRecord
import jp.studyplus.android.sdk.record.StudyRecordAmountRange
import jp.studyplus.android.sdk.record.StudyRecordAmountTotal
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.OffsetDateTime
import java.time.ZoneOffset

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class StudyRecordUnitTest {

    private val date = OffsetDateTime.of(2019, 6, 1, 1, 2, 3, 0, ZoneOffset.UTC)
    private val dateStr = "2019-06-01T01:02:03Z"

    @Test
    fun toJsonTest_littleParams() {
        val studyRecord = StudyRecord(
            duration = 2 * 60,
            recordedTime = date,
        )

        assertEquals(
            """{"record_datetime":"$dateStr","duration":120}""",
            studyRecord.json
        )
    }

    @Test
    fun toJsonTest_allParams_amountTotal() {
        val studyRecord = StudyRecord(
            duration = 2 * 60,
            amount = StudyRecordAmountTotal(30),
            comment = "perfect!",
            recordedTime = date,
        )

        assertEquals(
            """{"record_datetime":"$dateStr","duration":120,"comment":"perfect!","amount":30}""",
            studyRecord.json
        )
    }

    @Test
    fun toJsonTest_allParams_amountRange() {
        val studyRecord = StudyRecord(
            duration = 2 * 60,
            amount = StudyRecordAmountRange(5, 12),
            comment = "perfect!",
            recordedTime = date,
        )

        assertEquals(
            """{"record_datetime":"$dateStr","duration":120,"comment":"perfect!","start_position":5,"end_position":12}""",
            studyRecord.json
        )
    }

}
