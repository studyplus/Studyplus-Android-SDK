package jp.studyplus.android.sdk.internal.api

fun <T1, T2> notNull(p1: T1?, p2: T2?, f: (p1: T1, p2: T2) -> Unit) {
    if (p1 != null && p2 != null) f(p1, p2)
}