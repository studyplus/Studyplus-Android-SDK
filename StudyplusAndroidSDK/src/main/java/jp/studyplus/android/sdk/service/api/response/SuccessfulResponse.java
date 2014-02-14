package jp.studyplus.android.sdk.service.api.response;

import jp.studyplus.android.sdk.internal.api.ApiResponse;

public class SuccessfulResponse extends ResponseDelegatee {
	public static SuccessfulResponse create(ApiResponse response){
		return new SuccessfulResponse(response);
	}
	private SuccessfulResponse(ApiResponse response) {
		super(response);
	}
}
