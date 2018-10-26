package bmstu.ru.todoapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.BaseListAdapter
import bmstu.ru.todoapp.entities.NextActionsListNote
import kotlinx.android.synthetic.main.next_actions_list_edit_form.*
import java.text.SimpleDateFormat
import java.util.*


class NextActionsListEditActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "NextActListEditActivity"
        const val MAX_PRIORITY = 3
        val PRIORITIES = (1..MAX_PRIORITY).toList()
    }

    private lateinit var note: NextActionsListNote
    private var noteId: Int = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.next_actions_list_edit_form)
        syncLayoutWithDatabase()
        Log.i(TAG, "OnCreate")
    }

    private fun syncLayoutWithDatabase() {
        noteId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, noteId)
        Log.i(TAG, "Note id: $noteId")
        note = DatabaseLayer.getNextActionsListNoteById(noteId)
        next_actions_list_edit_note_name_edit_text.setText(note.name)
        next_actions_list_edit_note_content_edit_text.setText(note.content)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        next_actions_list_edit_creation_date_text_view.text = getString(R.string.edit_form_creation_date)
            .format(dateFormat.format(note.creationDate))

        next_actions_list_edit_update_date_text_view.text = getString(R.string.edit_form_update_date)
            .format(dateFormat.format(note.updateDate))

        next_actions_list_edit_priority_spinner.setItems(PRIORITIES)
        next_actions_list_edit_priority_spinner.selectedIndex = PRIORITIES.indexOf(note.priority)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = next_actions_list_edit_note_name_edit_text.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        "Нельзя сохранить заметку с пустым именем",
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = next_actions_list_edit_note_content_edit_text.text.toString()
                var updateFlag = false
                if (noteName != note.name) {
                    note.content = noteContent
                    updateFlag = true
                }
                if (noteContent != note.content) {
                    note.content = noteContent
                    updateFlag = true
                }
                val priority = PRIORITIES[next_actions_list_edit_priority_spinner.selectedIndex]
                if (priority != note.priority) {
                    note.priority = priority
                    updateFlag = true
                }
                if (updateFlag) {
                    val cal = Calendar.getInstance()
                    note.updateDate = cal.time
                }
                DatabaseLayer.updateNextActionsListEdit(noteId, note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
