package com.example.vod.main.utils

import java.text.SimpleDateFormat
import java.util.*

class DateHelper {


    fun getDurationStringFromDate(duration: Long): String {
        val sdf = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return sdf.format(duration?.minus(TimeZone.getDefault().rawOffset)?.let { Date(it) })
    }
}

