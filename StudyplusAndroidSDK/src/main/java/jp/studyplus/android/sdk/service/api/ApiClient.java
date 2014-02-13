package jp.studyplus.android.sdk.service.api;

/**
 * Represents a user of this API.
 */
public class ApiClient {
	private final ApiCertification certification;

	public ApiClient(ApiCertification certification) {
		this.certification = certification;
	}

	public void send(ApiRequest request){
		new ApiRequestTask(request, certification).execute();
	}
}
