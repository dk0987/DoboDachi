package com.devdk.socialmedia.core.presentation.util

import com.devdk.socialmedia.core.presentation.util.TImeConst.A_HOUR_AGO
import com.devdk.socialmedia.core.presentation.util.TImeConst.A_MINUTE_AGO
import com.devdk.socialmedia.core.presentation.util.TImeConst.DAY_MILLIS
import com.devdk.socialmedia.core.presentation.util.TImeConst.HOUR_MILLIS
import com.devdk.socialmedia.core.presentation.util.TImeConst.JUST_NOW
import com.devdk.socialmedia.core.presentation.util.TImeConst.MINUTE_MILLIS
import com.devdk.socialmedia.core.presentation.util.TImeConst.YESTERDAY

class TimeStampConverter {

    operator fun invoke(timeStamp : Long): String {
        val now = System.currentTimeMillis()
        if (timeStamp > now || timeStamp <= 0) {
            return ""
        }
        val diff = now - timeStamp
        return if (diff < MINUTE_MILLIS) JUST_NOW
        else if (diff < 2 * MINUTE_MILLIS) A_MINUTE_AGO
        else if (diff < 50 * MINUTE_MILLIS)"${ diff / MINUTE_MILLIS } minutes ago"
        else if (diff < 90 * MINUTE_MILLIS) A_HOUR_AGO
        else if (diff < 24 * HOUR_MILLIS)"${diff / HOUR_MILLIS} hours ago"
        else if (diff < 48 * HOUR_MILLIS ) YESTERDAY
        else "${diff / DAY_MILLIS} days ago"
    }
}