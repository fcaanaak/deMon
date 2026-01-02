package com.example.modsecapp.pages.report

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable


data class ReportEntry(val deviceName:String, val year:Int, val month:Int, val day:Int,
    val hour:Int, val minute:Int, val second: Int): Serializable{
    val displayDate = "Date: $year-$month-$day"
    val displayTime = "Time: $hour:$minute:$second"

}