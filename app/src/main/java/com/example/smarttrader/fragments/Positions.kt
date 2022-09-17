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
import kotlin.random.Random


class Positions : Fragment() {


    private val positionsViewModel: PositionsViewModel by viewModel()
    private val userViewModel: UserViewModel by viewModel()

    //Views
    private lateinit var shimmerFrame: ShimmerFrameLayout
    private lateinit var recyclerPositions: RecyclerView
    private lateinit var txtPositions: TextView
    private lateinit var refresher: SwipeRefreshLayout
    private lateinit var contentlayout: RelativeLayout
    private lateinit var txtTrades: TextView


    private var isLoading = MutableStateFlow(false)


    private var stagedTrades = mutableListOf<String>()
    private var counter: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_positions, container, false)


        //refresher = view.findViewById(R.id.swipe_to_refresh_layout)
        shimmerFrame = view.findViewById(R.id.mShimmer)
        contentlayout = view.findViewById(R.id.contentLayout)
        txtPositions = view.findViewById(R.id.txtActualPositions)
        txtTrades = view.findViewById(R.id.txtTrades)


        // refresher.setOnRefreshListener(this)

        // refresher.setProgressBackgroundColorSchemeColor(Color.TRANSPARENT)
        // refresher.setColorSchemeColors(Color.RED, Color.RED, Color.RED)

        recyclerPositions = view.findViewById(R.id.recyclerPositions)




        positionsViewModel.getOpenPositions()
        positionsViewModel.getAllMtPositions()
        //Thu Aug 18 08:30:57 GMT+03:00 2022
        //Sat Jan 15 00:37:47 GMT+03:00 2022

        lifecycleScope.launch {
            positionsViewModel.mtFetchState.collect { state ->
                when (state) {
                    is MtFetchState.Success -> {
                        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
                        val formattedDate = sdf.parse(state.positions[0].entryTime.dropLast(4))
                        val currentDateandTime = sdf.format(Date())
                        val current = sdf.parse(currentDateandTime)
                        Log.d("DATECURRENT", current.toString())
                        Log.d(
                            "DURATIONBTN",
                            calculateDifference(startDate = formattedDate, endDate = current)
                        )

                        Log.d("MTPOSITIONS", formattedDate.toString())
                        setDataList(state.positions)
                        val positions = state.positions

                        txtPositions.text = positions.size.toString()
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
                        Toast.makeText(
                            requireContext(),
                            "Data fetched successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        // refresher.isRefreshing = false
                        Log.d("POSITIONS", state.positions.openpositions.size.toString())
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


//        positionsViewModel.profileModel.observe(viewLifecycleOwner) { positions ->
//            setTable(dataTable = dataTable, context = requireContext(), positions = positions)
//        }
        positionsViewModel.positions.observe(viewLifecycleOwner) { positions ->
            //setMt4Positions(dataTable = dataTable, context = requireContext(), positions = positions)
        }





        return view
    }


    private fun setTable(dataTable: DataTable, context: Context, positions: List<Position>) {

        val header =
            DataTableHeader.Builder().item("Position", 1).item("Entry time", 1).item("Duration", 1)
                .item("Upside(%)", 1).item("Downside(%)", 1).build()

        val rows = ArrayList<DataTableRow>()

        for (i in positions) {
            val r = Random
            counter++
            val random = r.nextInt() + 1
            val randomDiscount = r.nextInt() + 20

            val sdf = SimpleDateFormat("M/dd/yyyy hh:mm:ss")
            val currentDateandTime = sdf.format(Date())

            val entrytime = sdf.parse(i.EntryTime.dropLast(2))
            val current = sdf.parse(currentDateandTime)

            Log.d("ENTRYTIME", entrytime.toString())
            Log.d("CURRENTTIME", current.toString())


            val row = DataTableRow.Builder()
                .value(counter.toString())
                .value(i.EntryTime)
                .value(calculateDifference(entrytime, current))
                .value(getUpside(entryPrice = i.EntryPrice, takeProfit = i.TakeProfit) ?: "-")
                .value(getDownSide(entryPrice = i.EntryPrice, stopLoss = i.StopLoss) ?: "-").build()
            rows.add(row)
        }


        //dataTable.typeface = typeface;
        dataTable.header = header;
        dataTable.rows = rows;
        dataTable.inflate(context);


    }


    private fun setMt4Positions(
        dataTable: DataTable,
        context: Context,
        positions: List<MtPosition>
    ) {
        val header =
            DataTableHeader.Builder().item("Position", 1).item("Entry time", 1).item("Duration", 1)
                .item("Upside(%)", 1).item("Downside(%)", 1).build()
        val rows = ArrayList<DataTableRow>()

        for (i in positions) {
            counter++
            val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
            val entryDate = sdf.parse(i.entryTime.dropLast(4))
            val currentDate = sdf.format(Date())
            val current = sdf.parse(currentDate)


            val row = DataTableRow.Builder()
                .value(counter.toString())
                .value(i.entryTime)
                .value(calculateDifference(startDate = entryDate, endDate = current))
                .value(getUpSideMt4(entryPrice = i.entryPrice, takeProfit = i.takeProfit))
                .value(getDownSideMt4(entryPrice = i.entryPrice, stopLoss = i.stopLoss)).build()
            rows.add(row)
        }

        dataTable.header = header
        dataTable.rows = rows
        dataTable.inflate(context)
    }

    override fun onResume() {
        super.onResume()
        shimmerFrame.startShimmer()
    }


    override fun onPause() {
        super.onPause()
        shimmerFrame.stopShimmer()
    }


    private fun calculateDifference(startDate: Date, endDate: Date): String {
        var different = endDate.time.minus(startDate.time)
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays: Long = different / daysInMilli
        different = different % daysInMilli

        val elapsedHours: Long = different / hoursInMilli
        different = different % hoursInMilli

        val elapsedMinutes: Long = different / minutesInMilli
        different = different % minutesInMilli

        val elapsedSeconds: Long = different / secondsInMilli
        Log.d(
            "DIFFERENCE",
            elapsedDays.toString() + "days" + elapsedHours + "hours" + elapsedMinutes.toString()
        )
        return elapsedDays.toString() + "d" + elapsedHours + "hr" + elapsedMinutes.toString() + "min"

    }


    private fun getUpside(entryPrice: Double?, takeProfit: Double?): String? {
        return if (entryPrice == null || takeProfit == null) {
            null
        } else {
            val difference = takeProfit - entryPrice
            val percentageDiff = ((difference / entryPrice) * 100)
            val number3digits: Double = String.format("%.3f", percentageDiff).toDouble()
            number3digits.toString()
        }

    }

    private fun getUpSideMt4(entryPrice: Double?, takeProfit: Double?): String {
        return if (entryPrice == null || takeProfit == null) {
            "-"
        } else {
            val difference = takeProfit - entryPrice
            val percentageDiff = ((difference / entryPrice) * 100)
            val number3digits: Double = String.format("%.3f", percentageDiff).toDouble()
            number3digits.toString()
        }
    }


    private fun getDownSideMt4(entryPrice: Double?, stopLoss: Double?): String {
        return if (entryPrice == null || stopLoss == null) {
            "-"
        } else {
            val difference = entryPrice - stopLoss
            val percentageDiff = ((difference / entryPrice) * 100)
            val number3digits: Double = String.format("%.3f", percentageDiff).toDouble()
            number3digits.toString()
        }
    }

    private fun getDownSide(entryPrice: Double?, stopLoss: Double?): String? {
        return if (entryPrice == null || stopLoss == null) {
            null
        } else {
            val difference = entryPrice - stopLoss
            val percentageDiff = ((difference / entryPrice) * 100)
            val number3digits: Double = String.format("%.3f", percentageDiff).toDouble()
            number3digits.toString()
        }
    }

//    override fun onRefresh() {
//        refresher.isRefreshing = true
//        positionsViewModel.getOpenPositions()
//    }


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
        var stagedList = mutableListOf<String>()
        for (i in trades) {
            if (!stagedList.contains(i)) {
                count += 1
                stagedList.add(i)
            }
        }
        return count
    }

}