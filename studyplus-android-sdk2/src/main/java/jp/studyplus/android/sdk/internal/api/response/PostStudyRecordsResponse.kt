package jp.studyplus.android.sdk.internal.api.response

import com.google.gson.annotations.SerializedName

internal data class PostStudyRecordsResponse(
        @SerializedName("record_id")
        val recordId: Long? = null
)