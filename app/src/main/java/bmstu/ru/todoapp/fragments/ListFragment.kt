package bmstu.ru.todoapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.*


class ListFragment : Fragment() {

    companion object {
        const val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): ListFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            return ListFragment().apply {
                arguments = args
            }
        }
    }

    private var mPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments!!.getInt(ARG_PAGE)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        val recView = view.findViewById<RecyclerView>(R.id.in_list_rec_view).apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = getListAdapter(activity!!)
//            divider between list item
//            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//            itemDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
//            addItemDecoration(itemDecoration)
        }
        return view
    }

    private fun getListAdapter(activity: FragmentActivity): BaseListAdapter? {
        return when (mPage) {
            1 -> InListAdapter(activity)
            2 -> NextActionsListAdapter(activity)
            3 -> WaitingForListAdapter(activity)
            4 -> SomedayListAdapter(activity)
            5 -> CalendarListAdapter(activity)
            else -> null
        }
    }
}