package ie.wit.moblieassignment2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener(){
            val username = RegisterUser.text?.trim().toString()
            val password = RegisterPassword.text?.trim().toString()
            val databaseHandler = MyDatabaseHelper(this)
            register(username,password,databaseHandler)
        }

    }

    private fun register(username:String, password:String, databaseHandler: MyDatabaseHelper){
        if(username.isBlank() || username.isEmpty()){
            Toast.makeText(this,"Please Enter Your Username", Toast.LENGTH_SHORT).show()
            return
        }
        if(password.isBlank() || password.isEmpty()){
            Toast.makeText(this,"Please Enter Your Password", Toast.LENGTH_SHORT).show()
            return
        }
        val count = databaseHandler.checkRepeatUsername(username)
        if (count != null) {
            if(count > 0){
                Toast.makeText(this,"This Username has been used", Toast.LENGTH_SHORT).show()
                return
            }else{
                val status = databaseHandler.addUser(username,password)
                if(status>-1){
                    val intent = Intent()
                    intent.setClass(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this,"Successfully Register!!", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }
}