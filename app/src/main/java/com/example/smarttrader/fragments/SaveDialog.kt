package com.example.smarttrader.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.smarttrader.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SaveDialog : BottomSheetDialogFragment() {

    private lateinit var cancel: TextView
    private lateinit var save: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.save_layout,container,false)
        cancel = view.findViewById(R.id.cancel)
        save = view.findViewById(R.id.save)

        cancel.setOnClickListener {
            this.dismiss()
        }
        save.setOnClickListener {
            this.dismiss()
            Toast.makeText(requireContext(),"To be made",Toast.LENGTH_SHORT).show()
        }

        return view
    }
  companion object {

      fun newInstance(): SaveDialog {
          return SaveDialog()
      }
  }

}