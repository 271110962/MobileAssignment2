package ie.wit.moblieassignment2.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.adapter.PageAdapter
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import ie.wit.moblieassignment2.entity.TabEntity
import ie.wit.moblieassignment2.fragment.*
import ie.wit.moblieassignment2.models.UserModel
import kotlinx.android.synthetic.main.activity_home.*

/*
*This is a page for collecting all the memos: filtering search function here.
 */
class HomeActivity : AppCompatActivity() {
    //Tab titles name
    private val tabTitles = arrayOf("HomePage", "Study","Life","Others")
    //Tab clicked images and nonclick images
    private val tabIconNonClicked = intArrayOf(
        R.mipmap.home_nonclicked, R.mipmap.book,R.mipmap.me_nonclicked,R.mipmap.idea
    )
    private val tabIconClicked = intArrayOf(
        R.mipmap.home_clicked, R.mipmap.book,R.mipmap.me_clicked,R.mipmap.idea
    )

    //List to store the Fragments
    private val tabFragments = arrayListOf<Fragment>()
    //List to store the Tab Entities(Name, clicked image, unclicked Image)
    private val tabEntities = arrayListOf<CustomTabEntity>()

    private var databaseHelper: MyDatabaseHelper = MyDatabaseHelper(this)
    private var user:UserModel?= null
    lateinit var userLoginName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val intent = intent
        user = intent.getParcelableExtra<Parcelable>("user") as UserModel?
        userLoginName = user!!.username
        val headerLayout: View = nav_view.inflateHeaderView(R.layout.nav_header)
        val header = headerLayout.findViewById<TextView>(R.id.headerName)
        header.text = userLoginName
        // Navigation Drawer Listener
        nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_add -> {
                    val intent = Intent()
                    intent.setClass(this,EditActivity::class.java)
                    intent.putExtra("un", user!!.username)
                    startActivity(intent)
                }
                R.id.nav_signout -> {
                    logout()
                }
            }
            drawer_layout.closeDrawers()
            true
        }


        //The list of Fragments must store all the corresponding Fragments
        tabFragments.add(HomeFragment.newInstance(userLoginName))
        tabFragments.add(StudyFragment.newInstance(userLoginName,"Study"))
        tabFragments.add(LifeFragment.newInstance(userLoginName,"Life"))
        tabFragments.add(OthersFragment.newInstance(userLoginName,"Others"))

        //The list of Tab Entities must store all the corresponding entities
        for(i in tabTitles.indices){
            val newTab = TabEntity(tabTitles[i],tabIconClicked[i],tabIconNonClicked[i])
            tabEntities.add(newTab)
        }
        //CommonTabLayout initialize the tab entities and listener.
        commonTabLayout.setTabData(tabEntities)
        commonTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewpager.currentItem = position
            }

            override fun onTabReselect(position: Int) {}
        })

        //adapter to actually initialize the layout.
        viewpager.adapter = PageAdapter(supportFragmentManager,tabTitles,tabFragments)
    }

    private fun logout(){
        val logoutIntent = Intent(this, LoginActivity::class.java)
        logoutIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(logoutIntent)
    }


}