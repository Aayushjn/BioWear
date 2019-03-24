package com.aayush.biowear.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.fragment.app.Fragment
import com.aayush.biowear.R
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.data.Set
import com.anychart.enums.*
import com.anychart.graphics.vector.Stroke
import kotlinx.android.synthetic.main.fragment_detail.*
import timber.log.Timber


private const val ARG_SOURCE = "source"

class DetailFragment : Fragment() {
    private var source: String? = null
    private lateinit var title: String
    private lateinit var yAxisTitle: String
    private lateinit var cartesian: Cartesian
    private var values = ArrayList<CustomDataEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            source = it.getString(ARG_SOURCE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        val supportActionBar = (activity as AppCompatActivity).supportActionBar!!
        supportActionBar.title = "Data Trends"
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_back)
        supportActionBar.setDisplayHomeAsUpEnabled(true)

        when (source) {
            "Steps" -> {
                title = "Trend of steps data"
                yAxisTitle = "Steps"

                cartesian = AnyChart.column()
                values.add(CustomDataEntry("09/03", 7884))
                values.add(CustomDataEntry("10/03", 2335))
                values.add(CustomDataEntry("11/03", 3257))
                values.add(CustomDataEntry("12/03", 6181))
                values.add(CustomDataEntry("13/03", 6587))
                values.add(CustomDataEntry("14/03", 6067))
                values.add(CustomDataEntry("15/03", 8766))
                values.add(CustomDataEntry("16/03", 12481))
                values.add(CustomDataEntry("17/03", 3467))
                values.add(CustomDataEntry("18/03", 7638))
                values.add(CustomDataEntry("19/03", 3847))
                values.add(CustomDataEntry("20/03", 5985))
                values.add(CustomDataEntry("21/03", 2431))
                values.add(CustomDataEntry("22/03", 6081))
                values.add(CustomDataEntry("23/03", 6824))

                val column = cartesian.column(values as List<DataEntry>?)

                column.tooltip()
                    .titleFormat("{%X}")
                    .position(Position.CENTER_BOTTOM)
                    .anchor(Anchor.CENTER_BOTTOM)
                    .offsetX(0.0)
                    .offsetY(5.0)
                    .format("{%Value}{groupsSeparator: }")

                cartesian.yScale().minimum(0.0)

                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }")

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
                cartesian.interactivity().hoverMode(HoverMode.BY_X)

                var sum = 0
                for (i in 0 until values.size) {
                    sum += (values[i].data as Int)
                }
                averageTextView.text = "You have covered ${sum/values.size} steps on an average"
            }
            "HeartRate" -> {
                title = "Trend of heart rate data"
                yAxisTitle = "Heart rate (BPM)"
                cartesian = AnyChart.line()
                cartesian.padding(10, 20, 5, 20)
                cartesian.crosshair().enabled()
                cartesian.crosshair()
                    .yLabel(true)
                    .yStroke(null as Stroke?, null, null, null as String?, null as String?)

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

                cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

                values.add(CustomDataEntry("09/03", 78))
                values.add(CustomDataEntry("10/03", 86))
                values.add(CustomDataEntry("11/03", 72))
                values.add(CustomDataEntry("12/03", 97))
                values.add(CustomDataEntry("13/03", 86))
                values.add(CustomDataEntry("14/03", 100))
                values.add(CustomDataEntry("15/03", 102))
                values.add(CustomDataEntry("16/03", 68))
                values.add(CustomDataEntry("17/03", 81))
                values.add(CustomDataEntry("18/03", 74))
                values.add(CustomDataEntry("19/03", 69))
                values.add(CustomDataEntry("20/03", 71))
                values.add(CustomDataEntry("21/03", 72))
                values.add(CustomDataEntry("22/03", 70))
                values.add(CustomDataEntry("23/03", 68))

                val set = Set.instantiate()
                set.data(values as List<DataEntry>?)
                val series1Mapping = set.mapAs("{ date: 'date', steps: 'steps' }")

                val series1 = cartesian.line(series1Mapping)
                series1.name("Heart rate")
                series1.hovered().markers().enabled(true)
                series1.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4.0)
                series1.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5.0)
                    .offsetY(5.0)

                cartesian.legend().enabled(true)
                cartesian.legend().fontSize(13.0)
                cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

                var sum = 0
                for (i in 0 until values.size) {
                    sum += (values[i].data as Int)
                }
                averageTextView.text = "Your average heart rate is ${sum/values.size} BPM"
            }
            "Temperature" -> {
                title = "Trend of temperature data"
                yAxisTitle = "Temperature (${0x00B0.toChar()}C)"
                cartesian = AnyChart.line()
                cartesian.padding(10, 20, 5, 20)
                cartesian.crosshair().enabled()
                cartesian.crosshair()
                    .yLabel(true)
                    .yStroke(null as Stroke?, null, null, null as String?, null as String?)

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT)

                cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

                values.add(CustomDataEntry("09/03", 36.5f))
                values.add(CustomDataEntry("10/03", 36.8f))
                values.add(CustomDataEntry("11/03", 36.4f))
                values.add(CustomDataEntry("12/03", 36.0f))
                values.add(CustomDataEntry("13/03", 36.6f))
                values.add(CustomDataEntry("14/03", 36.4f))
                values.add(CustomDataEntry("15/03", 37.4f))
                values.add(CustomDataEntry("16/03", 38.3f))
                values.add(CustomDataEntry("17/03", 38.0f))
                values.add(CustomDataEntry("18/03", 35.8f))
                values.add(CustomDataEntry("19/03", 36.1f))
                values.add(CustomDataEntry("20/03", 36.3f))
                values.add(CustomDataEntry("21/03", 36.0f))
                values.add(CustomDataEntry("22/03", 35.9f))
                values.add(CustomDataEntry("23/03", 36.4f))

                val set = Set.instantiate()
                set.data(values as List<DataEntry>?)
                val series1Mapping = set.mapAs("{ date: 'date', steps: 'steps' }")

                val series1 = cartesian.line(series1Mapping)
                series1.name("Temperature")
                series1.hovered().markers().enabled(true)
                series1.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4.0)
                series1.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5.0)
                    .offsetY(5.0)

                cartesian.legend().enabled(true)
                cartesian.legend().fontSize(13.0)
                cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

                var sum = 0f
                for (i in 0 until values.size) {
                    sum += (values[i].data as Float)
                }
                averageTextView.text = String.format("Your average body temperature is %.2f%sC",
                    sum/values.size, "${0x00B0.toChar()}")
            }
            else -> {
                Timber.wtf("How could anything else come up?\tsource=$source")
                title = "Dummy title"
                yAxisTitle = "Dummy title"
                cartesian = AnyChart.line()
            }
        }

        cartesian.title(title)
        cartesian.xAxis(0).title("Time")
        cartesian.yAxis(0).title(yAxisTitle)

        cartesian.animation(true)
        chartView.setZoomEnabled(true)
        chartView.setChart(cartesian)
        chartView.setProgressBar(ProgressBar(context))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            if (NavUtils.getParentActivityName(activity as AppCompatActivity) != null) {
                NavUtils.navigateUpFromSameTask(activity as AppCompatActivity)
            }
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private inner class CustomDataEntry internal constructor(date: String, var data: Number): ValueDataEntry(date, data)

    companion object {
        @JvmStatic
        fun newInstance(source: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_SOURCE, source)
                }
            }
    }
}
