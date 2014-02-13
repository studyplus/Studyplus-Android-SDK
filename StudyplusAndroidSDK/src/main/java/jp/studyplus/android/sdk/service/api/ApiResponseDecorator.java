package jp.studyplus.android.sdk.service.api;

import jp.studyplus.android.sdk.internal.api.ApiResponse;

/**
 * Converts a response into other user-defined {@link jp.studyplus.android.sdk.internal.api.ApiResponse},
 * which is subscribed by {@link jp.studyplus.android.sdk.service.api.ApiRequestListener}.
 */
public interface ApiResponseDecorator {

	public ApiResponse decorate(ApiResponse response);
}
