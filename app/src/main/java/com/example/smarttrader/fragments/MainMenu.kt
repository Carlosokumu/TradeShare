package com.example.smarttrader.fragments

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.smarttrader.BuildConfig
import com.example.smarttrader.R
import com.example.smarttrader.data.local.entity.User
import com.example.smarttrader.databinding.FragmentMainMenuBinding
import com.example.smarttrader.settings.Settings
import com.example.smarttrader.viewmodels.UserViewModel
import com.tombayley.dropdowntipslist.DropDownList
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class MainMenu : Fragment() {


    private lateinit var binding: FragmentMainMenuBinding
    private var isExpanded = false
    private lateinit var item: DropDownList.Item
    private var isFunding = false
    private val userViewModel: UserViewModel by viewModel()
    private  lateinit var user: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            userViewModel.getUser(Settings.getUserName() ?: "carlos")
        }

        userViewModel.fetchUserInfo(Settings.getUserName()!!)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainMenuBinding.inflate(layoutInflater, container, false)
        val dropDownListItems = LinkedList<DropDownList.Item>()




        binding.dropDownList.preferences =
            requireContext().getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)


        binding.showHide.setOnClickListener {
            if (isExpanded) {
                isExpanded = false
                (it as ImageView).setImageResource(R.drawable.show)
                binding.vManager.visibility = View.GONE
            } else {
                isExpanded = true
                (it as ImageView).setImageResource(R.drawable.ic_arrow_rise)
                userViewModel.user.observe(viewLifecycleOwner) { user ->
                    Log.d("USER", user.username)
                    binding.vManager.visibility = View.VISIBLE
                    binding.txtUserName.text = user.username
                    binding.txtfProfit.text = user.floatingProfit.toString()
                    binding.txtEquity.text = user.equity.toString()
                    binding.txtBalance.text = user.balance.toString()
                    item = DropDownList.Item(
                        title = "UserName",
                        description = user.username,
                    )
                    item.setAppearAfter(getAppInstallTime(), 0, "drop_list_example1")

                    
                    dropDownListItems.add(item)
                    item = DropDownList.Item(
                        title = "Floating Profit",
                        description = "0.0",
                    )
                    item.setAppearAfter(getAppInstallTime(), 0, "drop_list_example2")
                    dropDownListItems.add(item)

                    item = DropDownList.Item(
                        title = "Equity",
                        description = "0.00"
                    )
                    item.setAppearAfter(0, 0, "drop_list_example2")
                    dropDownListItems.add(item)

                    item = DropDownList.Item(
                        title = "Balance",
                        description = "0.00"
                    )
                    item.setAppearAfter(0, 0, "drop_list_example2")
                    dropDownListItems.add(item)
                }
            }

            // binding.dropDownList.visibility = View.VISIBLE
        }

        binding.fundShow.setOnClickListener {
            if (isFunding) {
                isFunding = false
                (it as ImageView).setImageResource(R.drawable.show)
                binding.fundContainer2.visibility = View.GONE
            } else {
                isFunding = true
                (it as ImageView).setImageResource(R.drawable.ic_arrow_rise)
                binding.fundContainer2.visibility = View.VISIBLE
            }

        }






        binding.dropDownList.addAll(dropDownListItems)
        return binding.root
    }


    private fun getAppInstallTime(): Long {
        return try {
            requireContext().packageManager.getPackageInfo(BuildConfig.APPLICATION_ID, 0).firstInstallTime
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            0
        }
    }


    companion object {
        const val PREFS_FILENAME = "prefs"
    }


}