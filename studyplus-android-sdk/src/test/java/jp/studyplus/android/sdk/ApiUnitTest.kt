package jp.studyplus.android.sdk

import android.os.Build
import jp.studyplus.android.sdk.internal.api.MockApiClient
import jp.studyplus.android.sdk.internal.api.PostCallback
import jp.studyplus.android.sdk.record.StudyRecord
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.fail

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ApiUnitTest {

    @Test
    fun mockApi() {
        val record = StudyRecord(2 * 60)
        MockApiClient.postStudyRecords(null, record, object : PostCallback {
            override fun onSuccess(recordId: Long?) {
                assertEquals(recordId, 9999L)
            }

            override fun onFailure(e: IOException) {
                fail("Failed Network Request")
            }
        })
    }
}
