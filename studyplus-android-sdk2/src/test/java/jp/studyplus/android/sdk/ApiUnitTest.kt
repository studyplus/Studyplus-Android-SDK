package jp.studyplus.android.sdk

import jp.studyplus.android.sdk.internal.api.MockApiClient
import jp.studyplus.android.sdk.record.StudyRecordBuilder
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ApiUnitTest {

    @Test
    fun mockApi() {
        val record = StudyRecordBuilder().build()
        runBlocking {
            try {
                val deferred = MockApiClient.apiClient.postStudyRecords(null, record)
                val result = deferred.await()

                assertEquals(result.recordId, 9999L)
            } catch (t: Throwable) {
                assertNull(t)
            }
        }
    }
}