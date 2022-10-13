package com.example.smarttrader.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smarttrader.R
import com.example.smarttrader.models.MessageData

class ChatsAdapter(private val messageData: List<MessageData>) :
    RecyclerView.Adapter<ChatsAdapter.ChatsVh>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsVh {

        var view: View? = null
        when (viewType) {
            CHAT_MINE -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_chatmine, parent, false)
            }
            CHAT_PEER -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_peerchat, parent, false)
            }

        }
        return ChatsVh(view!!)
    }

    override fun onBindViewHolder(holder: ChatsVh, position: Int) {
        when(holder.itemViewType){
            CHAT_MINE -> {
                Log.d("CHATMINE", messageData[position].chatDetails.text)
                holder.itemView.findViewById<TextView>(R.id.message).text = messageData[position].chatDetails.text
                holder.itemView.findViewById<TextView>(R.id.txtTime).text = messageData[position].chatDetails.time
            }
            CHAT_PEER -> {
                holder.itemView.findViewById<TextView>(R.id.message).text = messageData[position].chatDetails.text
            }

        }

    }

    override fun getItemCount(): Int {
        return messageData.size
    }

    override fun getItemViewType(position: Int): Int {
        return messageData[position].viewType
    }

    class ChatsVh(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    companion object {
        const val CHAT_MINE = 0
        const val CHAT_PEER = 1
    }
}