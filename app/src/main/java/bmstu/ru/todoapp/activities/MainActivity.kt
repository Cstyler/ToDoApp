package bmstu.ru.todoapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.TabsFragmentPagerAdapter
import bmstu.ru.todoapp.adapters.listadapters.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), TabLayout.OnTabSelectedListener {

    companion object {

        private const val TAG = "MainActivity"
        private var selectedTabPosition: Int = 0
        private const val DIALOG_TEXT_SIZE = 20f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabTitles = resources.getStringArray(R.array.tab_titles)
        val adapter = TabsFragmentPagerAdapter(supportFragmentManager, tabTitles)
        view_pager.adapter = adapter
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
            R.id.button_filter -> {
                val position = tab_layout.selectedTabPosition
                if (position == 0) {
                    Toast.makeText(
                        this,
                        getString(R.string.no_filter_in_note),
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onOptionsItemSelected(item)
                }
                val adapter = view_pager.adapter as TabsFragmentPagerAdapter
                val page = adapter.fragments[position]
                val recyclerView = page!!.recyclerView
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle(getString(R.string.filter_dialog_title))
                setDialogBuilderCancel(dialogBuilder)
                when (position) {
                    1 -> {
                        val arrayAdapter = getArrayAdapter(R.array.next_action_filter_types)
                        dialogBuilder.setAdapter(arrayAdapter) { _, which ->
                            val recViewAdapter =
                                recyclerView.adapter as NextActionsListAdapter
                            when (which) {
                                0 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                1 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                2 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                3 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                4 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                5 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getNextActionsNames()
                                }
                            }
                            recViewAdapter.notifyDataSetChanged()
                        }
                    }
                    2 -> {
                        val arrayAdapter = getArrayAdapter(R.array.waiting_for_filter_types)

                        dialogBuilder.setAdapter(arrayAdapter) { _, which ->
                            val recViewAdapter =
                                recyclerView.adapter as WaitingForListAdapter
                            when (which) {
                                0 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                1 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                2 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                3 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getWaitingForNoteNames()
                                }
                            }
                            recViewAdapter.notifyDataSetChanged()
                        }
                    }
                    3 -> {
                        val arrayAdapter = getArrayAdapter(R.array.someday_filter_types)
                        dialogBuilder.setAdapter(arrayAdapter) { _, which ->
                            val recViewAdapter =
                                recyclerView.adapter as SomedayListAdapter
                            when (which) {
                                0 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                1 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getSomedayNoteNames()
                                }
                            }
                            recViewAdapter.notifyDataSetChanged()
                        }
                    }
                    4 -> {
                        val arrayAdapter = getArrayAdapter(R.array.calendar_filter_types)
                        dialogBuilder.setAdapter(arrayAdapter) { _, which ->
                            val recViewAdapter =
                                recyclerView.adapter as CalendarListAdapter
                            when (which) {
                                0 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                1 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                2 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getInNoteNames()
                                }
                                3 -> {
                                    recViewAdapter.noteNames = DatabaseLayer.getCalendarNoteNames()
                                }
                            }
                            recViewAdapter.notifyDataSetChanged()
                        }
                    }
                }
                val dialog = dialogBuilder.show()
                val textView = dialog.findViewById<TextView>(android.R.id.message)
                textView?.textSize = DIALOG_TEXT_SIZE
            }
            R.id.button_sort -> {
                Toast.makeText(
                    this,
                    "Cортировка заметок...",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    private fun setDialogBuilderCancel(dialogBuilder: AlertDialog.Builder) {
        dialogBuilder.setNegativeButton(getString(R.string.cancel_dialog)) { dialog, _ ->
            dialog.dismiss()
        }
    }

    private fun getArrayAdapter(resId: Int): ArrayAdapter<String> {
        val filterTypes = resources
            .getStringArray(resId)

        val arrayAdapter = ArrayAdapter<String>(
            this,
            android.R.layout.select_dialog_singlechoice,
            filterTypes
        )
        return arrayAdapter
    }
}
