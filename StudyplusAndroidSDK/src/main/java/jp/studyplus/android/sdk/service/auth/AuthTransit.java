package jp.studyplus.android.sdk.service.auth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Opens an Activity for a SDK client to be authorized.
 */
public class AuthTransit {
	private final Activity activity;
	private final int requestCode;

	public static AuthTransit from(Activity activity){
		return new AuthTransit(activity, 0);
	}

	private AuthTransit(Activity activity, int requestCode) {
		this.activity = activity;
		this.requestCode = requestCode;
	}

	public AuthTransit setRequestCode(int requestCode){
		return new AuthTransit(activity, requestCode);
	}

	public void startActivity(String consumerKey, String consumerSecret){
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.putExtra("consumer_key", consumerKey);
		intent.putExtra("consumer_secret", consumerSecret);
		intent.setData(Uri.parse("studyplus://auth/external/start"));
		activity.startActivityForResult(intent, requestCode);
	}
}
