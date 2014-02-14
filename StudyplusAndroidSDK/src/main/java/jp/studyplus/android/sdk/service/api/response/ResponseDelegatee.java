package jp.studyplus.android.sdk.service.api.response;

import com.google.common.base.Optional;

import jp.studyplus.android.sdk.internal.api.ApiResponse;


class ResponseDelegatee implements ApiResponse {
	private final ApiResponse response;

	public ResponseDelegatee(ApiResponse response) {
		this.response = response;
	}
	@Override
	public Optional<String> getContent() {
		return response.getContent();
	}
	@Override
	public int getStatusCode() {
		return response.getStatusCode();
	}
}
