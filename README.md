# Studyplus-Android-SDK-V2

[![CircleCI](https://circleci.com/gh/studyplus/Studyplus-Android-SDK-V2/tree/master.svg?style=svg)](https://circleci.com/gh/studyplus/Studyplus-Android-SDK-V2/tree/master) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/jp.studyplus.android.sdk/studyplus-android-sdk2/badge.svg)](https://maven-badges.herokuapp.com/maven-central/jp.studyplus.android.sdk/studyplus-android-sdk2)

## Requirements
- Android 4.1+
- [Studyplus App 2.14+](https://play.google.com/store/apps/details?id=jp.studyplus.android.app)


## Import in your Project

### Gradle file (app)

```
repositories {
    mavenCentral()
}
dependencies {
    implementation 'jp.studyplus.android.sdk:studyplus-android-sdk2:2.2.1'
}
```

### Maven

```
<dependency>
  <groupId>jp.studyplus.android.sdk</groupId>
  <artifactId>studyplus-android-sdk2</artifactId>
  <version>2.2.1</version>
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
val record = StudyRecord(
    duration = 2 * 60,
    comment = "勉強した！！！",
    amount = StudyRecordAmount(30)
)
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
- See also [actual examples with Kotlin](https://github.com/studyplus/Studyplus-Android-SDK-V2/blob/master/sdk-example-kt/src/main/java/jp/studyplus/android/sdk_example_kt/MainActivity.kt).
- See also [actual examples with Java](https://github.com/studyplus/Studyplus-Android-SDK-V2/blob/master/sdk-example-java/src/main/java/jp/studyplus/android/sdk_example_java/MainActivity.java).

### License

```text
MIT License

Copyright (c) 2019 Studyplus, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```