package com.example.smarttrader.adapters

import android.graphics.Typeface
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.network.data.models.SwingPosition
import com.example.smarttrader.R
import com.example.smarttrader.convertUnixTimeToReadable
import com.example.smarttrader.models.MarketOrderModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class TableViewAdapter(private val swingPositions: List<MarketOrderModel>) :
    RecyclerView.Adapter<TableViewAdapter.RowViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_orders, parent, false)
        return RowViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return swingPositions.size + 1
    }


    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_cell_bg)
    }

    private fun setFont(view: View, font: String) {
        val face = Typeface.createFromAsset(view.context.assets, font)
        (view as TextView).typeface = face
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_cell_header)
    }

    fun setSwingPositions(swingPositions: ArrayList<SwingPosition>) {
        swingPositions.addAll(swingPositions)
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.bindingAdapterPosition

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            holder.apply {
                setHeaderBg(txtOrder)
                setHeaderBg(txtOpenTime)
                setHeaderBg(txtRisk)
                setHeaderBg(txtExpectedReturns)
                setHeaderBg(txtDuration)

                setFont(txtOrder, "fonts/gotham.ttf")
                setFont(txtOpenTime, "fonts/gotham.ttf")
                setFont(txtRisk, "fonts/gotham.ttf")
                setFont(txtExpectedReturns, "fonts/gotham.ttf")
                setFont(txtDuration, "fonts/gotham.ttf")


                txtOrder.text = "Order Id"
                txtOpenTime.text = "Open Time"
                txtRisk.text = "Risk"
                txtExpectedReturns.text = "Expected Returns"
                txtDuration.text = "Duration"
            }
        } else {
            val swingPosition = swingPositions[rowPos - 1]
            holder.apply {
                itemView
                setHeaderBg(txtOrder)
                setHeaderBg(txtOpenTime)
                setHeaderBg(txtRisk)
                setHeaderBg(txtExpectedReturns)
                setHeaderBg(txtDuration)

                setFont(txtOrder, "fonts/lato.ttf")
                setFont(txtRisk, "fonts/lato.ttf")
                setFont(txtOpenTime, "fonts/lato.ttf")
                txtOrder.text = swingPosition.orderId.toString()
                txtOpenTime.text = swingPosition.openTime
                txtOpenTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0f)
                txtOrder.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0f)
                txtDuration.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10.0f)
                txtDuration.text = swingPosition.duration

                txtOrder.setTextColor(itemView.context.resources.getColor(R.color.light_green))
                txtRisk.setTextColor(itemView.context.resources.getColor(R.color.red))
            }
        }
    }


    private fun calculateTimeDifference(startTime: Long): String {
        val currentUnixTime = System.currentTimeMillis() / 1000


        val durationInSeconds = startTime - currentUnixTime

        val days = TimeUnit.SECONDS.toDays(durationInSeconds)
        val hours = TimeUnit.SECONDS.toHours(durationInSeconds) % 24
        val minutes = TimeUnit.SECONDS.toMinutes(durationInSeconds) % 60
        val seconds = durationInSeconds % 60


        return "$days days, $hours hours, $minutes minutes, $seconds seconds"
    }


    fun getDuration(openTime: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val startDate = dateFormat.parse(openTime)
            val currentDate = Date()
            var difference = currentDate.time - startDate.time


            val secondsInMilli: Long = 1000
            val minutesInMilli = secondsInMilli * 60
            val hoursInMilli = minutesInMilli * 60
            val daysInMilli = hoursInMilli * 24

            val elapsedDays: Long = difference / daysInMilli
            difference %= daysInMilli

            val elapsedHours: Long = difference / hoursInMilli
            difference %= hoursInMilli

            val elapsedMinutes: Long = difference / minutesInMilli
            difference %= minutesInMilli
            val elapsedSeconds: Long = difference / secondsInMilli

            return "$elapsedDays days, $elapsedHours hours, $elapsedMinutes minutes, $elapsedSeconds seconds"
        } catch (e: ParseException) {
            e.printStackTrace()
            return null
        }
    }

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtOrder: TextView = itemView.findViewById(R.id.txtPosition)
        val txtOpenTime: TextView = itemView.findViewById(R.id.txtOpenTime)
        val txtRisk: TextView = itemView.findViewById(R.id.txtRisk)
        val txtExpectedReturns: TextView = itemView.findViewById(R.id.txtExpectedReturns)
        val txtDuration: TextView = itemView.findViewById(R.id.txtDuration)


    }
}