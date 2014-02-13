package jp.studyplus.android.sdk.service.api;

import android.os.AsyncTask;

import com.google.common.base.Optional;

import java.io.IOException;

import jp.studyplus.android.sdk.internal.api.ApiResponse;

class ApiRequestTask extends AsyncTask<Void, Void, ApiEither> {
	private final ApiRequest request;
	private final ApiCertification certification;

	ApiRequestTask(ApiRequest request, ApiCertification certification) {
		this.request = request;
		this.certification = certification;
	}

	@Override
	protected ApiEither doInBackground(Void... params) {
		ApiEither rawEither;
		try {
			rawEither = new ApiRequester(request, certification).request();
		} catch (IOException e) {
			rawEither = ApiEither.create(e);
		}
		ApiEither either;
		Optional<ApiResponse> response = rawEither.getRight();
		if (response.isPresent()){
			ApiResponse decorated = request.getResponseDecorator().decorate(response.get());
			either = ApiEither.create(decorated);
		} else {
			either = rawEither;
		}
		return either;
	}
	@Override
	protected void onPostExecute(ApiEither either) {
		for(Exception e : either.getLeft().asSet()){
			request.dispatch(e);
		}
		for(ApiResponse r : either.getRight().asSet()){
			request.dispatch(r);
		}
	}
}
