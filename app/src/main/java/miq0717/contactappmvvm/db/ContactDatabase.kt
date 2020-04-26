package miq0717.contactappmvvm.db

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import miq0717.contactappmvvm.R
import miq0717.contactappmvvm.model.Contact
import miq0717.contactappmvvm.model.ContactDao
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

@Database(entities = [Contact::class], version = 1, exportSchema = false)
/*Created by MiQ0717 on 25-Apr-2020.*/
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        private lateinit var context: Context
        fun getDatabase(context: Context): ContactDatabase {
            this.context = context
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDBCallBack)
                    .build()
                INSTANCE = instance
                return instance
            }
//            if (INSTANCE == null) {
//                INSTANCE = Room.databaseBuilder(
//                    context.applicationContext,
//                    ContactDatabase::class.java,
//                    "contact_database"
//                )
//                    .fallbackToDestructiveMigration()
//                    .build()
//            }
//            return INSTANCE as ContactDatabase
        }

        private val roomDBCallBack: Callback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { PopulateDbAsyncTask(it).execute() }
            }
        }

        private fun loadJSONArray(context: Context): JSONArray? {
            val builder = StringBuilder()
            val inputStream = context.resources.openRawResource(R.raw.contact_list)
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            try {
                while (bufferedReader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
                val jsonObject = JSONObject(builder.toString())
                return jsonObject.getJSONArray("contacts")
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: JSONException) {
                e.printStackTrace()
            }
            return null
        }

        fun fillWithStartingData(context: Context) {
            val contactDao = getDatabase(context = context).contactDao()
            val contacts = loadJSONArray(context = context)
            try {
                contacts?.let {
                    for (i in 0 until it.length()) {
                        val contact = contacts.getJSONObject(i)
                        val contactName = contact.getString("name")
                        val contactNumber = contact.getString("phone")
                        contactDao.insert(contact = Contact(contactName, contactNumber))
                    }
                }

            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
    }


    private class PopulateDbAsyncTask(contactDatabase: ContactDatabase) :
        AsyncTask<Void?, Void?, Void?>() {
        private val contactDao: ContactDao = contactDatabase.contactDao()

        override fun doInBackground(vararg params: Void?): Void? {
            contactDao.insert(Contact("Mahmood", "01748311388"))
            contactDao.insert(Contact("Abdullah", "01993994995"))
            fillWithStartingData(context.applicationContext)
            return null
        }
    }
}