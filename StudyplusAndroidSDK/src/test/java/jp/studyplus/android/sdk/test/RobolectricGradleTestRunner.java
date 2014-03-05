package jp.studyplus.android.sdk.test;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.robolectric.AndroidManifest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.res.Fs;

public class RobolectricGradleTestRunner extends RobolectricTestRunner {

	public RobolectricGradleTestRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {
		super.runChild(method, notifier);
	}

	@Override
	protected AndroidManifest getAppManifest(Config config) {
		String manifestProperty = System.getProperty("android.manifest");
		if (config.manifest().equals(Config.DEFAULT) && manifestProperty != null) {
			String resProperty = System.getProperty("android.resources");
			String assetsProperty = System.getProperty("android.assets");
			return new AndroidManifest(
				Fs.fileFromPath(manifestProperty),
				Fs.fileFromPath(resProperty),
				Fs.fileFromPath(assetsProperty));
		}
		return super.getAppManifest(config);
	}
}