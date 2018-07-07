# Studyplus-Android-SDK-V2

## Requirements
- Android 2.2+
- [Studyplus App 2.14+](https://play.google.com/store/apps/details?id=jp.studyplus.android.app)


## Import in your Project

### Gradle file (app)
```
repositories {
    mavenCentral()
}
dependencies {
    compile ('jp.studyplus.android.sdk:studyplus-android-sdk:2.0.0@aar') {
        transitive = true
    }
}
```

### Maven
```
<dependency>
  <groupId>jp.studyplus.android.sdk</groupId>
  <artifactId>studyplus-android-sdk</artifactId>
  <version>2.0.0</version>
</dependency>
```

or download the latest JAR [via Central Repository](http://search.maven.org/#search%7Cga%7C1%7Cstudyplus)

## Usage

### Setup
```
Studyplus.instance.setup("consumer_key", "consumer_secret")
```

### Authenticate

Open an Activity to connect with Studyplus.
```
try {
    Studyplus.instance.startAuth(this@MainActivity, REQUEST_CODE_AUTH)
} catch (e: ActivityNotFoundException) {
    e.printStackTrace()
    Toast.makeText(context, "Need for Studyplus 2.14.0+", Toast.LENGTH_LONG).show()
}
```

Then save its result.
```
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    when (requestCode) {
        REQUEST_CODE_AUTH -> {
            if (resultCode == Activity.RESULT_OK) {
                Studyplus.instance.setAuthResult(this, data)
                Toast.makeText(this@MainActivity, "Success!!", Toast.LENGTH_LONG).show()
            }
        }
    }
}
```

### Post a record to Studyplus
Create a record and post.
```
val record = StudyRecordBuilder()
        .setComment("勉強した！！！")
        .setAmountTotal(30)
        .setDurationSeconds(2 * 60)
        .build()
Studyplus.instance.postRecord(this@MainActivity, record,
        object : Studyplus.Companion.OnPostRecordListener {
            override fun onResult(success: Boolean, recordId: Long?, throwable: Throwable?) {
                if (success) {
                    Toast.makeText(context, "Post Study Record!! ($recordId)", Toast.LENGTH_LONG).show()
                } else {
                    throwable?.apply {
                        Toast.makeText(context, "error!!", Toast.LENGTH_LONG).show()
                        printStackTrace()
                    }
                }
            }
        })
```

### More
- See also [actual examples with Kotlin]().
- See also [actual examples with Java]().

### License
- [MIT License](http://opensource.org/licenses/MIT)
