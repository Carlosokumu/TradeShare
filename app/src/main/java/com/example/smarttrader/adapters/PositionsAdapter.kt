package com.example.smarttrader.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.core.network.data.models.MtPosition
import com.example.smarttrader.R
import com.example.smarttrader.calculateDifference
import com.example.smarttrader.getDownSideMt4
import com.example.smarttrader.getUpSideMt4
import java.text.SimpleDateFormat
import java.util.*

class PositionsAdapter : RecyclerView.Adapter<PositionsAdapter.PositionsViewHolder>() {
    private var positions: List<MtPosition> = listOf()
    private lateinit var context: Context

    fun setData(data: List<MtPosition>, context: Context) {
        positions = data
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_positions, parent, false)
        return PositionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PositionsViewHolder, position: Int) {
         holder.bind(position = positions[position])
    }

    override fun getItemCount(): Int {
        return positions.size
    }

    class PositionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val txtEntryTime: TextView = itemView.findViewById(R.id.txtRealEntry)
        private val txtDuration: TextView = itemView.findViewById(R.id.txtRealDuration)
        private val txtUpside: TextView = itemView.findViewById(R.id.txtRealUpside)
        private val txtDownside: TextView = itemView.findViewById(R.id.txtRealDownSide)


        fun bind(position: MtPosition) {
            val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
            val formattedDate = sdf.parse(position.entryTime.dropLast(4))
            val currentDateandTime = sdf.format(Date())
            val current = sdf.parse(currentDateandTime)
            val duration =
                itemView.context.calculateDifference(startDate = formattedDate, endDate = current)

            txtEntryTime.text = position.entryTime
            txtDuration.text = duration
            txtDownside.text =
                itemView.context.getDownSideMt4(position.entryPrice, position.stopLoss)
            txtUpside.text = itemView.context.getUpSideMt4(position.entryPrice, position.takeProfit)
        }
    }
}