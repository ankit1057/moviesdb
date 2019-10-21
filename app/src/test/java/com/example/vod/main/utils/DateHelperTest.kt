package com.example.vod.main.utils

import org.junit.Assert
import org.junit.Test
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.test.KoinTest

class DateHelperTest: KoinTest {

    private val dateHelper by inject<DateHelper>()
    @Test
    fun getDurationStringFromDate_WhenDateIs3600000() {
        var actual=dateHelper.getDurationStringFromDate(3600000)
        var expected="01:00:00"
        Assert.assertEquals(expected,actual)
    }
}