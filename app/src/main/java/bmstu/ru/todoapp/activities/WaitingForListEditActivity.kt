package bmstu.ru.todoapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.BaseListAdapter
import bmstu.ru.todoapp.entities.InListNote
import kotlinx.android.synthetic.main.in_list_edit_form.*
import java.text.SimpleDateFormat
import java.util.*

class WaitingForListEditActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "InListEditActivity"
    }

    private lateinit var note: InListNote
    private var noteId: Int = 0

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_list_edit_form)
        syncLayoutWithDatabase()
    }

    private fun syncLayoutWithDatabase() {
        noteId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, noteId)
        Log.i(TAG, "Note id: $noteId")
        note = DatabaseLayer.getInListNoteById(noteId)
        in_list_edit_note_name_edit_text.setText(note.name)
        in_list_edit_note_content_edit_text.setText(note.content)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        in_list_edit_creation_date_text_view.text = getString(R.string.edit_form_creation_date)
            .format(dateFormat.format(note.creationDate))
        in_list_edit_update_date_text_view.text = getString(R.string.edit_form_update_date)
            .format(dateFormat.format(note.updateDate))
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
                var updateFlag = false
                if (noteContent != note.content) {
                    note.content = noteContent
                    updateFlag = true
                }
                if (noteName != note.name) {
                    note.content = noteContent
                    updateFlag = true
                }
                if (updateFlag) {
                    val cal = Calendar.getInstance()
                    note.updateDate = cal.time
                }
                DatabaseLayer.updateInListEdit(noteId, note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
