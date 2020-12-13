package ie.wit.moblieassignment2.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ie.wit.moblieassignment2.fragment.HomeFragment
import ie.wit.moblieassignment2.models.MemoModel
import ie.wit.moblieassignment2.models.UserModel

class MyDatabaseHelper(context: Context?) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION){
 companion object{
     private val DATABASE_VERSION = 1
     private val DATABASE_NAME = "Memo"
     private val TABLE_CONTACTS1="Users"
     private val KEY_ID = "id"
     private val USERNAME = "username"
     private val PASSWORD = "password"

     private val TABLE_CONTACTS2="Memos"
     private val MEMOS_KEY_ID = "id"
     private val TITLE = "title"
     private val DESCRIPTION = "description"
     private val CATEGORY = "category"
 }


    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE1 = ("CREATE TABLE " + TABLE_CONTACTS1 + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USERNAME + " TEXT,"
                + PASSWORD + " TEXT"
                + ")")

        val CREATE_CONTACTS_TABLE2 = ("CREATE TABLE " + TABLE_CONTACTS2 + "("
                + MEMOS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE + " TEXT,"
                + DESCRIPTION + " TEXT,"
                + CATEGORY + " TEXT,"
                + USERNAME + " TEXT"
                + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE1)
        db?.execSQL(CREATE_CONTACTS_TABLE2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS1")
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS2")
        onCreate(db)
    }

    fun addUser(username: String, password: String):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(USERNAME,username)
        contentValues.put(PASSWORD,password)

        val success = db.insert(TABLE_CONTACTS1,null,contentValues)
        db.close()
        return success
    }

    fun findUser(username: String, password: String):UserModel?{
        var user: UserModel? = null
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS1 WHERE $USERNAME = '$username' and $PASSWORD = '$password'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do{
                var id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                var un = cursor.getString(cursor.getColumnIndex(USERNAME))
                var ps = cursor.getString(cursor.getColumnIndex(PASSWORD))
                user = UserModel(id,un,ps)
            }while(cursor.moveToNext())
        }
        db.close()
        return user
    }

    fun checkRepeatUsername(username: String):Int?{
        var number: Int
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS1 WHERE $USERNAME = '$username'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        number = cursor.count
        db.close()
        return number
    }

    fun addMemo(memoModel: MemoModel):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE,memoModel.title)
        contentValues.put(DESCRIPTION,memoModel.description)
        contentValues.put(CATEGORY,memoModel.category)
        contentValues.put(USERNAME,memoModel.username)
        val success = db.insert(TABLE_CONTACTS2,null,contentValues)
        db.close()
        return success
    }

    fun deleteMemo(id: Int):Int{
        val db = this.writableDatabase
        val success = db.delete(TABLE_CONTACTS2, "id=$id",null)
        db.close()
        return success
    }

    fun updateMemo(memoModel: MemoModel):Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(TITLE, memoModel.title)
        contentValues.put(DESCRIPTION,memoModel.description )
        val success = db.update(TABLE_CONTACTS2, contentValues,"id=${memoModel.id}",null)
        db.close()
        return success
    }

    fun listMemos(username: String):ArrayList<MemoModel>{
        var memo :MemoModel?
        val memosList:ArrayList<MemoModel> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS2 WHERE $USERNAME = '$username'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do{
                var id = cursor.getInt(cursor.getColumnIndex(MEMOS_KEY_ID))
                var title = cursor.getString(cursor.getColumnIndex(TITLE))
                var desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                var category = cursor.getString(cursor.getColumnIndex(CATEGORY))
                var username = cursor.getString(cursor.getColumnIndex(USERNAME))
                memo = MemoModel(id,title,category,desc,username)
                memosList.add(memo)
            }while(cursor.moveToNext())
        }

        db.close()
        return memosList

    }

    fun listMemosByCategory(username: String,category:String):ArrayList<MemoModel>{
        var memo :MemoModel?=null
        val memosListByCategory:ArrayList<MemoModel> = ArrayList<MemoModel>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS2 WHERE $USERNAME = '$username' AND $CATEGORY = '$category'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor.moveToFirst()){
            do{
                var id = cursor.getInt(cursor.getColumnIndex(MEMOS_KEY_ID))
                var title = cursor.getString(cursor.getColumnIndex(TITLE))
                var desc = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                var category = cursor.getString(cursor.getColumnIndex(CATEGORY))
                var username = cursor.getString(cursor.getColumnIndex(USERNAME))
                memo = MemoModel(id,title,category,desc,username)
                memosListByCategory.add(memo)
            }while(cursor.moveToNext())
        }

        db.close()
        return memosListByCategory

    }
}