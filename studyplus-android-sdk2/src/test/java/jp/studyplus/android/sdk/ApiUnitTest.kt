package jp.studyplus.android.sdk

import jp.studyplus.android.sdk.record.StudyRecordBuilder
import org.junit.Test

class ApiUnitTest {

    @Test
    fun mockApi() {
        val record = StudyRecordBuilder().build()
        MockApiClient.apiClient.postStudyRecords(null, record)
                .test()
                .assertNoErrors()
                .assertValue {
                    it.recordId?.let {
                        it == 9999L
                    } ?: run {
                        false
                    }
                }
    }
}