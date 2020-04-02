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
import java.util.*

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class StudyRecordUnitTest {

    @Test
    fun toJsonTest_littleParams() {
        val studyRecord = StudyRecord(2 * 60)

        assertEquals(
            "{\"recorded_at\":\"${StudyRecord.formatTime(studyRecord.recordedTime)}\",\"duration\":120}",
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
            "{\"recorded_at\":\"2019-06-01 01:02:03\",\"duration\":120,\"comment\":\"perfect!\",\"amount\":30}",
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
            "{\"recorded_at\":\"2019-06-01 01:02:03\",\"duration\":120,\"comment\":\"perfect!\",\"start_position\":5,\"end_position\":12}",
            studyRecord.toJson()
        )
    }

}
