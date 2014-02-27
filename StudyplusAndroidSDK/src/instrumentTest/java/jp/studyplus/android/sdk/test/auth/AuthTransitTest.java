package jp.studyplus.android.sdk.test.auth;

import android.test.ActivityUnitTestCase;

import jp.studyplus.android.sdk.service.auth.AuthTransit;
import jp.studyplus.android.sdk.test.mock.MockActivity;

public class AuthTransitTest extends ActivityUnitTestCase<MockActivity> {

    public AuthTransitTest() {
        super(MockActivity.class);
    }

    public void testTransitToStudyplus() {
        try {
            AuthTransit.from(getActivity()).startActivity("dummy_key", "dummy_key_secret");
        } catch (Exception e) {
            assertTrue(true);
            return ;
        }
        fail();
    }
}


