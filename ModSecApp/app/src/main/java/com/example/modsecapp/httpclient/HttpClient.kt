package com.example.modsecapp.httpclient
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.modsecapp.pages.report.ReportEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpClient(private val context: Context, private val serverIP: String) {

    private enum class REQUEST_METHODS{
        POST,
        GET,
        PUT,
        DELETE
    }


    var latestReports = arrayListOf<ReportEntry>()

    private val retrofit  = Retrofit.Builder().apply {
        baseUrl("http://${serverIP}:8000/")
        addConverterFactory(GsonConverterFactory.create())
    }.build()

    private val httpRequestManager = retrofit.create(HttpRequestManager::class.java)

    fun getReports(callback: (ArrayList<ReportEntry>) -> Unit){

        val call = httpRequestManager.getReports()

        call.enqueue(ReportGetHandler(context,serverIP,callback))


    }

}


