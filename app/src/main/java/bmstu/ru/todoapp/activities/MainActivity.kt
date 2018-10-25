package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.TabsFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabTitles = resources.getStringArray(R.array.tab_titles)
        view_pager.adapter = TabsFragmentPagerAdapter(supportFragmentManager, tabTitles)
        tab_layout.setupWithViewPager(view_pager)
    }
}
