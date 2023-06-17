package com.example.smarttrader.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.smarttrader.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseTradesDialog : BottomSheetDialogFragment() {


    fun newInstance(): ChooseTradesDialog {
        val args = Bundle()
        val fragment = ChooseTradesDialog()
        fragment.arguments = args
        return fragment
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.choose_trades, container, false)
        val txtLastFive = view.findViewById<TextView>(R.id.txtLastFive)
        val txtLat10 = view.findViewById<TextView>(R.id.txtLast10)
        val txtLast15 = view.findViewById<TextView>(R.id.txtLast15)
        val txtLast20 = view.findViewById<TextView>(R.id.txtLast20)

        txtLastFive.text = Html.fromHtml(
            getString(R.string.last_trades_5),
            Html.FROM_HTML_MODE_LEGACY
        )

        txtLat10.text = Html.fromHtml(
            getString(R.string.last_trades_10),
            Html.FROM_HTML_MODE_LEGACY
        )
        txtLast15.text = Html.fromHtml(
            getString(R.string.last_trades_15),
            Html.FROM_HTML_MODE_LEGACY
        )
        txtLast20.text = Html.fromHtml(
            getString(R.string.last_trades_20),
            Html.FROM_HTML_MODE_LEGACY
        )


        return view
    }

}