package miq0717.contactappmvvm.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import miq0717.contactappmvvm.R
import miq0717.contactappmvvm.viewmodel.ContactViewModel

class MainActivity : AppCompatActivity() {

    private val contactViewModel: ContactViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactsAdapter = ContactsAdapter()
        rcvContacts.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = contactsAdapter
        }

        contactViewModel.getAllContact().observe(this, Observer() { contacts ->
            contactsAdapter.setContacts(contacts = contacts)
        })

    }

}

