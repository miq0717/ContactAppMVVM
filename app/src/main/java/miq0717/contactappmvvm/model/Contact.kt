package miq0717.contactappmvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/*Created by MiQ0717 on 24-Apr-2020.*/

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String, val number: String
) {


    constructor(name: String, number: String) : this(0, name, number)
}