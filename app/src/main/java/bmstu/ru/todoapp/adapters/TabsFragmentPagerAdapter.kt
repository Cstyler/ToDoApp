package bmstu.ru.todoapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import bmstu.ru.todoapp.fragments.ListFragment


class TabsFragmentPagerAdapter(
    fm: FragmentManager,
    private val tabTitles: Array<String>
) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getItem(position: Int): Fragment {
        return ListFragment.newInstance(position + 1)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
}
