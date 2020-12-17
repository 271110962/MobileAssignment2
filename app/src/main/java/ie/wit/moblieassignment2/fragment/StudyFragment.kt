package ie.wit.moblieassignment2.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.activity.EditActivity
import ie.wit.moblieassignment2.adapter.HomeAdapter
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import ie.wit.moblieassignment2.listener.MemoListener
import ie.wit.moblieassignment2.models.MemoModel
import kotlinx.android.synthetic.main.fragment_study.*
import org.jetbrains.anko.support.v4.intentFor

/*
*Fragment for showing memos which is Study category
 */
private const val ARG_PARAM1 = "username"
private const val ARG_PARAM2 = "category"

class StudyFragment : Fragment(), MemoListener {
    private var username : String? = null
    private var category: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_study, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        studyRecyclerView.apply {
            val databaseHelper = MyDatabaseHelper(context)
            layoutManager = LinearLayoutManager(activity)
            adapter = HomeAdapter(databaseHelper.listMemosByCategory(username!!,category!!), this@StudyFragment, context)

        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.getString(ARG_PARAM1)?.let {
            username = it
        }
        arguments?.getString(ARG_PARAM2)?.let {
            category = it
        }

    }

    override fun onResume() {
        studyRecyclerView.apply {
            val databaseHelper = MyDatabaseHelper(context)
            layoutManager = LinearLayoutManager(activity)
            adapter = HomeAdapter(databaseHelper.listMemosByCategory(username!!,category!!), this@StudyFragment, context)
        }
        super.onResume()
    }

    companion object {

        @JvmStatic
        fun newInstance(username:String,category:String) =
                StudyFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1,username)
                        putString(ARG_PARAM2,category)
                    }
                }
    }

    override fun onMemoClick(memo: MemoModel) {
        startActivityForResult(intentFor<EditActivity>().putExtra("updateMemo",memo), 0)
    }
}