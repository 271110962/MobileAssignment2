package ie.wit.moblieassignment2.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import ie.wit.moblieassignment2.R
import ie.wit.moblieassignment2.database.MyDatabaseHelper
import ie.wit.moblieassignment2.models.MemoModel
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditActivity : AppCompatActivity(),AnkoLogger{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        val databaseHelper = MyDatabaseHelper(this)
        var edit = false
        var username:String?= null
        val intent = intent
        var memoDelete:MemoModel? = null
        if(intent.hasExtra("un")) {
            username = intent.extras?.getString("un")!!
        }



        if(intent.hasExtra("updateMemo")){
            edit = true
            memoDelete = intent.extras?.getParcelable<MemoModel>("updateMemo")!!
            MemoTitle.setText(memoDelete.title)
            if(memoDelete.category == "Study") {
                spinner.setSelection(0)
            }else if(memoDelete.category == "Life") {
                spinner.setSelection(1)
            }else{
                spinner.setSelection(2)
            }
            MemoDescription.setText(memoDelete.description)
            username = memoDelete.username
            spinner.isEnabled = false
            edit_save.setImageResource(R.mipmap.update)
            edit_delete.show()
        }

        edit_delete.setOnClickListener(){
                Snackbar.make(it, "Are you sure you want to Delete?", Snackbar.LENGTH_LONG)
                        .setAction("Yes") {
                            if (memoDelete != null) {
                                databaseHelper.deleteMemo(memoDelete.id!!)
                            }
                            info("Memo Delete: $memoDelete")
                            Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .show()
        }


        edit_save.setOnClickListener(){
            val title = MemoTitle.text.toString()
            val category = spinner.selectedItem.toString()
            val description = MemoDescription.text.toString()
            if(title.isNotEmpty() && category.isNotEmpty() && description.isNotEmpty()){

              if(edit){
                  val id = memoDelete!!.id
                  val memo = MemoModel(id, title, category, description, username!!)
                  databaseHelper.updateMemo(memo)
                  info("Memo Update: $memo")
                  Toast.makeText(this, "Update Successfully", Toast.LENGTH_SHORT).show()
                  finish()
              }else {
                  val memo = MemoModel(null, title, category, description, username!!)
                  databaseHelper.addMemo(memo)
                  info("Memo Create: $memo")
                  Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show()
                  finish()
              }

            }else{
                Toast.makeText(this,"You have something wrong with it",Toast.LENGTH_SHORT).show()
            }
        }

        edit_cancel.setOnClickListener(){
            Snackbar.make(it, "Are you sure you want to Leave?", Snackbar.LENGTH_LONG)
                .setAction("Yes") {
                    finish()
                }
                .show()
        }

    }
}