package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.InListAdapter
import kotlinx.android.synthetic.main.in_list_edit_form.*

class InListEditActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "InListEditActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.in_list_edit_form)
        setNoteData()
    }

    private fun setNoteData() {
        val noteId = intent.getIntExtra(InListAdapter.NOTE_ID_KEY, 0)
        val note = DatabaseLayer.getInListNoteById(noteId)
        note_name_edit_text.setText(note.name)
        note_content_edit_text.setText(note.content)
        Log.i(TAG, "Note id: $noteId")
    }
}
