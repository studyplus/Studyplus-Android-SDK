# Studyplus-Android-SDK

[![CircleCI](https://circleci.com/gh/studyplus/Studyplus-Android-SDK/tree/master.svg?style=svg)](https://circleci.com/gh/studyplus/Studyplus-Android-SDK/tree/master) [![](https://jitpack.io/v/studyplus/Studyplus-Android-SDK.svg)](https://jitpack.io/#studyplus/Studyplus-Android-SDK)

## Requirements

- Android 5+
- [Studyplus App 5.0.+](https://play.google.com/store/apps/details?id=jp.studyplus.android.app)

## Import in your Project

### Gradle file (app)

Add it in your root build.gradle at the end of repositories:

```groovy
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
    }
}
```

```groovy
    dependencies {
        implementation 'com.github.studyplus:Studyplus-Android-SDK:2.5.0'
    }
```

## Usage

### Setup

```kotlin
Studyplus.instance.setup("consumer_key", "consumer_secret")
```

### Authenticate

Open an Activity to connect with Studyplus.

```kotlin
try {
    Studyplus.instance.startAuth(this@MainActivity, REQUEST_CODE_AUTH)
} catch (e: ActivityNotFoundException) {
    e.printStackTrace()
    Toast.makeText(context, "Need for Studyplus 5.0.0+", Toast.LENGTH_LONG).show()
}
```

Then save its result.

```kotlin
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

```kotlin
val record = StudyRecord(
    duration = 2 * 60,
    amount = StudyRecordAmountTotal(30),
    comment = "勉強した！！！"
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

- See also [actual examples with Kotlin](https://github.com/studyplus/Studyplus-Android-SDK/blob/master/sdk-example-kt/src/main/java/jp/studyplus/android/sdk_example_kt/MainActivity.kt).
- See also [actual examples with Java](https://github.com/studyplus/Studyplus-Android-SDK/blob/master/sdk-example-java/src/main/java/jp/studyplus/android/sdk_example_java/MainActivity.java).

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
