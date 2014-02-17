Studyplus-Android-SDK
=====================

Studyplus SDK for Android

##Requirement

* Android 2.2+
* [Studyplus App 2.14+](https://play.google.com/store/apps/details?id=jp.studyplus.android.app)

##Import in your Project

Graddle Project

```groovy
repositories {
    maven { url 'http://raw.github.com/studyplus/Studyplus-Android-SDK/master/repository' }
}
dependencies {
    compile 'jp.studyplus.android.sdk:studyplus-android-sdk:1.0.0'
}
```

## Usage

### Authenticate

Open an Activity to connect with Studyplus.

```java
AuthTransit.from(activity).startActivity(
	context.getString("app_consumer_key"),
	context.getString("app_consumer_key_secret")
);
```

Then save its result.

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	CertificationStore.create(context).update(data);
}
```

### Post a record to Studyplus

Create ApiRequest and send it with ApiClient.

```java
ApiRequest request = StudyRecordPostRequest.of(
	new StudyRecordBuilder()
		.setComment("perfect!")
		.setAmountTotal(30)
		.setDurationSeconds(2 * 60)
		.build()
);
StudyplusApi.getClient(context).send(request);
```

### More

* See also [actual examples](https://github.com/studyplus/Studyplus-Android-SDK/blob/master/SDKExample/src/main/java/jp/studyplus/android/sdk/example/ExampleActivity.java).

## License

* [MIT License](http://opensource.org/licenses/MIT)
