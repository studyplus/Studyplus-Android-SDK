package jp.studyplus.android.sdk

import jp.studyplus.android.sdk.internal.api.ApiManager
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.concurrent.TimeUnit

object MockApiManager {
    val retrofit by lazy {
        val behavior = NetworkBehavior.create()
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)

        MockRetrofit.Builder(ApiManager.retrofit)
                .networkBehavior(behavior)
                .build()!!
    }
}