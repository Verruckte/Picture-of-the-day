package com.example.pictureoftheday.ui.picture.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

private const val TODAY = 0
private const val YESTERDAY = 1
private const val BEFORE_YESTERDAY = 2

class ViewPagerAdapter
(private val fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = arrayOf(
            TodayFragment.newInstance(),
            YesterdayFragment.newInstance(),
            BeforeYesterdayFragment.newInstance()
    )

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> fragments[TODAY]
            1 -> fragments[YESTERDAY]
            2 -> fragments[BEFORE_YESTERDAY]
            else -> fragments[TODAY]
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Сегодня"
            1 -> "Вчера"
            2 -> "Позавчера"
            else -> "Сегодня"
           }
    }
}