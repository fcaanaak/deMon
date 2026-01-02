package com.example.modsecapp

class Constants {


    companion object {
        const val DEVICE_NAME_CHAR_LIMIT: UInt = 30u


        // Filtration Constants


        // Filtration Time Constants
        const val DEFAULT_FILTER_TIME_VALUE: Int = 0
        const val FILTER_TIME_MIN: Int = 0
        const val FILTER_HOUR_MAX: Int = 23
        const val FILTER_MINUTE_MAX: Int = 59

        // Filtration Date Constants
        const val DEFAULT_FILTER_DATE_VALUE: Int = 0

        const val FILTER_MONTH_MIN: Int = 0
        const val FILTER_MONTH_MAX: Int = 12

        const val FILTER_DAY_MIN: Int = 1
        const val FILTER_DAY_MAX:Int = 31

        const val FILTER_YEAR_MIN:Int = 1900
        const val FILTER_YEAR_MAX:Int = 2100

        // Date utility constants

        val MONTH_STRINGS: List<String> = listOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        )



    }
}