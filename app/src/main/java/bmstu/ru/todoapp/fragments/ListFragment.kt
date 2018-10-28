package bmstu.ru.todoapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bmstu.ru.todoapp.R
import bmstu.ru.todoapp.adapters.listadapters.*


class ListFragment : Fragment() {

    companion object {
        const val ARG_PAGE = "ARG_PAGE"
        const val TAG = "ListFragment"

        fun newInstance(page: Int): ListFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            return ListFragment().apply {
                arguments = args
            }
        }
    }

    private var pageNum: Int = 0
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNum = arguments!!.getInt(ARG_PAGE)
        Log.i(TAG, "OnCreate. Page: $pageNum")
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.in_list_rec_view).apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = getListAdapter(activity!!, pageNum)
//            divider between list item
//            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
//            itemDecoration.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.divider)!!)
//            addItemDecoration(itemDecoration)
        }
        return view
    }

    fun getListAdapter(activity: FragmentActivity, page: Int): BaseListAdapter? {
        return when (page) {
            1 -> InListAdapter(activity)
            2 -> NextActionsListAdapter(activity)
            3 -> WaitingForListAdapter(activity)
            4 -> SomedayListAdapter(activity)
            5 -> CalendarListAdapter(activity)
            else -> null
        }
    }
}