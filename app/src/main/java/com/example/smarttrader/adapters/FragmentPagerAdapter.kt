package com.example.smarttrader.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smarttrader.fragments.MainMenu
import com.example.smarttrader.fragments.SignIn
import com.example.smarttrader.settings.Settings


class FragmentPagerAdapter(
    fragmentActivity: FragmentActivity,
): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            LOGIN_SCREEN_POSITION-> SignIn()
            MENU_SCREEN -> if (Settings.isUserLogged()!!)   MainMenu() else SignIn()
            else -> {
                throw IllegalArgumentException("no item")

            }
        }
    }


    companion object {
        internal const val LOGIN_SCREEN_POSITION = 1
        internal const val MENU_SCREEN = 0

    }


}