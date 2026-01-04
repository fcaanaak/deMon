package com.example.modsecapp.pages.report

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.nio.channels.FileLock


class ReportFetcher(val context: Context){

    val savedReportFileName = "savedReports"

     fun getStoredReports(): ArrayList<ReportEntry>{

         return try{
             val fileInput = context.openFileInput(savedReportFileName)
             val inputStream = ObjectInputStream(fileInput)

             val reports: ArrayList<ReportEntry> = inputStream.readObject() as ArrayList<ReportEntry>

             inputStream.close()
             fileInput.close()

             reports
         } catch (e: IOException){
             arrayListOf()
         }

    }


     fun updateSavedReports(latestReports:ArrayList<ReportEntry>){

         val fetchedReport = getStoredReports()

         val finalReports:ArrayList<ReportEntry> = (latestReports + fetchedReport) as ArrayList<ReportEntry>

         try {
             writeToReports(finalReports)
         } catch (e: IOException){
             Toast.makeText(context,"IO EXCEPTION OCCURED WHEN FETCHING REPORTS",Toast.LENGTH_LONG)
         }
     }


    private fun writeToReports(inputReports:ArrayList<ReportEntry>){

        val fos = context.openFileOutput(savedReportFileName, Context.MODE_PRIVATE)

        val os = ObjectOutputStream(fos)

        os.writeObject(inputReports)

        os.close()
        fos.close()


    }

    private fun clearStoredReports(){
        writeToReports(arrayListOf())
    }



}