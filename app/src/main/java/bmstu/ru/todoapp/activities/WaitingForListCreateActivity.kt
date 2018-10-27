package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.entities.InListNote
import kotlinx.android.synthetic.main.in_list_edit_form.*
import java.util.*

class WaitingForListCreateActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "InListCreateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_list_edit_form)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = in_list_edit_note_name_edit_text.text.toString()
                val noteContent = in_list_edit_note_content_edit_text.text.toString()
                val time = Calendar.getInstance().time
                val note = InListNote(noteName, noteContent, time, time)
                DatabaseLayer.putInListEdit(note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
