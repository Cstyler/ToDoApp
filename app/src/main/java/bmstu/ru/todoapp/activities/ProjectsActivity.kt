package bmstu.ru.todoapp.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.ProjectAdapter
import kotlinx.android.synthetic.main.list_fragment.*

class ProjectsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ProjectsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_fragment)
        rec_view.layoutManager = LinearLayoutManager(this)
        rec_view.setHasFixedSize(true)
        rec_view.adapter = ProjectAdapter(this)
    }

    override fun onResume() {
        super.onResume()
        val adapter = rec_view?.adapter as? ProjectAdapter
        adapter?.updateData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.projects_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.add_project_menu_item -> {
                val intent = Intent(this, ProjectCreateActivity::class.java)
                startIntent(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startIntent(intent: Intent) {
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}