package jp.studyplus.android.sdk.test.auth;

import android.content.ActivityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import jp.studyplus.android.sdk.service.auth.AuthTransit;
import jp.studyplus.android.sdk.test.MockActivityCreator;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AuthTransitTest {

	@Before
	public void setUp(){
		Robolectric.checkActivities(true);
	}

	@Test(expected = ActivityNotFoundException.class)
	public void shouldThrowActivityNotFound() {
		AuthTransit.from(MockActivityCreator.createActivity())
				.startActivity("dummy_key", "dummy_key_secret");
	}

}
