package jp.studyplus.android.sdk.test.service.api;

import org.junit.Test;

import jp.studyplus.android.sdk.service.api.ApiRequest;
import jp.studyplus.android.sdk.service.api.StudyplusApi;
import jp.studyplus.android.sdk.service.auth.AccessTokenNotFound;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordBuilder;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordPostRequest;
import jp.studyplus.android.sdk.test.RobolectricTest;

public class StudyplusApiTest extends RobolectricTest{

	@Test(expected = AccessTokenNotFound.class)
	public void testCatchAccessToken(){
		ApiRequest request = StudyRecordPostRequest.of(new StudyRecordBuilder().build());
		StudyplusApi.getClient(getActivity()).send(request);
	}
}
