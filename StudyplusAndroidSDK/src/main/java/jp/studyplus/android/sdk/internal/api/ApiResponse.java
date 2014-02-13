package jp.studyplus.android.sdk.internal.api;

import com.google.common.base.Optional;

public interface ApiResponse {

	public Optional<String> getContent();

	public int getStatusCode();
}
