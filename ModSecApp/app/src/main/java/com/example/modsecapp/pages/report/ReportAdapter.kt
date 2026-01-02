package com.example.modsecapp.pages.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.modsecapp.R
import com.example.modsecapp.Constants

class ReportAdapter( var reportList: List<ReportEntry>) :
    RecyclerView.Adapter<ReportAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.report_entry, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val reportEntry = reportList[position]

        holder.entryDeviceName.text = reportEntry.deviceName

        // Change the below lines back later
        holder.entryDate.text = "Date: ${reportEntry.year}-${Constants.MONTH_STRINGS[reportEntry.month-1]}-${reportEntry.day}"
        holder.entryTime.text = "Time: ${reportEntry.hour}:${reportEntry.minute}:${reportEntry.second}"

    }

    override fun getItemCount(): Int {
        return reportList.size
    }

    fun updateList(newList: List<ReportEntry>){
        reportList = newList
        notifyDataSetChanged()
    }

    // ViewHolder class
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entryDeviceName: TextView = itemView.findViewById(R.id.entryDeviceName)
        val entryDate: TextView = itemView.findViewById(R.id.entryDate)
        val entryTime: TextView = itemView.findViewById(R.id.entryTime)


    }
}