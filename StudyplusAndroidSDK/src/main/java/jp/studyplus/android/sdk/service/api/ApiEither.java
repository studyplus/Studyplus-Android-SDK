package jp.studyplus.android.sdk.service.api;

import com.google.common.base.Optional;

import jp.studyplus.android.sdk.internal.api.ApiResponse;


class ApiEither {
	private final Optional<Exception> exception;
	private final Optional<ApiResponse> response;

	static ApiEither create(Exception exception){
		return new ApiEither(
			Optional.of(exception),
			Optional.<ApiResponse>absent());
	}

	static ApiEither create(ApiResponse response){
		return new ApiEither(
			Optional.<Exception>absent(),
			Optional.of(response));
	}

	private ApiEither(Optional<Exception> exception, Optional<ApiResponse> response) {
		this.exception = exception;
		this.response = response;
	}

	Optional<Exception> getLeft(){
		return exception;
	}
	Optional<ApiResponse> getRight(){
		return response;
	}
}
