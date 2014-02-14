package jp.studyplus.android.sdk.service.studyrecord;

import com.google.common.base.Optional;

interface StudyRecordSnapshot {
	public String getRecordedTime();

	public String getComment();

	public int getDuration();

	public Optional<StudyRecordAmount> getAmount();
}
