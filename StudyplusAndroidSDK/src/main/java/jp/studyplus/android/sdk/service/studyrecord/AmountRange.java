package jp.studyplus.android.sdk.service.studyrecord;

public class AmountRange implements StudyRecordAmount {
	private final int start;
	private final int end;

	public AmountRange(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return end;
	}
}
