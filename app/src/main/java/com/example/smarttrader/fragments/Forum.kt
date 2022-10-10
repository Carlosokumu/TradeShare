package com.example.smarttrader.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.smarttrader.R
import com.example.smarttrader.models.WebSocketState
import com.example.smarttrader.viewmodels.ForumViewModel
import com.example.smarttrader.viewmodels.PositionsViewModel
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel


class Forum : Fragment() {

    private val forumViewModel: ForumViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        forumViewModel.observeConnection()
        // Inflate the layout for this fragment
        lifecycleScope.launch {
            forumViewModel.webState.collect { socketState ->
                when (socketState) {
                   is WebSocketState.MessageReceived -> {

                   }
                    is WebSocketState.ConnectionInitializing -> {

                        Toast.makeText(requireContext(),"initializing...",Toast.LENGTH_LONG).show()
                    }
                    is WebSocketState.ConnectionFailed -> {
                        Toast.makeText(requireContext(),"failed...",Toast.LENGTH_LONG).show()
                    }
                    is WebSocketState.ConnectionClosing -> {
                        Toast.makeText(requireContext(),"connection closing...",Toast.LENGTH_LONG).show()
                    }
                    is WebSocketState.ConnectionOpened -> {
                        Toast.makeText(requireContext(),"connection opened",Toast.LENGTH_LONG).show()
                    }
                    is WebSocketState.ConnectionClosed -> {
                        Toast.makeText(requireContext(),"connection closing...",Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
        return inflater.inflate(R.layout.fragment_forum, container, false)
    }



}