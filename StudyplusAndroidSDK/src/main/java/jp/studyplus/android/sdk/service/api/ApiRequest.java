package jp.studyplus.android.sdk.service.api;

import com.google.common.base.Optional;
import com.google.common.collect.Range;
import com.google.common.eventbus.EventBus;

import org.json.JSONException;
import org.json.JSONObject;

import jp.studyplus.android.sdk.internal.api.ApiResponse;
import jp.studyplus.android.sdk.service.api.response.ErrorResponse;
import jp.studyplus.android.sdk.service.api.response.SuccessfulResponse;

public abstract class ApiRequest {

	protected final EventBus eventBus;

	public ApiRequest() {
		eventBus = new EventBus();
	}
	public ApiRequest with(ApiRequestListener listener){
		eventBus.register(listener);
		return this;
	}
	public <T> void dispatch(T event){
		eventBus.post(event);
	}
	public abstract String getScriptPath();

	public abstract ApiMethod getMethod();

	public abstract Optional<JSONObject> getJsonContent() throws JSONException;

	public ApiResponseDecorator getResponseDecorator(){
		return new BasicResponseDecorator();
	}

	private static class BasicResponseDecorator implements ApiResponseDecorator {
		@Override
		public ApiResponse decorate(ApiResponse response) {
			int status = response.getStatusCode();
			ApiResponse decorated;
			if (Range.closedOpen(200, 300).contains(status)){
				decorated = SuccessfulResponse.create(response);
			} else {
				decorated = ErrorResponse.create(response);
			}
			return decorated;
		}
	}

}
