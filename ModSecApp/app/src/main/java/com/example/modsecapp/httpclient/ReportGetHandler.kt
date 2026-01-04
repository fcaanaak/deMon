package com.example.modsecapp.httpclient

import android.content.Context
import android.util.Log

import android.widget.Toast


import com.example.modsecapp.pages.report.ReportEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ReportGetHandler(val context: Context, serverIP:String,val callback:(ArrayList<ReportEntry>)-> Unit) : Callback<ArrayList<ReportEntry>> {


    private val serverConnectionError = "Error, could not connect to server: $serverIP"

    override fun onResponse(
        call: Call<ArrayList<ReportEntry>>,
        response: Response<ArrayList<ReportEntry>>
    ) {

        if (response.isSuccessful){
            callback(response.body()!!)
        }
    }

    override fun onFailure(
        call: Call<ArrayList<ReportEntry>>,
        t: Throwable
    ) {
        Toast.makeText(context,"DELETE ME LATER",Toast.LENGTH_LONG).show()
    }




}