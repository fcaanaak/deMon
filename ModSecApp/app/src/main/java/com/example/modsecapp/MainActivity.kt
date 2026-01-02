package com.example.modsecapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import com.example.modsecapp.pages.dashboard.DashboardFragment
import com.example.modsecapp.pages.devices.DevicesFragment
import com.example.modsecapp.pages.report.ReportFragment
import com.example.modsecapp.pages.report.ReportEntry
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var dashboardFragment: DashboardFragment
    private lateinit var reportFragment: ReportFragment
    private lateinit var devicesFragment: DevicesFragment

    private var reports = arrayListOf<ReportEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder().apply {
            baseUrl("http://10.0.0.114:8000/")
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        val httpClient = retrofit.create(HttpClient::class.java)

        val call = httpClient.getReports()


        call.enqueue(object : Callback<ArrayList<ReportEntry>>{

            override fun onResponse(
                call: Call<ArrayList<ReportEntry>>,
                response: Response<ArrayList<ReportEntry>>
            ) {

                if (response.isSuccessful){


                    if (response.body() != null) {
                        reports = response.body()!!
                        for (report in reports){
                            Log.d("DEBUG",report.toString())
                            var content = ""
                            content += "name: ${report.deviceName}\n"
                            content += "year: ${report.year}\n"
                            content += "month: ${report.month}\n"
                            content += "day: ${report.day}\n"
                            content += "hour: ${report.hour}\n"
                            content += "minute: ${report.minute}\n"
                            content += "second: ${report.second}\n"

                            Log.d("DEBUG",content)
                        }
                    }

                }
            }

            override fun onFailure(
                call: Call<ArrayList<ReportEntry>?>,
                t: Throwable
            ) {
                Log.e("HTTP ERROR",t.toString())
                Toast.makeText(this@MainActivity,"Error: ${t.toString()}",Toast.LENGTH_LONG).show()
            }


        })

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        dashboardFragment = DashboardFragment()
        reportFragment = ReportFragment()
        devicesFragment = DevicesFragment()

        setCurrentFragment(dashboardFragment)

        bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId){
                R.id.dashboard -> setCurrentFragment(dashboardFragment)
                R.id.report -> setCurrentFragment(reportFragment)
                R.id.devices -> setCurrentFragment(devicesFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment: Fragment){

        val reportBundle = Bundle()

        reportBundle.putSerializable("reports",reports)

        supportFragmentManager.beginTransaction().apply {
            reportFragment.arguments = reportBundle
            replace(R.id.flFragment,fragment)
            commit()
        }
    }



}