package ie.wit.moblieassignment2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.moblieassignment2.activity.LoginActivity
import ie.wit.moblieassignment2.activity.RegisterActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Loginbtn.setOnClickListener(){
            val intent = Intent()
            intent.setClass(this,LoginActivity::class.java)
            startActivity(intent)
        }

        Registerbtn.setOnClickListener(){
            val intent = Intent()
            intent.setClass(this,RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}