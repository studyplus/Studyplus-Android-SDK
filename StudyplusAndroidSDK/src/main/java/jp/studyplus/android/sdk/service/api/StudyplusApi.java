package jp.studyplus.android.sdk.service.api;

import android.content.Context;

import jp.studyplus.android.sdk.service.auth.AccessTokenNotFound;
import jp.studyplus.android.sdk.service.auth.CertificationStore;


/**
 * Entry point to use functions provided by Studyplus.
 */
public class StudyplusApi {

	/**
	 * @param context Specifies application context.
	 * @return client for StudyplusAPI
	 * @throws AccessTokenNotFound
	 */
	public static ApiClient getClient(Context context) {
		ApiCertification certification = CertificationStore.create(context).getDefault();
		return new ApiClient(certification);
	}
}
