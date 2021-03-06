package ie.wit.moblieassignment2.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import kotlinx.android.synthetic.main.activity_login.*
/*
*
* Login Page:
*   Login Function and Remember password function
 */
class LoginActivity : AppCompatActivity() {
    private lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //Using SharedPreference, will be uncheck when you first login.
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        var isRemember = pref.getBoolean("remember",false)

        //if you check the checkbox, your account and password will be save after you sign out.
        if(isRemember){
            var account = pref.getString("remember_user","")
            var password = pref.getString("remember_password","")
            loginUser.setText(account)
            LoginPassword.setText(password)
            remember_password.isChecked = true
        }
        //Login button listener
        btn_login.setOnClickListener(){
            val username = loginUser.text?.trim().toString()
            val password = LoginPassword.text?.trim().toString()
            val databaseHandler = MyDatabaseHelper(this)
            login(username,password,databaseHandler)
        }
    }
    //login function logic, connect with SQLite database.
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
            //get the value from sharedPreference if you check the checkbox before.
            val editor = pref.edit()
            if (remember_password.isChecked()) {
                editor.putBoolean("remember", true);
                editor.putString("remember_user", username);
                editor.putString("remember_password", password);
            } else {
                editor.clear();
            }
            editor.apply();
            //Transfer the login usermodel to HomeActivity for authentication
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