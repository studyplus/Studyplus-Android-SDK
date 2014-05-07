package jp.studyplus.android.sdk.service.studyrecord;

import com.google.common.base.Optional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class StudyRecordBuilder {

	private Optional<String> comment = Optional.absent();
	private Optional<StudyRecordAmount> amount = Optional.absent();
	private Optional<Integer> duration = Optional.absent();
	private Optional<String> recordedTime = Optional.absent();

	public StudyRecordBuilder setComment(String comment){
		this.comment = Optional.of(comment);
		return this;
	}

	public StudyRecordBuilder setAmountTotal(int amount){
		this.amount = Optional.<StudyRecordAmount>of(new AmountTotal(amount));
		return this;
	}

	public StudyRecordBuilder setAmountRange(int start, int end){
		this.amount = Optional.<StudyRecordAmount>of(new AmountRange(start, end));
		return this;
	}

	public StudyRecordBuilder setDurationSeconds(int seconds){
		this.duration = Optional.of(seconds);
		return this;
	}

	public StudyRecordBuilder setRecordedTime(Calendar calendar){
		SimpleDateFormat format = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss", Locale.US);
		format.setTimeZone(calendar.getTimeZone());
		String time = format.format(calendar.getTime());
		this.recordedTime = Optional.of(time);
		return this;
	}

	public StudyRecordSnapshot build(){
		if (!recordedTime.isPresent()){
			Calendar current = new GregorianCalendar(TimeZone.getTimeZone("UTC"), Locale.US);
			setRecordedTime(current);
		}
		if (!comment.isPresent()){
			setComment("");
		}
		if (!duration.isPresent()){
			setDurationSeconds(0);
		}
		return new StudyRecordSnapshotImpl();
	}

	private class StudyRecordSnapshotImpl implements StudyRecordSnapshot {

		@Override
		public String getRecordedTime() {
			return recordedTime.get();
		}

		@Override
		public String getComment() {
			return comment.get();
		}

		@Override
		public int getDuration() {
			return duration.get();
		}

		@Override
		public Optional<StudyRecordAmount> getAmount() {
			return amount;
		}
	}
}
