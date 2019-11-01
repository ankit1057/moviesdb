package com.example.vod.main.model

import android.annotation.SuppressLint
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


@Suppress("UNREACHABLE_CODE")
data class MovieModel(var popularity: Float, var vote_count: Int, var id: Int,
                      var original_language: String?, var original_title: String?,
                      var overview: String?, var poster_path: String?, var release_date: String?):Serializable,Comparable<MovieModel> {

    override fun compareTo(other: MovieModel): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date1 = dateFormat.parse(other.release_date)
        val date2 = dateFormat.parse(release_date)
        val mCalendar = Calendar.getInstance()
        mCalendar.time = date1
       val month1 = mCalendar.get(Calendar.MONTH)
        mCalendar.time = date2
        val month2 = mCalendar.get(Calendar.MONTH)
        when {
            month2 != month1 -> month2 -month1
        }

    }
}



