package bmstu.ru.todoapp.activities

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.format.DateFormat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import bmstu.ru.todoapp.DatabaseLayer
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.entities.ContextName
import bmstu.ru.todoapp.entities.CustomDate
import bmstu.ru.todoapp.entities.ProjectName
import bmstu.ru.todoapp.entities.WaitingForListNote
import kotlinx.android.synthetic.main.waiting_for_list_edit_form.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
class WaitingForListCreateActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "WaitForListCreateAct"
        val fullDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
    }

    private var remindYear: Int? = null
    private var remindMonth: Int? = null
    private var remindDay: Int? = null
    private var remindHour: Int? = null
    private var remindMinute: Int? = null
    private var waitingYear: Int? = null
    private var waitingMonth: Int? = null
    private var waitingDay: Int? = null
    private var waitingHour: Int? = null
    private var waitingMinute: Int? = null
    private lateinit var projectNames: List<ProjectName>
    private lateinit var contextNames: List<ContextName>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.waiting_for_list_edit_form)

        setProjectSpinner()
        setContextSpinner()

        waiting_for_list_edit_image_button_remind_date.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { v, y, m, d ->
                onRemindDateSet(v, y, m, d)
            }
            dateOnClick(listener)
        }

        waiting_for_list_edit_image_button_remind_time.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { v, h, m ->
                onRemindTimeSet(v, h, m)
            }
            timeOnClick(listener)
        }

        waiting_for_list_edit_image_button_waiting_date.setOnClickListener {
            val listener = DatePickerDialog.OnDateSetListener { v, y, m, d ->
                onWaitingDateSet(v, y, m, d)
            }
            dateOnClick(listener)
        }

        waiting_for_list_edit_image_button_waiting_time.setOnClickListener {
            val listener = TimePickerDialog.OnTimeSetListener { v, h, m ->
                onWaitingTimeSet(v, h, m)
            }
            timeOnClick(listener)
        }
        Log.i(TAG, "OnCreate")
    }

    private fun setProjectSpinner() {
        projectNames = DatabaseLayer.getProjectNames()
        waiting_for_list_edit_project_spinner.setItems(listOf("Нет проекта") + projectNames.map { it.name })
    }

    private fun setContextSpinner() {
        contextNames = DatabaseLayer.getContextNames()
        waiting_for_list_edit_context_spinner.setItems(listOf("Нет контекста") + contextNames.map { it.name })
    }


    private fun timeOnClick(listener: TimePickerDialog.OnTimeSetListener) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(
            this,
            listener,
            hour,
            minute,
            DateFormat.is24HourFormat(this)
        ).show()
    }

    private fun dateOnClick(listener: DatePickerDialog.OnDateSetListener) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, listener, year, month, day).show()
    }

    private fun onRemindDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        remindYear = year
        remindMonth = month
        remindDay = dayOfMonth
        remindHour = remindHour ?: 0
        remindMinute = remindMinute ?: 0
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, remindHour!!, remindMinute!!)
        updateTextViewDate(
            waiting_for_list_edit_remind_time_text_view,
            calendar.time,
            R.string.remind_time_format
        )
    }

    private fun onRemindTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (!validateRemindDate()) return
        remindHour = hourOfDay
        remindMinute = minute
        val calendar = Calendar.getInstance()
        calendar.set(remindYear!!, remindMonth!!, remindDay!!, hourOfDay, minute)
        updateTextViewDate(
            waiting_for_list_edit_remind_time_text_view,
            calendar.time,
            R.string.remind_time_format
        )
    }

    private fun onWaitingDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        waitingYear = year
        waitingMonth = month
        waitingDay = dayOfMonth
        waitingHour = waitingHour ?: 0
        waitingMinute = waitingMinute ?: 0
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth, waitingHour!!, waitingMinute!!)
        updateTextViewDate(
            waiting_for_list_edit_waiting_time_text_view,
            calendar.time,
            R.string.waiting_time_format
        )
    }

    private fun onWaitingTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (!validateWaitingDate()) return
        waitingHour = hourOfDay
        waitingMinute = minute
        val calendar = Calendar.getInstance()
        calendar.set(waitingYear!!, waitingMonth!!, waitingDay!!, hourOfDay, minute)
        updateTextViewDate(
            waiting_for_list_edit_waiting_time_text_view,
            calendar.time,
            R.string.waiting_time_format
        )
    }

    @SuppressLint("SetTextI18n")
    private fun updateTextViewDate(textView: TextView, date: Date, id: Int) {
        textView.text = getString(id)
            .format(fullDateFormat.format(date))
    }

    private fun validateRemindDate(): Boolean {
        if (remindYear == null || remindMonth == null || remindDay == null) {
            return false
        }
        return true
    }

    private fun validateWaitingDate(): Boolean {
        if (waitingYear == null || waitingMonth == null || waitingDay == null) {
            return false
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.create_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.form_edit_ok_button -> {
                val noteName = waiting_for_list_edit_note_name_edit_text.text.toString()
                if (noteName == "") {
                    Toast.makeText(
                        this,
                        "Нельзя сохранить заметку с пустым именем",
                        Toast.LENGTH_SHORT
                    ).show()
                    return super.onContextItemSelected(item)
                }
                val noteContent = waiting_for_list_edit_note_content_edit_text.text.toString()
                val time = Calendar.getInstance().time
                val remindTime = if (validateRemindDate()) CustomDate(
                    remindYear!!,
                    remindMonth!!,
                    remindDay!!,
                    remindHour!!,
                    remindMinute!!
                ) else null
                val waitingTime = if (validateWaitingDate()) CustomDate(
                    waitingYear!!,
                    waitingMonth!!,
                    waitingDay!!,
                    waitingHour!!,
                    waitingMinute!!
                ) else null
                val projectSelectedIndex = waiting_for_list_edit_project_spinner.selectedIndex
                val projectId: Int? = if (projectSelectedIndex != 0) {
                    val projectName = projectNames[projectSelectedIndex - 1]
                    projectName.id
                } else {
                    null
                }
                val contextSelectedIndex = waiting_for_list_edit_context_spinner.selectedIndex
                val contextId: Int? = if (contextSelectedIndex != 0) {
                    val contextName = contextNames[contextSelectedIndex - 1]
                    contextName.id
                } else {
                    null
                }
                val note = WaitingForListNote(
                    noteName,
                    noteContent,
                    time,
                    time,
                    waitingTime,
                    remindTime,
                    contextId,
                    projectId
                )
                DatabaseLayer.putWaitingForNote(note)
                finish()
            }
        }
        return super.onContextItemSelected(item)
    }
}
