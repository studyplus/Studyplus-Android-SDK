package jp.studyplus.android.sdk.service.api.response;

import jp.studyplus.android.sdk.internal.api.ApiResponse;

public class ErrorResponse extends ResponseDelegatee {
	public static ErrorResponse create(ApiResponse response){
		return new ErrorResponse(response);
	}
	private ErrorResponse(ApiResponse response) {
		super(response);
	}
}
