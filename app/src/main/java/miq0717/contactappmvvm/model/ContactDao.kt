package miq0717.contactappmvvm.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/*Created by MiQ0717 on 24-Apr-2020.*/

@Dao
interface ContactDao {
    @Insert
    fun insert(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    fun getAllContacts(): LiveData<List<Contact>>
}