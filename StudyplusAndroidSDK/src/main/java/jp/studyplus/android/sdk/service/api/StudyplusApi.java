package jp.studyplus.android.sdk.service.api;

import android.content.Context;

import jp.studyplus.android.sdk.service.auth.CertificationStore;


/**
 * Entry point to use functions provided by Studyplus.
 */
public class StudyplusApi {

	public static ApiClient getClient(Context context){
		ApiCertification certification = CertificationStore.create(context).getDefault();
		return new ApiClient(certification);
	}
}
