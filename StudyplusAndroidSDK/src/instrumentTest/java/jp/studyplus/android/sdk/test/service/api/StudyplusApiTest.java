package jp.studyplus.android.sdk.test.service.api;

import android.test.AndroidTestCase;

import jp.studyplus.android.sdk.service.api.ApiRequest;
import jp.studyplus.android.sdk.service.api.StudyplusApi;
import jp.studyplus.android.sdk.service.auth.AccessTokenNotFound;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordBuilder;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordPostRequest;

public class StudyplusApiTest extends AndroidTestCase {

    public void testCatchAccessTokenNotFoundException() {
        final ApiRequest request = StudyRecordPostRequest.of(new StudyRecordBuilder().build());

        try {
            StudyplusApi.getClient(getContext()).send(request);
        } catch (AccessTokenNotFound e) {
            assertTrue(true);
            return ;
        }
        assertTrue(false);
    }
}
