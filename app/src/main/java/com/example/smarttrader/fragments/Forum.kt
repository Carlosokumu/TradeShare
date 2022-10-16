package com.example.smarttrader.fragments

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.util.ObjectsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.network.data.models.ChatDetails
import com.example.smarttrader.R
import com.example.smarttrader.adapters.ChatsAdapter
import com.example.smarttrader.models.MessageData
import com.example.smarttrader.models.MessageType
import com.example.smarttrader.models.WebSocketState
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.viewmodels.ForumViewModel
import com.github.topbottomsnackbar.TBSnackbar
import com.google.gson.Gson
import com.tinder.scarlet.Message
import kotlinx.coroutines.launch
import org.jetbrains.anko.support.v4.runOnUiThread
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class Forum : Fragment() {

    private val forumViewModel: ForumViewModel by viewModel()
    private val chatList: ArrayList<MessageData> = arrayListOf()
    private lateinit var chatsAdapter: ChatsAdapter

    //Views
    private lateinit var recyclerChats: RecyclerView
    private lateinit var btnSend: ImageButton
    private lateinit var editChat: EditText


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forum, container, false)

        btnSend = view.findViewById(R.id.sendMessage)
        recyclerChats = view.findViewById(R.id.recyclerViewChat)
        editChat = view.findViewById(R.id.editChat)

        chatsAdapter = ChatsAdapter(chatList)

        recyclerChats.layoutManager = LinearLayoutManager(requireContext())
        recyclerChats.adapter = chatsAdapter
        btnSend.setOnClickListener {
            sendMessage()
        }
        forumViewModel.observeConnection()


        lifecycleScope.launch {
            forumViewModel.webState.collect { socketState ->
                when (socketState) {
                    is WebSocketState.MessageReceived -> {
                        val data =
                            Gson().fromJson(socketState.message.toValue(), ChatDetails::class.java)
                        //Update to the current time
                        data.time = getCurrentTime()
                        /*
                              When the message comes back:this means am the sender of the message,so need to  display on recyclerview(haha)
                         */
                        if (ObjectsCompat.equals(data.text, editChat.text.toString())) {
                            editChat.text.clear()
                        } else {
                            val messageData =
                                MessageData(
                                    chatDetails = data,
                                    viewType = MessageType.CHAT_PEER.index
                                )
                            addItemToRecyclerView(message = messageData)
                            Toast.makeText(requireContext(), data.username, Toast.LENGTH_LONG)
                                .show()
                        }

                    }
                    is WebSocketState.ConnectionInitializing -> {
                        val websocketMessage = Html.fromHtml(getString(R.string.connection_lost))
                        TBSnackbar.make(
                            requireActivity().window.decorView,
                            websocketMessage,
                            TBSnackbar.LENGTH_SHORT,
                            TBSnackbar.STYLE_SHOW_TOP_FITSYSTEMWINDOW
                        ).show();


                    }
                    is WebSocketState.ConnectionFailed -> {

                        Toast.makeText(requireContext(), "failed...", Toast.LENGTH_LONG).show()
                    }
                    is WebSocketState.ConnectionClosing -> {
                        Toast.makeText(requireContext(), "connection closing...", Toast.LENGTH_LONG)
                            .show()
                    }
                    is WebSocketState.ConnectionOpened -> {
                        val websocketMessage = Html.fromHtml(getString(R.string.connected))
                        TBSnackbar.make(
                            requireActivity().window.decorView,
                            websocketMessage,
                            TBSnackbar.LENGTH_SHORT,
                            TBSnackbar.STYLE_SHOW_TOP_FITSYSTEMWINDOW
                        ).show();
                        Toast.makeText(requireContext(), "connection opened", Toast.LENGTH_LONG)
                            .show()
                    }
                    is WebSocketState.ConnectionClosed -> {
                        Toast.makeText(requireContext(), "connection closing...", Toast.LENGTH_LONG)
                            .show()
                    }
                }

            }
        }
        return view
    }

    private fun Message.toValue(): String {
        return when (this) {
            is Message.Text -> value
            is Message.Bytes -> value.toString()

        }
    }

    private fun addItemToRecyclerView(message: MessageData) {

        //Since this function is inside of the listener,
        // You need to do it on UIThread!

        runOnUiThread {
            chatList.add(message)
            chatsAdapter.notifyItemInserted(chatList.size)
            //editChat.setText("")
            recyclerChats.scrollToPosition(chatList.size - 1) //move focus on last message
        }
    }


    private fun sendMessage() {

        val messageInput = editChat.text.toString()
        val chatDetails = ChatDetails(Settings.getUserName() ?: "user", messageInput, time = getCurrentTime())
        forumViewModel.sendMessage(chatDetails)
        val messageData =
            MessageData(chatDetails, MessageType.CHAT_MINE.index)
        addItemToRecyclerView(messageData)

    }

    private fun getCurrentTime() = java.text.DateFormat.getDateTimeInstance().format(Date())


    fun getSentTime() = java.text.DateFormat.getDateTimeInstance().format(Date()).drop(12)

}