package com.example.smarttrader.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.smarttrader.R

class ResetPass : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.detfragnograd);
    }


    override fun onResume() {
        super.onResume()
        val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.width = WindowManager.LayoutParams.MATCH_PARENT

        dialog!!.window!!.attributes = params as WindowManager.LayoutParams
    }

    companion object {
        fun newInstance(): ResetPass {


            val resetPass = ResetPass()
            return resetPass
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootie = inflater.inflate(R.layout.reset_layout, container, false)

        val resetBut = rootie.findViewById<Button>(R.id.btnReset)
        val dismissBut = rootie.findViewById<TextView>(R.id.back)


        dismissBut.setOnClickListener {
            dialog?.dismiss()
        }


        resetBut.setOnClickListener {
           Toast.makeText(requireContext(),"Check back later",Toast.LENGTH_SHORT).show()
        }
        return rootie
    }
}