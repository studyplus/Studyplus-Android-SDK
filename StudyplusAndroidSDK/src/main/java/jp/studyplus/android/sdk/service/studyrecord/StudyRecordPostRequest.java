package jp.studyplus.android.sdk.service.studyrecord;

import com.google.common.base.Optional;

import org.json.JSONException;
import org.json.JSONObject;

import jp.studyplus.android.sdk.service.api.ApiMethod;
import jp.studyplus.android.sdk.service.api.ApiRequest;


public class StudyRecordPostRequest extends ApiRequest {
	private final StudyRecordSnapshot snapshot;

	public static StudyRecordPostRequest of(StudyRecordSnapshot snapshot){
		return new StudyRecordPostRequest(snapshot);
	}

	private StudyRecordPostRequest(StudyRecordSnapshot snapshot) {
		super();
		this.snapshot = snapshot;
	}

	@Override
	public String getScriptPath() {
		return "/v1/study_records";
	}

	@Override
	public ApiMethod getMethod() {
		return ApiMethod.POST;
	}

	@Override
	public Optional<JSONObject> getJsonContent() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("recorded_at", snapshot.getRecordedTime());
		json.put("duration", snapshot.getDuration());
		json.put("comment", snapshot.getComment());

		for (StudyRecordAmount amount : snapshot.getAmount().asSet()){
			if (amount instanceof AmountTotal){
				json.put("amount", ((AmountTotal) amount).getTotal());
			} else if(amount instanceof AmountRange) {
				json.put("start_position", ((AmountRange) amount).getStart());
				json.put("end_position", ((AmountRange) amount).getEnd());
			} else {
				// ignore unknown amount type
			}
		}
		return Optional.of(json);
	}
}
