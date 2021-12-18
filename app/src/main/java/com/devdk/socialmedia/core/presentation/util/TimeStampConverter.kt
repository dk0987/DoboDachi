package com.devdk.socialmedia.core.presentation.util

import android.text.format.DateUtils

class TimeStampConverter {
    private val SECOND_MILLIS = 1000;
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private val DAY_MILLIS = 24 * HOUR_MILLIS;
    operator fun invoke(timestamp : Long) : String? {

        val now = System.currentTimeMillis()
        if (timestamp > now || timestamp <= 0) {
            return null;
        }
        println(if (timestamp < 1000000000000L) " Correct" else "")
        val diff = now - timestamp;
        return if (diff < MINUTE_MILLIS) {
            "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            " ${ diff / MINUTE_MILLIS } minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            "${diff / HOUR_MILLIS} hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday";
        } else {
            " ${diff / DAY_MILLIS} days ago"
        }
    }
}