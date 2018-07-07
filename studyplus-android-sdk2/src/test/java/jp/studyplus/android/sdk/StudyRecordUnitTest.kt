package jp.studyplus.android.sdk

import jp.studyplus.android.sdk.record.StudyRecord
import jp.studyplus.android.sdk.record.StudyRecordBuilder
import org.junit.Test

import org.junit.Assert.*
import java.util.*

/**
 * StudyRecord unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class StudyRecordUnitTest {
    @Test
    fun amountTotal() {
        val now = Calendar.getInstance()
        now.time = Date()

        val studyRecord = StudyRecordBuilder()
                .setRecordedTime(now)
                .setComment("perfect!")
                .setAmountTotal(30)
                .setDurationSeconds(2 * 60)
                .build()

        assertEquals("perfect!", studyRecord.comment)
        assertEquals(30, studyRecord.amountTotal)
        assertEquals(2 * 60, studyRecord.duration)
        assertEquals(StudyRecord.getTime(now), studyRecord.recordedTime)
    }

    @Test
    fun amountRange() {
        val now = Calendar.getInstance()
        now.time = Date()

        val studyRecord = StudyRecordBuilder()
                .setRecordedTime(now)
                .setComment("perfect!")
                .setAmountRange(5, 12)
                .setDurationSeconds(2 * 60)
                .build()

        assertEquals("perfect!", studyRecord.comment)
        assertEquals(5, studyRecord.startPosition)
        assertEquals(12, studyRecord.endPosition)
        assertEquals(2 * 60, studyRecord.duration)
        assertEquals(StudyRecord.getTime(now), studyRecord.recordedTime)
    }
}