package jp.studyplus.android.sdk

import android.os.Build
import jp.studyplus.android.sdk.internal.api.Repository
import jp.studyplus.android.sdk.internal.createRequest
import jp.studyplus.android.sdk.internal.requestBody
import jp.studyplus.android.sdk.record.StudyRecord
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.fail

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class ApiUnitTest {

    @Test
    fun mockApi() {
        val server = MockWebServer()
        server.enqueue(MockResponse())
        server.start()

        val record = StudyRecord(2 * 60)
        val body = record.requestBody()
        val request = createRequest(
            url = server.url("/v1/study_records"),
            accessToken = "access_token",
            body = body,
        )

        Repository().post(request = request, callback = object : PostCallback {
            override fun onSuccess() {
                assert(true)
            }

            override fun onFailure(e: StudyplusError) {
                fail("Failed Network Request")
            }
        })
    }
}
