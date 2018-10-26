package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.entities.NextActionsListNote
import kotlinx.android.synthetic.main.in_list_edit_form.*
import java.util.*

class NextActionsListCreateActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "InListCreateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_list_edit_form)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.in_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = in_list_edit_note_name_edit_text.text.toString()
                val noteContent = in_list_edit_note_content_edit_text.text.toString()
                val time = Calendar.getInstance().time
                val note = NextActionsListNote(
                    noteName,
                    noteContent,
                    time,
                    time,
                    null,
                    null,
                    null,
                    null,
                    null
                )
                DatabaseLayer.putNextActionsListEdit(note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
