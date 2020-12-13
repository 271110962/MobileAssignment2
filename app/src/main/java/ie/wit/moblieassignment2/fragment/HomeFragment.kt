package ie.wit.moblieassignment2.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.activity.EditActivity
import ie.wit.moblieassignment2.adapter.HomeAdapter
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import ie.wit.moblieassignment2.listener.MemoListener
import ie.wit.moblieassignment2.models.MemoModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.intentFor


private const val ARG_PARAM1 = "list"

class HomeFragment : Fragment(), MemoListener {

    private var username : String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val databaseHelper = MyDatabaseHelper(context)
        homeRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = HomeAdapter(databaseHelper.listMemos(username!!), this@HomeFragment, context)
        }
        searchBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                (homeRecyclerView.adapter as HomeAdapter).filter.filter(s.toString());
            }
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(ARG_PARAM1)?.let {
            username = it
        }

    }

    override fun onResume() {
        searchBox.text = null
        homeRecyclerView.apply {
            val databaseHelper = MyDatabaseHelper(context)
            layoutManager = LinearLayoutManager(activity)
            adapter = HomeAdapter(databaseHelper.listMemos(username!!), this@HomeFragment, context)
        }
        super.onResume()
    }

    companion object {
        @JvmStatic
        fun newInstance(username:String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1,username)
                }
            }
    }

    override fun onMemoClick(memo: MemoModel) {
        startActivityForResult(intentFor<EditActivity>().putExtra("updateMemo",memo), 0)
    }
}