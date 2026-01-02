package com.example.modsecapp

import android.content.Context
import com.example.modsecapp.pages.report.ReportEntry

import retrofit2.Call
import retrofit2.http.GET


interface HttpClient {

    @GET("reports")
    fun getReports(): Call<ArrayList<ReportEntry>>
}