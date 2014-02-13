package jp.studyplus.android.sdk.internal.api;

import com.google.common.base.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class ApiAccess {

	private final String url;
	private final String accessToken;

	public ApiAccess(String url, String accessToken) {
		this.url = url;
		this.accessToken = accessToken;
	}

	public ApiResponse get() throws IOException {
		HttpGet get = new HttpGet(url);
		return request(get);
	}
	public ApiResponse post() throws IOException {
		HttpPost post = new HttpPost(url);
		return request(post);
	}
	public ApiResponse post(JSONObject parameter) throws IOException {
		HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(parameter.toString(), "UTF-8"));
		return request(post);
	}

	private ApiResponse request(HttpUriRequest request) throws IOException {
		HttpParams params = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
		HttpConnectionParams.setSoTimeout(params, 10 * 1000);

		DefaultHttpClient client = new DefaultHttpClient(params);
		final ApiResponse response;
		try {
			request.setHeader("Content-type", "application/json");
			request.setHeader("HTTP_AUTHORIZATION", "OAuth " + accessToken);
			response = client.execute(request, new OnLoadResponse());
		} finally {
			client.getConnectionManager().shutdown();
		}
		return response;
	}

	private static class OnLoadResponse implements ResponseHandler<ApiResponse> {
		@Override
		public ApiResponse handleResponse(HttpResponse httpResponse) throws IOException {
			return ApiHttpResponse.create(httpResponse);
		}
	}

	private static class ApiHttpResponse implements ApiResponse {

		private final Optional<String> content;
		private final int statusCode;

		public static ApiHttpResponse create(HttpResponse response) throws IOException {
			HttpEntity entity = response.getEntity();
			Optional<String> content = (entity == null) ?
				Optional.<String>absent() :
				Optional.of(EntityUtils.toString(entity, "UTF-8"));

			return new ApiHttpResponse(
				content,
				response.getStatusLine().getStatusCode());
		}

		private ApiHttpResponse(Optional<String> content, int statusCode) {
			this.content = content;
			this.statusCode = statusCode;
		}

		@Override
		public Optional<String> getContent() {
			return content;
		}

		@Override
		public int getStatusCode() {
			return statusCode;
		}
	}
}
