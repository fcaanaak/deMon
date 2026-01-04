package com.example.modsecapp.httpclient

import com.example.modsecapp.pages.report.ReportEntry
import retrofit2.Call
import retrofit2.http.GET

interface HttpRequestManager {
    @GET("reports")
    fun getReports(): Call<ArrayList<ReportEntry>>
}