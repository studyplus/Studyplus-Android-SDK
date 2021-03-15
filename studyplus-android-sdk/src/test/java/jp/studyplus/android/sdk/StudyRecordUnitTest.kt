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
import java.time.format.DateTimeFormatter

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class StudyRecordUnitTest {

    private val date = OffsetDateTime.of(2019, 6, 1, 1, 2, 3, 0, ZoneOffset.UTC)
    private val dateStr = date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

    @Test
    fun toJsonTest_littleParams() {
        val studyRecord = StudyRecord(2 * 60)

        assertEquals(
            "{\"record_datetime\":\"${studyRecord.recordedTime.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)}\",\"duration\":120}",
            studyRecord.json()
        )
    }

    @Test
    fun toJsonTest_allParams_amountTotal() {
        val studyRecord = StudyRecord(
            duration = 2 * 60,
            amount = StudyRecordAmountTotal(30),
            comment = "perfect!",
            recordedTime = date
        )

        assertEquals(
            "{\"record_datetime\":\"$dateStr\",\"duration\":120,\"comment\":\"perfect!\",\"amount\":30}",
            studyRecord.json()
        )
    }

    @Test
    fun toJsonTest_allParams_amountRange() {
        val studyRecord = StudyRecord(
            duration = 2 * 60,
            amount = StudyRecordAmountRange(5, 12),
            comment = "perfect!",
            recordedTime = date
        )

        assertEquals(
            "{\"record_datetime\":\"$dateStr\",\"duration\":120,\"comment\":\"perfect!\",\"start_position\":5,\"end_position\":12}",
            studyRecord.json()
        )
    }

}
