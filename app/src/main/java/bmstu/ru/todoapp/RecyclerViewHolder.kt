package bmstu.ru.todoapp

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView = itemView
        .findViewById<TextView>(R.id.list_item_text)

    fun setData(data: String) {
        textView.text = data
    }
}