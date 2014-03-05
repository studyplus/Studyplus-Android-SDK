package jp.studyplus.android.sdk.test;

import android.app.Activity;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

@Ignore
@RunWith(RobolectricGradleTestRunner.class)
public class RobolectricTest {

	@Before
	public void setUp(){
		Robolectric.checkActivities(true);
	}

	protected Activity getActivity(){
		return Robolectric.buildActivity(Activity.class).create().get();
	}
}
