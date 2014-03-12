package jp.studyplus.android.sdk.service.api;

import org.json.JSONException;

import java.io.IOException;

import jp.studyplus.android.sdk.BuildConfig;
import jp.studyplus.android.sdk.internal.api.ApiAccess;
import jp.studyplus.android.sdk.internal.api.ApiResponse;


class ApiRequester {
	private final ApiRequest request;
	private final ApiAccess access;

	public ApiRequester(ApiRequest request, ApiCertification certification) {
		this.request = request;
		this.access = new ApiAccess(
			BuildConfig.API_ENDPOINT + request.getScriptPath(),
			certification.getAccessToken());
	}
	public ApiEither request() throws IOException {
		ApiEither either;
		try {
			switch (request.getMethod()){
			case GET:
				either = ApiEither.create(access.get());
				break;
			case POST:
				ApiResponse response;
				if (request.getJsonContent().isPresent()){
					response = access.post(request.getJsonContent().get());
				} else {
					response = access.post();
				}
				either = ApiEither.create(response);
				break;
			default:
				either = ApiEither.create(new IllegalArgumentException("unknown method"));
			}
		} catch (JSONException e) {
			either = ApiEither.create(e);
		}
		return either;
	}
}
