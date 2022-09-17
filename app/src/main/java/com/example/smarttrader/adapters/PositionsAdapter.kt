package com.example.smarttrader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.network.data.models.MtPosition
import com.example.core.network.data.models.Position
import com.example.smarttrader.R

class PositionsAdapter : RecyclerView.Adapter<PositionsAdapter.PositionsViewHolder>()  {
    private var positions: List<MtPosition> = listOf()

    fun setData(data: List<MtPosition>){
        positions = data
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_positions,parent,false)
        return  PositionsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PositionsViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
       return  4
    }

    class  PositionsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }
}