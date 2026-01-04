package com.example.modsecapp.pages.report

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.modsecapp.R
import com.example.modsecapp.httpclient.HttpClient

import java.util.Calendar
import com.example.modsecapp.pages.report.filter.ReportFilter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

/**
 * A simple [androidx.fragment.app.Fragment] subclass.
 * Use the [ReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReportFragment : Fragment() {


    // UI Widgets
    private lateinit var dateFilterButton: Button
    private lateinit var timeFilterButton: Button
    private lateinit var reportFiltersChipGroup: ChipGroup
    private lateinit var reportFilterChipYear: Chip
    private lateinit var reportFilterChipMonth: Chip
    private lateinit var reportFilterChipDay: Chip
    private lateinit var reportFilterChipHour: Chip
    private lateinit var reportFilterChipMinute: Chip

    private lateinit var reportRecyclerView: RecyclerView
    private lateinit var reportEntryAdapter: ReportAdapter

    // Entry arrays

    private lateinit var originalEntries: MutableList<ReportEntry>


    // Filter Object

    private lateinit var reportFilter: ReportFilter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        reportFilter = ReportFilter

        // Inflate the layout for this fragment
        val view: View? = inflater.inflate(R.layout.fragment_report, container, false)
        setupReportData(view)

        dateFilterButton = view!!.findViewById(R.id.reportFilterDateButton)
        timeFilterButton = view!!.findViewById(R.id.reportFilterTimeButton)

        reportFiltersChipGroup = view!!.findViewById(R.id.reportFiltersChipGroup)

        reportFilterChipYear = view!!.findViewById(R.id.reportFilterChipYear)
        reportFilterChipMonth = view!!.findViewById(R.id.reportFilterChipMonth)
        reportFilterChipDay = view!!.findViewById(R.id.reportFilterChipDay)
        reportFilterChipHour = view!!.findViewById(R.id.reportFilterChipHour)
        reportFilterChipMinute = view!!.findViewById(R.id.reportFilterChipMinute)

        setupFilterGroup()
        setupDateFilter(view,dateFilterButton)
        setupTimeFilter(view,timeFilterButton)


        updateReportEntries(ReportFetcher(context!!).getStoredReports())


        return view
    }



    /**
     * Get all the reports filtered by the active filter keys
     * @return A (mutable) list of all reports filtered by active keys that match the filters
     */
    private fun getFilteredReportEntries(): MutableList<ReportEntry>{

        return originalEntries.filter{

            mutableMapOf(
                reportFilter.yearKey to it.year,
                reportFilter.monthKey to it.month,
                reportFilter.dayKey to it.day,
                reportFilter.hourKey to it.hour,
                reportFilter.minuteKey to it.minute,
            ).filter{(k, _)->
                k.active
            }.all{
                (k,v) -> k.data == v
            }

        }.toMutableList()

    }

    /**
     * Set up behavior for the filtration system
     */
    private fun setupFilterGroup(){

        reportFiltersChipGroup.setOnCheckedStateChangeListener { group, checkedIds ->

            reportFilter.disableAllFilters()

            for (id in checkedIds){

                when (id){
                    R.id.reportFilterChipYear -> reportFilter.yearKey.active = true
                    R.id.reportFilterChipMonth -> reportFilter.monthKey.active = true
                    R.id.reportFilterChipDay -> reportFilter.dayKey.active = true
                    R.id.reportFilterChipHour -> reportFilter.hourKey.active = true
                    R.id.reportFilterChipMinute -> reportFilter.minuteKey.active = true
                }
            }

            reportEntryAdapter.updateList(getFilteredReportEntries())

        }
    }


    private fun setupTimeFilter(view: View?, timeFilterButton: Button){

        val leastDoubleDigitNumber = 10

        timeFilterButton.setOnClickListener {

            val c = Calendar.getInstance()

            val hour = c.get(Calendar.HOUR)
            val minute = c.get(Calendar.MINUTE)

            val timePickerDialog = this.context?.let {it ->
                TimePickerDialog(
                    it,
                    { view, hour, minute ->

                        val hourString = if (hour < leastDoubleDigitNumber) "0$hour" else hour
                        val minuteString = if (minute < leastDoubleDigitNumber) "0$minute" else minute

                        timeFilterButton.text = "Time Filter $hourString:$minuteString"

                        reportFilter.minuteKey.data = minute
                        reportFilter.hourKey.data = hour

                        reportFilterChipHour.text = getString(R.string.report_filter_chip_hour_text, hour)
                        reportFilterChipMinute.text = getString(R.string.report_filter_chip_minute_text, minute)

                    },
                    hour,
                    minute,
                    false
                ).apply { show() }
            }
        }

    }
    private fun setupDateFilter(view: View?, dateFilterButton: Button){

        dateFilterButton.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = this.context?.let { it1 ->
                DatePickerDialog(

                    it1,
                    { view, year, monthOfYear, dayOfMonth ->
                        val dat = "Date Filter ${(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)}"
                        dateFilterButton.text = dat

                        reportFilter.yearKey.data = year
                        // datepicker months go from [0-11] but java localdatetime months go from [1,12]
                        reportFilter.monthKey.data = monthOfYear+1
                        reportFilter.dayKey.data = dayOfMonth

                        setDateChipsText(year,monthOfYear,dayOfMonth)
                    },
                    year,
                    month,
                    day
                )
            }
            datePickerDialog!!.show()
        }
    }


    /**
     * Set the text for all the date related filter chips to the passed in date values
     *
     * @param year the year for the year filter
     * @param month the year for the month filter
     * @param day the year for the day filter
     */
    private fun setDateChipsText(year:Int, month:Int, day: Int){

        val monthNames: Array<String> = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "July", "Aug", "Sept", "Oct" , "Nov", "Dec"
        )

        reportFilterChipYear.text = getString(
            R.string.report_filter_chip_year_text,
            year
        )

        reportFilterChipMonth.text = getString(
            R.string.report_filter_chip_month_text,
            monthNames[month]
        )

        reportFilterChipDay.text = getString(
            R.string.report_filter_chip_day_text,
            day
        )
    }

    /**
     * Hook up the data array that holds the reports to the UI
     *
     * @param view the view object for the reports page
     */
    private fun setupReportData(view: View?){

        // Ignore the warning here, we are passing in a view created earlier
        reportRecyclerView = view!!.findViewById(R.id.recyclerView)

        originalEntries = mutableListOf()

        reportRecyclerView.layoutManager = LinearLayoutManager(this@ReportFragment.context)

        reportEntryAdapter = ReportAdapter(originalEntries)
        reportRecyclerView.adapter = reportEntryAdapter

    }


    private fun updateReportEntries(reportsList: ArrayList<ReportEntry>){

        originalEntries = reportsList.toMutableList()
        reportEntryAdapter.updateList(originalEntries)

    }

}

