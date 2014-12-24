package jp.studyplus.android.sdk.test;

import android.app.Activity;

import org.robolectric.Robolectric;

public final class MockActivityCreator {

	private MockActivityCreator() {}

	public static Activity createActivity() {
		return Robolectric.buildActivity(Activity.class).create().get();
	}

}
