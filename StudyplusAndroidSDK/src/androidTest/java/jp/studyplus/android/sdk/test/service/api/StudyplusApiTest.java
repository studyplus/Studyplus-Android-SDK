package jp.studyplus.android.sdk.test.service.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import jp.studyplus.android.sdk.service.api.ApiRequest;
import jp.studyplus.android.sdk.service.api.StudyplusApi;
import jp.studyplus.android.sdk.service.auth.AccessTokenNotFound;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordBuilder;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordPostRequest;
import jp.studyplus.android.sdk.test.MockActivityCreator;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class StudyplusApiTest {

	@Test(expected = AccessTokenNotFound.class)
	public void testCatchAccessToken(){
		ApiRequest request = StudyRecordPostRequest.of(new StudyRecordBuilder().build());
		StudyplusApi.getClient(MockActivityCreator.createActivity()).send(request);
	}

}
