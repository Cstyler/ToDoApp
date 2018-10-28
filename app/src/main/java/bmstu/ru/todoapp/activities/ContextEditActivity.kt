package bmstu.ru.todoapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.BaseListAdapter
import bmstu.ru.todoapp.entities.Context
import kotlinx.android.synthetic.main.context_edit_form.*

@SuppressLint("SimpleDateFormat")
class ContextEditActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ContextEditActivity"
    }

    private lateinit var context: Context
    private var contextId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.context_edit_form)
        syncLayoutWithDatabase()
        Log.i(TAG, "OnCreate")
    }

    private fun syncLayoutWithDatabase() {
        contextId = intent.getIntExtra(BaseListAdapter.NOTE_ID_KEY, contextId)
        Log.i(TAG, "Note id: $contextId")
        context = DatabaseLayer.getContextById(contextId)
        context_edit_name_text_edit.setText(context.name)
        context_edit_content_edit_text.setText(context.content)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.context_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = context_edit_name_text_edit.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        getString(R.string.empty_context_name_toast),
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = context_edit_content_edit_text.text.toString()
                context.content = noteContent
                context.name = noteName
                DatabaseLayer.updateContext(contextId, context)
                finish()
            }
            R.id.form_edit_delete_button -> {
                Toast.makeText(this, getString(R.string.delete_context_toast), Toast.LENGTH_SHORT).show()
                DatabaseLayer.deleteContextById(contextId)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
