package ie.wit.moblieassignment2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        val pref = PreferenceManager.getDefaultSharedPreferences(this);
//        var isRemember = pref.getBoolean("remember_password",false)
//
//        if(isRemember){
//            var account = pref.getString("username","")
//            var password = pref.getString("password","")
//            loginUser.setText(account)
//            LoginPassword.setText(password)
//            remember_password.isChecked = true
//        }

        btn_login.setOnClickListener(){
            val username = loginUser.text?.trim().toString()
            val password = LoginPassword.text?.trim().toString()
            val databaseHandler = MyDatabaseHelper(this)
            login(username,password,databaseHandler)
        }
    }

    private fun login(username:String, password:String, databaseHelper: MyDatabaseHelper){
        if(username.isBlank() || username.isEmpty()){
            Toast.makeText(this,"Please Enter Your Username",Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isBlank() || password.isEmpty()){
            Toast.makeText(this,"Please Enter Your Password",Toast.LENGTH_SHORT).show()
            return
        }

        val user = databaseHelper.findUser(username,password)
        if(user!=null){
            val intent = Intent()
            intent.setClass(this,HomeActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("user", user)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
            Toast.makeText(this,"Welcome to MEMO",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"Wrong Username or Password",Toast.LENGTH_SHORT).show()
            return
        }
    }
}