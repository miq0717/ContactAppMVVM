package miq0717.contactappmvvm.db

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import miq0717.contactappmvvm.model.Contact
import miq0717.contactappmvvm.model.ContactDao

/*Created by MiQ0717 on 25-Apr-2020.*/
class ContactRepository(application: Application) {
    private var contactDao: ContactDao? = null
    private var allContacts: LiveData<List<Contact>>? = null

    init {
        val contactDatabase = ContactDatabase.getDatabase(application)
        contactDao = contactDatabase.contactDao()
        allContacts = contactDao?.getAllContacts()
    }

    fun insert(contact: Contact) {
        contactDao?.let { InsertContactAsyncTask(it).execute(contact) }
    }

    fun delete(contact: Contact) {
        contactDao?.let { DeleteContactAsyncTask(it).execute(contact) }
    }

    fun getAllContacts(): LiveData<List<Contact>>? {
        return allContacts
    }

    class InsertContactAsyncTask constructor(private val contactDao: ContactDao) :
        AsyncTask<Contact, Void?, Void?>() {

        override fun doInBackground(vararg contacts: Contact): Void? {
            contactDao.insert(contact = contacts[0])
            return null
        }

    }

    class DeleteContactAsyncTask constructor(private val contactDao: ContactDao) :
        AsyncTask<Contact, Void?, Void?>() {

        override fun doInBackground(vararg contacts: Contact): Void? {
            contactDao.delete(contact = contacts[0])
            return null
        }

    }
}