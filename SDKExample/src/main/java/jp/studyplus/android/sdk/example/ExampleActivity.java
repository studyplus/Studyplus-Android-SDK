package jp.studyplus.android.sdk.example;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.common.eventbus.Subscribe;

import jp.studyplus.android.sdk.service.api.ApiRequest;
import jp.studyplus.android.sdk.service.api.ApiRequestListener;
import jp.studyplus.android.sdk.service.api.StudyplusApi;
import jp.studyplus.android.sdk.service.api.response.ErrorResponse;
import jp.studyplus.android.sdk.service.api.response.SuccessfulResponse;
import jp.studyplus.android.sdk.service.auth.AuthTransit;
import jp.studyplus.android.sdk.service.auth.CertificationStore;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordBuilder;
import jp.studyplus.android.sdk.service.studyrecord.StudyRecordPostRequest;

import static android.view.View.OnClickListener;

public class ExampleActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

		findViewById(R.id.start_auth)
			.setOnClickListener(new OnClickToAuth(this));

		findViewById(R.id.post_study_record)
			.setOnClickListener(new OnClickToPost(this));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		CertificationStore.create(this).update(data);
	}

	private static class OnClickToPost implements OnClickListener, ApiRequestListener {
		private final Context context;

		private OnClickToPost(Context context) {
			this.context = context;
		}
		@Override
		public void onClick(View v) {
			ApiRequest request = StudyRecordPostRequest.of(
				new StudyRecordBuilder()
					.setComment("勉強した！！！")
					.setAmountTotal(30)
					.setDurationSeconds(2 * 60)
					.build());

			StudyplusApi.getClient(context).send(request.with(this));
		}

		@Subscribe @SuppressWarnings("unused")
		public void showMessage(SuccessfulResponse response){
			Toast.makeText(context, "投稿完了！", Toast.LENGTH_SHORT).show();
		}
		@Subscribe
		public void showError(ErrorResponse response){
			Log.e(OnClickToPost.class.getName(), "response:" + response.getStatusCode());
		}
		@Subscribe
		public void showException(Exception e){
			e.printStackTrace();
		}
	}

	private static class OnClickToAuth implements OnClickListener {
		private final Activity activity;
		private final Context context;

		private OnClickToAuth(Activity activity) {
			this.activity = activity;
			this.context = activity.getApplicationContext();
		}
		@Override
		public void onClick(View v) {
			AuthTransit.from(activity).startActivity(
				context.getString(R.string.sample_consumer_key),
				context.getString(R.string.sample_consumer_key_secret)
			);
		}
	}

}
