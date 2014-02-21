package jp.studyplus.android.sdk.service.auth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import com.google.common.base.Optional;

import jp.studyplus.android.sdk.R;
import jp.studyplus.android.sdk.service.api.ApiCertification;

import static android.content.SharedPreferences.Editor;

/**
 * Saves and retrieves a certification which is required to use API.
 */
public class CertificationStore {
	private final SharedPreferences preferences;

	private static String KEY_ACCESS_TOKEN = "access_token";
	private final String baseUrl;

	public static CertificationStore create(Context context) {
		String baseUrl = context.getString(R.string.studyplus_api_base_url);
		String prefName = "Certification:" + Uri.parse(baseUrl).getHost();
		SharedPreferences pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
		return new CertificationStore(pref, baseUrl);
	}
	private CertificationStore(SharedPreferences preferences, String baseUrl) {
		this.preferences = preferences;
		this.baseUrl = baseUrl;
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

	public ApiCertification getDefault() throws AccessTokenNotFound {
		Optional<String> token = findAccessToken();
		if (!token.isPresent()){
			throw new AccessTokenNotFound();
		}
		return new ApiCertificationImpl(token.get(), baseUrl);
	}

	private static class ApiCertificationImpl implements ApiCertification{
		private final String accessToken;
		private final String baseUrl;

		private ApiCertificationImpl(String accessToken, String baseUrl) {
			this.accessToken = accessToken;
			this.baseUrl = baseUrl;
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
