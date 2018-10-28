package bmstu.ru.todoapp.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.entities.Context
import kotlinx.android.synthetic.main.context_edit_form.*

class ContextCreateActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "ContextCreateActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.context_edit_form)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_menu, menu)
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
                val note = Context(noteName, noteContent)
                DatabaseLayer.putContext(note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
