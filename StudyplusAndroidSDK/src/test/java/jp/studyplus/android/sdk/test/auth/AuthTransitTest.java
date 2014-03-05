package jp.studyplus.android.sdk.test.auth;

import android.content.ActivityNotFoundException;

import org.junit.Test;

import jp.studyplus.android.sdk.service.auth.AuthTransit;
import jp.studyplus.android.sdk.test.RobolectricTest;

public class AuthTransitTest extends RobolectricTest {

	@Test(expected = ActivityNotFoundException.class)
	public void shouldThrowActivityNotFound() {
		AuthTransit.from(getActivity()).startActivity("dummy_key", "dummy_key_secret");
	}
}
