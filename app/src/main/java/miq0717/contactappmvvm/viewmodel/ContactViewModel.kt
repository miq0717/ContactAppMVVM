package miq0717.contactappmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import miq0717.contactappmvvm.db.ContactRepository
import miq0717.contactappmvvm.model.Contact

/*Created by MiQ0717 on 25-Apr-2020.*/
class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ContactRepository = ContactRepository(application)
    private val allContacts: LiveData<List<Contact>>

    init {
        allContacts = repository.getAllContacts()!!
    }

    fun insert(contact: Contact) {
        repository.insert(contact)
    }

    fun delete(contact: Contact) {
        repository.delete(contact)
    }

    fun getAllContact(): LiveData<List<Contact>> {
        return allContacts
    }
}