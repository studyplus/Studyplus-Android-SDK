package jp.studyplus.android.sdk.service.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.common.base.Optional;

import jp.studyplus.android.sdk.BuildConfig;
import jp.studyplus.android.sdk.service.api.ApiCertification;

import static android.content.SharedPreferences.Editor;

/**
 * Saves and retrieves a certification which is required to use API.
 */
public class CertificationStore {
	private final SharedPreferences preferences;

	private static String KEY_ACCESS_TOKEN = "access_token";

	public static CertificationStore create(Context context) {
		String prefName = "Certification:" + Uri.parse(BuildConfig.API_ENDPOINT).getHost();
		SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		return new CertificationStore(pref);
	}
	private CertificationStore(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	public void update(Intent data){
		if (data == null){
			return;
		}
		String code = data.getStringExtra("sp_auth_result_code");
		if ("AUTHENTICATED".equals(code)){
			Editor editor = preferences.edit();
			editor.putString(KEY_ACCESS_TOKEN, data.getStringExtra("sp_auth_access_token"));
			editor.commit();
		}
	}
	private Optional<String> findAccessToken(){
		return Optional.fromNullable(preferences.getString(KEY_ACCESS_TOKEN, null));
	}

	/**
	 * Return the basic {@link ApiCertification} instance for API.
	 *
	 * @return certification instance for API.
	 * @throws AccessTokenNotFound
	 */
	public ApiCertification getDefault() {
		Optional<String> token = findAccessToken();
		if (!token.isPresent()){
			throw new AccessTokenNotFound();
		}
		return new ApiCertificationImpl(token.get());
	}

	private static class ApiCertificationImpl implements ApiCertification{
		private final String accessToken;
		private final String baseUrl;

		private ApiCertificationImpl(String accessToken) {
			this.accessToken = accessToken;
			this.baseUrl = BuildConfig.API_ENDPOINT;
		}

		@Override
		public String getAccessToken() {
			return accessToken;
		}

		@Override
		public String getBaseUrl() {
			return baseUrl;
		}
	}
}
