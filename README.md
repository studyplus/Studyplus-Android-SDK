# Studyplus-Android-SDK

[![](https://jitpack.io/v/studyplus/Studyplus-Android-SDK.svg)](https://jitpack.io/#studyplus/Studyplus-Android-SDK)
![Unit test](https://github.com/studyplus/Studyplus-Android-SDK/workflows/Unit%20test/badge.svg)

## Requirements

- Android 6+
- [Studyplus App 7.0.+](https://play.google.com/store/apps/details?id=jp.studyplus.android.app)

## Import in your Project

### Gradle file (app)

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
android {
    compileOptions {
        coreLibraryDesugaringEnabled true
    }
}

dependencies {
    coreLibraryDesugaring 'com.android.tools:desugar_jdk_libs:2.0.4'

    implementation 'com.github.studyplus:Studyplus-Android-SDK:4.0.2'
}
```

## Usage

### Setup

If you want to handle StudyplusSDK instance as a singleton, use [Dagger](https://dagger.dev).

```kotlin
private val instance by lazy {
    Studyplus(
        context = this,
        consumerKey = "consumer_key",
        consumerSecret =  "consumer_secret",
    )
}
```

### Authenticate

Open an Activity to connect with Studyplus.

```kotlin
instance.startAuth(this@MainActivity, REQUEST_CODE_AUTH)
```

Then save the result.

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode != RESULT_OK || data == null) {
        Toast.makeText(this, "error!!", Toast.LENGTH_LONG).show()
        return
    }

    if (requestCode == REQUEST_CODE_AUTH) {
        instance.setAuthResult(data)
        Toast.makeText(this, "Success!!", Toast.LENGTH_LONG).show()
    }
}
```

### Unauth

```kotlin
instance.cancelAuth()
```

### Post a record to Studyplus

Create a record and post.

```kotlin
val record = StudyRecord(
    duration = 2 * 60,
    amount = StudyRecordAmountTotal(30),
    comment = "勉強した！！！",
)
instance.postRecord(record, object : PostCallback {
    override fun onSuccess() {
        Toast.makeText(context, "Post Study Record!!", Toast.LENGTH_LONG).show()
    }

    override fun onFailure(e: StudyplusError) {
        Toast.makeText(context, "error!!", Toast.LENGTH_LONG).show()
    }
})
```

### More

- See also [Sample Project](https://github.com/studyplus/Studyplus-Android-SDK/blob/master/sdk-example-kt/src/main/java/jp/studyplus/android/sdk_example_kt/MainActivity.kt).

### License

```text
MIT License

Copyright (c) 2021 Studyplus, Inc.

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
