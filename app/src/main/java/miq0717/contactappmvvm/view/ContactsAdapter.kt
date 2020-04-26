package miq0717.contactappmvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import miq0717.contactappmvvm.R
import miq0717.contactappmvvm.model.Contact

/*Created by MiQ0717 on 25-Apr-2020.*/
class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private var contacts = emptyList<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val currentContact = contacts[position]
        holder.setData(currentContact)
    }

    fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

    inner class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val contactName: TextView = itemView.findViewById(R.id.txvContactName)
        private val contactNumber: TextView = itemView.findViewById(R.id.txvContactNumber)

        fun setData(currentContact: Contact) {
            contactName.text = currentContact.name
            contactNumber.text = currentContact.number
        }
    }
}