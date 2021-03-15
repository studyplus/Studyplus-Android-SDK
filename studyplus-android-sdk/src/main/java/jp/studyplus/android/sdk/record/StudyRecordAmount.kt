package jp.studyplus.android.sdk.record

/**
 * Studyplusに投稿する学習記録の学習量
 *
 * @since 2.5.0
 */
interface StudyRecordAmount {
    val amountTotal: Int?
    val startPosition: Int?
    val endPosition: Int?
}

/**
 * 学習記録の合計学習量
 *
 * @param amountTotal 合計学習量
 * @since 2.5.0
 */
data class StudyRecordAmountTotal(override val amountTotal: Int) : StudyRecordAmount {
    override val startPosition: Int? = null
    override val endPosition: Int? = null
}

/**
 * 学習記録の学習範囲
 *
 * @param startPosition 学習量の起点
 * @param endPosition   学習量の終点
 * @since 2.5.0
 */
data class StudyRecordAmountRange(override val startPosition: Int, override val endPosition: Int) :
    StudyRecordAmount {
    override val amountTotal: Int? = null
}
