package bmstu.ru.todoapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.TabsFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    companion object {

        private const val TAG = "MainActivity"
        private var selectedTabPosition: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabTitles = resources.getStringArray(R.array.tab_titles)
        view_pager.adapter = TabsFragmentPagerAdapter(supportFragmentManager, tabTitles)
        tab_layout.setupWithViewPager(view_pager)
        tab_layout.getTabAt(selectedTabPosition)?.select()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        selectedTabPosition = tab?.position ?: 0
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.main_activity_add_menu_item -> {
                Log.i(
                    TAG, "add note pressed." +
                            " selectedTabPosition ${tab_layout.selectedTabPosition}"
                )
                val activities = arrayOf(
                    InListCreateActivity::class.java,
                    NextActionsListCreateActivity::class.java,
                    WaitingForListCreateActivity::class.java,
                    SomedayListCreateActivity::class.java,
                    CalendarListCreateActivity::class.java
                )

                val intent = Intent(this, activities[tab_layout.selectedTabPosition])
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        }
        return super.onContextItemSelected(item)
    }
}
