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
    mavenCentral()
}
dependencies {
    compile 'jp.studyplus.android.sdk:studyplus-android-sdk:1.0.2'
}
```

## Usage

### Authenticate

Open an Activity to connect with Studyplus.

```java
try {
    AuthTransit.from(activity).startActivity(
	    context.getString("app_consumer_key"),
	    context.getString("app_consumer_key_secret")
    );
} catch (ActivityNotFoundException e) {
    e.printStackTrace();
    Toast.makeText(context, "Need for Studyplus 2.14.0+", Toast.LENGTH_SHORT).show();
}
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

try {
    StudyplusApi.getClient(context).send(request);
} catch (AccessTokenNotFound e) {
    e.printStackTrace();
    Toast.makeText(context, "Access token not exists", Toast.LENGTH_SHORT).show();
}
```

### More

* See also [actual examples](https://github.com/studyplus/Studyplus-Android-SDK/blob/master/SDKExample/src/main/java/jp/studyplus/android/sdk/example/ExampleActivity.java).

## License

* [MIT License](http://opensource.org/licenses/MIT)
