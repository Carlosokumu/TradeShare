package com.example.smarttrader.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.core.network.data.models.MtPosition
import com.example.core.network.data.models.Position
import com.example.core.utils.CirclePagerIndicatorDecoration
import com.example.smarttrader.R
import com.example.smarttrader.adapters.PositionsAdapter
import com.example.smarttrader.models.MtFetchState
import com.example.smarttrader.models.OpenPositionFetchState
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.viewmodels.PositionsViewModel
import com.example.smarttrader.viewmodels.UserViewModel
import com.facebook.shimmer.ShimmerFrameLayout
import ir.androidexception.datatable.DataTable
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.truncate
import kotlin.random.Random


class Positions : Fragment() {


    private val positionsViewModel: PositionsViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()

    //Views
    private lateinit var shimmerFrame: ShimmerFrameLayout
    private lateinit var recyclerPositions: RecyclerView
    private lateinit var txtPositions: TextView
    private lateinit var contentlayout: RelativeLayout
    private lateinit var txtTrades: TextView
    private lateinit var txtActualBalance: TextView
    private lateinit var currentDateTime: TextView


    private var isLoading = MutableStateFlow(false)


    private var stagedTrades = mutableListOf<String>()
    private var counter: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_positions, container, false)
        shimmerFrame = view.findViewById(R.id.mShimmer)
        contentlayout = view.findViewById(R.id.contentLayout)
        txtPositions = view.findViewById(R.id.txtActualPositions)
        txtTrades = view.findViewById(R.id.txtTradesActual)
        txtActualBalance = view.findViewById(R.id.txtActualBalance)
        currentDateTime = view.findViewById(R.id.txtTime)


        currentDateTime.text = getCurrentTime()
        recyclerPositions = view.findViewById(R.id.recyclerPositions)




        positionsViewModel.getOpenPositions()
        positionsViewModel.getAllMtPositions()
        //Thu Aug 18 08:30:57 GMT+03:00 2022
        //Sat Jan 15 00:37:47 GMT+03:00 2022

        lifecycleScope.launch {
            positionsViewModel.mtFetchState.collect { state ->
                when (state) {
                    is MtFetchState.Success -> {

                        setDataList(state.positions)
                        val positions = state.positions

                        txtPositions.text = positions.size.toString()

                        userViewModel.user.observe(viewLifecycleOwner) { user ->
                            txtActualBalance.text = truncate(user.balance).toString()
                        }

                        for (i in state.positions) {
                            stagedTrades.add(i.symbol)
                        }
                        val count = findTrades(stagedTrades)
                        txtTrades.text = count.toString()

                    }
                    is MtFetchState.Loading -> {
                        Toast.makeText(requireContext(), "Loading", Toast.LENGTH_SHORT).show()
                    }
                    is MtFetchState.Error -> {

                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }

        lifecycleScope.launch {
            positionsViewModel.uiState.collect { state ->
                when (state) {
                    is OpenPositionFetchState.Success -> {


                        //positionsViewModel.setPositions(state.positions.openpositions)
                        isLoading.value = true
                        shimmerFrame.visibility = View.GONE
                        contentlayout.visibility = View.VISIBLE
                        shimmerFrame.stopShimmer()

                    }
                    is OpenPositionFetchState.Loading -> {
                        // refresher.isRefreshing = false
                    }
                    is OpenPositionFetchState.Error -> {
                        // refresher.isRefreshing = false
                        Toast.makeText(requireContext(), "Couldn't fetch data", Toast.LENGTH_SHORT)
                            .show()


                    }
                }
            }


        }
        return view
    }







    override fun onResume() {
        super.onResume()
        shimmerFrame.startShimmer()
    }


    override fun onPause() {
        super.onPause()
        shimmerFrame.stopShimmer()
    }







    private fun setDataList(positions: List<MtPosition>) {
        val positionsAdapter = PositionsAdapter()
        positionsAdapter.setData(positions, requireContext())
        recyclerPositions.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )

        // add pager behavior
        val snapHelper = PagerSnapHelper()
        recyclerPositions.addItemDecoration(CirclePagerIndicatorDecoration())
        snapHelper.attachToRecyclerView(recyclerPositions)
        recyclerPositions.adapter = positionsAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            Settings.getUserName()?.let { username ->
                run {
                    userViewModel.getUser(username)
                }
            }

        }
    }


    private fun findTrades(trades: List<String>): Int {

        var count = 0
        val stagedList = mutableListOf<String>()
        for (i in trades) {
            if (!stagedList.contains(i)) {
                count += 1
                stagedList.add(i)
            }
        }
        return count
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm")
        return sdf.format(Date())
    }

}