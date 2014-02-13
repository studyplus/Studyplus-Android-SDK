package jp.studyplus.android.sdk.service.studyrecord;

public class AmountTotal implements StudyRecordAmount {
	private final int total;

	public AmountTotal(int total) {
		this.total = total;
	}
	public int getTotal() {
		return total;
	}
}
