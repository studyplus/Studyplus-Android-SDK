package jp.studyplus.android.sdk.internal.api.response

import com.google.gson.annotations.SerializedName

data class PostStudyRecordsResponse(
        @SerializedName("record_id")
        val recordId: Long? = null
)