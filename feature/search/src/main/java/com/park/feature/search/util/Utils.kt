package com.park.feature.search.util

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.toDateText(): String {
    val inputFormat = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        Locale.US
    ).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    val outputFormat = SimpleDateFormat(
        "yyyy년 M월 d일",
        Locale.KOREA
    ).apply {
        timeZone = TimeZone.getDefault()
    }

    val date = runCatching {
        inputFormat.parse(this)
    }.getOrNull()

    return date?.let { outputFormat.format(it) }.orEmpty()
}